public class IntersectionThreeWay extends Intersection{
    public IntersectionThreeWay(int id, Road.DIRECTION[] directions)
    {
        super(id, "3-WayIntersection");
        for(Road.DIRECTION direction: directions)
        {
            RoadIntersection newRoad = new RoadIntersection(direction);
            for(Road.DIRECTION otherDirection: directions)
            {
                if(direction == otherDirection)
                    continue;

                switch (direction)
                {
                    case NORTH:
                        switch (otherDirection) {
                            case EAST -> newRoad.AddLeftSegment();
                            case SOUTH -> newRoad.AddStraightSegment();
                            case WEST -> newRoad.AddRightSegment();
                        }
                        break;
                    case EAST:
                        switch (otherDirection) {
                            case NORTH -> newRoad.AddRightSegment();
                            case SOUTH -> newRoad.AddLeftSegment();
                            case WEST -> newRoad.AddStraightSegment();
                        }
                        break;
                    case SOUTH:
                        switch (otherDirection) {
                            case NORTH -> newRoad.AddStraightSegment();
                            case EAST -> newRoad.AddRightSegment();
                            case WEST -> newRoad.AddLeftSegment();
                        }
                        break;
                    case WEST:
                        switch (otherDirection) {
                            case NORTH -> newRoad.AddLeftSegment();
                            case EAST -> newRoad.AddStraightSegment();
                            case SOUTH -> newRoad.AddRightSegment();
                        }
                        break;
                }
            }

            AddRoadIntersection(newRoad, direction);
        }
        InitializeRoadIntersectionConnections();
    }
}
