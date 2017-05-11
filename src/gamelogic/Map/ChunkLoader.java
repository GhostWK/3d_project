package gamelogic.Map;

import gamelogic.SettingsClass;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 30.03.2017.
 */
public class ChunkLoader {


  public static ArrayList<RawChunk> read() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(SettingsClass.MAP));
    String temp = null;
    ArrayList<RawChunk> list = new ArrayList<>();
    while ((temp = reader.readLine()) != null){
      int id_x = Integer.parseInt(temp.split(" ")[0]);
      int id_z = Integer.parseInt(temp.split(" ")[1]);
      float[][] data = new float[21][21];
      for (int i = 0; i < data.length; i++) {
        temp = reader.readLine();
        String[] s = temp.split(" ");
        for (int j = 0; j < s.length; j++) {
          data[i][j] = Float.parseFloat(s[j]);
        }
      }
      list.add(new RawChunk(id_x,id_z,data));
    }
    reader.close();
    return list;
  }

  public static void write(ArrayList<Chunk> list) throws IOException{
    BufferedWriter writer = new BufferedWriter(new FileWriter(SettingsClass.MAP));
    for (int i = 0; i < list.size(); i++) {
      Chunk c = list.get(i);
      String temp =  c.getID_X() + " " + c.getID_Z();
      writer.write(temp+"\n");
      float[][] data = c.getData();
      for (int j = 0; j < data.length; j++) {
        String t = "";
        for (int k = 0; k < data.length; k++) {
          t += data[j][k] + " ";
        }
        writer.write(t+"\n");
      }
    }
    writer.close();
  }
}
