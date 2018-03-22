import java.io.IOException;

import javax.swing.JOptionPane;
public class Bitmap
{
    public static void main(String args[])//Main Driver
    {
        BitmapGUI sick = new BitmapGUI(Integer.parseInt(JOptionPane.showInputDialog("Please enter number of rows")), Integer.parseInt(JOptionPane.showInputDialog("Please enter number of columns")));
    }
}