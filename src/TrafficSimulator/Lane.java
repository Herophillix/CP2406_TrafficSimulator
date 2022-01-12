package TrafficSimulator;

import GUI.TrafficFrame;
import Utility.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Lane extends TrafficObject{
    public enum SEGMENT_POSITION {
        FIRST,
        SECOND,
        SECOND_LAST,
        LAST
    }

    private Road.DIRECTION direction;
    protected ArrayList<Segment> roadSegments;
    private Road connectedRoad;

    public Lane(int id, String name, int length, Road.DIRECTION direction)
    {
        super(id, name + "-" + direction.name() + "_Lane");
        this.direction = direction;

        switch (direction) {
            case NORTH, SOUTH -> {
                this.scale = new Vector2(GRAPHIC_SCALE / 2, length * GRAPHIC_SCALE);
            }
            case EAST, WEST -> {
                this.scale = new Vector2(length * GRAPHIC_SCALE, GRAPHIC_SCALE / 2);
            }
        }

        // Create segments based on the length of the road
        roadSegments = new ArrayList<>();
        Segment oldSegment = null;
        for(int i = 0; i < length; ++i)
        {
            // Connect the segments to one another
            Segment newSegment = new Segment((length - 1) - i, GetName() + "-", direction);
            newSegment.AddNextSegment(oldSegment);

            this.roadSegments.add(newSegment);

            oldSegment = newSegment;
        }
        Collections.reverse(roadSegments);
    }

    public Road.DIRECTION GetDirection() { return direction; }

    public Segment GetSegment(SEGMENT_POSITION position)
    {
        switch (position)
        {
            case FIRST -> { return roadSegments.get(0); }
            case SECOND -> { return roadSegments.get(1); }
            case SECOND_LAST -> { return roadSegments.get(roadSegments.size() - 2); }
            case LAST -> { return roadSegments.get(roadSegments.size() - 1); }
            default -> { return  null; }
        }
    }

    public Segment[] GetSegments(SEGMENT_POSITION position, int count)
    {
        Segment[] toReturn = new Segment[count];
        for(int i = 0; i < count; ++i)
        {
            toReturn[i] = roadSegments.get(i);
        }
        Collections.reverse(Arrays.asList(toReturn));
        return toReturn;
    }

    public void ConnectSegment(Road road, Segment nextSegment, SEGMENT_POSITION position)
    {
        // Connect a segment to another road
        connectedRoad = road == null ? connectedRoad : road;
        switch (position)
        {
            case FIRST -> { this.roadSegments.get(0).AddNextSegment(nextSegment); }
            case SECOND -> { this.roadSegments.get(1).AddNextSegment(nextSegment); }
            case SECOND_LAST -> { this.roadSegments.get(roadSegments.size() - 2).AddNextSegment(nextSegment); }
            case LAST -> { this.roadSegments.get(roadSegments.size() - 1).AddNextSegment(nextSegment); }
        }
    }

    public Road GetConnectedRoad() { return connectedRoad; }

    @Override
    public void SetJPanelBounds(Vector2 position, Vector2 scale)
    {
        switch (direction)
        {
            case NORTH:
            {
                for(int i = 0; i < roadSegments.size(); ++i)
                {
                    Segment roadSegment = roadSegments.get(i);
                    int xTranslate = scale.x - roadSegment.scale.x / 2;
                    int yTranslate = (roadSegments.size() - 1 - i) * (scale.y / roadSegments.size()) + ((scale.y / roadSegments.size()) - roadSegment.scale.y) / 2;
                    roadSegment.SetPosition(this.position.Add(new Vector2(xTranslate, yTranslate)));
                }
                break;
            }
            case EAST:
            {
                for(int i = 0; i < roadSegments.size(); ++i)
                {
                    Segment roadSegment = roadSegments.get(i);
                    int xTranslate = i * (scale.x / roadSegments.size()) + ((scale.x / roadSegments.size()) - roadSegment.scale.x) / 2;
                    int yTranslate = scale.y - roadSegment.scale.y / 2;
                    roadSegment.SetPosition(this.position.Add(new Vector2(xTranslate, yTranslate)));
                }
                break;
            }
            case SOUTH:
            {
                for(int i = 0; i < roadSegments.size(); ++i)
                {
                    Segment roadSegment = roadSegments.get(i);
                    int xTranslate = scale.x - roadSegment.scale.x / 2;
                    int yTranslate = i * (scale.y / roadSegments.size()) + ((scale.y / roadSegments.size()) - roadSegment.scale.y) / 2;
                    roadSegment.SetPosition(this.position.Add(new Vector2(xTranslate, yTranslate)));
                }
                break;
            }
            case WEST:
            {
                for(int i = 0; i < roadSegments.size(); ++i)
                {
                    Segment roadSegment = roadSegments.get(i);
                    int xTranslate = (roadSegments.size() - 1 - i) * (scale.x / roadSegments.size()) + ( (scale.x / roadSegments.size()) - roadSegment.scale.x) / 2;
                    int yTranslate = scale.y - roadSegment.scale.y / 2;
                    roadSegment.SetPosition(this.position.Add(new Vector2(xTranslate, yTranslate)));
                }
                break;
            }
        }
    }

    @Override
    public void AddObjectToFrame(TrafficFrame frame)
    {
        for(Segment segment: roadSegments)
        {
            segment.AddObjectToFrame(frame);
        }
    }
}
