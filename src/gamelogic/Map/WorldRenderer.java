package gamelogic.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;

import java.io.IOException;
import java.util.*;

/**
 * Created by USER on 30.03.2017.
 */
public class WorldRenderer {

    private ArrayList<Chunk> chunks = new ArrayList<>();
    private MasterRenderer renderer;

    public WorldRenderer(MasterRenderer renderer, Loader loader) {
        this.renderer = renderer;
        Chunk.loader = loader;
    }

    public void renderAll(){
        for(Chunk c : chunks) renderer.processEntity(c.getEntity());
    }

    public void add(Chunk c){
        chunks.add(c);
    }
    public void cleanUp(){
        chunks.clear();
    }


    public float calculateLowCollision(float x, float z) {
        Chunk chunk = getChunk(x, z);
        if(chunk != null){
            float[][] data = chunk.data;
            float size = 20 * Chunk.SCALE;
            float pos_x = getPosInChunk(x, size);
            float pos_z = getPosInChunk(z, size);
            int quad_x = (int) (pos_x / Chunk.SCALE);
            int quad_z = (int) (pos_z / Chunk.SCALE);
            float qp_x = pos_x % Chunk.SCALE;
            float qp_z = pos_z % Chunk.SCALE;

            Vector3f v1;
            Vector3f v2;
            Vector3f v3;


            if((quad_x + quad_z) % 2 == 0){
                v1 = new Vector3f(quad_x * Chunk.SCALE, data[quad_z][quad_x],quad_z * Chunk.SCALE);
                v3 = new Vector3f((quad_x + 1) * Chunk.SCALE, data[quad_z + 1][quad_x + 1],(quad_z + 1) * Chunk.SCALE);
                if(qp_x - qp_z >= 0){

                    v2 = new Vector3f((quad_x + 1) * Chunk.SCALE, data[quad_z][quad_x + 1],quad_z * Chunk.SCALE);
                }else{

                    v2 = new Vector3f(quad_x * Chunk.SCALE, data[quad_z + 1][quad_x],(quad_z + 1) * Chunk.SCALE);
                }
            }else{

                v1 = new Vector3f((quad_x + 1) * Chunk.SCALE, data[quad_z][quad_x + 1],quad_z * Chunk.SCALE);
                v3 = new Vector3f(quad_x * Chunk.SCALE, data[quad_z + 1][quad_x],(quad_z + 1) * Chunk.SCALE);
                if(qp_x + qp_z >= Chunk.SCALE){
                    v2 = new Vector3f((quad_x + 1) * Chunk.SCALE, data[quad_z + 1][quad_x + 1],(quad_z + 1) * Chunk.SCALE);
                }else{
                    v2 = new Vector3f(quad_x * Chunk.SCALE, data[quad_z][quad_x],quad_z * Chunk.SCALE);

                }
            }
            float H = getHeight(v1,v2,v3,pos_x, pos_z);

            return H;
        }
        return -11;
    }
    private float getHeight(Vector3f v1, Vector3f v2, Vector3f v3, float x, float z){
        Vector3f norm = Maths.getNormal(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v3.x, v3.y, v3.z);
        float D = -Vector3f.dot(norm, v1);
        return -(norm.x*x + norm.z*z+ D)/norm.y;
    }

    /**
     * terraformin of map
     * @param x
     * @param z
     * @param direction  true - up / false - down
     */
    public void terraform(float x, float z, boolean direction){
        Set<Chunk> chunkSet = helpTerraform(x, z, 2);
        if(chunkSet == null) return;
        for(Chunk c : chunkSet){
            if(c == null) return;

            float[][] data = c.getData();
            float pos_x = getPosInChunk(x, 20*Chunk.SCALE);
            float pos_z = getPosInChunk(z, 20*Chunk.SCALE);
            //System.out.println("\t"+pos_x + " " + pos_z);
            int quad_x = (int) (pos_x / Chunk.SCALE - 0.00001f);
            int quad_z = (int) (pos_z / Chunk.SCALE - 0.00001f);

            data[quad_z][quad_x] += (direction ? 1 : -1) * DisplayManager.getFrameTimeSeconds() * 1;
            //System.out.println(qp_x + " " + qp_z);
            c.data = data;
            c.initChunk();
            c.reloadModel();
        }


    }

    public void terraform(float x, float z, boolean direction, PowerTerraforming POWER){
        Set<Chunk> chunkSet = helpTerraform(x, z, POWER.getEdge());
        float CELL_SIZE = Chunk.SCALE;

    }

    /**
     * return a set of chunks, that contains a square with a center by X and Z
     * @param x
     * @param z
     * @return
     */
    private Set<Chunk> helpTerraform(float x, float z, int edge){
        final float size = edge*Chunk.SCALE;
        Set<Chunk> chunkSet = new HashSet<>();
        float temp_x = x + size;
        float temp_z = z + size;
        Chunk tempChunk = getChunk(temp_x, temp_z);
        if(tempChunk != null) chunkSet.add(tempChunk);
        temp_z = z - size;
        tempChunk = getChunk(temp_x, temp_z);
        if(tempChunk != null) chunkSet.add(tempChunk);
        temp_x = x - size;
        tempChunk = getChunk(temp_x, temp_z);
        if(tempChunk != null) chunkSet.add(tempChunk);
        temp_z = z + size;
        tempChunk = getChunk(temp_x, temp_z);
        if(tempChunk != null) chunkSet.add(tempChunk);
        return chunkSet;
    }


    private float getPosInChunk (float pos, float size){
        if(pos >= 0) return pos % size;
        else return size + (pos % size);
    }

    /**
     * метод, определяющий чанк, которому принадлежат координаты
     * @param x
     * @param z
     * @return
     */
    private Chunk getChunk(float x, float z){
        Chunk c = chunks.get(0);
        float size = (c.data.length - 1) * Chunk.SCALE;
        int id_x = getIdUsingCoord(x, size);
        int id_z = getIdUsingCoord(z, size);
        for(int i = 0; i < chunks.size(); i++){
            c = chunks.get(i);
            if(id_x == c.getID_X() && id_z == c.getID_Z()) return c;
        }
        return null;
    }

    private Set<Chunk> getChunks(float x, float z){
        Chunk c = chunks.get(0);
        Set<Chunk> chunkSet = new HashSet<>();
        float size = (c.data.length - 1) * Chunk.SCALE;
        int id_x = getIdUsingCoord(x, size);
        int id_z = getIdUsingCoord(z, size);
        for(int i = 0; i < chunks.size(); i++){
            c = chunks.get(i);
            if(id_x == c.getID_X() && id_z == c.getID_Z()) chunkSet.add(c);
        }
        if(chunkSet.size() == 0) return null;
        else return chunkSet;
    }

    /**
     * метод, возвращающий значение ID по значению координаты и размера чанка
     * @param num
     * @param size
     * @return
     */
    private int getIdUsingCoord(float num, float size){
        if(num >= 0) return (int) (num / size);
        else return (int) (num / size) - 1;
    }

    /**
     * Усечение значения между минимумом и максимумом
     * @param data
     * @param min
     * @param max
     * @return
     */
    private float truncation(float data, float min, float max){
        if(data > max) return max;
        else if(data < min) return min;
        else return data;
    }

    public void write(){
        try {
            ChunkLoader.write(chunks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void read(){
        ArrayList<RawChunk> list = null;
        try {
            list = ChunkLoader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < list.size() ; i++) {
            RawChunk r = list.get(i);
            chunks.add(new Chunk(r.getData(), r.getId_x(), r.getId_z()));
        }
    }
}
