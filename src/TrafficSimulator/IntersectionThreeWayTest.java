package TrafficSimulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntersectionThreeWayTest {
    IntersectionThreeWay threeWay = new IntersectionThreeWay(0, new Road.DIRECTION[] { Road.DIRECTION.WEST, Road.DIRECTION.NORTH, Road.DIRECTION.SOUTH });
    Road road = new Road(0,"Road", 3, Road.DIRECTION.EAST);

    @Test
    public void TestRoadConnection() {
        threeWay.ConnectRoad(road);
        Segment roadFinalSegment = road.GetLane(Road.DIRECTION.EAST).GetSegment(Lane.SEGMENT_POSITION.LAST);
        assertTrue(roadFinalSegment.GetNextSegments().size() > 0);
    }
}
