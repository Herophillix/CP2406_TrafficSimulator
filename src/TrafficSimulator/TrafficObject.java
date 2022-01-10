package TrafficSimulator;

public abstract class TrafficObject {
    private int id;
    private String name;
    protected int saveId;
    public TrafficObject(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public void SetSaveID(int saveId) { this.saveId = saveId; }

    public int GetID() { return id; }
    public String GetName() { return name; }
    public String GetInfo() { return name + "_" + id; }
    public int GetSaveID() { return saveId; }

    public void PrintInformation()
    {
        System.out.println(name + "_" + id);
    }
}
