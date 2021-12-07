import java.util.ArrayList;

public class RoadSegment {
    private ArrayList<Vehicle> currentVehicles;

    public RoadSegment()
    {
        this.currentVehicles = new ArrayList<>();
    }

    public void AssignVehicle(Vehicle vehicle)
    {
        double totalLength = 0.0;
        for(Vehicle currentVehicle: this.currentVehicles)
        {
            totalLength += currentVehicle.GetLength();
        }
        if(totalLength + vehicle.GetLength() <= 1.0f)
        {
            currentVehicles.add(vehicle);
        }
    }
}
