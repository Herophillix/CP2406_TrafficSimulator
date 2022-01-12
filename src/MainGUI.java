import GUI.*;
import FileManagement.*;
import TrafficSimulator.Road;
import TrafficSimulator.RoadManager;
import TrafficSimulator.Segment;
import TrafficSimulator.VehicleManager;
import java.io.*;

import java.util.ArrayList;

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

        InitializeMenuCallbacks();
        InitializeFileCallbacks();
        InitializeSimulationCallbacks();

        frame.ShowFrame();
    }

    // File MenuBar
    private static void InitializeMenuCallbacks()
    {
        frame.menuBar.AddNewFileButtonCallback(e -> CreateNewRoad());
        frame.menuBar.AddLoadPresetButtonCallback(e -> CreatePresetRoad());
    }

    private static void InitializeFileCallbacks()
    {
        frame.fileChooser.AddOnFileOpenedCallback(e -> OnFileLoaded(e.getSource()));
        frame.fileChooser.AddOnFileSavedCallback(e -> SaveRoad(e.getSource()));
    }

    private static void CreateNewRoad()
    {
        roadManager.AddRoad(5, Road.DIRECTION.EAST, null);
        roadManager.AddRoadToFrame(frame);
    }

    private static void SaveRoad(Object desiredFile)
    {
        try
        {
            fileManager.SaveFile((File)desiredFile);
        } catch (Exception e) { };
    }

    private static void OnFileLoaded(Object loadedFile)
    {
        try
        {
            File file = (File)loadedFile;
            ArrayList<String> instructions = fileManager.LoadFile(file);
            roadManager.CreateRoadFromFile(instructions);
            roadManager.AddRoadToFrame(frame);
        } catch (Exception e) { };
    }

    private static void CreatePresetRoad()
    {
        ArrayList<String> instructions = new ArrayList<>();
        instructions.add("[0]:[R-5-EAST]");
        instructions.add("[1]:[4]:[0-WEST]");
        instructions.add("[2]:[R-5-NORTH]:[1]");
        instructions.add("[3]:[R-5-EAST]:[1]");
        instructions.add("[4]:[R-5-SOUTH]:[1]");

        roadManager.CreateRoadFromFile(instructions);
        roadManager.AddRoadToFrame(frame);
    }

    //Simulation MenuBar
    private static void InitializeSimulationCallbacks()
    {
        frame.AddStartSimulationCallback(e -> StartSimulation(e.getSource()));
    }

    private static void StartSimulation(Object simulationInformation)
    {
        try
        {
            ArrayList<Integer> information = (ArrayList<Integer>)simulationInformation;
            CreateVehicles(information.get(0), information.get(1), information.get(2));
            Simulate(information.get(3));
        } catch (Exception e)
        {
            System.out.println("Cannot");
        }
    }

    private static void CreateVehicles(int carCount, int busCount, int motorbikeCount)
    {
        for(int i = 0; i < busCount; ++i)
        {
            Segment[] randomSegments = roadManager.GetRandomSegments(3);
            if(randomSegments == null)
            {
                System.out.println("Unable to create bus");
                break;
            }
            vehicleManager.AddBus(randomSegments, 1);
        }

        for(int i = 0; i < carCount; ++i)
        {
            Segment randomSegment = roadManager.GetRandomSegment();
            if(randomSegment == null)
            {
                System.out.println("Unable to create car");
                break;
            }
            vehicleManager.AddCar(randomSegment, 1);
        }

        for(int i = 0; i < motorbikeCount; ++i)
        {
            Segment randomSegment = roadManager.GetRandomSegment();
            if(randomSegment == null)
            {
                System.out.println("Unable to create motorbike");
                break;
            }
            vehicleManager.AddMotorbike(randomSegment, 1);
        }
    }

    private static void Simulate(int speedOfSim)
    {
        int cycleNumber = 1;
        while (vehicleManager.GetVehicleCount() > 0) {
            roadManager.Simulate();
            vehicleManager.Simulate();

            //System.out.println(time + " Seconds have passed.\n");
            ++cycleNumber;
            try {
                Thread.sleep(speedOfSim); // set speed of simulation.

            } catch (InterruptedException sim) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Simulation over");
    }
}
