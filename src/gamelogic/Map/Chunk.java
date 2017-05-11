package gamelogic.Map;



import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import renderEngine.Loader;
import renderEngine.VAO;
import textures.ModelTexture;
import toolbox.Maths;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by USER on 30.03.2017.
 */
public class Chunk {
    public static Loader loader;
    public static final String textPath = "colorTexture";
//    public static final String textPath = "text";



    private int vaoID;
    private int vertexCount;

    private int ID_X;
    private int ID_Z;

    public float[][] data;
    public int[] indices;
    public float[] vertices;
    public float[] texts;
    public float[] normals;

    private RawModel model;

    private ModelTexture texture;
    private Entity entity;

    public int getID_X() {
        return ID_X;
    }

    public int getID_Z() {
        return ID_Z;
    }

    public float[][] getData() {
        return data;
    }

    public Entity getEntity() {
        return entity;
    }

    public Chunk(float[][] data, int ID_X, int ID_Z) {
        this.data = data;
        this.ID_X = ID_X;
        this.ID_Z = ID_Z;

        initChunk();
        prepare();
    }

    private void prepare(){
        model = loader.initChunk(vertices,texts,normals,indices);
        texture = new ModelTexture(loader.loadTexture(textPath));
        entity = new Entity(new TexturedModel(model, texture), new Vector3f(ID_X*SCALE*(data.length - 1),0,ID_Z*SCALE*(data.length - 1)), 0,0,0,1);
    }

    public void reloadModel(){

        List<VAO> listVAO = loader.getVaoList();
        for(int i = 0; i < listVAO.size(); i++){
            if(listVAO.get(i).getVAO_ID() == model.getVaoID()){
                listVAO.get(i).cleanUp();
                listVAO.remove(i);
                i--;
            }
        }
        loader.setVaoList(listVAO);

//        for(VAO x : listVAO) {
//            if (x.getVAO_ID() == model.getVaoID()) {
//                x.cleanUp();
//
//            }
//
//        }



        model = loader.initChunk(vertices,texts,normals,indices);
        entity = new Entity(new TexturedModel(model, texture), new Vector3f(ID_X*SCALE*(data.length - 1),0,ID_Z*SCALE*(data.length - 1)), 0,0,0,1);
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }



    public ModelTexture getTexture() {
        return texture;
    }

    public void setTexture(ModelTexture texture) {
        this.texture = texture;
    }

    public void initRender(int vaoID, int vertexCount){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }




    public static final float SCALE = 5;

    public Chunk(int vaoID, int vertexCount) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

//TODO: redo using colors
    public void initChunk(){
        int size = data.length;
        int counter = size / 2;
        ArrayList<Integer> indList = new ArrayList<>();
        ArrayList<Float> textList = new ArrayList<>();
        ArrayList<Float> vertList = new ArrayList<>();
        ArrayList<Float> normsList = new ArrayList<>();
        float x1;
        float y1;
        float z1;
        float x2;
        float y2;
        float z2;
        float x3;
        float y3;
        float z3;
        int k  = 0;
        for(int i = 0; i < counter; i++){
            for(int j = 0; j < counter; j++){
                //first

                x1 = (j*2 + 0)*SCALE;
                y1 = data[2*i][2*j];
                z1 = (i*2 + 0)*SCALE;

                x2 = (j*2 + 0)*SCALE;
                y2 = data[i*2 + 1][2*j];
                z2 = (i*2 + 1)*SCALE;

                x3 = (j*2 + 1)*SCALE;
                y3 = data[2*i + 1][2*j + 1];
                z3 = (i*2 + 1)*SCALE;

                helpInit(x1,y1,z1, x2,y2,z2, x3,y3,z3, vertList, normsList);

//                textList.add(0f); textList.add(0f);
//                textList.add(0f); textList.add(1f);
//                textList.add(1f); textList.add(1f);

                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());

                //second

                x1 = (j*2 + 1)*SCALE;
                y1 = data[2*i + 1][2*j + 1];
                z1 = (i*2 + 1)*SCALE;

                x2 = (j*2 + 0)*SCALE;
                y2 = data[i*2 + 1][2*j];
                z2 = (i*2 + 1)*SCALE;

                x3 = (j*2 + 0)*SCALE;
                y3 = data[2*i+2][2*j + 0];
                z3 = (i*2 + 2)*SCALE;

                helpInit(x1,y1,z1, x2,y2,z2, x3,y3,z3, vertList, normsList);

//                textList.add(1f); textList.add(0f);
//                textList.add(0f); textList.add(0f);
//                textList.add(0f); textList.add(1f);

                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());


                //third

                x1 = (j*2 + 0)*SCALE;
                y1 = data[2*i+2][2*j];
                z1 = (i*2 + 2)*SCALE;

                x2 = (j*2 + 1)*SCALE;
                y2 = data[2*i + 2][2*j + 1];
                z2 = (i*2 + 2)*SCALE;

                x3 = (j*2 + 1)*SCALE;
                y3 = data[2*i + 1][2*j + 1];
                z3 = (i*2 + 1)*SCALE;

                helpInit(x1,y1,z1, x2,y2,z2, x3,y3,z3, vertList, normsList);

//                textList.add(0f); textList.add(1f);
//                textList.add(1f); textList.add(1f);
//                textList.add(1f); textList.add(0f);
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());

