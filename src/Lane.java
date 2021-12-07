import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Lane {
    public enum SEGMENT_POSITION {
        FIRST,
        SECOND,
        SECOND_LAST,
        LAST
    }

    private Road.DIRECTION direction;
    protected ArrayList<Segment> roadSegments;

    public Lane(int length, Road.DIRECTION direction)
    {
        this.direction = direction;
        roadSegments = new ArrayList<>();
        Segment oldSegment = null;
        for(int i = 0; i < length; ++i)
        {
            Segment newSegment = new Segment();
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

    public void ConnectSegment(Segment nextSegment, SEGMENT_POSITION position)
    {
        switch (position)
        {
            case FIRST -> { this.roadSegments.get(0).AddNextSegment(nextSegment); }
            case SECOND -> { this.roadSegments.get(1).AddNextSegment(nextSegment); }
            case SECOND_LAST -> { this.roadSegments.get(roadSegments.size() - 2).AddNextSegment(nextSegment); }
            case LAST -> { this.roadSegments.get(roadSegments.size() - 1).AddNextSegment(nextSegment); }
        }
    }
}
