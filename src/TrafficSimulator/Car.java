package TrafficSimulator;

public class Car extends Vehicle {
    public Car(int id, Segment currentSegment, int speed)
    {
        super(id, "TrafficSimulator.Car");
        SetCurrentSegment(currentSegment);
        SetSpeed(MathUtility.Clamp(speed, 1, 5));
    }
}
