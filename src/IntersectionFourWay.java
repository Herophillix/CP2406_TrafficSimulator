public class IntersectionFourWay extends Intersection {
    public IntersectionFourWay()
    {
        super();
        for(Road.DIRECTION direction: Road.DIRECTION.values())
        {
            RoadIntersection newRoad = new RoadIntersection(direction);
            newRoad.AddStraightSegment();
            newRoad.AddRightSegment();
            newRoad.AddLeftSegment();

            AddIntersectionRoad(newRoad, direction);
        }
    }
}
