public class Car extends Vehicle {
    public Car(int id, int speed)
    {
        super(id);
        this.speed = MathUtility.Clamp(speed, 1, 5);
    }
}
