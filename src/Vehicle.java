public class Vehicle {
    protected int id;
    protected double length;
    protected double breadth;
    protected int speed;

    private Vehicle()
    {
        this.id = 0;
        this.length = 1;
        this.breadth = 0.5;
        this.speed = 1;
    }

    protected Vehicle(int id)
    {
        this();
        this.id = id;
    }
}