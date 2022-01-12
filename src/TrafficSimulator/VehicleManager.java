package TrafficSimulator;

import java.util.ArrayList;

public class VehicleManager {
    private ArrayList<Vehicle> vehicles;

    public VehicleManager()
    {
        vehicles = new ArrayList<>();
    }

    public int GetVehicleCount() { return vehicles.size(); }

    public Car AddCar(Segment currentSegment, int speed)
    {
        Car car = new Car(vehicles.size(), currentSegment, speed);
        vehicles.add(car);
        return car;
    }

    public Bus AddBus(Segment[] currentSegments, int speed)
    {
        Bus bus = new Bus(vehicles.size(), currentSegments, speed);
        vehicles.add(bus);
        return bus;
    }

    public Motorbike AddMotorbike(Segment currentSegment, int speed)
    {
        Motorbike motorbike = new Motorbike(vehicles.size(), currentSegment, speed);
        vehicles.add(motorbike);
        return motorbike;
    }

    public void Simulate()
    {
        for(int i = 0; i < vehicles.size();)
        {
            Vehicle vehicle = vehicles.get(i);
            if(!vehicle.Move())
            {
                vehicles.remove(vehicle);
            }
            else
            {
                //vehicle.PrintInformation();
                ++i;
            }
        }
    }
}
