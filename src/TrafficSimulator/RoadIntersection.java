package TrafficSimulator;

import GUI.TrafficFrame;
import Utility.Vector2;

import java.awt.*;
import java.util.Collections;
import java.util.Arrays;

public class RoadIntersection extends TrafficObject{
    private final int STRAIGHT_LENGTH = 2;
    private final int RIGHT_LENGTH = 2;

    private Road.DIRECTION dirOutwardsIntersection;
    private Road road;
    private Segment[] straightSegment; // Intermediary segment when vehicles travel straight through intersection
    private TrafficLight straightTrafficLight;
    private Segment[] rightTurnSegment; // Intermediary segment when vehicles turn right through intersection
    private TrafficLight rightTrafficLight;
    private Segment leftTurnSegment; // Intermediary segment when vehicles turn left through intersection

    public RoadIntersection(Road.DIRECTION direction, String name)
    {
        super(0, name);
        this.dirOutwardsIntersection = direction;
        this.road = new Road(direction.ordinal(), name + direction + "_", Road.MIN_LENGTH, direction);
        this.straightSegment = null;
        this.rightTurnSegment = null;
        this.leftTurnSegment = null;
        this.scale = new Vector2(this.road.scale.x, this.road.scale.y);
    }

    public Road GetRoad() { return road; }

    // Add a straight segment if there is a road on the opposite of the intersection
    public void AddStraightSegment()
    {
        if(this.straightSegment != null)
            return;

        String objectName = road.GetLane(Road.DIRECTION.OppositeDirection(dirOutwardsIntersection)).GetName() + "-Straight_";

        this.straightSegment = new Segment[STRAIGHT_LENGTH];
        Segment oldSegment = null;
        for(int i = 0; i < STRAIGHT_LENGTH; ++i)
        {
            Segment segment = new Segment((STRAIGHT_LENGTH - 1) - i, objectName, dirOutwardsIntersection);
            segment.AddNextSegment(oldSegment);

            this.straightSegment[i] = segment;
            oldSegment = segment;
        }
        Collections.reverse(Arrays.asList(straightSegment));

        boolean isGreen = (dirOutwardsIntersection.ordinal() % 2) == 0;
        int startTick;
        switch (dirOutwardsIntersection)
        {
            case NORTH, SOUTH -> startTick = 0;
            case EAST, WEST -> startTick = TrafficLight.SWITCH_THRESHOLD + 2 + 2;
            default -> startTick = 0;
        }

        straightTrafficLight = new TrafficLight(0, objectName, isGreen, startTick);
        straightSegment[0].AssignTrafficLight(straightTrafficLight);

        road.ConnectSegment(straightSegment[0], Lane.SEGMENT_POSITION.LAST, Road.DIRECTION.OppositeDirection(dirOutwardsIntersection));
    }

    // Add a right segment if there is a road on the right of the intersection
    public void AddRightSegment()
    {
        if(this.rightTurnSegment != null)
            return;

        String objectName = road.GetLane(Road.DIRECTION.OppositeDirection(dirOutwardsIntersection)).GetName() + "-Right";

        this.rightTurnSegment = new Segment[RIGHT_LENGTH];
        Segment oldSegment = null;
        for(int i = 0; i < RIGHT_LENGTH; ++i)
        {
            Segment segment = new Segment((RIGHT_LENGTH - 1) - i, objectName, dirOutwardsIntersection);
            segment.AddNextSegment(oldSegment);

            this.rightTurnSegment[i] = segment;
            oldSegment = segment;
        }
        Collections.reverse(Arrays.asList(rightTurnSegment));

        boolean isGreen = false;
        int startTick;
        switch (dirOutwardsIntersection)
        {
            case NORTH, SOUTH -> startTick = (TrafficLight.SWITCH_THRESHOLD + 2) * 2 + 2;
            case EAST, WEST -> startTick = 2;
            default -> startTick = 0;
        }

        rightTrafficLight = new TrafficLight(0, objectName, isGreen, startTick);
        rightTurnSegment[0].AssignTrafficLight(rightTrafficLight);

        road.ConnectSegment(rightTurnSegment[0], Lane.SEGMENT_POSITION.LAST, Road.DIRECTION.OppositeDirection(dirOutwardsIntersection));

    }

    // Add a left segment if there is a road on the left of the intersection
    public void AddLeftSegment()
    {
        if(this.leftTurnSegment != null)
            return;

        Segment segment = new Segment(0, road.GetLane(Road.DIRECTION.OppositeDirection(dirOutwardsIntersection)).GetName() + "-Left", dirOutwardsIntersection);
        road.ConnectSegment(segment, Lane.SEGMENT_POSITION.SECOND_LAST, Road.DIRECTION.OppositeDirection(dirOutwardsIntersection));
        this.leftTurnSegment = segment;
    }

