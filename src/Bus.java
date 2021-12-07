public class Bus extends Vehicle {
    public Bus(int id, int speed)
    {
        super(id);
        SetLength(3);
        SetSpeed(MathUtility.Clamp(speed, 1, 3));
    }
}
