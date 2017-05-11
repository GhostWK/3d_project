package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.TexturedModel;
import org.lwjgl.opengl.Display;

import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();

		RawModel cubed = OBJLoader.loadObjModel("my", loader);
		ModelTexture modelTexture = new ModelTexture(loader.loadTexture("my"));
		TexturedModel cubeTextured = new TexturedModel(cubed, modelTexture);


		Player player = new Player(cubeTextured, new Vector3f(0, -10, -30),0,0,0,1);


		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("white"));

		RawModel cube = OBJLoader.loadObjModel("stall", loader);
		ModelTexture cubeTexture = new ModelTexture(loader.loadTexture("stallTexture"));
		TexturedModel myCube = new TexturedModel(cube, cubeTexture);

		Entity myCubeEntity = new Entity(myCube, new Vector3f(0,0,-10),0,0,0,1);

		TexturedModel staticModel = new TexturedModel(model, texture);
		ModelTexture testure = staticModel.getTexture();
		testure.setReflectibity(10);
		testure.setReflectibity(1);
		Entity entity1 = new Entity(staticModel, new Vector3f(0,0,-5), 0,0,0,1);
		//Entity entity2 = new Entity(staticModel, new Vector3f(0,0,-5), 0,0,0,1);

		Light light = new Light(new Vector3f(3000,2000,2000), new Vector3f(1,1,1));

		Terrain terrain = new Terrain(0,0,loader, new ModelTexture(loader.loadTexture("cubeTexture")));
		Terrain terrain2 = new Terrain(0,0,loader, new ModelTexture(loader.loadTexture("cubeTexture")));

		MasterRenderer renderer = new MasterRenderer();

		List<Entity> entities = new ArrayList<>();

		Camera camera = new Camera(player);
		entity1.setScale(0.1f);
		while(!Display.isCloseRequested()){
			//game logic
			//entity.increasePosition(0,0,-0.02f);
			entity1.increaseRotation(0,0.5f,0);
			camera.move();
			renderer.processEntity(entity1);
			//renderer.processEntity(entity2);

			renderer.processTerrain(terrain);
			//renderer.processTerrain(terrain2);

			renderer.processEntity(myCubeEntity);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();			
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
