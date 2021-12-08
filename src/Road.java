public class Road extends TrafficObject{
    public static final int MIN_LENGTH = 3;
    public enum DIRECTION {
        NORTH, EAST, SOUTH, WEST, DIRECTION_COUNT;
        public static DIRECTION OppositeDirection(DIRECTION direction)
        {
            return DIRECTION.values()[(direction.ordinal() + 2) % DIRECTION_COUNT.ordinal()];
        }
    }

    private int length;
    private Road.DIRECTION direction;
    private Lane[] lanes;

    public Road(int id, String name, int length, Road.DIRECTION direction)
    {
        super(id, name + "Road");
        this.length = MathUtility.Clamp(length, MIN_LENGTH, MIN_LENGTH * 5);
        this.direction = direction;

        lanes = new Lane[2];
        switch (direction) {
            case NORTH, SOUTH -> {
                lanes[0] = new Lane(0, GetName(), this.length, DIRECTION.NORTH);
                lanes[1] = new Lane(1, GetName(), this.length, DIRECTION.SOUTH);
            }
            case EAST, WEST -> {
                lanes[0] = new Lane(0, GetName(), this.length, DIRECTION.EAST);
                lanes[1] = new Lane(1, GetName(), this.length, DIRECTION.WEST);
            }
        }
    }

    public int GetLength() { return length; }
    public DIRECTION GetDirection() { return this.direction; }
    public Lane GetLane(Road.DIRECTION direction)
    {
        for(Lane lane: lanes)
        {
            if(lane.GetDirection() == direction)
            {
                return lane;
            }
        }
        return null;
    }

    public Road GetConnectedRoad(DIRECTION direction) { return lanes[direction.ordinal() / 2].GetConnectedRoad(); }

    public void ConnectSegment(Segment nextSegment, Lane.SEGMENT_POSITION position, Road.DIRECTION direction)
    {
        for(Lane lane: lanes)
        {
            if(lane.GetDirection() == direction)
            {
                lane.ConnectSegment(null, nextSegment, position);
                break;
            }
        }
    }

    public void Connect(Road roadToConnect)
    {
        if(lanes[0].GetDirection() == roadToConnect.lanes[0].GetDirection())
        {
            lanes[0].ConnectSegment(roadToConnect, roadToConnect.lanes[0].GetSegment(Lane.SEGMENT_POSITION.FIRST), Lane.SEGMENT_POSITION.LAST);
        }
        if(lanes[1].GetDirection() == roadToConnect.lanes[1].GetDirection())
        {
            roadToConnect.lanes[1].ConnectSegment(roadToConnect, lanes[1].GetSegment(Lane.SEGMENT_POSITION.FIRST), Lane.SEGMENT_POSITION.LAST);
        }
    }

    public Segment GetRandomSegment()
    {
        return lanes[(int)Math.round(Math.random())].GetSegment(Lane.SEGMENT_POSITION.values()[(int)Math.ceil(Math.random() * Lane.SEGMENT_POSITION.LAST.ordinal() - 1)]);
    }

    public Segment[] GetRandomSegments(int count)
    {
        return lanes[(int)Math.round(Math.random())].GetSegments(Lane.SEGMENT_POSITION.FIRST, MathUtility.Clamp(count, 1, length));
    }
}
