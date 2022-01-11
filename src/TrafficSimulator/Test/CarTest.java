package TrafficSimulator.Test;

import TrafficSimulator.Car;
import TrafficSimulator.Road;
import TrafficSimulator.Segment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarTest {
    Road road = new Road(0, "TrafficSimulator.Road", 10, Road.DIRECTION.EAST);
    Segment segment = road.GetRandomSegment();
    Car car = new Car(0, segment, 1);

    @Test
    void GetLength() { assertEquals(1, car.GetLength());}

    @Test
    void GetBreadth() { assertEquals(0.5f, car.GetBreadth());}

    @Test
    void GetSpeed() { assertEquals(1, car.GetSpeed());}

    @Test
    void TestPosition() {assertEquals(segment, car.GetCurrentSegments()[0]);}

    @Test
    void TestMovement() {
        car.Move();
        assertEquals(segment.GetNextSegments().get(0), car.GetCurrentSegments()[0]);
    }
}
