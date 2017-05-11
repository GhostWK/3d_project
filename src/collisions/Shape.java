package collisions;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by User on 28.04.2017.
 */
public abstract class Shape implements ICollidable{
    private Vector3f position;
    private Vector3f rotation;
    private float rad;

    public Shape(Vector3f position, Vector3f rotation, float rad) {
        this.position = position;
        this.rotation = rotation;
        this.rad = rad;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRad() {
        return rad;
    }

    public void setRad(float rad) {
        this.rad = rad;
    }


}
