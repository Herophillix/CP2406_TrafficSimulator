package GUI;

import java.awt.event.*;
import javax.swing.*;

public class TrafficMenuBar extends JMenuBar{
    // File
    private JMenu fileMenu;

    private JMenuItem newItem;

    private JMenuItem saveItem;

    private JMenu loadMenu;
    private JMenuItem loadFileItem;
    private JMenuItem loadPresetItem;

    // Simulation
    private JMenu simulateMenu;

    private JMenuItem startSimulationMenu;
    private JMenuItem stopSimulationMenu;

    public TrafficMenuBar()
    {
        InitializeFileMenu();
        InitializeSimulationMenu();
    }

    private void InitializeFileMenu()
    {
        fileMenu = new JMenu("File");

        newItem = new JMenuItem("New");
        fileMenu.add(newItem);

        saveItem = new JMenuItem("Save");
        saveItem.setVisible(false);
        fileMenu.add(saveItem);

        loadMenu = new JMenu("Load");
        loadFileItem = new JMenuItem("File");
        loadPresetItem = new JMenuItem("Preset");
        loadMenu.add(loadFileItem);
        loadMenu.add(loadPresetItem);
        fileMenu.add(loadMenu);

        this.add(fileMenu);

        newItem.addActionListener(e -> ShowButtonsWithFile());
    }

    public void AddNewFileButtonCallback(ActionListener callback)
    {
        newItem.addActionListener(callback);
    }

    public void AddSaveFileButtonCallback(ActionListener callback) { saveItem.addActionListener(callback); }

    public void AddLoadFileButtonCallback(ActionListener callback)
    {
        loadFileItem.addActionListener(callback);
    }

    public void AddLoadPresetButtonCallback(ActionListener callback)
    {
        loadPresetItem.addActionListener(callback);
    }

    private void InitializeSimulationMenu()
    {
        simulateMenu = new JMenu("Simulate");

        startSimulationMenu = new JMenuItem("Start");
        simulateMenu.add(startSimulationMenu);

        stopSimulationMenu = new JMenuItem("Stop");
        stopSimulationMenu.setVisible(false);
        simulateMenu.add(stopSimulationMenu);

        this.add(simulateMenu);

        simulateMenu.setVisible(false);
        AddStopSimulationButtonCallback(e -> stopSimulationMenu.setVisible(false));

    }

    public void AddStartSimulationButtonCallback(ActionListener callback)
    {
        startSimulationMenu.addActionListener(callback);
    }
    public void AddStopSimulationButtonCallback(ActionListener callback)
    {
        stopSimulationMenu.addActionListener(callback);
    }

    public void ShowButtonsWithFile()
    {
        saveItem.setVisible(true);
        simulateMenu.setVisible(true);
    }

}
