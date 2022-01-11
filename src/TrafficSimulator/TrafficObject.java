package TrafficSimulator;

import Utility.Vector2;

import java.awt.*;

public abstract class TrafficObject {
    protected final int SCALE = 40;
    private int id;
    private String name;
    protected int saveId;

    public Vector2 position;
    public Vector2 scale;

    public TrafficObject(int id, String name)
    {
        this.id = id;
        this.name = name;
        position = new Vector2(0,0);
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

    public void SetPosition(Vector2 position)
    {
        this.position.x = position.x;
        this.position.y = position.y;
    }

    public void Draw(Graphics g)
    {

    }
}
