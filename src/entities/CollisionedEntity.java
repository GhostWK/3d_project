package entities;

import collisions.Shape;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 28.04.2017.
 */
public class CollisionedEntity extends Entity {
    private List<Shape> collisionList = new ArrayList<>();
    public List<Shape> getCollisionList() {
        return collisionList;
    }



    public CollisionedEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Shape> collisionList) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.collisionList = collisionList;
    }
}
