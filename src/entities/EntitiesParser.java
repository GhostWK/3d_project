package entities;

import collisions.Cylinder;
import collisions.Parallelepiped;
import collisions.Shape;
import collisions.Sphere;
import com.sun.istack.internal.NotNull;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import textures.ModelTexture;
import toolbox.Maths;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 06.05.2017.
 */
public class EntitiesParser {
    private final String MESH_FILE;
    private final String TEXTURE_FILE;
    private final String OBJECTS_FILE;
    private final Loader loader;

    private Document meshDocument = null;
    private Document textureDocument = null;
    private Document entityDocument = null;

    private List<Mesh> meshList = new ArrayList<>();
    private List<Texture> textureList = new ArrayList<>();
    private List<TexturedEntity> textEntityList = new ArrayList<>();

    public EntitiesParser(String MESH_FILE, String TEXTURE_FILE, String OBJECTS_FILE, Loader loader) {
        this.MESH_FILE = MESH_FILE;
        this.TEXTURE_FILE = TEXTURE_FILE;
        this.OBJECTS_FILE = OBJECTS_FILE;
        this.loader = loader;
    }

    public List<CollisionedEntity> getEntities(){
        parseMesh();
        parseTexture();
        parseEntity();

        List<CollisionedEntity> collisionedEntityList = new ArrayList<>();
        loadToVAOMeshes();
        loadtoVAOTextures();
        for (int i = 0; i < textEntityList.size(); i++) {
            TexturedEntity textured = textEntityList.get(i);
            TexturedModel texturedModel = new TexturedModel(textured.getTexture().getMesh().getRawModel(), new ModelTexture(textured.getTexture().getId()));
            float scale = textured.getScale();
            List<Shape> newShapes = new ArrayList<>();
            List<Shape> oldShapes = textured.getTexture().getMesh().getCollisionList();
            for (int j = 0; j < oldShapes.size(); j++) {
                Shape shape = oldShapes.get(j);
                if(shape instanceof Cylinder){
                    Cylinder oldCylinder = (Cylinder) shape;
                    Vector3f position = Maths.scaleVector(oldCylinder.getPosition(), scale);
                    float height = oldCylinder.getHeight() * scale;
                    float radius = oldCylinder.getRadius() * scale;

                    newShapes.add(new Cylinder(position, oldCylinder.getRotation(), height, radius));
                }
                if(shape instanceof Sphere){
                    Sphere oldSphere = (Sphere) shape;
                    Vector3f position = Maths.scaleVector(oldSphere.getPosition(), scale);
                    float radius = oldSphere.getRad() * scale;

                    newShapes.add(new Sphere(position, radius));
                }
                if(shape instanceof Parallelepiped){
                    Parallelepiped oldParallelepiped = (Parallelepiped) shape;
                    Vector3f position = Maths.scaleVector(oldParallelepiped.getPosition(), scale);
                    Vector3f abc = Maths.scaleVector(oldParallelepiped.getABC(), scale);

                    newShapes.add(new Parallelepiped(position, oldParallelepiped.getRotation(), abc.x, abc.y, abc.z));
                }
            }

            CollisionedEntity newCollisionedEntity =
                    new CollisionedEntity(texturedModel,textured.getPosition(),
                    textured.getRotation().x, textured.getRotation().y, textured.getRotation().z, scale, newShapes);
            collisionedEntityList.add(newCollisionedEntity);
        }


        return collisionedEntityList;
    }

    private void loadToVAOMeshes(){
        for (int i = 0; i < meshList.size(); i++) {
            Mesh mesh = meshList.get(i);
            RawModel temp = OBJLoader.loadObjModel(mesh.getPath(),loader);
            mesh.setRawModel(temp);
        }
    }

    private void loadtoVAOTextures(){
        for (int i = 0; i < textureList.size(); i++) {
            Texture t = textureList.get(i);
            t.setId(loader.loadTexture(t.getTexture()));
        }
    }

