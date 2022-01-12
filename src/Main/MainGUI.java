package Main;

import GUI.*;
import FileManagement.*;
import TrafficSimulator.*;

import java.io.*;
import javax.swing.*;
import java.awt.event.*;

import java.util.ArrayList;

public class MainGUI {
    public static TrafficFrame frame;
    public static RoadManager roadManager;
    public static VehicleManager vehicleManager;
    public static FileManager fileManager;
    private static Timer timer;
    public static boolean inSimulation = false;

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

    private static void Reset()
    {
        if(fileManager.saveIndex > 0)
        {
            frame.setVisible(false);
            frame.dispose();

            frame = new TrafficFrame();
            vehicleManager = new VehicleManager();
            fileManager = new FileManager();
            roadManager = new RoadManager(fileManager);

            InitializeMenuCallbacks();
            InitializeFileCallbacks();
            InitializeSimulationCallbacks();

            frame.menuBar.ShowButtonsWithFile();
            frame.ShowFrame();
        }
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
        Reset();
        roadManager.AddRoad(5, Road.DIRECTION.EAST, null);
        roadManager.AddRoadToFrame(frame);
    }

    private static void SaveRoad(Object desiredFile)
    {
        try
        {
            fileManager.SaveFile((File)desiredFile);
            frame.optionPane.ShowMessageDialog("File saved", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) { };
    }

    private static void OnFileLoaded(Object loadedFile)
    {
        try
        {
            Reset();
            File file = (File)loadedFile;
            ArrayList<String> instructions = fileManager.LoadFile(file);
            roadManager.CreateRoadFromFile(instructions);
            roadManager.AddRoadToFrame(frame);
        } catch (Exception e) { };
    }

    private static void CreatePresetRoad()
    {
        Reset();
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

    private static int cycle;

    private static void Simulate(int speedOfSim)
    {
        inSimulation = true;
        cycle = 0;
        timer = new Timer(speedOfSim, e -> {
            System.out.println("Cycle " + ++cycle);
            if (vehicleManager.GetVehicleCount() > 0) {
                roadManager.Simulate();
                vehicleManager.Simulate();
            }
            else
            {
                inSimulation = false;
                timer.stop();
                System.out.println("Simulation over");
                frame.optionPane.ShowMessageDialog("Simulation over", JOptionPane.PLAIN_MESSAGE);
            }
            frame.repaint();
            System.out.println();
        });
        timer.start();
    }

    public static void PromptAddRoadType(Road toConnect)
    {
        if(inSimulation)
            return;

        String[] choices = new String[3];
        choices[0] = "Road";
        choices[1] = "4-Way Intersection";
        choices[2] = "3-Way Intersection";

        int userChoice = frame.optionPane.ShowOptionDialog("What type of road would you like to add?", choices, choices[0]);

        switch (userChoice)
        {
            case 0 -> PromptAddRoad(toConnect);
            case 1 -> PromptAdd4WayIntersection(toConnect);
            case 2 -> PromptAdd3WayIntersection(toConnect);
        }
    }

    public static void PromptAddRoad(Road toConnect)
    {
        int roadLength = frame.optionPane.GetUserInt("Input road length", 3, 15);
        if(roadLength > 1)
        {
            Road newRoad = roadManager.AddRoad(roadLength, toConnect.GetDirection(), toConnect);
            newRoad.AddObjectToFrame(frame);
            frame.repaint();
            frame.optionPane.ShowMessageDialog("Road added", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void PromptAdd4WayIntersection(Road toConnect)
    {
        IntersectionFourWay newFourWay = roadManager.AddFourWayIntersection(toConnect);
        newFourWay.AddObjectToFrame(frame);
        frame.repaint();
        frame.optionPane.ShowMessageDialog("4-Way intersection added", JOptionPane.PLAIN_MESSAGE);
    }

    public static void PromptAdd3WayIntersection(Road toConnect)
    {
        Road.DIRECTION usedDirection = Road.DIRECTION.OppositeDirection(toConnect.GetDirection());
        ArrayList<Road.DIRECTION> directions = new ArrayList<>();
        for(Road.DIRECTION tmpDirection : Road.DIRECTION.values())
        {
            if(!(tmpDirection == usedDirection || tmpDirection == Road.DIRECTION.DIRECTION_COUNT))
            {
                directions.add(tmpDirection);
            }
        }

        int userChoice = frame.optionPane.ShowOptionDialog("Choose Direction 1 of the intersection", directions.toArray(), directions.get(0));
        if (userChoice != -1)
        {
            Road.DIRECTION otherDirection = directions.get(userChoice);
            directions.remove(userChoice);

            userChoice = frame.optionPane.ShowOptionDialog("Choose Direction 2 of the intersection", directions.toArray(), directions.get(0));

            if(userChoice != -1)
            {
                Road.DIRECTION[] newDirections = new Road.DIRECTION[3];
                newDirections[0] = usedDirection;
                newDirections[1] = otherDirection;
                newDirections[2] = directions.get(userChoice);

                IntersectionThreeWay newThreeWay = roadManager.AddThreeWayIntersection(newDirections, toConnect);
                newThreeWay.AddObjectToFrame(frame);
                frame.repaint();
                frame.optionPane.ShowMessageDialog("3-Way intersection added", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}
