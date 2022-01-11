package TrafficSimulator;

import Utility.Vector2;

import java.awt.*;

public abstract class Intersection extends TrafficObject{
    private RoadIntersection[] roadIntersections;

    public Intersection(int id, String name)
    {
        super(id, name);
        roadIntersections = new RoadIntersection[Road.DIRECTION.DIRECTION_COUNT.ordinal()];
        scale = new Vector2(GRAPHIC_SCALE, GRAPHIC_SCALE);
    }

    @Override
    public void InitializeJPanelAttributes()
    {
        setBackground(Color.GRAY);
    }

    public void AddRoadIntersection(RoadIntersection roadIntersection, Road.DIRECTION direction)
    {
        if(roadIntersections[direction.ordinal()] != null)
            return;

        roadIntersections[direction.ordinal()] = roadIntersection;
    }

    public void InitializeRoadIntersectionConnections()
    {
        // Connect each road intersection with other road intersections
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

    public void UpdateRoadIntersections()
    {
        for(RoadIntersection roadIntersection: roadIntersections)
        {
            if(roadIntersection != null)
            {
                roadIntersection.UpdateTrafficLights();
            }
        }
    }

    public void ConnectRoad(Road roadToConnect)
    {
        // Connect intersection with a road
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

    @Override
    public void SetSaveID(int saveId)
    {
        this.saveId = saveId;
        for(RoadIntersection intersection : roadIntersections)
        {
            if(intersection != null)
                intersection.GetRoad().SetSaveID(saveId);
        }
    }

    @Override
    public void SetJPanelBounds(Vector2 position, Vector2 scale)
    {
        for(RoadIntersection roadIntersection: roadIntersections)
        {
            if(roadIntersection != null)
            {
                Road road = roadIntersection.GetRoad();
                switch (road.GetDirection())
                {
                    case NORTH -> road.SetPosition(position.Minus(new Vector2(0, road.scale.y)));
                    case EAST -> road.SetPosition(position.Add(new Vector2(scale.x, 0)));
                    case SOUTH -> road.SetPosition(position.Add(new Vector2(0, scale.y)));
                    case WEST -> road.SetPosition(position.Minus(new Vector2(road.scale.x, 0)));
                }
            }
        }
        setBounds(position.x, position.y, scale.x, scale.y);
    }
}
