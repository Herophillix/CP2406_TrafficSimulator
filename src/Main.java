import FileManagement.FileManager;
import TrafficSimulator.*;
import GUI.TrafficFrame;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static Scanner scanner;
    public static RoadManager roadManager;
    public static VehicleManager vehicleManager;
    public static FileManager fileManager;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        vehicleManager = new VehicleManager();
        fileManager = new FileManager();
        roadManager = new RoadManager(fileManager);

        new TrafficFrame();

        System.out.println("Welcome to Traffic Simulator 1.0");
        System.out.println("Would you like to use preset road?");
        boolean usePresetRoad = GetUserBoolean();
        if (usePresetRoad)
        {
            LoadPresetFile();
        }
        else
        {
            System.out.println("What would you like to do?");
            System.out.println("1)Create new roads\n2)Load roads from file");
            System.out.println("Input: ");
            int nextChoice = Integer.parseInt(scanner.nextLine());
            switch (nextChoice)
            {
                case 1 -> CreateFile();
                case 2 -> LoadFile();
            }
        }

        CreateVehicles();

        StartSimulation();
    }

    public static void LoadPresetFile()
    {
        ArrayList<String> instructions = fileManager.LoadDefaultFile();
        if(instructions.size() == 0)
        {
            System.out.println("Creating preset roads");
            instructions = new ArrayList<>();
            instructions.add("0-R-5-EAST");
            instructions.add("1-4-0");
            instructions.add("2-R-5-NORTH-1");
            instructions.add("3-R-5-EAST-1");
            instructions.add("4-R-5-SOUTH-1");
        }
        roadManager.CreateRoadFromFile(instructions);

        fileManager.SaveDefaultFile();
    }

    public static void LoadFile()
    {
        System.out.println("File name: ");
        String fileName = scanner.nextLine();
        ArrayList<String> instructions = fileManager.LoadFile(fileName + ".txt");
        if(instructions.size() == 0)
        {
            System.out.println("Using preset roads");
            LoadPresetFile();
        }
        else
        {
            roadManager.CreateRoadFromFile(instructions);
        }
        fileManager.SaveFile(fileName + ".txt");
    }

    public static void CreateFile()
    {
        CreateRoads();
        System.out.println("Would you like to save the road?");
        boolean saveRoad = GetUserBoolean();
        if(saveRoad)
        {
            System.out.println("File name: ");
            String fileName = scanner.nextLine();
            fileManager.SaveFile(fileName + ".txt");
        }
    }

    public static void CreateRoads()
    {
        System.out.println("Let's create your first road!");
        Road currentRoad = CreateRoadFromUser(null);

        boolean isAddingRoad = true;
        while(isAddingRoad)
        {
            System.out.println("What would you like to do next?");
            System.out.println("1)Add new road\n2)Add 4-Way Intersection\n3)Add 3-way Intersection\n4)Simulate");
            System.out.println("Input: ");
            int nextChoice = Integer.parseInt(scanner.nextLine());
            switch (nextChoice)
            {
                case 1 -> currentRoad = CreateRoadFromUser(currentRoad);
                case 2 -> currentRoad = CreateFourWayIntersectionFromUser(currentRoad);
                case 3 -> currentRoad = CreateThreeWayIntersectionFromUser(currentRoad);
                case 4 -> isAddingRoad = false;
            }
        }
    }

    public static Road CreateRoadFromUser(Road previousRoad)
    {
        int roadLength = -1;
        while (roadLength <= 2)
        {
            System.out.println("Road Length(3-15): ");
            roadLength = GetUserInt(3, 15);
        }

        Road.DIRECTION direction;
        // If user has already created previous road, use its direction
        if(previousRoad == null)
        {
            System.out.println("Direction of Road");
            direction = GetUserDirection();
        }
        else
        {
            direction = previousRoad.GetDirection();
        }
        return roadManager.AddRoad(roadLength, direction, previousRoad);
    }

    public static Road CreateFourWayIntersectionFromUser(Road previousRoad)
    {
        IntersectionFourWay intersectionFourWay = roadManager.AddFourWayIntersection(previousRoad);

        System.out.println("Would you like to add a road at the end of each exit? ");
        boolean addRoad = GetUserBoolean();
        if(addRoad)
        {
            for(int i = 0; i < Road.DIRECTION.DIRECTION_COUNT.ordinal(); ++i)
            {
                Road.DIRECTION tmpDirection = Road.DIRECTION.values()[i];
                Road intersectionRoad = intersectionFourWay.GetRoad(tmpDirection);
                if(intersectionRoad.GetConnectedRoad(tmpDirection) == null)
                {
                    System.out.println("Add road for " + tmpDirection.name() + " direction: ");
                    CreateRoadFromUser(intersectionRoad);
                }
            }
        }

        Road.DIRECTION previousDirection = Road.DIRECTION.OppositeDirection(previousRoad.GetDirection());
        Road.DIRECTION direction;
        System.out.println("Which direction would you like to go? ");
        direction = GetUserDirection();
        // Prevent user from going the same direction as they started
        while(direction == previousDirection)
        {
            System.out.println("You are unable to go back to your previous direction");
            System.out.println("Which direction would you like to go? ");
            direction = GetUserDirection();
        }

        Road toReturn = intersectionFourWay.GetRoad(direction);
        // Check whether the intersection has a road connected to it
        if(toReturn.GetConnectedRoad(direction) != null)
            toReturn = toReturn.GetConnectedRoad(direction);
        return toReturn;
    }

    public static Road CreateThreeWayIntersectionFromUser(Road previousRoad)
    {
        Road.DIRECTION[] directions = new Road.DIRECTION[3];
        directions[0] = Road.DIRECTION.OppositeDirection(previousRoad.GetDirection());
        System.out.println(directions[0] + " has been added as direction 1");

        for(int i = 1; i < directions.length; ++i)
        {
            Road.DIRECTION directionToAdd = Road.DIRECTION.DIRECTION_COUNT;
            while(directionToAdd == Road.DIRECTION.DIRECTION_COUNT)
            {
                System.out.println("Input direction " + (i + 1) + " of the intersection");
                Road.DIRECTION tempDirection = GetUserDirection();

                boolean isValid = true;
                for(int j = 0; j < i; ++j)
                {
                    if(directions[j] == tempDirection)
                    {
                        isValid = false;
                        break;
                    }
                }
                if(isValid)
                    directionToAdd = tempDirection;
            }
            directions[i] = directionToAdd;
        }

        IntersectionThreeWay intersectionThreeWay = roadManager.AddThreeWayIntersection(directions, previousRoad);

        System.out.println("Would you like to add a road at the end of each exit? ");
        boolean addRoad = GetUserBoolean();
        if(addRoad)
        {
            for (Road.DIRECTION tmpDirection : directions) {
                Road intersectionRoad = intersectionThreeWay.GetRoad(tmpDirection);
                if (intersectionRoad.GetConnectedRoad(tmpDirection) == null) {
                    System.out.println("Add road for " + tmpDirection.name() + " direction: ");
                    CreateRoadFromUser(intersectionRoad);
                }
            }
        }

        Road.DIRECTION previousDirection = Road.DIRECTION.OppositeDirection(previousRoad.GetDirection());

        boolean isValid = false;
        Road nextRoad = null;
        while(!isValid)
        {
            System.out.println("Which direction would you like to go? ");
            Road.DIRECTION direction = GetUserDirection();

            // Prevent user from going the same direction as they started
            if(direction == previousDirection)
            {
                System.out.println("You are unable to go back to your previous direction");
                continue;
            }

            // Check whether the direction that user wants to go is in the intersection
            nextRoad = intersectionThreeWay.GetRoad(direction);
            if (nextRoad == null)
            {
                System.out.println("This direction does not exist");
            }
            else
            {
                isValid = true;
                // Check whether the intersection has a road connected to it
                if(nextRoad.GetConnectedRoad(direction) != null)
                    nextRoad = nextRoad.GetConnectedRoad(direction);
            }
        }
        return nextRoad;
    }

    public static void CreateVehicles()
    {
        System.out.println("How many cars would you like to have?");
        int carCount = GetUserInt(1, 5);
        System.out.println("How many busses would you like to have?");
        int busCount = GetUserInt(0, 2);
        System.out.println("How many motorbikes would you like to have?");
        int motorbikeCount = GetUserInt(0, 3);

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

    public static void StartSimulation()
    {
        int time = 0;
        System.out.print("Set time scale in milliseconds: ");
        int speedOfSim = Integer.parseInt(scanner.nextLine());
        int cycleNumber = 1;
        while (vehicleManager.GetVehicleCount() > 0) {
            System.out.println();
            System.out.println("Cycle " + cycleNumber);
            roadManager.Simulate();
            vehicleManager.Simulate();

            time = time + 1;
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

    public static Road.DIRECTION GetUserDirection()
    {
        Road.DIRECTION direction = Road.DIRECTION.DIRECTION_COUNT;
        while (direction == Road.DIRECTION.DIRECTION_COUNT)
        {
            System.out.println("1)North\n2)East\n3)South\n4)West");
            System.out.println("Input: ");
            int directionChoice = Integer.parseInt(scanner.nextLine());
            if (directionChoice >= Road.DIRECTION.NORTH.ordinal() + 1 && directionChoice <= Road.DIRECTION.WEST.ordinal() + 1)
                direction = Road.DIRECTION.values()[directionChoice - 1];
        }
        return direction;
    }

    public static boolean GetUserBoolean()
    {
        boolean isInputValid = false;
        boolean userChoice = false;
        while(!isInputValid)
        {
            System.out.println("1)Yes\n2)No");
            System.out.println("Input: ");
            int userInput = Integer.parseInt(scanner.nextLine());
            switch (userInput)
            {
                case 1 -> {
                    isInputValid = true;
                    userChoice = true;
                }
                case 2 -> {
                    isInputValid = true;
                }
            }
        }
        return userChoice;
    }

    public static int GetUserInt(int min, int max)
    {
        System.out.println("Input(" + min + " - " + max + "): ");
        int userInput = Integer.parseInt(scanner.nextLine());
        while(userInput < min || userInput > max)
        {
            System.out.println("Input(" + min + " - " + max + "): ");
            userInput = Integer.parseInt(scanner.nextLine());
        }
        return userInput;
    }
}