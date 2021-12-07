public class Vehicle {
    private int id;
    private float length;
    private float breadth;
    private int speed;

    private Vehicle()
    {
        this.id = 0;
        this.length = 1;
        this.breadth = 0.5f;
        this.speed = 1;
    }

    protected Vehicle(int id)
    {
        this();
        this.id = id;
    }

    public void SetID(int id) { this.id = id; }
    public int GetID() { return this.id; }

    public void SetLength(float length) { this.length = length; }
    public float GetLength() { return this.length; }

    public void SetBreadth(float breadth) { this.breadth = breadth; }
    public float GetBreadth() { return this.breadth; }

    public void SetSpeed(int speed) { this.speed = speed; }
    public int GetSpeed() { return this.speed; }
}