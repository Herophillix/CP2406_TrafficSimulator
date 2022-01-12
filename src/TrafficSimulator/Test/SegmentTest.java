package TrafficSimulator.Test;

import TrafficSimulator.Car;
import TrafficSimulator.Segment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SegmentTest {
    Segment segment1 = new Segment(0, "TrafficSimulator.Segment", null);
    Segment segment2 = new Segment(1, "TrafficSimulator.Segment", null);
    Car car = new Car(0, segment1, 1);

    @Test
    public void TestSegmentAvailability() {
        assertFalse(segment1.IsSegmentAvailable());
        assertTrue(segment2.IsSegmentAvailable());
    }

    @Test
    public void TestSegmentConnection() {
        segment1.AddNextSegment(segment2);
        assertEquals(segment2, segment1.GetNextSegments().get(0));
    }

    @Test
    public void TestVehicleMovementInSegment() {
        assertFalse(segment1.IsSegmentAvailable());
        car.Move();
        assertTrue(segment1.IsSegmentAvailable());
    }
}
