import java.util.ArrayList;

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
            toReturn.Connect(toConnect);
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
}
