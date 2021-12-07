public class Car extends Vehicle {
    public Car(int id, int speed)
    {
        super(id);
        SetSpeed(MathUtility.Clamp(speed, 1, 5));
    }
}
