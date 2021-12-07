public class Bus extends Vehicle {
    public Bus(int id, int speed)
    {
        super(id);
        this.length = this.length * 3;
        this.speed = MathUtility.Clamp(speed, 1, 3);
    }
}