    private void parseEntity(){
        try{
            entityDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(OBJECTS_FILE);
            Node root = entityDocument.getDocumentElement();
            NodeList entities = root.getChildNodes();
            for (int i = 0; i < entities.getLength(); i++) {
                Node entity = entities.item(i);
                if(entity.getNodeType() == Node.TEXT_NODE) continue;
                NodeList options = entity.getChildNodes();
                String entityName = entity.getNodeName();
                Vector3f position = new Vector3f(0,0,0);
                Vector3f rotation = new Vector3f(0,0,0);
                float scale = 1;
                //System.out.println(entityName);
                for (int j = 0; j < options.getLength(); j++) {
                    Node option = options.item(j);
                    if(option.getNodeType() == Node.TEXT_NODE) continue;

                    if(option.getNodeName().equals("position")){
                        position = parseVector(option);
                        //System.out.println(position);
                    }
                    if(option.getNodeName().equals("rotation")){
                        rotation = parseVector(option);
                        //System.out.println(rotation);
                    }
                    if(option.getNodeName().equals("scale")){
                        scale = parseFloat(option);
                        //System.out.println(scale);
                    }
                }

                M:
                for(int j = 0; j < textureList.size(); j++){
                    if(textureList.get(j).getName().equals(entityName)){
                        textEntityList.add(new TexturedEntity(textureList.get(j), position, rotation, scale));
                        break M;
                    }
                }
            }
        }catch (Exception e){
            System.err.println("Exception in parseObjects");
            e.printStackTrace();
            System.exit(-1);
        }


//        for(TexturedEntity e : textEntityList){
//            System.out.println("------------------------------------------");
//            System.out.println(e.toString());
//        }
    }

    private void parseTexture(){
        try{

            textureDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(TEXTURE_FILE);
            Node root = textureDocument.getDocumentElement();
            NodeList textures = root.getChildNodes();
            for (int i = 0; i < textures.getLength(); i++) {
                String name = null;
                String textureName = "cube";
                String meshName = null;
                Node texture = textures.item(i);
                if(texture.getNodeType() == Node.TEXT_NODE) continue;
                name = texture.getNodeName();
                NodeList options = texture.getChildNodes();
                for (int j = 0; j < options.getLength(); j++) {
                    Node option = options.item(j);
                    if(option.getNodeType() == Node.TEXT_NODE) continue;
                    if(option.getNodeName().equals("mesh")) meshName = option.getTextContent().trim();
                    if(option.getNodeName().equals("texture")) textureName = option.getTextContent().trim();
                }
                if(meshName != null){
                    if(textureName.equals("cube")){
                        System.err.println("There no texture file. Using default");
                    }else{
                        Mesh mesh = null;
                        for (int j = 0; j < meshList.size(); j++) {
                            if(meshList.get(j).getPath().equals(meshName)){
                                mesh = meshList.get(j);
                            }
                        }
                        if(mesh != null){
                            Texture newTexture = new Texture(name, textureName, mesh);
                            textureList.add(newTexture);
                        }else System.err.println("There no mesh object");

                    }

                }else{
                    System.err.println("There no mesh file");
                }
            }
        }catch (IllegalStateException e){
            System.err.println("There no mesh file");
        }catch (Exception e){
            System.err.println("Exception in parseMesh");
            e.printStackTrace();
            System.exit(-1);
        }



//        System.out.println("\n\n\n");
//        for (Texture x: textureList) {
//            System.out.println(x);
//        }
    }

    private void parseMesh(){
        try {
            meshDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(MESH_FILE);
            Node root = meshDocument.getDocumentElement();
            NodeList meshes = root.getChildNodes();
            for(int i = 0; i < meshes.getLength(); i++){
                Node mesh = meshes.item(i);
                if(mesh.getNodeType() != Node.TEXT_NODE){
                    String fileName = null;
                    List<Shape> shapeList = null;
                    NodeList options = mesh.getChildNodes();
                    for(int j = 0; j < options.getLength(); j++){
                        Node option = options.item(j);
                        if(option.getNodeType() != Node.TEXT_NODE){
                            if(option.getNodeName().equals("filename")){
                                fileName = option.getTextContent().trim();
                                //System.out.println(fileName);
                            }
                            if(option.getNodeName().equals("primitives")){
                                shapeList = parsePrimitives(option);

                            }
                        }

                    }
                    if(fileName != null){
                        Mesh newMesh = new Mesh(fileName, shapeList);
                        meshList.add(newMesh);

                    }
                }


            }


        } catch (Exception e) {
            System.err.println("Exception in parseMesh");
            e.printStackTrace();
            System.exit(-1);
        }

    }

    private List<Shape> parsePrimitives(Node node){
        List<Shape> shapeList = new ArrayList<>();
        NodeList shapes = node.getChildNodes();
        for (int i = 0; i < shapes.getLength(); i++) {
            Node shape = shapes.item(i);
            if(shape.getNodeType() == Node.TEXT_NODE) continue;

            if(shape.getNodeName().equals("Sphere")){
                shapeList.add(parseSphere(shape));
            }

            if(shape.getNodeName().equals("Parallelepiped")){
                shapeList.add(parseParallelepiped(shape));
            }

            if(shape.getNodeName().equals("Cylinder")){
                shapeList.add(parseCylender(shape));
            }
        }
        return shapeList;
    }

