package renderEngine;

import java.nio.Buffer;

/**
 * Created by USER on 21.04.2017.
 */
public class VBO {
    private Buffer buffer;
    int VBO_ID;

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public int getVBO_ID() {
        return VBO_ID;
    }

    public void setVBO_ID(int VBO_ID) {
        this.VBO_ID = VBO_ID;
    }

    public VBO(int VBO_ID) {
        this.VBO_ID = VBO_ID;
    }
}
