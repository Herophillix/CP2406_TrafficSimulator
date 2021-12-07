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
    private Lane[] lanes;
    public Road(int id, int length, Road.DIRECTION direction)
    {
        super(id, "Road");
        this.length = MathUtility.Clamp(length, MIN_LENGTH, MIN_LENGTH * 5);

        lanes = new Lane[2];
        switch (direction) {
            case NORTH, SOUTH -> {
                lanes[0] = new Lane(0, this, this.length, DIRECTION.NORTH);
                lanes[1] = new Lane(1, this, this.length, DIRECTION.SOUTH);
            }
            case EAST, WEST -> {
                lanes[0] = new Lane(0, this, this.length, DIRECTION.EAST);
                lanes[1] = new Lane(1, this, this.length, DIRECTION.WEST);
            }
        }
    }

    public void ConnectSegment(Segment nextSegment, Lane.SEGMENT_POSITION position, Road.DIRECTION direction)
    {
        for(Lane lane: lanes)
        {
            if(lane.GetDirection() == direction)
            {
                lane.ConnectSegment(nextSegment, position);
                break;
            }
        }
    }

    public void Connect(Road roadToConnect)
    {
        if(lanes[0].GetDirection() == roadToConnect.lanes[0].GetDirection())
        {
            lanes[0].ConnectSegment(roadToConnect.lanes[0].GetSegment(Lane.SEGMENT_POSITION.FIRST), Lane.SEGMENT_POSITION.LAST);
        }
        if(lanes[1].GetDirection() == roadToConnect.lanes[1].GetDirection())
        {
            roadToConnect.lanes[1].ConnectSegment(lanes[1].GetSegment(Lane.SEGMENT_POSITION.FIRST), Lane.SEGMENT_POSITION.LAST);
        }
    }

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

    public Segment GetRandomSegment()
    {
        return lanes[0].GetSegment(Lane.SEGMENT_POSITION.FIRST);
    }

    public Segment[] GetRandomSegments(int count)
    {
        return lanes[0].GetSegments(Lane.SEGMENT_POSITION.FIRST, count);
    }

    public void PrintRoad()
    {
        for(int i = 0; i < length; ++i)
        {
            System.out.println("Segment " + i +": " + lanes[0].roadSegments.get(i).toString() + "," + lanes[1].roadSegments.get(i).toString());
        }
        System.out.println();
    }
}
