public class IntersectionFourWay extends Intersection {
    public IntersectionFourWay()
    {
        super();
        for(Road.DIRECTION direction: Road.DIRECTION.values())
        {
            if(direction == Road.DIRECTION.DIRECTION_COUNT)
                continue;
            RoadIntersection newRoad = new RoadIntersection(direction);
            newRoad.AddStraightSegment();
            newRoad.AddRightSegment();
            newRoad.AddLeftSegment();

            AddRoadIntersection(newRoad, direction);
        }
        InitializeRoadIntersectionConnections();
    }
}
