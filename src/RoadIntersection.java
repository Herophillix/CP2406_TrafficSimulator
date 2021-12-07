import java.util.Collections;
import java.util.Arrays;

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
        this.road = new Road(direction.ordinal(), Road.MIN_LENGTH, direction);
        this.straightSegment = null;
        this.rightTurnSegment = null;
        this.leftTurnSegment = null;
    }

    public Road GetRoad() { return road; }

    public void AddStraightSegment()
    {
        if(this.straightSegment != null)
            return;

        this.straightSegment = new Segment[STRAIGHT_LENGTH];
        Segment oldSegment = null;
        for(int i = 0; i < STRAIGHT_LENGTH; ++i)
        {
            Segment segment = new Segment(i);
            segment.AddNextSegment(oldSegment);

            this.straightSegment[i] = segment;
            oldSegment = segment;
        }
        Collections.reverse(Arrays.asList(straightSegment));
        road.ConnectSegment(straightSegment[0], Lane.SEGMENT_POSITION.LAST, Road.DIRECTION.OppositeDirection(dirOutwardsIntersection));
    }

    public void AddRightSegment()
    {
        if(this.rightTurnSegment != null)
            return;

        this.rightTurnSegment = new Segment[RIGHT_LENGTH];
        Segment oldSegment = null;
        for(int i = 0; i < RIGHT_LENGTH; ++i)
        {
            Segment segment = new Segment(i);
            segment.AddNextSegment(oldSegment);

            this.rightTurnSegment[i] = segment;
            oldSegment = segment;
        }
        Collections.reverse(Arrays.asList(rightTurnSegment));
        road.ConnectSegment(rightTurnSegment[0], Lane.SEGMENT_POSITION.LAST, Road.DIRECTION.OppositeDirection(dirOutwardsIntersection));

    }

    public void AddLeftSegment()
    {
        if(this.leftTurnSegment != null)
            return;

        Segment segment = new Segment(0);
        road.ConnectSegment(segment, Lane.SEGMENT_POSITION.SECOND_LAST, Road.DIRECTION.OppositeDirection(dirOutwardsIntersection));
        this.leftTurnSegment = segment;
    }

    public Segment GetLastStraightSegment() { return straightSegment[straightSegment.length - 1]; }
    public Segment GetLastRightSegment() { return rightTurnSegment[straightSegment.length - 1]; }
    public Segment GetLastLeftSegment() { return leftTurnSegment; }

    public void ConnectIncomingSegment(RoadIntersection otherRoad)
    {
        switch (dirOutwardsIntersection)
        {
            case N:
                switch (otherRoad.dirOutwardsIntersection)
                {
                    case E -> { otherRoad.GetLastRightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST));}
                    case S -> { otherRoad.GetLastStraightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST)); }
                    case W -> { otherRoad.GetLastLeftSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.SECOND));}
                }
                break;
            case E:
                switch (otherRoad.dirOutwardsIntersection)
                {
                    case N -> { otherRoad.GetLastLeftSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.SECOND));}
                    case S -> { otherRoad.GetLastRightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST)); }
                    case W -> { otherRoad.GetLastStraightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST));}
                }
                break;
            case S:
                switch (otherRoad.dirOutwardsIntersection)
                {
                    case N -> { otherRoad.GetLastStraightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST));}
                    case E -> { otherRoad.GetLastLeftSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.SECOND)); }
                    case W -> { otherRoad.GetLastRightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST));}
                }
                break;
            case W:
                switch (otherRoad.dirOutwardsIntersection)
                {
                    case N -> { otherRoad.GetLastRightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST));}
                    case E -> { otherRoad.GetLastStraightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST)); }
                    case S -> { otherRoad.GetLastLeftSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST));}
                }
                break;
        }
    }
}
