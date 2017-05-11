package collisions;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by USER on 07.05.2017.
 */
public class Plane {
    public static final Vector3f xPlane = new Vector3f(1,0,0);
    public static final Vector3f yPlane = new Vector3f(0,1,0);
    public static final Vector3f zPlane = new Vector3f(0,0,1);

    private Vector3f normal;

    public Plane(Vector3f normal) {
        this.normal = normal;
    }

    public Vector3f getNormal() {
        return normal;
    }
}
