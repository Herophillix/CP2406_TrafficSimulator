public class RoadIntersection {
    private final int STRAIGHT_LENGTH = 2;
    private final int RIGHT_LENGTH = 2;

    private Road road;
    private Segment[] straightSegment;
    private Segment[] rightTurnSegment;
    private Segment leftTurnSegment;

    public RoadIntersection(Road.DIRECTION direction)
    {
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
            this.straightSegment[i] = new Segment();
        }
    }

    public void AddRightSegment()
    {
        if(this.rightTurnSegment != null)
            return;

        this.rightTurnSegment = new Segment[RIGHT_LENGTH];
        for(int i = 0; i < RIGHT_LENGTH; ++i)
        {
            this.rightTurnSegment[i] = new Segment();
        }
    }

    public void AddLeftSegment()
    {
        if(this.leftTurnSegment != null)
            return;

        this.leftTurnSegment = new Segment();
    }
}
