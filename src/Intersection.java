public abstract class Intersection {
    private RoadIntersection[] roadIntersections;

    public Intersection()
    {
        roadIntersections = new RoadIntersection[Road.DIRECTION.DIRECTION_COUNT.ordinal()];
    }

    public void AddIntersectionRoad(RoadIntersection intersectionRoad, Road.DIRECTION direction)
    {
        if(roadIntersections[direction.ordinal()] != null)
            return;

        roadIntersections[direction.ordinal()] = intersectionRoad;
    }
}
