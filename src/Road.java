import java.util.ArrayList;

public class Road {
    public static final int MIN_LENGTH = 3;
    public enum DIRECTION { N, E, S, W, DIRECTION_COUNT }

    private int length;
    private ArrayList<Lane> lanes;
    public Road(int length, Road.DIRECTION direction)
    {
        this.length = MathUtility.Clamp(length, MIN_LENGTH, MIN_LENGTH * 5);

        lanes = new ArrayList<>();
        switch (direction) {
            case N, S -> {
                lanes.add(new Lane(this.length, DIRECTION.N));
                lanes.add(new Lane(this.length, DIRECTION.S));
            }
            case E, W -> {
                lanes.add(new Lane(this.length, DIRECTION.E));
                lanes.add(new Lane(this.length, DIRECTION.W));
            }
        }
    }
}
