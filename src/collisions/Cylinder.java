package collisions;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 28.04.2017.
 */
public class Cylinder extends Shape {
    private float height;
    private float radius;

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }



    public Cylinder(Vector3f position, Vector3f rotation, float height, float radius) {
        super(position, rotation, (float) Math.sqrt(radius*radius + height*height/4));
        this.radius = radius;
        this.height = height;
    }

    @Override
    public List<Vector2f> getConvexHull(Plane plane) {
        return null;
    }

    @Override
    public List<Vector3f> getNormals() {
        List<Vector3f> list = new ArrayList<>();
        list.add(Plane.xPlane);
        list.add(Plane.yPlane);
        list.add(Plane.zPlane);
        return list;
    }

    @Override
    public String toString() {
        return "CYLINDER"
                +"\n\tPOSITION : " + getPosition()
                +"\n\tROTATION : " + getRotation()
                +"\n\tRAD : " + getRad()
                +"\n\tHEIGHT: "+height+"  RADIUS: "+radius;
    }
}
