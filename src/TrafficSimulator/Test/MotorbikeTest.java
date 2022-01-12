package TrafficSimulator.Test;

import TrafficSimulator.Motorbike;
import TrafficSimulator.Road;
import TrafficSimulator.Segment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MotorbikeTest {
    Road road = new Road(0, "TrafficSimulator.Road", 10, Road.DIRECTION.EAST);
    Segment segment = road.GetRandomSegment();
    Motorbike motorbike = new Motorbike(0, segment, 1);

    @Test
    void GetLength() { assertEquals(0.5f, motorbike.GetLength());}

    @Test
    void GetBreadth() { assertEquals(0.5f, motorbike.GetBreadth());}

    @Test
    void GetSpeed() { assertEquals(1, motorbike.GetSpeed());}

    @Test
    void TestPosition() {assertEquals(segment, motorbike.GetCurrentSegments()[0]);}

    @Test
    void TestMovement() {
        motorbike.Move();
        assertEquals(segment.GetNextSegments().get(0), motorbike.GetCurrentSegments()[0]);
    }
}
