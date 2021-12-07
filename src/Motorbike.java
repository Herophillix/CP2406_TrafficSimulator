public class Motorbike extends Vehicle {
    public Motorbike(int id, int speed)
    {
        super(id);
        SetLength(0.5f);
        SetSpeed(MathUtility.Clamp(speed, 1, 7));
    }
}
