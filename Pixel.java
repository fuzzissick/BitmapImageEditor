public class Pixel
{
    private int rgb; // the single int used for rgb values

    public Pixel(int r, int g, int b) // constructor for pixel object. takes in an red green and blue 
    {
        setRed(r);
        setGreen(g);
        setBlue(b);
    }

    public void clear()//clears the rgb value
    {
        rgb = 0;
    }
    public void setRed(int r)//sets the red value, making sure the value is between 0 and 255
    {
        if(r >= 0 && r <=255)
        {
        rgb = (r<<16);
        }
    }
    public int getRed()//gets the red value, making sure there are no other bytes left
    {
        return (rgb >> 16) & 0xFF;
    }

    public void setGreen(int g) // sets the green value, making sure the value is between 0 and 255
    {
        if(g >= 0 && g <=255)
        {
             rgb = rgb + (g<<8);
        }
    }
    public int getGreen() // gets the green value, making sure there are no other bytes left
    {
        return (rgb >> 8) & 0xFF;
    }
    public void setBlue(int b) // sets the blue value, making sure the value is between 0 and 255
    {
        if(b >= 0 && b <=255)
        {
            rgb = rgb + (b);
        }
    }
    public int getBlue() // gets the blue value, making sure there are no other bytes left
    {
        return (rgb) & 0xFF;
    }

    public int getBit(int num, int place)//method to get the byte needed
    {
        return (num >> 8*place) & 0xFF;
    }

    public void printHex() //prints the hex value of the pixel.
    {
        String hexred = new String(Integer.toHexString(getBit(rgb, 2)).toUpperCase());
        if(hexred.equals("0"))//used incase the value is 0
        {
            hexred = "00";
        }
        String hexgreen = new String(Integer.toHexString(getBit(rgb, 1)).toUpperCase());
        if(hexgreen.equals("0"))
        {
            hexgreen = "00";
        }
        String hexblue = new String(Integer.toHexString(getBit(rgb, 0)).toUpperCase());
        if(hexblue.equals("0"))
        {
            hexblue = "00";
        }

        System.out.print("#" + hexred + hexgreen + hexblue + " "); //prints the actual value.

    }
}
