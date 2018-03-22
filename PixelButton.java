import javax.swing.JButton;

public class PixelButton extends JButton

{
	private int xCoor;
	private int yCoor;
	public PixelButton(int xcoor, int ycoor)
	{
		super("");
        setXCoor(xcoor);
        setYCoor(ycoor);
    }

    public int getXCoor()
    {
        return xCoor;
    }

    public void setXCoor(int x)
    {
        if(x >= 0)
        {
            xCoor = x;
        }
        else
        {
            xCoor = 0;
        }
    }

    public int getYCoor()
    {
        return yCoor;
    }

    public void setYCoor(int y)
    {
        if(y >= 0)
        {
            yCoor = y;
        }
        else
        {
            yCoor = 0;
        }
    }
}
