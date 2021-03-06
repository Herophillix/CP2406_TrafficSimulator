package TrafficSimulator;

import java.util.ArrayList;
import java.util.Random;
import java.awt.*;

public abstract class Vehicle extends TrafficObject{
    private float length;
    private float breadth;
    private int speed;
    private Segment[] currentSegments;

    public Color vehicleColor;

    protected Vehicle(int id, String name)
    {
        super(id, name);
        this.length = 1;
        this.breadth = 0.5f;
        this.speed = 1;
        this.currentSegments = new Segment[1];
        Random rand = new Random();
        vehicleColor = new Color(rand.nextInt(225) + 30, rand.nextInt(225) + 30, rand.nextInt(225) + 30);
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
        currentSegment.AssignVehicle(this);
    }
    public void SetCurrentSegments(Segment[] currentSegments)
    {
        this.currentSegments = currentSegments;
        for(Segment currentSegment: currentSegments)
        {
            currentSegment.AssignVehicle(this);
        }
    }
    public Segment[] GetCurrentSegments() { return currentSegments; }

    public boolean Move()
    {
        for(int i = 0; i < speed; ++i)
        {
            Segment firstSegment = currentSegments[0];

            // Check if vehicle is out of the boundaries
            if(firstSegment == null) {
                return false;
            }

            ArrayList<Segment> nextSegments = firstSegment.GetNextSegments();

            // Check if there is nowhere else to go
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

                // Check availability of the segments
                for(Segment nextSegment : nextSegments)
                {
                    if(nextSegment.IsSegmentAvailable())
                    {
                        availableSegments.add(nextSegment);
                    }
                }
                if(availableSegments.size() > 0)
                {
                    // Select random segment out of the available segments
                    Random rand = new Random();
                    Segment segmentToMove = availableSegments.get(rand.nextInt(availableSegments.size()));

                    // Move the vehicle
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
        return true;
    }

    @Override
    public void PrintInformation()
    {
        Segment segment = currentSegments[0];
        if(segment != null)
        {
            System.out.println(GetInfo() + " on " + segment.GetInfo());
        }
    }
}