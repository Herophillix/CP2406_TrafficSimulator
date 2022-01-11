package TrafficSimulator;

import java.util.ArrayList;
import java.util.Random;

import FileManagement.FileManager;
import GUI.TrafficFrame;
import Utility.Vector2;
import java.awt.*;

public class RoadManager {
    private ArrayList<Road> roads;
    private ArrayList<Intersection> intersections;
    private FileManager fileManager;

    public RoadManager(FileManager fileManager)
    {
        roads = new ArrayList<>();
        intersections = new ArrayList<>();
        this.fileManager = fileManager;
    }

    public Road AddRoad(int length, Road.DIRECTION direction, Road toConnect)
    {
        Road toReturn = new Road(roads.size(), "", length, direction);
        if(toConnect != null)
        {
            switch (direction)
            {
                case NORTH, EAST -> {
                    toConnect.Connect(toReturn);
                }
                case SOUTH, WEST -> {
                    toReturn.Connect(toConnect);
                }
            }
            //toConnect.Connect(toReturn);
            switch (direction)
            {
                case NORTH -> toReturn.SetPosition(toConnect.position.Minus(new Vector2(0, toReturn.scale.y)));
                case EAST -> toReturn.SetPosition(toConnect.position.Add(new Vector2(toConnect.scale.x, 0)));
                case SOUTH -> toReturn.SetPosition(toConnect.position.Add(new Vector2(0, toConnect.scale.y)));
                case WEST -> toReturn.SetPosition(toConnect.position.Minus(new Vector2(toReturn.scale.x, 0)));
            }
        }
        else
        {
            toReturn.SetPosition(new Vector2(TrafficFrame.STARTING_X, TrafficFrame.STARTING_Y));
        }
        roads.add(toReturn);
        fileManager.AddRoadToBuffer(toReturn, toConnect);
        return toReturn;
    }

    public IntersectionFourWay AddFourWayIntersection(Road toConnect)
    {
        IntersectionFourWay toReturn = new IntersectionFourWay(intersections.size());
        if(toConnect != null)
        {
            toReturn.ConnectRoad(toConnect);

            Vector2 squareScale = toReturn.scale;

            // Direction of where the road is coming from
            Road.DIRECTION direction = toConnect.GetDirection();

            switch (direction)
            {
                case NORTH:
                {
                    Road connectingRoadFromIntersection = toReturn.GetRoad(Road.DIRECTION.OppositeDirection(direction));
                    Vector2 valueToMove = new Vector2(0, connectingRoadFromIntersection.scale.y + squareScale.y);
                    toReturn.SetPosition(toConnect.position.Minus(valueToMove));
                    break;
                }
                case EAST:
                {
                    Road connectingRoadFromIntersection = toReturn.GetRoad(Road.DIRECTION.OppositeDirection(direction));
                    Vector2 valueToMove = new Vector2(toConnect.scale.x + connectingRoadFromIntersection.scale.x, 0);
                    toReturn.SetPosition(toConnect.position.Add(valueToMove));
                    break;
                }
                case SOUTH:
                {
                    Road connectingRoadFromIntersection = toReturn.GetRoad(Road.DIRECTION.OppositeDirection(direction));
                    Vector2 valueToMove = new Vector2(0, toConnect.scale.y + connectingRoadFromIntersection.scale.y);
                    toReturn.SetPosition(toConnect.position.Add(valueToMove));
                    break;
                }
                case WEST:
                {
                    Road connectingRoadFromIntersection = toReturn.GetRoad(Road.DIRECTION.OppositeDirection(direction));
                    Vector2 valueToMove = new Vector2(connectingRoadFromIntersection.scale.x + squareScale.x, 0);
                    toReturn.SetPosition(toConnect.position.Minus(valueToMove));
                    break;
                }
            }
        }
        intersections.add(toReturn);
        fileManager.AddFourWayIntersectionToBuffer(toReturn, toConnect);
        return toReturn;
    }

