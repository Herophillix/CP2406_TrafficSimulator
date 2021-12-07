public class Bus extends Vehicle {
    public Bus(int id, Segment[] currentSegments, int speed)
    {
        super(id, "Bus");
        SetLength(3);
        SetCurrentSegments(currentSegments);
        SetSpeed(MathUtility.Clamp(speed, 1, 3));
    }
}
