import java.util.ArrayList;

public class Road {
    public static final int MIN_LENGTH = 3;
    public enum DIRECTION {
        N, E, S, W, DIRECTION_COUNT;
        public static DIRECTION OppositeDirection(DIRECTION direction)
        {
            return DIRECTION.values()[direction.ordinal() + 2 % (DIRECTION_COUNT.ordinal() - 1)];
        }
    }

    private int length;
    private Lane[] lanes;
    public Road(int length, Road.DIRECTION direction)
    {
        this.length = MathUtility.Clamp(length, MIN_LENGTH, MIN_LENGTH * 5);

        lanes = new Lane[2];
        switch (direction) {
            case N, S -> {
                lanes[0] = new Lane(this.length, DIRECTION.N);
                lanes[1] = new Lane(this.length, DIRECTION.S);
            }
            case E, W -> {
                lanes[0] = new Lane(this.length, DIRECTION.E);
                lanes[1] = new Lane(this.length, DIRECTION.W);
            }
        }
    }

    public void Connect(Segment nextSegment, int position, Road.DIRECTION direction)
    {
        for(Lane lane: lanes)
        {
            if(lane.GetDirection() == direction)
            {
                lane.Connect(nextSegment, position);
                break;
            }
        }
    }
}