    public Segment GetLastStraightSegment() { return straightSegment[straightSegment.length - 1]; }
    public Segment GetLastRightSegment() { return rightTurnSegment[rightTurnSegment.length - 1]; }
    public Segment GetLastLeftSegment() { return leftTurnSegment; }

    public void ConnectIncomingSegment(RoadIntersection otherRoad)
    {
        // Connect a road intersection with another road intersection to allows turning and crossing
        switch (dirOutwardsIntersection)
        {
            case NORTH:
                switch (otherRoad.dirOutwardsIntersection)
                {
                    case EAST -> { otherRoad.GetLastRightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST));}
                    case SOUTH -> { otherRoad.GetLastStraightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST)); }
                    case WEST -> { otherRoad.GetLastLeftSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.SECOND));}
                }
                break;
            case EAST:
                switch (otherRoad.dirOutwardsIntersection)
                {
                    case NORTH -> { otherRoad.GetLastLeftSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.SECOND));}
                    case SOUTH -> { otherRoad.GetLastRightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST)); }
                    case WEST -> { otherRoad.GetLastStraightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST));}
                }
                break;
            case SOUTH:
                switch (otherRoad.dirOutwardsIntersection)
                {
                    case NORTH -> { otherRoad.GetLastStraightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST));}
                    case EAST -> { otherRoad.GetLastLeftSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.SECOND)); }
                    case WEST -> { otherRoad.GetLastRightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST));}
                }
                break;
            case WEST:
                switch (otherRoad.dirOutwardsIntersection)
                {
                    case NORTH -> { otherRoad.GetLastRightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST));}
                    case EAST -> { otherRoad.GetLastStraightSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.FIRST)); }
                    case SOUTH -> { otherRoad.GetLastLeftSegment().AddNextSegment(road.GetLane(dirOutwardsIntersection).GetSegment(Lane.SEGMENT_POSITION.SECOND));}
                }
                break;
        }
    }

    public void UpdateTrafficLights()
    {
        if(straightTrafficLight != null)
            straightTrafficLight.AddTick();
        if(rightTrafficLight != null)
            rightTrafficLight.AddTick();
    }

    @Override
    public void SetPosition(Vector2 position)
    {
        super.SetPosition(position);
        road.SetPosition(new Vector2(position.x, position.y));
        for(int i = 0; i < straightSegment.length; ++i)
        {
            switch (dirOutwardsIntersection)
            {
                case NORTH -> {
                    Segment straight = straightSegment[i];
                    int xTranslate = scale.x * 3 / 4 - straight.scale.x / 2;
                    int yTranslate = GRAPHIC_SCALE * (straightSegment.length + i + 1) + (GRAPHIC_SCALE - straight.scale.y) / 2;
                    straight.SetPosition(position.Add(new Vector2(xTranslate, yTranslate)));
                }
                case EAST -> {
                    Segment straight = straightSegment[i];
                    int xTranslate = GRAPHIC_SCALE * (- i - 1) - (GRAPHIC_SCALE / 2 - straight.scale.x) / 2;
                    int yTranslate = scale.y * 3 / 4 - straight.scale.y / 2;
                    straight.SetPosition(position.Add(new Vector2(xTranslate, yTranslate)));
                }
                case SOUTH -> {
                    Segment straight = straightSegment[i];
                    int xTranslate = scale.x / 4 - straight.scale.x / 2;
                    int yTranslate = GRAPHIC_SCALE * (- i - 1) - (GRAPHIC_SCALE / 2 - straight.scale.y) / 2;
                    straight.SetPosition(position.Add(new Vector2(xTranslate, yTranslate)));
                }
                case WEST -> {
                    Segment straight = straightSegment[i];
                    int xTranslate = GRAPHIC_SCALE * (straightSegment.length + i + 1) + (GRAPHIC_SCALE - straight.scale.x) / 2;
                    int yTranslate = scale.y / 4 - straight.scale.y / 2;
                    straight.SetPosition(position.Add(new Vector2(xTranslate, yTranslate)));
                }
            }
        }
    }

    @Override
    public void AddObjectToFrame(TrafficFrame frame)
    {
        for(Segment straight: straightSegment)
        {
            straight.AddObjectToFrame(frame);
        }
        road.AddObjectToFrame(frame);
    }
}
