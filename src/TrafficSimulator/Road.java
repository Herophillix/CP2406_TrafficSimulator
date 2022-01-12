package TrafficSimulator;

import GUI.TrafficFrame;
import Utility.MathUtility;
import Utility.Vector2;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Road extends TrafficObject implements MouseListener {
    public static final int MIN_LENGTH = 3;

    public enum DIRECTION {
        NORTH, EAST, SOUTH, WEST, DIRECTION_COUNT;
        public static DIRECTION OppositeDirection(DIRECTION direction)
        {
            return DIRECTION.values()[(direction.ordinal() + 2) % DIRECTION_COUNT.ordinal()];
        }
    }

    private int length;
    private Road.DIRECTION direction;
    private Lane[] lanes;

    public Road(int id, String name, int length, Road.DIRECTION direction)
    {
        super(id, name + "Road");
        this.length = MathUtility.Clamp(length, MIN_LENGTH, MIN_LENGTH * 5);
        this.direction = direction;

        // Create 2 lanes, 1 in the direction of road, and the other in the opposing direction
        lanes = new Lane[2];
        switch (direction) {
            case NORTH, SOUTH -> {
                lanes[0] = new Lane(0, GetInfo(), this.length, DIRECTION.NORTH);
                lanes[1] = new Lane(1, GetInfo(), this.length, DIRECTION.SOUTH);
                this.scale = new Vector2(2 * GRAPHIC_SCALE, length * GRAPHIC_SCALE);
            }
            case EAST, WEST -> {
                lanes[0] = new Lane(0, GetInfo(), this.length, DIRECTION.EAST);
                lanes[1] = new Lane(1, GetInfo(), this.length, DIRECTION.WEST);
                this.scale = new Vector2(length * GRAPHIC_SCALE, 2 * GRAPHIC_SCALE);
            }
        }
        addMouseListener(this);
    }

    @Override
    public void InitializeJPanelAttributes()
    {
        setBackground(Color.GRAY);
    }

    public int GetLength() { return length; }
    public DIRECTION GetDirection() { return this.direction; }
    public Lane GetLane(Road.DIRECTION direction)
    {
        for(Lane lane: lanes)
        {
            if(lane.GetDirection() == direction)
            {
                return lane;
            }
        }
        return null;
    }

    public void Connect(Road roadToConnect)
    {
        // Connect a road to another road
        if(lanes[0].GetDirection() == roadToConnect.lanes[0].GetDirection())
        {
            lanes[0].ConnectSegment(roadToConnect, roadToConnect.lanes[0].GetSegment(Lane.SEGMENT_POSITION.FIRST), Lane.SEGMENT_POSITION.LAST);
        }
        if(lanes[1].GetDirection() == roadToConnect.lanes[1].GetDirection())
        {
            roadToConnect.lanes[1].ConnectSegment(roadToConnect, lanes[1].GetSegment(Lane.SEGMENT_POSITION.FIRST), Lane.SEGMENT_POSITION.LAST);
        }
    }

    public Road GetConnectedRoad(DIRECTION direction) { return lanes[direction.ordinal() / 2].GetConnectedRoad(); }

    public void ConnectSegment(Segment nextSegment, Lane.SEGMENT_POSITION position, Road.DIRECTION direction)
    {
        for(Lane lane: lanes)
        {
            if(lane.GetDirection() == direction)
            {
                lane.ConnectSegment(null, nextSegment, position);
                break;
            }
        }
    }

    public Segment GetRandomSegment()
    {
        // Get a random segment from one of the lanes
        return lanes[(int)Math.round(Math.random())].GetSegment(Lane.SEGMENT_POSITION.values()[(int)Math.ceil(Math.random() * Lane.SEGMENT_POSITION.LAST.ordinal() - 1)]);
    }

    public Segment[] GetRandomSegments(int count)
    {
        // Get random segments from one of the lanes
        return lanes[(int)Math.round(Math.random())].GetSegments(Lane.SEGMENT_POSITION.FIRST, MathUtility.Clamp(count, 1, length));
    }

    @Override
    public void SetJPanelBounds(Vector2 position, Vector2 scale)
    {
        setBounds(position.x, position.y, scale.x, scale.y);
        switch (direction) {
            case EAST, WEST -> {
                for (int i = 0; i < lanes.length; ++i) {
                    lanes[i].SetPosition(new Vector2(position.x, position.y + i * (scale.y / 2)));
                }
            }
            case NORTH, SOUTH -> {
                for (int i = 0; i < lanes.length; ++i) {
                    lanes[i].SetPosition(new Vector2(position.x + i * (scale.x / 2), position.y));
                }
            }
        }
    }

    @Override
    public void AddObjectToFrame(TrafficFrame frame)
    {
        for(Lane lane: lanes)
        {
            lane.AddObjectToFrame(frame);
        }
        frame.add(this);
    }



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //setBackground(Color.CYAN);
    }

    @Override
    public void mouseExited(MouseEvent e) {

        //setBackground(Color.GRAY);
    }
}
