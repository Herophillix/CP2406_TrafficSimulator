public abstract class Intersection extends TrafficObject{
    private RoadIntersection[] roadIntersections;

    public Intersection(int id, String name)
    {
        super(id, name);
        roadIntersections = new RoadIntersection[Road.DIRECTION.DIRECTION_COUNT.ordinal()];
    }

    public void AddRoadIntersection(RoadIntersection roadIntersection, Road.DIRECTION direction)
    {
        if(roadIntersections[direction.ordinal()] != null)
            return;

        roadIntersections[direction.ordinal()] = roadIntersection;
    }

    public void InitializeRoadIntersectionConnections()
    {
        for(RoadIntersection roadIntersection: roadIntersections)
        {
            if(roadIntersection == null)
                continue;
            for(RoadIntersection neighbouringRoadIntersection: roadIntersections)
            {
                if (neighbouringRoadIntersection == null || neighbouringRoadIntersection == roadIntersection)
                    continue;

                roadIntersection.ConnectIncomingSegment(neighbouringRoadIntersection);
            }
        }
    }

    public void ConnectRoad(Road roadToConnect)
    {
        Road.DIRECTION direction = Road.DIRECTION.OppositeDirection(roadToConnect.GetDirection());
        if(roadIntersections[direction.ordinal()] != null)
        {
            switch (direction)
            {
                case NORTH, EAST -> {
                    roadIntersections[direction.ordinal()].GetRoad().Connect(roadToConnect);
                }
                case SOUTH, WEST -> {
                    roadToConnect.Connect(roadIntersections[direction.ordinal()].GetRoad());
                }
            }
        }
    }

    public Road GetRoad(Road.DIRECTION direction)
    {
        if(roadIntersections[direction.ordinal()] != null)
        {
            return roadIntersections[direction.ordinal()].GetRoad();
        }
        return null;
    }

    public void PrintIntersection()
    {
        for(int i = 0; i < roadIntersections.length; ++i)
        {
            System.out.println("Direction: " + Road.DIRECTION.values()[i]);
            roadIntersections[i].GetRoad().PrintRoad();
        }
        System.out.println();
    }
}
