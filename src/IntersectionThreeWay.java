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
                    case N:
                        switch (otherDirection) {
                            case E -> newRoad.AddLeftSegment();
                            case S -> newRoad.AddStraightSegment();
                            case W -> newRoad.AddRightSegment();
                        }
                        break;
                    case E:
                        switch (otherDirection) {
                            case N -> newRoad.AddRightSegment();
                            case S -> newRoad.AddLeftSegment();
                            case W -> newRoad.AddStraightSegment();
                        }
                        break;
                    case S:
                        switch (otherDirection) {
                            case N -> newRoad.AddStraightSegment();
                            case E -> newRoad.AddRightSegment();
                            case W -> newRoad.AddLeftSegment();
                        }
                        break;
                    case W:
                        switch (otherDirection) {
                            case N -> newRoad.AddLeftSegment();
                            case E -> newRoad.AddStraightSegment();
                            case S -> newRoad.AddRightSegment();
                        }
                        break;
                }
            }

            AddRoadIntersection(newRoad, direction);
        }
        InitializeRoadIntersectionConnections();
    }
}
