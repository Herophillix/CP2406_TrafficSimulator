import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static RoadManager roadManager;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        roadManager = new RoadManager();

        System.out.println("Welcome to Traffic Simulator 1.0");
        System.out.println("Let's create your first road!");
        Road currentRoad = CreateRoadFromUser(null);
        Car car = new Car(0, currentRoad.GetRandomSegment(), 1);

        System.out.println("What would you like to do next?");
        System.out.println("1)Add new road\n2)Add 4-Way Intersection\n3)Add 3-way Intersection");
        System.out.println("Input: ");
        int nextChoice = scanner.nextInt();
        switch (nextChoice)
        {
            case 1 -> currentRoad = CreateRoadFromUser(currentRoad);
            case 2 -> currentRoad = CreateFourWayIntersectionFromUser(currentRoad);
            case 3 -> currentRoad = CreateThreeWayIntersectionFromUser(currentRoad);
        }

        car.PrintInformation();
        for(int i = 0; i < 10; ++i)
        {
            car.Move();
            car.PrintInformation();
        }
    }

    public static Road CreateRoadFromUser(Road previousRoad)
    {
        int roadLength = -1;
        while (roadLength <= 2)
        {
            System.out.println("Road Length(3-15): ");
            roadLength = scanner.nextInt();
        }
        Road.DIRECTION direction = Road.DIRECTION.DIRECTION_COUNT;
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

    public static void OnRoadCreatedFromUser()
    {

    }

    public static Road CreateFourWayIntersectionFromUser(Road previousRoad)
    {
        IntersectionFourWay intersectionFourWay = roadManager.AddFourWayIntersection(previousRoad);
        System.out.println("Which direction would you like to go? ");
        Road.DIRECTION direction = GetUserDirection();
        return intersectionFourWay.GetRoad(direction);
    }

    public static Road CreateThreeWayIntersectionFromUser(Road previousRoad)
    {
        Road.DIRECTION[] directions = new Road.DIRECTION[3];
        directions[0] = previousRoad.GetDirection();
        System.out.println(previousRoad.GetDirection() + " has been added as direction 1");

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
                        isValid = false;
                }
                if(isValid)
                    directionToAdd = tempDirection;
            }
            directions[i] = directionToAdd;
        }

        IntersectionThreeWay intersectionThreeWay = roadManager.AddThreeWayIntersection(directions, previousRoad);
        Road nextRoad = null;
        while (nextRoad == null)
        {
            System.out.println("Which direction would you like to go? ");
            Road.DIRECTION direction = GetUserDirection();
            nextRoad = intersectionThreeWay.GetRoad(direction);
        }
        return nextRoad;
    }

    public static Road.DIRECTION GetUserDirection()
    {
        Road.DIRECTION direction = Road.DIRECTION.DIRECTION_COUNT;
        while (direction == Road.DIRECTION.DIRECTION_COUNT)
        {
            System.out.println("1)North\n2)East\n3)South\n4)West");
            System.out.println("Input: ");
            int directionChoice = scanner.nextInt();
            if (directionChoice >= Road.DIRECTION.NORTH.ordinal() + 1 && directionChoice <= Road.DIRECTION.WEST.ordinal() + 1)
                direction = Road.DIRECTION.values()[directionChoice - 1];
        }
        return direction;
    }
}