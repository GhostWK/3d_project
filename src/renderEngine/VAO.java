package renderEngine;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 21.04.2017.
 */
public class VAO {
    private final int VAO_ID;
    private List<VBO> vboList;

    public int getVAO_ID() {
        return VAO_ID;
    }

    public List<VBO> getVboList() {
        return vboList;
    }

    public VAO(int VAO_ID) {
        this.VAO_ID = VAO_ID;
        vboList = new ArrayList<>();
    }

    public void cleanUp(){
        GL30.glBindVertexArray(VAO_ID);
        for(VBO x : vboList) {
            GL15.glDeleteBuffers(x.getVBO_ID());
            x.getBuffer().clear();
            x.getBuffer().flip();

        }
        vboList.clear();
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(VAO_ID);
    }
}
