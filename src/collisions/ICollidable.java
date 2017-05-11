package collisions;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;


import java.util.List;

/**
 * Created by USER on 07.05.2017.
 */
public interface ICollidable {
    float getRad();
    Vector3f getPosition();

    /**
     * Searching projections on plane
     * @param plane
     * @return
     */
    List<Vector2f> getConvexHull(Plane plane);

    /**
     * Returns a List of normals. Must be realised for every primitive.
     * @return
     */
    List<Vector3f> getNormals();

}
