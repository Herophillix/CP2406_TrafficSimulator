public class IntersectionFourWay extends Intersection {
    public IntersectionFourWay(int id)
    {
        super(id, "4WayIntersection");
        for(Road.DIRECTION direction: Road.DIRECTION.values())
        {
            if(direction == Road.DIRECTION.DIRECTION_COUNT)
                continue;
            RoadIntersection newRoad = new RoadIntersection(direction, GetInfo() + "-");
            newRoad.AddStraightSegment();
            newRoad.AddRightSegment();
            newRoad.AddLeftSegment();

            AddRoadIntersection(newRoad, direction);
        }
        InitializeRoadIntersectionConnections();
    }
}
