package TrafficSimulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoadTest {
    Road road = new Road(0, "TrafficSimulator.Road", 10, Road.DIRECTION.EAST);
    Road road2 = new Road(1, "TrafficSimulator.Road", 10, Road.DIRECTION.EAST);

    @Test
    public void TestLength() { assertEquals(10, road.GetLength()); }

    @Test
    public void TestDirection() { assertEquals(Road.DIRECTION.EAST, road.GetDirection()); }

    @Test
    public void TestConnectedRoad() {
        road.Connect(road2);
        assertEquals(road2, road.GetConnectedRoad(Road.DIRECTION.EAST));
    }

    @Test
    public void TestRandomSegment() {
        assertNotNull(road.GetRandomSegment());
        assertNotNull(road.GetRandomSegments(3));
    }
}
