import java.util.ArrayList;

public class Segment extends TrafficObject {
    private ArrayList<Vehicle> currentVehicles;
    private ArrayList<Segment> nextSegments;
    private TrafficLight trafficLight;

    public Segment(int id, String name) {
        super(id, name + "Segment");
        this.currentVehicles = new ArrayList<>();
        this.nextSegments = new ArrayList<>();
    }

    public void AssignTrafficLight(TrafficLight trafficLight)
    {
        this.trafficLight = trafficLight;
    }

    public void AssignVehicle(Vehicle vehicle) {
        double totalLength = 0.0;
        for (Vehicle currentVehicle : this.currentVehicles) {
            totalLength += currentVehicle.GetLength();
        }
        if (totalLength + vehicle.GetLength() <= 1.0f) {
            currentVehicles.add(vehicle);
        }
    }

    public void RemoveVehicle(Vehicle vehicle) {
        currentVehicles.remove(vehicle);
    }

    public void AddNextSegment(Segment nextSegment) {
        if (nextSegments.contains(nextSegment) || nextSegment == null)
            return;
        nextSegments.add(nextSegment);
    }

    public boolean IsSegmentAvailable() {
        if(trafficLight != null)
        {
            //trafficLight.PrintInformation();
            return currentVehicles.size() == 0 && trafficLight.GetIsGreen();
        }
        else
            return currentVehicles.size() == 0;
    }

    public ArrayList<Segment> GetNextSegments() {
        return nextSegments;
    }
}
