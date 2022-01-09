package TrafficSimulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusTest {
    Road road = new Road(0, "TrafficSimulator.Road", 10, Road.DIRECTION.EAST);
    Segment[] segments = road.GetRandomSegments(3);
    Bus bus = new Bus(0, segments, 1);

    @Test
    void GetLength() { assertEquals(3, bus.GetLength());}

    @Test
    void GetBreadth() { assertEquals(0.5f, bus.GetBreadth());}

    @Test
    void GetSpeed() { assertEquals(1, bus.GetSpeed());}

    @Test
    void TestPosition() {
        Segment[] currentSegments = bus.GetCurrentSegments();
        for(int i = 0; i < segments.length; ++i)
        {
            assertEquals(segments[i], currentSegments[i]);
        }
    }

    @Test
    void TestMovement() {
        bus.Move();
        Segment[] currentSegments = bus.GetCurrentSegments();
        for(int i = 0; i < segments.length; ++i)
        {
            assertEquals(segments[i], currentSegments[i]);
        }
    }
}
