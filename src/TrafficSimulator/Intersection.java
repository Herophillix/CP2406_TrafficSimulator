package TrafficSimulator;

import GUI.TrafficFrame;
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

    @Override
    public void AddObjectToFrame(TrafficFrame frame)
    {
        frame.add(this);
        for(RoadIntersection intersection: roadIntersections)
        {
            intersection.AddObjectToFrame(frame);
        }
    }

//    @Override
//    public void paintComponent(Graphics g)
//    {
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D)g;
//        g2.setColor(Color.GRAY);
//        for(RoadIntersection roadIntersection: roadIntersections)
//        {
//            if(roadIntersection != null)
//            {
//                Road currentRoad = roadIntersection.GetRoad();
//                switch (currentRoad.GetDirection())
//                {
//                    case NORTH:
//                    {
//                        if(roadIntersections[Road.DIRECTION.EAST.ordinal()] != null)
//                        {
//                            Road otherRoad = roadIntersections[Road.DIRECTION.EAST.ordinal()].GetRoad();
//                            Polygon trapezium = new Polygon();
//                            trapezium.addPoint(currentRoad.position.x + currentRoad.scale.x, currentRoad.position.y + (currentRoad.scale.y / currentRoad.GetLength()));
//                            trapezium.addPoint(currentRoad.position.x + currentRoad.scale.x, currentRoad.position.y + (currentRoad.scale.y / currentRoad.GetLength()) * 2);
//                            trapezium.addPoint(otherRoad.position.x + (otherRoad.scale.x / otherRoad.GetLength()), otherRoad.position.y);
//                            trapezium.addPoint(otherRoad.position.x + (otherRoad.scale.x / otherRoad.GetLength()) * 2, otherRoad.position.y);
//                            g2.fillPolygon(trapezium);
//                        }
//                        break;
//                    }
//                    case EAST:
//                    {
//                        if(roadIntersections[Road.DIRECTION.SOUTH.ordinal()] != null)
//                        {
//                            Road otherRoad = roadIntersections[Road.DIRECTION.SOUTH.ordinal()].GetRoad();
//                            Polygon trapezium = new Polygon();
//                            trapezium.addPoint(currentRoad.position.x + (currentRoad.scale.x / currentRoad.GetLength()), currentRoad.position.y + currentRoad.scale.y);
//                            trapezium.addPoint(currentRoad.position.x + (currentRoad.scale.x / currentRoad.GetLength()) * 2, currentRoad.position.y + currentRoad.scale.y);
//                            trapezium.addPoint(otherRoad.position.x + otherRoad.scale.x, otherRoad.position.y + (otherRoad.scale.y / otherRoad.GetLength()) * 2);
//                            trapezium.addPoint(otherRoad.position.x + otherRoad.scale.x, otherRoad.position.y + (otherRoad.scale.y / otherRoad.GetLength()));
//                            g2.fillPolygon(trapezium);
//                        }
//                        break;
//                    }
//                    case SOUTH:
//                    {
//                        if(roadIntersections[Road.DIRECTION.WEST.ordinal()] != null)
//                        {
//                            Road otherRoad = roadIntersections[Road.DIRECTION.WEST.ordinal()].GetRoad();
//                            Polygon trapezium = new Polygon();
//                            trapezium.addPoint(currentRoad.position.x, currentRoad.position.y + (currentRoad.scale.y / currentRoad.GetLength()));
//                            trapezium.addPoint(currentRoad.position.x, currentRoad.position.y + (currentRoad.scale.y / currentRoad.GetLength()) * 2);
//                            trapezium.addPoint(otherRoad.position.x + (otherRoad.scale.x / otherRoad.GetLength()), otherRoad.position.y + otherRoad.scale.y);
//                            trapezium.addPoint(otherRoad.position.x + (otherRoad.scale.x / otherRoad.GetLength()) * 2, otherRoad.position.y + otherRoad.scale.y);
//                            g2.fillPolygon(trapezium);
//                        }
//                        break;
//                    }
//                    case WEST:
//                    {
//                        if(roadIntersections[Road.DIRECTION.NORTH.ordinal()] != null)
//                        {
//                            Road otherRoad = roadIntersections[Road.DIRECTION.NORTH.ordinal()].GetRoad();
//                            Polygon trapezium = new Polygon();
//                            trapezium.addPoint(currentRoad.position.x + (currentRoad.scale.x / currentRoad.GetLength()), currentRoad.position.y);
//                            trapezium.addPoint(currentRoad.position.x + (currentRoad.scale.x / currentRoad.GetLength()) * 2, currentRoad.position.y);
//                            trapezium.addPoint(otherRoad.position.x, otherRoad.position.y + (otherRoad.scale.y / otherRoad.GetLength() * 2));
//                            trapezium.addPoint(otherRoad.position.x, otherRoad.position.y + (otherRoad.scale.y / otherRoad.GetLength()));
//                            g2.fillPolygon(trapezium);
//                        }
//                        break;
//                    }
//                }
//            }
//        }
//    }
//
}
