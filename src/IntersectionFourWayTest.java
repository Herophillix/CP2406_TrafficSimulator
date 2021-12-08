import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntersectionFourWayTest {
    IntersectionFourWay fourWay = new IntersectionFourWay(0);
    Road road = new Road(0,"Road", 3, Road.DIRECTION.EAST);

    @Test
    public void TestRoadConnection() {
        fourWay.ConnectRoad(road);
        Segment roadFinalSegment = road.GetLane(Road.DIRECTION.EAST).GetSegment(Lane.SEGMENT_POSITION.LAST);
        assertTrue(roadFinalSegment.GetNextSegments().size() > 0);
    }
}
