public class TrafficLight extends TrafficObject{
    private final int SWITCH_THRESHOLD = 10;

    private boolean isGreen;
    private int currentTick;

    public TrafficLight(int id, String name){
        super(id, name + "TrafficLight");
        isGreen = true;
    }

    public boolean GetIsGreen() { return isGreen; }

    public void AddTick()
    {
        ++currentTick;
        if(currentTick == 5)
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
