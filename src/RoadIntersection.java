public class RoadIntersection {
    private final int STRAIGHT_LENGTH = 2;
    private final int RIGHT_LENGTH = 2;

    private Road.DIRECTION dirOutwardsIntersection;
    private Road road;
    private Segment[] straightSegment;
    private Segment[] rightTurnSegment;
    private Segment leftTurnSegment;

    public RoadIntersection(Road.DIRECTION direction)
    {
        this.dirOutwardsIntersection = direction;
        this.road = new Road(Road.MIN_LENGTH, direction);
        this.straightSegment = null;
        this.rightTurnSegment = null;
        this.leftTurnSegment = null;
    }

    public void AddStraightSegment()
    {
        if(this.straightSegment != null)
            return;

        this.straightSegment = new Segment[STRAIGHT_LENGTH];
        for(int i = 0; i < STRAIGHT_LENGTH; ++i)
        {
            Segment segment = new Segment();
            if(i == 0)
            {
                road.Connect(segment, 3, Road.DIRECTION.OppositeDirection(dirOutwardsIntersection));
            }
            this.straightSegment[i] = segment;
        }
    }

    public void AddRightSegment()
    {
        if(this.rightTurnSegment != null)
            return;

        this.rightTurnSegment = new Segment[RIGHT_LENGTH];
        for(int i = 0; i < RIGHT_LENGTH; ++i)
        {
            Segment segment = new Segment();
            if(i == 0)
            {
                road.Connect(segment, 3, Road.DIRECTION.OppositeDirection(dirOutwardsIntersection));
            }
            this.rightTurnSegment[i] = segment;
        }
    }

    public void AddLeftSegment()
    {
        if(this.leftTurnSegment != null)
            return;

        Segment segment = new Segment();
        road.Connect(segment, 2, Road.DIRECTION.OppositeDirection(dirOutwardsIntersection));
        this.leftTurnSegment = segment;
    }

    public Segment GetLastStraightSegment() { return straightSegment[straightSegment.length - 1]; }
    public Segment GetLastRightSegment() { return straightSegment[straightSegment.length - 1]; }
    public Segment GetLastLeftSegment() { return straightSegment[straightSegment.length - 1]; }

    public void ConnectIncomingSegment(RoadIntersection otherRoad)
    {
        switch (dirOutwardsIntersection)
        {
            case N:
                switch (otherRoad.dirOutwardsIntersection)
                {
                    case E -> { road.Connect(otherRoad.GetLastRightSegment(), 0, dirOutwardsIntersection); }
                    case S -> { road.Connect(otherRoad.GetLastStraightSegment(), 0, dirOutwardsIntersection); }
                    case W -> { road.Connect(otherRoad.GetLastLeftSegment(), 1, dirOutwardsIntersection); }
                }
                break;
            case E:
                switch (otherRoad.dirOutwardsIntersection)
                {
                    case N -> { road.Connect(otherRoad.GetLastLeftSegment(), 1, dirOutwardsIntersection); }
                    case S -> { road.Connect(otherRoad.GetLastRightSegment(), 0, dirOutwardsIntersection); }
                    case W -> { road.Connect(otherRoad.GetLastStraightSegment(), 0, dirOutwardsIntersection); }
                }
                break;
            case S:
                switch (otherRoad.dirOutwardsIntersection)
                {
                    case N -> { road.Connect(otherRoad.GetLastStraightSegment(), 0, dirOutwardsIntersection); }
                    case E -> { road.Connect(otherRoad.GetLastLeftSegment(), 1, dirOutwardsIntersection); }
                    case W -> { road.Connect(otherRoad.GetLastRightSegment(), 0, dirOutwardsIntersection); }
                }
                break;
            case W:
                switch (otherRoad.dirOutwardsIntersection)
                {
                    case N -> { road.Connect(otherRoad.GetLastRightSegment(), 0, dirOutwardsIntersection); }
                    case E -> { road.Connect(otherRoad.GetLastStraightSegment(), 0, dirOutwardsIntersection); }
                    case S -> { road.Connect(otherRoad.GetLastLeftSegment(), 1, dirOutwardsIntersection); }
                }
                break;
        }
    }
}
