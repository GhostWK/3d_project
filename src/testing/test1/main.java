package testing.test1;


import entities.EntitiesParser;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import toolbox.Maths;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by USER on 30.03.2017.
 */
public class main {
    public static void main(String[] args){
        /*EntitiesParser e = new EntitiesParser(
                "D:\\Idea Projects\\3dProject\\src\\entities\\mesh.xml",
                "D:\\Idea Projects\\3dProject\\src\\entities\\textures.xml",
                "D:\\Idea Projects\\3dProject\\src\\entities\\objects.xml");
        e.parseMesh();
        e.parseTexture();*/

        Vector3f v = new Vector3f(1,0,0);
        Quaternion q1 = Maths.getQuaternion(new Vector3f(0,0,1),90);
        Quaternion q2 = Maths.getQuaternion(new Vector3f(1,1,1), 120);
        Maths.rotate(v, q2);
        Maths.rotate(v, q1);
        System.out.println(v);



    }
}
