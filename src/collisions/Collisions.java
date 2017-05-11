package collisions;

import entities.CollisionedEntity;
import entities.Player;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 28.04.2017.
 */
public class Collisions {
    private Player player;

    public Collisions(Player player) {
        this.player = player;
    }

    private List<CollisionedEntity> collisionedEntities = new ArrayList<>();
    public void add(CollisionedEntity entity){
        collisionedEntities.add(entity);

    }

    public boolean checkCollision(CollisionedEntity entity){
       if (!checkSphereCollision(entity)){
           return false;
       }
        Vector3f playerPos = Vector3f.add(player.getCollision().getPosition(),player.getPosition(),null);
        Shape shape = null;
        Cylinder cylinder = player.getCollision();
        float cylinderRad = cylinder.getRadius();
        float cylinderHeight = cylinder.getHeight();
        float playerY = playerPos.getY();
        float playerX = playerPos.getX();
        for (int i = 0; i < entity.getCollisionList().size(); i++) {

            Shape current = entity.getCollisionList().get(i);
            if (current instanceof Sphere) {
                Vector3f spherePos = Vector3f.add(current.getPosition(), entity.getPosition(), null);
                float sphereY = spherePos.getY();
                float sphereX = spherePos.getX();
                Sphere sphere = (Sphere) current;
                float sphereRad = sphere.getRad();

                float deltaHeight = Math.abs(playerY - sphereY);
                float deltaX  = Math.abs(playerX - sphereX);
                if (deltaHeight > (cylinderHeight/2 + sphereRad)){
                    return false;
                }else if (deltaX > (sphereRad + cylinderRad)){
                    return false;
                }else
                    return true;


            }
        }
      return false;
    }



    public boolean checkSphereCollision(CollisionedEntity entity){
        if(player.getCollision() == null ||  entity.getCollisionList() == null) return false;
        Vector3f playerPos = Vector3f.add(player.getCollision().getPosition(), player.getPosition(), null);
        float playerRad = player.getCollision().getRad();

        for (Shape x : entity.getCollisionList() ) {
            Vector3f xPos = Vector3f.add(x.getPosition(), entity.getPosition(), null);
            float xRad = x.getRad();
            xPos = Vector3f.sub(playerPos, xPos, xPos);
            if(xPos.length() < playerRad + xRad) return  true;
        }

        return false;
    }


    /**
     * Returns all possible planes, that will use for projections
     * @param
     * @return
     */
    private List<Plane> getPlanes(ICollidable player, ICollidable primitive){
        List<Plane> planes = new ArrayList<>();
        List<Vector3f> firstNormals = player.getNormals();
        List<Vector3f> secondNormals = primitive.getNormals();
        for (int i = 0; i < firstNormals.size(); i++) {
            planes.add(new Plane(firstNormals.get(i)));
        }
//        M:
//        for (int i = 0; i < secondNormals.size() ; i++) {
//            Vector3f temp = secondNormals.get(i);
//            for (int j = 0; j < planes.size() ; j++) {
//                if (planes.get(j).getNormal().equals(temp))continue M;
//            }
//            planes.add(new Plane(temp));
//        }
        return planes;
    }


}
