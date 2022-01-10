package FileManagement;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import TrafficSimulator.*;

public class FileManager {
    public final String fileName = "SaveFile.txt";
    private ArrayList<String> buffers;
    private int saveIndex;
    private ArrayList<TrafficObject> trafficObjects;

    public FileManager()
    {
        buffers = new ArrayList<String>();
        saveIndex = 0;
        trafficObjects = new ArrayList<TrafficObject>();
    }

    public void AddRoadToBuffer(Road road, Road toConnect)
    {
        trafficObjects.add(road);
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
        trafficObjects.add(intersectionFourWay);
        intersectionFourWay.SetSaveID(saveIndex);
        String toSave = String.valueOf(saveIndex) + "-4" +
                (toConnect == null ? "" : "-" + String.valueOf(toConnect.GetSaveID()));
        buffers.add(toSave);
        ++saveIndex;
    }

    public void AddThreeWayIntersectionToBuffer(IntersectionThreeWay intersectionThreeWay, Road toConnect)
    {

    }

    public void SaveFile(String fileName)
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
        }
    }

    public void SaveDefaultFile()
    {
        SaveFile(this.fileName);
    }

    public ArrayList<String> LoadFile(String fileName)
    {
        ArrayList<String> toReturn = new ArrayList<String>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                toReturn.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " is not found.");
        }
        return toReturn;
    }

    public ArrayList<String> LoadDefaultFile()
    {
        return LoadFile(this.fileName);
    }

    public TrafficObject GetTrafficObject(int index)
    {
        return trafficObjects.size() > index ? trafficObjects.get(index) : null;
    }
}