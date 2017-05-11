package gamelogic.Map;

/**
 * Created by USER on 27.04.2017.
 */
public enum BrightEnum {
    DARK_0(1f/64), DARK_1(5f/64), MEDIUM_0(9f/64), MEDIUM_1(13f/64);


    float brightness;



    BrightEnum(float brightness) {
        this.brightness = brightness;
    }

    public float getBrightness() {
        return brightness;
    }
}
