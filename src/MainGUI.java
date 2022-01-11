import GUI.*;
import FileManagement.*;
import TrafficSimulator.RoadManager;
import TrafficSimulator.VehicleManager;

import java.util.Scanner;

public class MainGUI {
    public static TrafficFrame frame;
    public static RoadManager roadManager;
    public static VehicleManager vehicleManager;
    public static FileManager fileManager;

    public static void main(String[] args)
    {
        frame = new TrafficFrame();
        vehicleManager = new VehicleManager();
        fileManager = new FileManager();
        roadManager = new RoadManager(fileManager);
    }
}
