package TrafficSimulator;

import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import FileManagement.FileManager;

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
        }
        intersections.add(toReturn);
        fileManager.AddThreeWayIntersectionToBuffer(toReturn, toConnect);
        return toReturn;
    }

    public void CreateRoadFromFile(ArrayList<String> instructions)
    {
        for(String instruction: instructions)
        {
            String[] parts = instruction.split("-");
            int saveId = Integer.parseInt(parts[0]);
            char roadType = parts[1].charAt(0);
            switch (roadType)
            {
                case 'R':
                    int roadLength = Integer.parseInt(parts[2]);
                    Road.DIRECTION direction = Road.DIRECTION.valueOf(parts[3]);
                    Road toConnect = null;
                    if(parts.length > 4)
                    {
                        int connectingSaveId = Integer.parseInt(parts[4]);

                        TrafficObject trafficObject = fileManager.GetTrafficObject(connectingSaveId);
                        Road trialRoad = null;
                        try{
                            trialRoad = (Road)trafficObject;
                        } catch (Exception e) { }

                        if(trialRoad != null)
                            toConnect = trialRoad;
                        else
                        {
                            trafficObject = fileManager.GetTrafficObject(connectingSaveId);
                            IntersectionFourWay trialFourWayIntersection = null;
                            try{
                                trialFourWayIntersection = (IntersectionFourWay)trafficObject;
                            } catch (Exception e) { }

                            if(trialFourWayIntersection != null)
                            {
                                toConnect = trialFourWayIntersection.GetRoad(direction);
                            }
                            else
                            {
                                trafficObject = fileManager.GetTrafficObject(connectingSaveId);
                                IntersectionThreeWay trialThreeWayIntersection = null;
                                try{
                                    trialThreeWayIntersection = (IntersectionThreeWay)trafficObject;
                                } catch (Exception e) { }

                                if(trialThreeWayIntersection != null)
                                {
                                    toConnect = trialThreeWayIntersection.GetRoad(direction);
                                }
                            }
                        }
                    }
                    Road newRoad = AddRoad(roadLength, direction, toConnect);
                    break;
                case '4':
                    toConnect = null;
                    if(parts.length > 2)
                    {
                        int connectingSaveId = Integer.parseInt(parts[2]);
                        toConnect = (Road)fileManager.GetTrafficObject(connectingSaveId);
                    }
                    IntersectionFourWay newFourWay = AddFourWayIntersection(toConnect);
                    break;
                case '3':
                    break;
            }
        }
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
}
