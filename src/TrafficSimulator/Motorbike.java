package TrafficSimulator;

public class Motorbike extends Vehicle {
    public Motorbike(int id, Segment currentSegment, int speed)
    {
        super(id, "Motorbike");
        SetLength(0.5f);
        SetCurrentSegment(currentSegment);
        SetSpeed(MathUtility.Clamp(speed, 1, 7));
    }
}
