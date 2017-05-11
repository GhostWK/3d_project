package gamelogic.Map;

/**
 * Created by USER on 22.04.2017.
 */
public enum PowerTerraforming {
    LOW(0.1f, 3), MEDIUM (0.5f, 3);

    private float power;
    private int edge;

    public float getPower() {
        return power;
    }

    public int getEdge() {
        return edge;
    }

    PowerTerraforming(float power, int edge) {
        this.power = power;
        this.edge = edge;
    }
}
