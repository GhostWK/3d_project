package gamelogic.Map;

/**
 * Created by USER on 27.04.2017.
 */
public enum ColorEnum {

    GREEN(1f/64);

    public static float SCALE = 64;
    float pos;

    ColorEnum(float pos) {
        this.pos = pos;
    }

    public float getPos() {
        return pos;
    }
}
