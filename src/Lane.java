import java.util.ArrayList;

public class Lane {
    private Road.DIRECTION direction;
    private ArrayList<Segment> roadSegments;

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
    }

    public Road.DIRECTION GetDirection() { return direction; }

    public void Connect(Segment nextSegment, int position)
    {
        switch (position)
        {
            case 0 -> { this.roadSegments.get(0).AddNextSegment(nextSegment); }
            case 1 -> { this.roadSegments.get(1).AddNextSegment(nextSegment); }
            case 2 -> { this.roadSegments.get(roadSegments.size() - 2).AddNextSegment(nextSegment); }
            case 3 -> { this.roadSegments.get(roadSegments.size() - 1).AddNextSegment(nextSegment); }
        }
    }
}