    public IntersectionThreeWay AddThreeWayIntersection(Road.DIRECTION[] directions, Road toConnect)
    {
        IntersectionThreeWay toReturn = new IntersectionThreeWay(intersections.size(), directions);
        if(toConnect != null)
        {
            toReturn.ConnectRoad(toConnect);

            Vector2 squareScale = toReturn.scale;

            // Direction of where the road is coming from
            Road.DIRECTION direction = toConnect.GetDirection();

            switch (direction)
            {
                case NORTH:
                {
                    Road connectingRoadFromIntersection = toReturn.GetRoad(Road.DIRECTION.OppositeDirection(direction));
                    Vector2 valueToMove = new Vector2(0, connectingRoadFromIntersection.scale.y + squareScale.y);
                    toReturn.SetPosition(toConnect.position.Minus(valueToMove));
                    break;
                }
                case EAST:
                {
                    Road connectingRoadFromIntersection = toReturn.GetRoad(Road.DIRECTION.OppositeDirection(direction));
                    Vector2 valueToMove = new Vector2(toConnect.scale.x + connectingRoadFromIntersection.scale.x, 0);
                    toReturn.SetPosition(toConnect.position.Add(valueToMove));
                    break;
                }
                case SOUTH:
                {
                    Road connectingRoadFromIntersection = toReturn.GetRoad(Road.DIRECTION.OppositeDirection(direction));
                    Vector2 valueToMove = new Vector2(0, toConnect.scale.y + connectingRoadFromIntersection.scale.y);
                    toReturn.SetPosition(toConnect.position.Add(valueToMove));
                    break;
                }
                case WEST:
                {
                    Road connectingRoadFromIntersection = toReturn.GetRoad(Road.DIRECTION.OppositeDirection(direction));
                    Vector2 valueToMove = new Vector2(connectingRoadFromIntersection.scale.x + squareScale.x, 0);
                    toReturn.SetPosition(toConnect.position.Minus(valueToMove));
                    break;
                }
            }
        }
        intersections.add(toReturn);
        fileManager.AddThreeWayIntersectionToBuffer(toReturn, directions, toConnect);
        return toReturn;
    }

    public void CreateRoadFromFile(ArrayList<String> instructions)
    {
        for(String instruction : instructions)
        {
            String[] parts = instruction.split(":");
            for(int i = 0; i < parts.length; ++i)
            {
                parts[i] = parts[i].substring(1, parts[i].length() - 1);
            }

            int saveId = Integer.parseInt(parts[0]);
            String[] roadInformation = parts[1].split("-");
            switch (roadInformation[0])
            {
                case "R" -> ParseRoad(roadInformation, parts.length > 2 ? parts[2] : "");
                case "4" -> ParseFourWay(parts.length > 2 ? parts[2] : "");
                case "3" -> ParseThreeWay(roadInformation, parts.length > 2 ? parts[2] : "");
            }
        }
    }

    private void ParseRoad(String[] roadInformation, String connectingRoad)
    {
        int roadLength = Integer.parseInt(roadInformation[1]);
        Road.DIRECTION direction = Road.DIRECTION.valueOf(roadInformation[2]);
        Road toConnect = null;
        if(!connectingRoad.equals(""))
        {
            String[] connectingRoadInformation = connectingRoad.split("-");
            int connectingSaveId = Integer.parseInt(connectingRoadInformation[0]);

            TrafficObject trafficObject = fileManager.GetTrafficObject(connectingSaveId);
            Road trialRoad = null;
            try {
                trialRoad = (Road)trafficObject;
            } catch (Exception e) { };

            if(trialRoad != null)
            {
                toConnect = trialRoad;
            }
            else
            {
                trafficObject = fileManager.GetTrafficObject(connectingSaveId);
                IntersectionFourWay trialFourWay = null;
                try {
                    trialFourWay = (IntersectionFourWay)trafficObject;
                } catch (Exception e) { };

                if(trialFourWay != null)
                {
                    toConnect = trialFourWay.GetRoad(direction);
                }
                else
                {
                    trafficObject = fileManager.GetTrafficObject(connectingSaveId);
                    IntersectionThreeWay trialThreeWay = null;
                    try {
                        trialThreeWay = (IntersectionThreeWay)trafficObject;
                    } catch (Exception e) { };

                    if(trialThreeWay != null)
                    {
                        toConnect = trialThreeWay.GetRoad(direction);
                    }
                }
            }
        }
        AddRoad(roadLength, direction, toConnect);
    }

    private void ParseFourWay(String connectingRoad)
    {
        Road toConnect = null;
        if(!connectingRoad.equals(""))
        {
            String[] connectingRoadInformation = connectingRoad.split("-");
            int connectingSaveId = Integer.parseInt(connectingRoadInformation[0]);

            TrafficObject trafficObject = fileManager.GetTrafficObject(connectingSaveId);
            Road trialRoad = null;
            try {
                trialRoad = (Road)trafficObject;
            } catch (Exception e) { };

            if(trialRoad != null)
            {
                toConnect = trialRoad;
            }
            else
            {
                Road.DIRECTION direction = Road.DIRECTION.valueOf(connectingRoadInformation[1]);

                trafficObject = fileManager.GetTrafficObject(connectingSaveId);
                IntersectionFourWay trialFourWay = null;
                try {
                    trialFourWay = (IntersectionFourWay)trafficObject;
                } catch (Exception e) { };

                if(trialFourWay != null)
                {
                    toConnect = trialFourWay.GetRoad(direction);
                }
                else
                {
                    trafficObject = fileManager.GetTrafficObject(connectingSaveId);
                    IntersectionThreeWay trialThreeWay = null;
                    try {
                        trialThreeWay = (IntersectionThreeWay)trafficObject;
                    } catch (Exception e) { };

                    if(trialThreeWay != null)
                    {
                        toConnect = trialThreeWay.GetRoad(direction);
                    }
                }
            }
        }
        AddFourWayIntersection(toConnect);
    }

