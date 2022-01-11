package GUI;

import javax.swing.*;
import java.awt.*;

public class TrafficFrame extends JFrame{
    private TrafficMenuBar menuBar;
    private TrafficFileChooser fileChooser;

    public TrafficFrame()
    {
        Initialize();
        InitializeFileChooser();
        InitializeMenuBar();

        this.setVisible(true);
    }

    private void Initialize()
    {
        this.setTitle("Traffic Simulator 2.0");
        this.setSize(1366, 768);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
    }

    private void InitializeFileChooser()
    {
        fileChooser = new TrafficFileChooser();
    }

    private void InitializeMenuBar()
    {
        menuBar = new TrafficMenuBar();

        menuBar.AddLoadFileCallback(e -> fileChooser.OpenFile());
        fileChooser.AddOnFileOpenedCallback(e -> System.out.println(e.getSource()));

        this.setJMenuBar(menuBar);
    }

}