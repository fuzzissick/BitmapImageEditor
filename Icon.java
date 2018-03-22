import java.util.ArrayList;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
public class Icon
{
    ArrayList<ArrayList<Pixel>> IconDefault = new ArrayList<ArrayList<Pixel>>();
    


    public Icon() //Default Constructor for the Icon Class. 
    {
        for(int row = 0; row < 40; row++) // Creates a 40x40 ArrayList of Pixel objects.
        {
            ArrayList<Pixel> dank = new ArrayList<Pixel>();
            for(int col = 0; col < 40; col++)
            {
                Pixel sick = new Pixel(255,255,255);
                dank.add(sick);
            }
            IconDefault.add(dank);
        }
    }
    public Icon(int row, int col) // Constructor for the Pixel Class. Creates an ArrayList of Pixel objects based on the rows and collumns user put in.
    {
        for(int i = 0; i < row; i++)
        {
            ArrayList<Pixel> tempArray = new ArrayList<Pixel>();
            for(int j = 0; j < col; j++)
            {
                Pixel temp = new Pixel(255,255,255);
                tempArray.add(temp);
            }
            IconDefault.add(tempArray);
        }

    }
    
    public ArrayList<ArrayList<Pixel>> getPixels()
    {
        return IconDefault;
    }
    
    private ArrayList<Byte> createFile() //Creates the bitmap ArrayList that holds all the data needed for a bitmap image.
    {
        ArrayList<Byte> bmpfile = new ArrayList<Byte>();
        int sizeOfFile = bmpFileCalculator();
        int rawBitMapData = sizeOfFile - 54;
        // **  START OF HEADER **
        //      *BMP Header*
        //adds the letters "B" and "M" to the header 
        bmpfile.add((byte)0x42);
        bmpfile.add((byte)0x4D);

        // Adds the size of file to the header
        bmpfile.add((byte)sizeOfFile);
        bmpfile.add((byte)(sizeOfFile>>8));
        bmpfile.add((byte)(sizeOfFile>>16));
        bmpfile.add((byte)(sizeOfFile>>24));

        //unused data
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);

        //offset where pixel data can be found (THIS IS ALWAYS THE SAME: 54 for the header)
        bmpfile.add((byte)0x36);
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);

        //     *DIB Header*
        //Number of bytes in DIB header
        bmpfile.add((byte)0x28);
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);

        //Width of the Bitmap
        bmpfile.add((byte)getWidth());
        bmpfile.add((byte)(getWidth()>>8));
        bmpfile.add((byte)(getWidth()>>16));
        bmpfile.add((byte)(getWidth()>>24));

        //Height of the Bitmap
        bmpfile.add((byte)getHeight());
        bmpfile.add((byte)(getHeight()>>8));
        bmpfile.add((byte)(getHeight()>>16));
        bmpfile.add((byte)(getHeight()>>24));

        //Number of Color Planes being used
        bmpfile.add((byte)0x01);
        bmpfile.add((byte)0x00);

        //Number of bits per pixel
        bmpfile.add((byte)0x18);
        bmpfile.add((byte)0x00);

        //Pixel compression used
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);

        //Size of Raw Bitmap data
        bmpfile.add((byte)rawBitMapData);
        bmpfile.add((byte)(rawBitMapData>>8));
        bmpfile.add((byte)(rawBitMapData>>16));
        bmpfile.add((byte)(rawBitMapData>>24));

        //Print resolution of the image
        bmpfile.add((byte)0x13);
        bmpfile.add((byte)0x0B);
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x13);
        bmpfile.add((byte)0x0B);
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);

        //Number of Colors in this pallette
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);

        //number of important colors (0 means all are important)
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);
        bmpfile.add((byte)0x00);
        //STARTOFPIXELARRAY
        
        for(int i = 0; i < getHeight(); i++)//Starts at the bottom and goes down from there, because the pixel array is backwards when put into the file
        {
            for(int j = getWidth() - 1; j >=0; j--)//Starts at 0 because this doesn't get put backwards. 
            {
                //gets each value in the array and get there red green and blue values based off the rgb number 
                bmpfile.add((byte)IconDefault.get(i).get(j).getBlue());
                bmpfile.add((byte)IconDefault.get(i).get(j).getGreen());
                bmpfile.add((byte)IconDefault.get(i).get(j).getRed());
            }

            if(((getWidth()*3)%4) > 0)//Checks for padding. 
            {
                for(int l = 0; l < (4-((getWidth() *3)%4)); l++)//Does the math for how many pads are needed
                {
                    bmpfile.add((byte)0x00); // a pad is worth 0
                }
            }
        }    
        return bmpfile; //returns the array of bytes that is the data needed to create a file
    }

    // ** STILL NEED TO CREATE A HEX PRINTER FOR THE PIXEL ARRAY**

    public void printIconHex()
    {
        for(int i = getWidth() - 1; i >=0; i--)
        {

            for(int j = getHeight() - 1; j >=0; j--)
            {
                IconDefault.get(i).get(j).printHex();
            }
            System.out.println("\n");
            
        }
    }
    private int bmpFileCalculator() //Calculates how big the file is based on the header, number of pixels, and number of paddings needed
    {
        int header = 54;
        int rawPixelData = 3*getWidth()*getHeight();
        int padding = 0;
        if((getWidth()*3)%4 != 0)
        {
            padding = getHeight() * (4-((getWidth() *3)%4));
        }
        return header + rawPixelData + padding;
    }
    public int getHeight() // gets the height of the icon using the ArrayList size
    {
        return IconDefault.size();
    }
     public int getWidth() // gets the width of the icon using the Arraylist.get(x) size
    {
        return IconDefault.get(0).size();
    }

    public void setPixel(int col, int row, int red, int green, int blue) // sets a pixel in the pixelarray to a certain color
    {
        IconDefault.get(col).get(row).clear();
        IconDefault.get(col).get(row).setRed(red);
        IconDefault.get(col).get(row).setGreen(green);
        IconDefault.get(col).get(row).setBlue(blue);
    }

public void createBitmap(String filepath)
{
     try{
        
        File file = new File(filepath); // creates the file object for where the file will go
        FileOutputStream fos = new FileOutputStream(file); // creates the file output stream for which the file
        ArrayList<Byte> bmp = createFile(); // creates an arrayList to be manipulated
        byte[] sick = new byte[bmp.size()]; // creates a regular array to be manipulated
        for(int i = 0; i < bmp.size(); i++) //for loop to iterate every byte from the arraylist made for the bmpfile into a regular array
        {
            sick[i] = (byte) bmp.get(i);
        }
     
        fos.write(sick); 
        
        fos.close(); // closes the file output stream
        }
   catch(Exception e)
    {
        System.out.println("hi");
    }
 }

//created during bitmapeditor project. added in because I needed a method that threw in a color.
	public void setPixel(int col, int row, Color color) 
	{
	 IconDefault.get(col).get(row).clear();
     IconDefault.get(col).get(row).setRed(color.getRed());
     IconDefault.get(col).get(row).setGreen(color.getGreen());
     IconDefault.get(col).get(row).setBlue(color.getBlue());
	}
}

//iconDefault.get(4).get(4).setRed();
// when uisng hex, 0x first.
// 4 - ((row * 3)%4)