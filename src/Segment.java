import java.util.ArrayList;

public class Segment {
    private ArrayList<Vehicle> currentVehicles;
    private ArrayList<Segment> nextSegments;

    public Segment() {
        this.currentVehicles = new ArrayList<>();
        this.nextSegments = new ArrayList<>();
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

    public int GetVehicleCount() {
        return currentVehicles.size();
    }

    public ArrayList<Segment> GetNextSegments() {
        return nextSegments;
    }
}
