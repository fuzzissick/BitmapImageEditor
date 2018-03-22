import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class lastUsedButton extends JButton
{
	private int red;
	private int green;
	private int blue;
	public lastUsedButton(int r, int g, int b)
	{
		super("Last Used Color");
		setRed(r);
		setGreen(g);
		setBlue(b);
    }
	public int getRed() {
		return red;
	}
	public void setRed(int red) {
		this.red = red;
	}
	public int getGreen() {
		return green;
	}
	public void setGreen(int green) {
		this.green = green;
	}
	public int getBlue() {
		return blue;
	}
	public void setBlue(int blue) {
		this.blue = blue;
	}
	public void setColors(Color col)
	{
		red = col.getRed();
		green = col.getGreen();
		blue = col.getBlue();
	}
	public Color getColors()
	{
		Color retval = new Color(red, green, blue);
		return retval;
	}
	
}
	