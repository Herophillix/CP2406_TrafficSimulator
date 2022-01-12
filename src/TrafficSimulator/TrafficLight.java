package TrafficSimulator;

import Utility.Vector2;

import java.awt.*;

public class TrafficLight extends TrafficObject{
    public static final int SWITCH_THRESHOLD = 5;

    private boolean isGreen;
    private int currentTick;

    public TrafficLight(int id, String name, boolean isGreen, int startTick, Road.DIRECTION direction){
        super(id, name + "TrafficLight");
        this.isGreen = isGreen;
        this.currentTick = startTick;

        switch (direction) {
            case NORTH, SOUTH -> {
                this.scale = new Vector2(GRAPHIC_SCALE, GRAPHIC_SCALE / 2).Divide(2);
            }
            case EAST, WEST -> {
                this.scale = new Vector2(GRAPHIC_SCALE / 2, GRAPHIC_SCALE).Divide(2);
            }
        }
    }

    public void InitializeJPanelAttributes()
    {
        setBackground(isGreen ? Color.GREEN : Color.RED);
    }

    public boolean GetIsGreen() { return isGreen; }

    public void AddTick()
    {
        ++currentTick;
        int tickThreshold = isGreen ? SWITCH_THRESHOLD : (SWITCH_THRESHOLD + 2) * 3 + 2;
        if(currentTick == tickThreshold)
        {
            currentTick = 0;
            isGreen = !isGreen;
            setBackground(isGreen ? Color.GREEN : Color.RED);
        }
        //PrintInformation();
    }

    @Override
    public void PrintInformation()
    {
        System.out.println(GetName() + " State: " + (isGreen ? "Green" : "Red"));
    }
}
