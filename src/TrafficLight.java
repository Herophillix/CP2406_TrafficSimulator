public class TrafficLight extends TrafficObject{
    public static final int SWITCH_THRESHOLD = 5;

    private boolean isGreen;
    private int currentTick;

    public TrafficLight(int id, String name, boolean isGreen, int startTick){
        super(id, name + "TrafficLight");
        this.isGreen = isGreen;
        this.currentTick = startTick;
    }

    public boolean GetIsGreen() { return isGreen; }

    public void AddTick()
    {
        ++currentTick;
        int tickThreshold = isGreen ? SWITCH_THRESHOLD : SWITCH_THRESHOLD * 3;
        if(currentTick == tickThreshold)
        {
            currentTick = 0;
            isGreen = !isGreen;
        }
        PrintInformation();
    }

    @Override
    public void PrintInformation()
    {
        System.out.println(GetName() + " State: " + (isGreen ? "Green" : "Red"));
    }
}
