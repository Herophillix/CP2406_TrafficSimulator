package FileManagement;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import TrafficSimulator.*;

public class FileManager {
    final String fileName = "SaveFile.txt";
    private ArrayList<String> buffers;
    private int saveIndex;

    public FileManager()
    {
        buffers = new ArrayList<String>();
        saveIndex = 0;
    }

    public void AddRoadToBuffer(Road road, Road toConnect)
    {
        road.SetSaveID(saveIndex);
        String toSave = String.valueOf(saveIndex) + "-R-" +
                road.GetLength() + "-" +
                road.GetDirection().name() +
                (toConnect == null ? "" : "-" + String.valueOf(toConnect.GetSaveID()));
        buffers.add(toSave);
        ++saveIndex;
    }

    public void AddFourWayIntersectionToBuffer(IntersectionFourWay intersectionFourWay, Road toConnect)
    {
        intersectionFourWay.SetSaveID(saveIndex);
        String toSave = String.valueOf(saveIndex) + "-4" +
                (toConnect == null ? "" : "-" + String.valueOf(toConnect.GetSaveID()));
        buffers.add(toSave);
        ++saveIndex;
    }

    public void AddThreeWayIntersectionToBuffer(IntersectionThreeWay intersectionThreeWay, Road toConnect)
    {

    }

    public void SaveFile()
    {
        try
        {
            FileWriter writer = new FileWriter("./" + fileName);
            for(String buffer: buffers)
            {
                writer.write(buffer + "\n");
            }
            writer.close();
            System.out.println("File saved as " + fileName);
        }
        catch(IOException e){
            System.out.println("Unable to save road data from " + fileName);
            e.printStackTrace();
        }
    }

}