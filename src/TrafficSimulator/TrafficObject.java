package TrafficSimulator;

import GUI.TrafficFrame;
import Utility.Vector2;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public abstract class TrafficObject extends JPanel {
    protected static final int GRAPHIC_SCALE = 20;
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

        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        setBorder(blackline);

        InitializeJPanelAttributes();
    }

    public void InitializeJPanelAttributes()
    {

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
        SetJPanelBounds(position, scale);
    }

    public void SetJPanelBounds(Vector2 position, Vector2 scale)
    {
        setBounds(position.x, position.y, scale.x, scale.y);
    }

    public void AddObjectToFrame(TrafficFrame frame)
    {
        frame.add(this);
    }
}
