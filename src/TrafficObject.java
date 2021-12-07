public abstract class TrafficObject {
    private int id;
    private String name;
    public TrafficObject(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int GetID() { return id; }
    public String GetName() { return name + "_" + id; }

    public void PrintInformation()
    {
        System.out.println(name + "_" + id);
    }
}