                //fourth

                x1 = (j*2 + 1)*SCALE;
                y1 = data[2*i + 1][2*j + 1];
                z1 = (i*2 + 1)*SCALE;

                x2 = (j*2 + 1)*SCALE;
                y2 = data[2*i + 2][2*j + 1];
                z2 = (i*2 + 2)*SCALE;

                x3 = (j*2 + 2)*SCALE;
                y3 = data[2*i + 2][2*j + 2];
                z3 = (i*2 + 2)*SCALE;

                helpInit(x1,y1,z1, x2,y2,z2, x3,y3,z3, vertList, normsList);

//                textList.add(0f); textList.add(0f);
//                textList.add(0f); textList.add(1f);
//                textList.add(1f); textList.add(1f);
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());

                //fifth
                x1 = (j*2 + 2)*SCALE;
                y1 = data[2*i + 2][2*j + 2];
                z1 = (i*2 + 2)*SCALE;

                x2 = (j*2 + 2)*SCALE;
                y2 = data[2*i + 1][2*j + 2];
                z2 = (i*2 + 1)*SCALE;

                x3 = (j*2 + 1)*SCALE;
                y3 = data[2*i + 1][2*j + 1];
                z3 = (i*2 + 1)*SCALE;

                helpInit(x1,y1,z1, x2,y2,z2, x3,y3,z3, vertList, normsList);

//                textList.add(1f); textList.add(1f);
//                textList.add(1f); textList.add(0f);
//                textList.add(0f); textList.add(0f);;

                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());

                //sixth

                x1 = (j*2 + 1)*SCALE;
                y1 = data[2*i + 1][2*j + 1];
                z1 = (i*2 + 1)*SCALE;

                x2 = (j*2 + 2)*SCALE;
                y2 = data[2*i + 1][2*j + 2];
                z2 = (i*2 + 1)*SCALE;

                x3 = (j*2 + 2)*SCALE;
                y3 = data[2*i + 0][2*j + 2];
                z3 = (i*2 + 0)*SCALE;

                helpInit(x1,y1,z1, x2,y2,z2, x3,y3,z3, vertList, normsList);

//                textList.add(0f); textList.add(1f);
//                textList.add(1f); textList.add(1f);
//                textList.add(1f); textList.add(0f);
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());

                //seventh
                x1 = (j*2 + 2)*SCALE;
                y1 = data[2*i + 0][2*j + 2];
                z1 = (i*2 + 0)*SCALE;

                x2 = (j*2 + 1)*SCALE;
                y2 = data[2*i + 0][2*j + 1];
                z2 = (i*2 + 0)*SCALE;

                x3 = (j*2 + 1)*SCALE;
                y3 = data[2*i + 1][2*j + 1];
                z3 = (i*2 + 1)*SCALE;

                helpInit(x1,y1,z1, x2,y2,z2, x3,y3,z3, vertList, normsList);

//                textList.add(1f); textList.add(0f);
//                textList.add(0f); textList.add(0f);
//                textList.add(0f); textList.add(1f);
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());

                //eighth
                x1 = (j*2 + 1)*SCALE;
                y1 = data[2*i + 1][2*j + 1];
                z1 = (i*2 + 1)*SCALE;

                x2 = (j*2 + 1)*SCALE;
                y2 = data[2*i + 0][2*j + 1];
                z2 = (i*2 + 0)*SCALE;

                x3 = (j*2 + 0)*SCALE;
                y3 = data[2*i + 0][2*j + 0];
                z3 = (i*2 + 0)*SCALE;

                helpInit(x1,y1,z1, x2,y2,z2, x3,y3,z3, vertList, normsList);

//                textList.add(1f); textList.add(1f);
//                textList.add(1f); textList.add(0f);
//                textList.add(0f); textList.add(0f);
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());
                textList.add(ColorEnum.GREEN.getPos()); textList.add(BrightEnum.DARK_1.getBrightness());

                for(int p = 0; p < 24; p++) indList.add(k++);

            }
        }

        normals = new float[normsList.size()];
        for(int i = 0; i < normsList.size(); i++) normals[i] = normsList.get(i);

        vertices = new float[vertList.size()];
        for(int i = 0; i < vertList.size(); i++) vertices[i] = vertList.get(i);

        indices = new int[indList.size()];
        for(int i = 0; i < indList.size(); i++) indices[i] = indList.get(i);

        texts = new float[textList.size()];
        for(int i = 0; i < textList.size(); i++) texts[i] = textList.get(i);
    }

    private void helpInit(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3,
                          ArrayList<Float> v, ArrayList<Float> n){
        v.add(x1);
        v.add(y1);
        v.add(z1);
        v.add(x2);
        v.add(y2);
        v.add(z2);
        v.add(x3);
        v.add(y3);
        v.add(z3);
        Vector3f temp = Maths.getNormal(x1, y1, z1, x2, y2, z2, x3, y3, z3);
        for(int i = 0; i < 3;  i++){
            n.add(temp.x);
            n.add(temp.y);
            n.add(temp.z);
        }

    }
}
