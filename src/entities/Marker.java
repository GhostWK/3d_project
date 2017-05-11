package entities;

import gamelogic.Main;
import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

/**
 * Created by USER on 20.04.2017.
 */
public class Marker extends Entity {
    public static final int speed = 60;

    public Marker(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }
    public void move(){
        float dx = 0;
        float dz = 0;
        float dy = 0;
        float delta = DisplayManager.getFrameTimeSeconds();

        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)){
                dz = speed * delta;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)){
                dz = -speed * delta;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4)){
            dx = speed * delta;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6)){
            dx = -speed * delta;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD9)){
            dy = speed * delta;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3)){
            dy = -speed * delta;
        }
        super.increasePosition(dx,dy,dz);

        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5)){
            super.setPosition(new Vector3f(Main.getPlayer().getPosition().x, Main.getPlayer().getPosition().y + 5, Main.getPlayer().getPosition().z) );
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD7)){
            Main.getWorldRenderer().terraform(super.getPosition().getX(), super.getPosition().getZ(), true);
        }else if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1)){
            Main.getWorldRenderer().terraform(super.getPosition().getX(), super.getPosition().getZ(), false);
        }
    }



}
