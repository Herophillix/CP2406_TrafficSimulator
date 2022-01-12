package GUI;

import TrafficSimulator.RoadManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TrafficFrame extends JFrame{
    private static final int SCREEN_WIDTH = 1600;
    private static final int SCREEN_HEIGHT = 900;
    public static final int STARTING_X = 0;
    public static final int STARTING_Y = SCREEN_HEIGHT / 2 - 20;

    public TrafficMenuBar menuBar;
    public TrafficFileChooser fileChooser;
    public TrafficOptionPane optionPane;

    private ArrayList<ActionListener> onStartSimulation;

    public TrafficFrame()
    {
        Initialize();
        InitializeFileChooser();
        InitializeMenuBar();
        InitializeOptionPane();
        InitializeCallbacks();

        onStartSimulation = new ArrayList<>();
    }

    private void Initialize()
    {
        this.setTitle("Traffic Simulator 2.0");
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(10, 70, 10));
        this.setLayout(null);
    }

    private void InitializeFileChooser()
    {
        fileChooser = new TrafficFileChooser();
    }

    private void InitializeMenuBar()
    {
        menuBar = new TrafficMenuBar();
        this.setJMenuBar(menuBar);
    }

    private void InitializeOptionPane()
    {
        optionPane = new TrafficOptionPane();
    }

    private void InitializeCallbacks()
    {
        // Open a file chooser when load file button is pressed
        menuBar.AddLoadFileButtonCallback(e -> fileChooser.OpenFile());
        // Open a file chooser when save file button is pressed
        menuBar.AddSaveFileButtonCallback(e -> fileChooser.SaveFile());
        // Open the simulation dialog to start simulation
        menuBar.AddStartSimulationButtonCallback(e -> ShowSimulationDialog());

        // Enable save button when a file is loaded
        fileChooser.AddOnFileOpenedCallback(e -> menuBar.ShowButtonsWithFile());
        // Enable save button when a preset is loaded
        menuBar.AddLoadPresetButtonCallback(e -> menuBar.ShowButtonsWithFile());
    }

    public void ShowSimulationDialog()
    {
        int numOfCar = optionPane.GetUserInt("Input number of car", 1, RoadManager.roadCount);
        if (numOfCar == -1)
            return;

        int numOfBus = optionPane.GetUserInt("Input number of bus", 0, RoadManager.roadCount / 3);
        if (numOfBus == -1)
            return;

        int numOfBike = optionPane.GetUserInt("Input number of motorbike", 0, RoadManager.roadCount / 2);
        if (numOfBike == -1)
            return;

        int updateRate = optionPane.GetUserInt("Input update rate in ms", 100, 1000);
        if (updateRate == -1)
            return;

        for(ActionListener action: onStartSimulation)
        {
            action.actionPerformed(new ActionEvent(new ArrayList<>(Arrays.asList(numOfCar, numOfBus, numOfBike, updateRate)), ActionEvent.ACTION_PERFORMED, ""));
        }
    }

    public void AddStartSimulationCallback(ActionListener callback)
    {
        onStartSimulation.add(callback);
    }

    public void ShowFrame()
    {
        this.setVisible(true);
    }

}