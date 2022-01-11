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

        ArrayList<String> roadInformation = new ArrayList<String>();
        roadInformation.add(String.valueOf(saveIndex));
        roadInformation.add("R-" + road.GetLength() + "-" + road.GetDirection().name());
        if(toConnect != null)
        {
            roadInformation.add(String.valueOf(toConnect.GetSaveID()));
        }

        String toSave = GetSaveText(roadInformation);
        buffers.add(toSave);
        ++saveIndex;
    }

    public void AddFourWayIntersectionToBuffer(IntersectionFourWay intersectionFourWay, Road toConnect)
    {
        trafficObjects.add(intersectionFourWay);
        intersectionFourWay.SetSaveID(saveIndex);

        ArrayList<String> roadInformation = new ArrayList<String>();
        roadInformation.add(String.valueOf(saveIndex));
        roadInformation.add("4");
        if(toConnect != null)
        {
            roadInformation.add(String.valueOf(toConnect.GetSaveID() + "-" + Road.DIRECTION.OppositeDirection(toConnect.GetDirection()).name()));
        }

        String toSave = GetSaveText(roadInformation);
        buffers.add(toSave);
        ++saveIndex;
    }

    public void AddThreeWayIntersectionToBuffer(IntersectionThreeWay intersectionThreeWay, Road.DIRECTION[] directions, Road toConnect)
    {
        trafficObjects.add(intersectionThreeWay);
        intersectionThreeWay.SetSaveID(saveIndex);

        String directionsToSave = "";
        for(int i = 0; i < directions.length; ++i)
        {
            directionsToSave = directionsToSave + directions[i].name() + (i == directions.length - 1 ? "" : ",");
        }

        ArrayList<String> roadInformation = new ArrayList<String>();
        roadInformation.add(String.valueOf(saveIndex));
        roadInformation.add("3-" + directionsToSave);
        if(toConnect != null)
        {
            roadInformation.add(String.valueOf(toConnect.GetSaveID() + "-" + Road.DIRECTION.OppositeDirection(toConnect.GetDirection()).name()));
        }

        String toSave = GetSaveText(roadInformation);
        buffers.add(toSave);
        ++saveIndex;
    }

    private String GetSaveText(ArrayList<String> roadInformation)
    {
        String toReturn = "";
        for(int i = 0; i < roadInformation.size(); ++i)
        {
            toReturn += "[" + roadInformation.get(i) + "]" + (i == roadInformation.size() - 1 ? "" : ":");
        }
        return toReturn;
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

    public void SaveFile(File file)
    {
        try
        {
            FileWriter writer = new FileWriter(file);
            for(String buffer: buffers)
            {
                writer.write(buffer + "\n");
            }
            writer.close();
            System.out.println("File saved as " + file.getName());
        }
        catch(IOException e){
            System.out.println("Unable to save road data from " + file.getName());
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

    public ArrayList<String> LoadFile(File file)
    {
        ArrayList<String> toReturn = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                toReturn.add(scanner.nextLine());
            }
            System.out.println("File " + file.getName() + " loaded");
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File is not found.");
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