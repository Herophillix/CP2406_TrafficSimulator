package TrafficSimulator;

import Utility.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class Segment extends TrafficObject {
    private ArrayList<Vehicle> currentVehicles;
    private ArrayList<Segment> nextSegments;
    private TrafficLight trafficLight;

    public Segment(int id, String name, Road.DIRECTION direction) {
        super(id, name + "Segment");
        this.currentVehicles = new ArrayList<>();
        this.nextSegments = new ArrayList<>();

        switch (direction) {
            case NORTH, SOUTH -> {
                this.scale = new Vector2(GRAPHIC_SCALE / 2, GRAPHIC_SCALE).Divide(10).Multiply(8);
            }
            case EAST, WEST -> {
                this.scale = new Vector2(GRAPHIC_SCALE, GRAPHIC_SCALE / 2).Divide(10).Multiply(8);
            }
        }
    }

    @Override
    public void InitializeJPanelAttributes()
    {
        setBackground(Color.GRAY);
        setVisible(false);
    }

    public void AssignVehicle(Vehicle vehicle) {
        setBackground(vehicle.vehicleColor);
        setVisible(true);
        currentVehicles.add(vehicle);
    }

    public void RemoveVehicle(Vehicle vehicle) {
        setBackground(Color.GRAY);
        setVisible(false);
        currentVehicles.remove(vehicle);
    }

    public ArrayList<Segment> GetNextSegments() {
        return nextSegments;
    }

    public void AddNextSegment(Segment nextSegment) {
        if (nextSegments.contains(nextSegment) || nextSegment == null)
            return;
        nextSegments.add(nextSegment);
    }

    public void AssignTrafficLight(TrafficLight trafficLight)
    {
        this.trafficLight = trafficLight;
    }

    public boolean IsSegmentAvailable() {
        // Check if the segment can be travelled to
        if(trafficLight != null)
        {
            trafficLight.PrintInformation();
            return currentVehicles.size() == 0 && trafficLight.GetIsGreen();
        }
        else
            return currentVehicles.size() == 0;
    }
}
