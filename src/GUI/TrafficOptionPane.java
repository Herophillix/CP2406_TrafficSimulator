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
                    JOptionPane.showMessageDialog(null, "Please input number within the range.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    isInputValid = true;
                }
            } catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "Please input a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return toReturn;
    }

}
