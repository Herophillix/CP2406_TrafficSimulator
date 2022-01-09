package FileManagement;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import TrafficSimulator.*;

public class FileManager {
    final String fileName = "SaveFile.txt";
    private ArrayList<String> buffers;

    public FileManager()
    {
        buffers = new ArrayList<String>();
    }

    public void AddRoadToBuffer(int length, Road.DIRECTION direction, Road toConnect)
    {
        buffers.add("R-" + length + "-" + direction.name());
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