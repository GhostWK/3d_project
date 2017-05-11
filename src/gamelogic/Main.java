package gamelogic;

import collisions.Collisions;
import collisions.Cylinder;
import collisions.Shape;
import collisions.Sphere;
import entities.*;

import gamelogic.Map.WorldRenderer;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;

import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import textures.ModelTexture;

import static gamelogic.SettingsClass.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by USER on 27.03.2017.
 */
public class Main {
    private static WorldRenderer worldRenderer;
    public static WorldRenderer getWorldRenderer() {
        return worldRenderer;
    }
    private static Player player;

    public static Player getPlayer() {
        return player;
    }

    public static void main(String[] args) {




        DisplayManager.createDisplay();
        Loader loader = new Loader();
        MasterRenderer renderer = new MasterRenderer();

        worldRenderer = new WorldRenderer(renderer, loader);
        //GuiRenderer guiRenderer = new GuiRenderer(loader);


        //List<GuiTexture> guis = new ArrayList<>();
        //GuiTexture guiTexture = new GuiTexture(loader.loadTexture("skorp"),new Vector2f(0.5f, 0.5f),new Vector2f(0.25f, 0.25f) );
        //guis.add(guiTexture);
        float data[][] = new float[][]{
                {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 12, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
                {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,},


        };

        /**
         * Создание чанков. Параметры - карта высот (например, массив data)
         * Указываются позиции чанков по ID
         */
        worldRenderer.read();


        RawModel cube = OBJLoader.loadObjModel("monkey", loader);
        ModelTexture modelTexture = new ModelTexture(loader.loadTexture("Monkey"));
        TexturedModel cubeTextured = new TexturedModel(cube, modelTexture);


        RawModel marker = OBJLoader.loadObjModel("marker", loader);
        ModelTexture markerTexture = new ModelTexture(loader.loadTexture("Marker"));
        TexturedModel markerTextured = new TexturedModel(marker, markerTexture);

        Marker mark = new Marker(markerTextured, new Vector3f(20,10,20), 0,90,0,4);

         player = new Player(cubeTextured, new Vector3f(20, 0, 20),0,0,0,1);

        Light light = new Light(new Vector3f(0,2000,0), new Vector3f(1,1,1));
        Camera camera = new Camera(player);

        Collisions collisionsClass = new Collisions(player);
        player.setCollision(new Cylinder(new Vector3f(0,1,0), new Vector3f(0,0,0), 2f,1f));

        Sphere sphere = new Sphere(new Vector3f(0,0,0), 5);
        List<Shape> list = new ArrayList<>();
        list.add(sphere);
        CollisionedEntity entity = new CollisionedEntity(new TexturedModel(OBJLoader.loadObjModel("sphere", loader),new ModelTexture(loader.loadTexture("sphere"))),
                new Vector3f(100,20,100),0,0,0,5, list);


        EntitiesParser entitiesParser = new EntitiesParser(MESH_FILE, TEXTURE_FILE, ENTITIES_FILE, loader);
        List<CollisionedEntity> entitiesToDraw = entitiesParser.getEntities();
        System.out.println(entitiesParser.getEntities().get(0).getCollisionList().size());

        System.out.println(entitiesToDraw.get(0).getCollisionList().get(0).toString());

       // t.setReflectibity(0);
        //t.setShineDamper(0.99f);

        int l = 0;
        /**
         * Окно для вывода дополнительной информации
         */
        Thread t = new Thread(){
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setSize(100,100);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(true);
                frame.setVisible(true);


                JLabel l = new JLabel("no");
                frame.add(l);
                while(true){
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }




                        if(collisionsClass.checkCollision(entitiesToDraw.get(0))) {
                            l.setText("yes");
                        }else{
                            l.setText("no");
                        }





                }
            }
        };
        t.start();

//        int l  = 0;
        Random r = new Random();
        while(!Display.isCloseRequested()){
            MasterRenderer.disableCulling();
            camera.move();
            mark.move();
            //player.move();
            renderer.processEntity(mark);


            renderer.processEntity(player);

            renderer.processEntity(entity);

            renderer.render(light, camera);


            for(CollisionedEntity collisionedEntity : entitiesToDraw){
                renderer.processEntity(collisionedEntity);
            }
            //guiRenderer.render(guis);

            //centity.increaseRotation(0,0.1f,0);
            //MasterRenderer.disableCulling();
            //renderer.processEntity(centity);
            worldRenderer.renderAll();
            if(l == 5) {
                //light.increasePosition(new Vector3f(7, 2000, 2000));
                //if(light.getPosition().x > 2000) light.setPosition(new Vector3f(-2000, 2000, 0));
                //light.setPosition(new Vector3f(r.nextInt(4000) - 2000,2000,r.nextInt(4000) - 2000));
                l = 0;

            }

            //l++;
            //MasterRenderer.disableCulling();

            //renderer.processEntity(ecube);

            DisplayManager.updateDisplay();
        }
        worldRenderer.write();
        worldRenderer.cleanUp();
        //guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
