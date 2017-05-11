package toolbox;

import entities.Camera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by USER on 24.03.2017.
 */
public class Maths {

    public static Matrix4f createTransformationMatrix(
            Vector3f translation, float rx, float ry, float rz, float scale){

        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix,matrix);
        Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix,matrix);
        Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix,matrix);
        Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera){
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewMatrix,
                viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), viewMatrix,
                viewMatrix);

        //TODO: May be add ROLL
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
        return viewMatrix;
    }

    public static Vector3f getNormal(float x1, float y1, float z1, float x2, float y2, float z2,float x3, float y3, float z3){
        Vector3f p = new Vector3f(x1 - x2, y1 - y2, z1 - z2);
        Vector3f q = new Vector3f(x3 - x2, y3 - y2, z3 - z2);
        p = Vector3f.cross(p,q,p);
        if(p.y < 0) p.negate();
        p.normalise(p);
        return p;
    }

    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
        return matrix;
    }

    public static Vector3f rotate(Vector3f vector3f, Quaternion quaternion){
        Quaternion q = new Quaternion(vector3f.x, vector3f.y, vector3f.z, 0);
        Quaternion.mul(quaternion, q, q);
        Quaternion.mul(q, Quaternion.negate(quaternion,null), q);
        vector3f.set(q.x,q.y,q.z);
        return vector3f;
    }

    public static Quaternion getQuaternion(Vector3f vector3f, float angle){
        Quaternion q = new Quaternion();
        vector3f.normalise();
        q.setW((float) Math.cos(Math.toRadians(angle/2)));
        q.setX((float) (vector3f.x*Math.sin(Math.toRadians(angle/2))));
        q.setY((float) (vector3f.y*Math.sin(Math.toRadians(angle/2))));
        q.setZ((float) (vector3f.z*Math.sin(Math.toRadians(angle/2))));
        return q;
    }

    public static Vector3f scaleVector(Vector3f vector, float scale){
        Vector3f newVector = new Vector3f();
        newVector.setX(vector.getX() * scale);
        newVector.setY(vector.getY() * scale);
        newVector.setZ(vector.getZ() * scale);
        return newVector;
    }

}
