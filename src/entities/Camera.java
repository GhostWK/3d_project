package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by USER on 24.03.2017.
 */
public class Camera {
    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0,0,0);
    private float pitch;//horizontal UP/DOWN
    private float yaw;//Vertical
    private float roll;

    Player player;

    boolean freeCamera = false;


    public Camera(Player player) {this.player = player;}

    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_V)){
            freeCamera = !freeCamera;
        }
        if(freeCamera) movingCamera();
        else {
            player.move();
            calculateZoom();
            calculatePitch();
            calculateAngleAroundPlayer();
            float horizontalDistance = calculateHorizontalDistance();
            float verticalDistance = calculateVerticalDistance();
            calculateCameraPosition(horizontalDistance, verticalDistance);
            this.yaw = 180 - (player.getRotY()+ angleAroundPlayer);
        }

    }

    private void movingCamera(){
                float speed = 0.2f;
        float speed_rot = 3;
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z -= speed * Math.cos(Math.toRadians(yaw));
            position.x += speed * Math.sin(Math.toRadians(yaw));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.z += speed * Math.cos(Math.toRadians(yaw));
            position.x -= speed * Math.sin(Math.toRadians(yaw));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.z += speed * Math.sin(Math.toRadians(yaw));
            position.x += speed * Math.cos(Math.toRadians(yaw));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.z -= speed * Math.sin(Math.toRadians(yaw));
            position.x -= speed * Math.cos(Math.toRadians(yaw));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            position.y += speed;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
            position.y -= speed;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            yaw += speed_rot;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            yaw -= speed_rot;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
            pitch -= speed_rot;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            pitch += speed_rot;
        }
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance){
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + verticalDistance + 5f;
    }

    private float calculateHorizontalDistance(){
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }
    private float calculateVerticalDistance(){
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    private void calculateZoom(){
        float zoomLevel = Mouse.getDWheel() * 0.1f;
        distanceFromPlayer -= zoomLevel;
    }
    private void calculatePitch(){
        if(Mouse.isButtonDown(1)){
            float pitchChange = Mouse.getDY() * 0.1f;
            pitch -= pitchChange;

            float angleChanges = Mouse.getDX() * 0.3f;
            angleAroundPlayer -= angleChanges;
        }
    }
    private void calculateAngleAroundPlayer(){
        if(Mouse.isButtonDown(0)){
            float angleChange = Mouse.getDX() * 0.3f;
            angleAroundPlayer -= angleChange;
        }
    }


}
