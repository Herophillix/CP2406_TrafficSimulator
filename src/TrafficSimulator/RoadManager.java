package TrafficSimulator;

import java.util.ArrayList;
import java.util.Random;
import FileManagement.FileManager;

public class RoadManager {
    private ArrayList<Road> roads;
    private ArrayList<Intersection> intersections;
    private FileManager fileManager;

    public RoadManager()
    {
        roads = new ArrayList<>();
        intersections = new ArrayList<>();
        fileManager = new FileManager();
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
        fileManager.AddRoadToBuffer(length, direction, toConnect);
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
        return toReturn;
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

    public void SaveFile()
    {
        fileManager.SaveFile();
    }
}
