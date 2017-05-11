package collisions;


import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 28.04.2017.
 */
public class Parallelepiped extends Shape{

    private float a;
    private float b;
    private float c;


    public Parallelepiped(Vector3f position, Vector3f rotation,float a, float b, float c) {
        super(position, rotation, (float) (Math.sqrt(a*a+b*b +c*c)/4));
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Vector3f getABC(){
        return new Vector3f(a,b,c);
    }

    @Override
    public List<Vector2f> getConvexHull(Plane plane) {
        return null;
    }

    @Override
    public List<Vector3f> getNormals() {
        return null;
    }

    @Override
    public String toString() {
        return "PARALLELEPIPED"
                +"\n\tPOSITION : " + getPosition()
                +"\n\tROTATION : " + getRotation()
                +"\n\tRAD : " + getRad()
                +"\n\tA: "+a+"  B: "+b+"  C: "+c;
    }
}
