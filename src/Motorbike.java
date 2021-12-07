public class Motorbike extends Vehicle {
    public Motorbike(int id, int speed)
    {
        super(id);
        this.length = this.length * 0.5;
        this.speed = MathUtility.Clamp(speed, 1, 7);
    }
}
