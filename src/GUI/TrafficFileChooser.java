package GUI;

import javax.swing.*;
import java.io.File;
import java.awt.event.*;
import java.util.*;

public class TrafficFileChooser extends JFileChooser{

    private ArrayList<ActionListener> onFileOpened;
    private ArrayList<ActionListener> onFileSaved;

    public TrafficFileChooser()
    {
        onFileOpened = new ArrayList<ActionListener>();
        setCurrentDirectory(new File("."));
    }

    public void OpenFile()
    {
        int response = showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION)
        {
            for(ActionListener action: onFileOpened)
            {
                action.actionPerformed(new ActionEvent(getSelectedFile(), ActionEvent.ACTION_PERFORMED, JFileChooser.APPROVE_SELECTION));
            }
        }
    }

    public void AddOnFileOpenedCallback(ActionListener l)
    {
        onFileOpened.add(l);
    }

    public void SaveFile()
    {
        int response = showSaveDialog(null);
        if(response == JFileChooser.APPROVE_OPTION)
        {
            for(ActionListener action: onFileSaved)
            {
                action.actionPerformed(new ActionEvent(getSelectedFile(), ActionEvent.ACTION_PERFORMED, JFileChooser.APPROVE_SELECTION));
            }
        }
    }

    public void AddOnFileSavedCallback(ActionListener l)
    {
        onFileSaved.add(l);
    }
}
