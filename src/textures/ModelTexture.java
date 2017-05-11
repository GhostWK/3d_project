package textures;

/**
 * Created by USER on 24.03.2017.
 */
public class ModelTexture {
    private int textureID;

    private float shineDamper = 1;
    private float reflectibity = 0;

    private boolean hasTransparency = false;

    public float getShineDamper() {
        return shineDamper;
    }

    public boolean isHasTransparency() {
        return hasTransparency;
    }

    public void setHasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectibity() {
        return reflectibity;
    }

    public void setReflectibity(float reflectibity) {
        this.reflectibity = reflectibity;
    }

    public int getTextureID() {
        return textureID;
    }

    public ModelTexture(int textureID) {

        this.textureID = textureID;
    }
}
