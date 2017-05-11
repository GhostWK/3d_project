package entities;

import collisions.Cylinder;
import gamelogic.Main;
import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

/**
 * Created by USER on 07.04.2017.
 */
public class Player extends Entity{

    private boolean b1 = false;
    private boolean b2 = false;

    private boolean v = false;

    private static final float RUN_SPEED = 100;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;

    private Cylinder collision;

    public Cylinder getCollision() {
        return collision;
    }

    public void setCollision(Cylinder collision) {
        this.collision = collision;
    }

    public float getTERRAIN_HEIGHT() {
        return TERRAIN_HEIGHT;
    }

    public void setTERRAIN_HEIGHT(float TERRAIN_HEIGHT) {
        this.TERRAIN_HEIGHT = TERRAIN_HEIGHT;
    }

    private  float TERRAIN_HEIGHT = 2;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(){

        checkInputs();
        super.increaseRotation(0,currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance* Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance* Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);

        if(!v){
            upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
            super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
            if(upwardsSpeed != 0) setTERRAIN_HEIGHT(Main.getWorldRenderer().calculateLowCollision(getPosition().x, getPosition().z));
            if(super.getPosition().y< TERRAIN_HEIGHT){
//            setTERRAIN_HEIGHT(Main.getWorldRenderer().calculateLowCollision(getPosition().x, getPosition().z));
                upwardsSpeed = 0;
                super.getPosition().y = TERRAIN_HEIGHT+0.00001f;
            }
        }


    }

    private void jump(){
        this.upwardsSpeed = JUMP_POWER;

    }

    private void checkInputs(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            this.currentSpeed = RUN_SPEED;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            this.currentSpeed = -RUN_SPEED;
        }else{
            this.currentSpeed = 0;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            this.currentTurnSpeed = -TURN_SPEED;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            this.currentTurnSpeed = TURN_SPEED;
        }else{
            this.currentTurnSpeed = 0;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !v){
            jump();
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_B)){
            b1 = true;
        }else{
            b1 = false;
        }

        if(b1 && !b2){
            v = !v;
        }
        if(v){
            if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
                super.increasePosition(0,30* DisplayManager.getFrameTimeSeconds(),0);
            }else if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
                super.increasePosition(0,-30*DisplayManager.getFrameTimeSeconds(),0);
            }
        }


        b2 = b1;
    }
}
