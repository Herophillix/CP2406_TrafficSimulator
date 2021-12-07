import java.util.ArrayList;

public class Lane {
    private Road.DIRECTION direction;
    private ArrayList<RoadSegment> roadSegments;

    public Lane(int length, Road.DIRECTION direction)
    {
        this.direction = direction;
        roadSegments = new ArrayList<>();
        for(int i = 0; i < length; ++i)
        {
            this.roadSegments.add(new RoadSegment());
        }
    }
}
