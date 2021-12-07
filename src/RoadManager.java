import java.util.ArrayList;
import java.util.Random;

public class RoadManager {
    private ArrayList<Road> roads;
    private ArrayList<Intersection> intersections;

    public RoadManager()
    {
        roads = new ArrayList<>();
        intersections = new ArrayList<>();
    }

    public Road AddRoad(int length, Road.DIRECTION direction, Road toConnect)
    {
        Road toReturn = new Road(roads.size(), "", length, direction);
        if(toConnect != null)
        {
            toConnect.Connect(toReturn);
        }
        roads.add(toReturn);
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
        Random random = new Random();
        Road randomRoad = roads.get(random.nextInt(roads.size()));
        return randomRoad.GetRandomSegment();
    }

    public Segment[] GetRandomSegments(int count)
    {
        Random random = new Random();
        Road randomRoad = roads.get(random.nextInt(roads.size()));
        return randomRoad.GetRandomSegments(count);
    }
}
