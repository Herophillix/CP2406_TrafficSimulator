import java.util.ArrayList;
import java.util.Random;

public abstract class Vehicle extends TrafficObject{
    private float length;
    private float breadth;
    private int speed;
    private Segment[] currentSegments;

    protected Vehicle(int id, String name)
    {
        super(id, name);
        this.length = 1;
        this.breadth = 0.5f;
        this.speed = 1;
        this.currentSegments = new Segment[1];
    }

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
            if(firstSegment == null) {
                return;
            }

            ArrayList<Segment> nextSegments = firstSegment.GetNextSegments();

            if(nextSegments.size() == 0)
            {
                for(int j = currentSegments.length - 1; j >= 0; --j)
                {
                    if(currentSegments[j] != null)
                    {
                        currentSegments[j].RemoveVehicle(this);
                        currentSegments[j] = null;
                        break;
                    }
                }
            }
            else
            {
                ArrayList<Segment> availableSegments = new ArrayList<>();
                for(Segment nextSegment : nextSegments)
                {
                    if(nextSegment.GetVehicleCount() == 0)
                    {
                        availableSegments.add(nextSegment);
                    }
                }
                if(availableSegments.size() > 0)
                {
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
    }
}