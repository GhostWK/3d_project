package gamelogic.Map;

/**
 * Created by User on 14.04.2017.
 */
public class RawChunk {
    private int id_x;
    private int id_z;
    private float[][] data;

    public RawChunk(int id_x, int id_z, float[][] data) {
        this.id_x = id_x;
        this.id_z = id_z;
        this.data = data;
    }

    public int getId_x() {
        return id_x;
    }

    public int getId_z() {
        return id_z;
    }

    public float[][] getData() {
        return data;
    }
}