    private Cylinder parseCylender(Node node){
        Vector3f position = new Vector3f(0,0,0);
        Vector3f rotation = new Vector3f(0,0,0);
        float height = 0;
        float radius = 0;


        NodeList options = node.getChildNodes();

        for (int i = 0; i < options.getLength(); i++) {
            Node option = options.item(i);
            if(option.getNodeType() == Node.TEXT_NODE) continue;

            if(option.getNodeName().equals("position")){
                position = parseVector(option);
            }
            if(option.getNodeName().equals("rotation")){
                rotation = parseVector(option);
            }
            if(option.getNodeName().equals("height")){
                height = parseFloat(option);
            }
            if(option.getNodeName().equals("radius")){
                radius = parseFloat(option);
            }
        }
        return new Cylinder(position, rotation, height, radius);
    }

    private Parallelepiped parseParallelepiped(Node node){
        Vector3f position = new Vector3f(0,0,0);
        Vector3f rotation = new Vector3f(0,0,0);
        Vector3f abc = new Vector3f(0,0,0);


        NodeList options = node.getChildNodes();

        for (int i = 0; i < options.getLength(); i++) {
            Node option = options.item(i);
            if(option.getNodeType() == Node.TEXT_NODE) continue;

            if(option.getNodeName().equals("position")){
                position = parseVector(option);
            }
            if(option.getNodeName().equals("rotation")){
                rotation = parseVector(option);
            }
            if(option.getNodeName().equals("abc")){
                abc = parseVector(option);
            }
        }
        return new Parallelepiped(position, rotation, abc.x, abc.y, abc.z);
    }

    private Sphere parseSphere(Node node){
        Vector3f position = new Vector3f(0,0,0);
        float rad = 0;
        NodeList options = node.getChildNodes();

        for (int i = 0; i < options.getLength(); i++) {
            Node option = options.item(i);
            if(option.getNodeType() == Node.TEXT_NODE) continue;

            if(option.getNodeName().equals("position")){
                position = parseVector(option);
            }
            if(option.getNodeName().equals("rad")){
                rad = parseFloat(option);
            }
        }
        return new Sphere(position, rad);
    }

    private Vector3f parseVector(Node option){
        Vector3f vector3f = new Vector3f(0,0,0);

        NodeList list = option.getChildNodes();
        for(int i = 0; i < list.getLength(); i++){
            Node node = list.item(i);
            if(node.getNodeType() != Node.TEXT_NODE){
                if(node.getNodeName().equals("x")) vector3f.setX(Float.parseFloat(node.getTextContent()));
                if(node.getNodeName().equals("y")) vector3f.setY(Float.parseFloat(node.getTextContent()));
                if(node.getNodeName().equals("z")) vector3f.setZ(Float.parseFloat(node.getTextContent()));
            }
        }

        return vector3f;
    }

    private float parseFloat(Node option){
        return Float.parseFloat(option.getTextContent());
    }

}


class Mesh{
    private RawModel rawModel;

    public RawModel getRawModel() {
        return rawModel;
    }

    public void setRawModel(RawModel rawModel) {
        this.rawModel = rawModel;
    }

    public String getPath() {
        return path;
    }

    private final String path;
    private List<Shape> collisionList;

    public Mesh(String path, List<Shape> collisionList) {
        this.path = path;
        this.collisionList = collisionList;
    }

    public List<Shape> getCollisionList() {
        return collisionList;
    }

    @Override
    public String toString() {
        return "Mesh{" +
                "path='" + path + '\'' +
                ", collisionList=\n" + collisionList +
                '}';
    }
}

class Texture{
    private int id;
    private final String name;
    private final String texture;
    private Mesh mesh;

    public String getTexture() {
        return texture;
    }

    public Texture(String name, String texture, @NotNull Mesh mesh) {
        this.name = name;
        this.texture = texture;
        this.mesh = mesh;
    }

    public String getName() {
        return name;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Texture{" +
                "name='" + name + '\'' +
                ", texture='" + texture + '\'' +
                ", mesh=" + mesh +
                '}';
    }
}

class TexturedEntity{
    private Texture texture;
    private Vector3f position;
    private Vector3f rotation;
    private float scale;

    public TexturedEntity(Texture texture, Vector3f position, Vector3f rotation, float scale) {
        this.texture = texture;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    @Override
    public String toString() {
        return "TexturedEntity{" +
                "\ntexture=" + texture +
                ", \nposition=" + position +
                ", \nrotation=" + rotation +
                ", \nscale=" + scale +
                '}';
    }
}