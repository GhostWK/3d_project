package collisions;


import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 28.04.2017.
 */
public class Sphere extends Shape{


    public Sphere(Vector3f position, float rad) {
        super(position, new Vector3f(0,0,0), rad);
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
        return "SPHERE"+
                "\n\tPOSITION : " + getPosition()+
                "\n\tRADIUS : " + getRad() + "\n";
    }
}
