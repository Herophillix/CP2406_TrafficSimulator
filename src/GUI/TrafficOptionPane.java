package GUI;

import javax.swing.*;

public class TrafficOptionPane {

    public int GetUserInt(String message, int min, int max)
    {
        boolean isInputValid = false;
        int toReturn = 0;
        while (!isInputValid)
        {
            try
            {
                String userInput = JOptionPane.showInputDialog(null, message + "(" + min + " - " + max + "):");
                if (userInput == null)
                    return -1;
                toReturn = Integer.parseInt(userInput);
                if (toReturn < min || toReturn > max)
                {
                    ShowMessageDialog("Please input number within the range.", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    isInputValid = true;
                }
            } catch (Exception e)
            {
                ShowMessageDialog("Please input a valid number.", JOptionPane.ERROR_MESSAGE);
            }
        }
        return toReturn;
    }

    public void ShowMessageDialog(String message, int messageType)
    {
        String title = "";
        switch (messageType)
        {
            case JOptionPane.ERROR_MESSAGE -> title = "Error";
            case JOptionPane.PLAIN_MESSAGE -> title = "Message";
        }
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }

    public int ShowOptionDialog(String message, Object[] choices, Object defaultValue)
    {
        return JOptionPane.showOptionDialog(null, message, "", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, defaultValue);
    }

}
