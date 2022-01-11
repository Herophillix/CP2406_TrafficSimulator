package GUI;

import java.awt.event.*;
import javax.swing.*;

public class TrafficMenuBar extends JMenuBar{
    // File
    private JMenu fileMenu;

    private JMenuItem newItem;

    private JMenu loadMenu;
    private JMenuItem loadFileItem;
    private JMenuItem loadPresetItem;

    // Simulation
    private JMenu simulateMenu;

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

        loadMenu = new JMenu("Load");
        loadFileItem = new JMenuItem("File");
        loadPresetItem = new JMenuItem("Preset");
        loadMenu.add(loadFileItem);
        loadMenu.add(loadPresetItem);
        fileMenu.add(loadMenu);

        this.add(fileMenu);
    }

    public void AddNewFileCallback(ActionListener callback)
    {
        newItem.addActionListener(callback);
    }

    public void AddLoadFileCallback(ActionListener callback)
    {
        loadFileItem.addActionListener(callback);
    }

    public void AddLoadPresetCallback(ActionListener callback)
    {
        loadPresetItem.addActionListener(callback);
    }

    private void InitializeSimulationMenu()
    {
        simulateMenu = new JMenu("Simulate");

        this.add(simulateMenu);
    }

    public void AddSimulateCallback(ActionListener callback)
    {
        simulateMenu.addActionListener(callback);
    }

}