    private void ParseThreeWay(String[] roadInformation, String connectingRoad)
    {
        Road.DIRECTION[] directions = new Road.DIRECTION[3];
        String[] directionsString = roadInformation[1].split(",");
        for(int i = 0; i < directionsString.length; ++i)
        {
            directions[i] = Road.DIRECTION.valueOf(directionsString[i]);
        }
        Road toConnect = null;

        if(!connectingRoad.equals(""))
        {
            String[] connectingRoadInformation = connectingRoad.split("-");
            int connectingSaveId = Integer.parseInt(connectingRoadInformation[0]);

            TrafficObject trafficObject = fileManager.GetTrafficObject(connectingSaveId);
            Road trialRoad = null;
            try {
                trialRoad = (Road)trafficObject;
            } catch (Exception e) { };

            if(trialRoad != null)
            {
                toConnect = trialRoad;
            }
            else
            {
                Road.DIRECTION direction = Road.DIRECTION.valueOf(connectingRoadInformation[1]);

                trafficObject = fileManager.GetTrafficObject(connectingSaveId);
                IntersectionFourWay trialFourWay = null;
                try {
                    trialFourWay = (IntersectionFourWay)trafficObject;
                } catch (Exception e) { };

                if(trialFourWay != null)
                {
                    toConnect = trialFourWay.GetRoad(direction);
                }
                else
                {
                    trafficObject = fileManager.GetTrafficObject(connectingSaveId);
                    IntersectionThreeWay trialThreeWay = null;
                    try {
                        trialThreeWay = (IntersectionThreeWay)trafficObject;
                    } catch (Exception e) { };

                    if(trialThreeWay != null)
                    {
                        toConnect = trialThreeWay.GetRoad(direction);
                    }
                }
            }
        }
        AddThreeWayIntersection(directions, toConnect);
    }

    public Segment GetRandomSegment()
    {
        // Find a random segment from the road available
        Random random = new Random();
        Segment toReturn = null;
        int i = 0;
        while (toReturn == null && i <= 5)
        {
            Road randomRoad = roads.get(random.nextInt(roads.size()));
            Segment randomSegment = randomRoad.GetRandomSegment();
            if(randomSegment.IsSegmentAvailable())
                toReturn = randomSegment;
            else while(!randomSegment.IsSegmentAvailable())
            {
                ArrayList<Segment> nextSegments = randomSegment.GetNextSegments();
                if(nextSegments.size() != 0)
                {
                    randomSegment = randomSegment.GetNextSegments().get(0);
                }
                else
                {
                    break;
                }
            }
            ++i;
        }
        return toReturn;
    }

    public Segment[] GetRandomSegments(int count)
    {
        // Find random segments from the road available
        Random random = new Random();
        Segment[] toReturn = null;
        int i = 0;
        while(toReturn == null && i <= 5)
        {
            Road randomRoad = roads.get(random.nextInt(roads.size()));
            Segment[] randomSegments = randomRoad.GetRandomSegments(count);
            boolean isValid = true;
            for(Segment segment: randomSegments)
            {
                if(!segment.IsSegmentAvailable())
                {
                    isValid = false;
                    break;
                }
            }
            if(isValid)
                toReturn = randomSegments;
            ++i;
        }
        return toReturn;
    }

    public void Simulate()
    {
        for(Intersection intersection: intersections)
        {
            intersection.UpdateRoadIntersections();
        }
    }

    public void AddRoadToFrame(TrafficFrame frame)
    {
        for(Road road: roads)
        {
            frame.add(road);
        }
        for(Intersection intersection: intersections)
        {
            frame.add(intersection);
            for(int i = 0; i < Road.DIRECTION.DIRECTION_COUNT.ordinal(); ++i)
            {
                Road road = intersection.GetRoad(Road.DIRECTION.values()[i]);
                if(road != null)
                {
                    frame.add(road);
                }
            }
        }
        frame.repaint();
    }
}
