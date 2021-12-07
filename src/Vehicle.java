import java.util.ArrayList;
import java.util.Random;

public abstract class Vehicle {
    private int id;
    private float length;
    private float breadth;
    private int speed;
    private Segment[] currentSegments;

    private Vehicle()
    {
        this.id = 0;
        this.length = 1;
        this.breadth = 0.5f;
        this.speed = 1;
        this.currentSegments = new Segment[1];
    }

    protected Vehicle(int id)
    {
        this();
        this.id = id;
    }

    public void SetID(int id) { this.id = id; }
    public int GetID() { return this.id; }

    public void SetLength(float length)
    {
        this.length = length;
        this.currentSegments = new Segment[(int)Math.ceil(length)];
    }
    public float GetLength() { return this.length; }

    public void SetBreadth(float breadth) { this.breadth = breadth; }
    public float GetBreadth() { return this.breadth; }

    public void SetSpeed(int speed) { this.speed = speed; }
    public int GetSpeed() { return this.speed; }

    public void SetCurrentSegment(Segment currentSegment)
    {
        this.currentSegments = new Segment[] { currentSegment };
    }
    public void SetCurrentSegments(Segment[] currentSegments)
    {
        this.currentSegments = currentSegments;
    }

    public void Move()
    {
        for(int i = 0; i < speed; ++i)
        {
            Segment firstSegment = currentSegments[0];
            ArrayList<Segment> nextSegments = firstSegment.GetNextSegments();
            ArrayList<Segment> availableSegments = new ArrayList<>();
            for(Segment nextSegment : nextSegments)
            {
                if(nextSegment.GetVehicleCount() == 0)
                {
                    availableSegments.add(nextSegment);
                }
            }
            Random rand = new Random();
            Segment segmentToMove = availableSegments.get(rand.nextInt(availableSegments.size()));
            for(int j = 0; j < currentSegments.length; ++j)
            {
                Segment oldSegment = currentSegments[j];
                oldSegment.RemoveVehicle(this);
                currentSegments[j] = segmentToMove;
                currentSegments[j].AssignVehicle(this);
                segmentToMove = oldSegment;
            }
        }
    }
}