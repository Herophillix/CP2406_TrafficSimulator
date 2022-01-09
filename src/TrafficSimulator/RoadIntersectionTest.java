package TrafficSimulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoadIntersectionTest {
    RoadIntersection roadIntersection = new RoadIntersection(Road.DIRECTION.EAST, "roadIntersection");

    @Test
    public void TestStraightSegment()
    {
        roadIntersection.AddStraightSegment();
        assertNotNull(roadIntersection.GetLastStraightSegment());
    }

    @Test
    public void TestRightSegment()
    {
        roadIntersection.AddRightSegment();
        assertNotNull(roadIntersection.GetLastRightSegment());
    }
    @Test
    public void TestLeftSegment()
    {
        roadIntersection.AddLeftSegment();
        assertNotNull(roadIntersection.GetLastLeftSegment());
    }

    @Test
    public void TestRoad()
    {
        assertNotNull(roadIntersection.GetRoad());
    }
}
