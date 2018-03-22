import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;


public class BitmapGUI extends JFrame

{
    private JPanel buttonLayout;
    private JPanel colorSliders;
    private JPanel lastUsedColors;
    private JSlider redSlider;
    private JSlider greenSlider;
    private JSlider blueSlider;
    private Icon newIcon;
    private int currentRed;
    private int currentGreen;
    private int currentBlue;
    private JTextField currentColor;
    private JCheckBox advanced;

    private ArrayList<lastUsedButton> lastUsedButtons;
    
    private ArrayList<ArrayList<PixelButton>> buttonReferences;
    
    //Constructor, throws in rows and columns as prompted by the user.
    public BitmapGUI(int rows, int columns)
    {
    	super("Quade's Bitmap Editor"); // gotta super because Inheritance.
    	newIcon = new Icon(rows, columns); // create new icon object based on the rows and columns, helps 
    	
    	
    	lastUsedButtons = new ArrayList<lastUsedButton>(5);
    	
    	redSlider = new JSlider(0,255);
    	greenSlider = new JSlider(0,255);
    	blueSlider = new JSlider(0,255);
    	
    	// Initializes buttonReferences ArrayList. This is used because its hard to set the color of Jbuttons
    	// when you down have the addresses of JButton Objects in a GridLayout. The PixelButton class gives the 
    	// Jbuttons an address (and some methods) to make it easy on the programmer.
    	//Adds empty arrayLists to 2D ArrayList to make sure there are no OutOfBounds exceptions when creating
    	//The JButton ArrayList
    	setEmptyArrayLists(rows,columns);
    	
    	//Sets the Layout of the JFrame. Border Layout is used to give JFrame components a location
    	setLayout(new BorderLayout());
    	
    	//adds the button Layout where the Buttons will be added.
    	//This Method has the Method call to add the Jbuttons.
    	setButtonLayout(rows, columns);
    	
    	//Adds the Color Sliders along with other functionality
    	setColorSlider();
    	
    	//Initializes the last used colors to a default white color.
    	//each color is given an action listener that sets the color of the lastUsedButton
    	//to it.
    	setlastUsedColors();
    	
    	//some JFrame stuff to make it look pretty
    	setResizable(false);
    	setSize(1000,1000);
    	setLocation(400, 200);
    	setLocationRelativeTo(null);
    	setVisible(true);
    }
    
    //Sets the buttonReferneces ArrayList and adds empty arraylists to the 2d Arraylist so there are no errors(:
    private void setEmptyArrayLists(int rows,int columns)
    {
    	buttonReferences = new ArrayList<ArrayList<PixelButton>>(rows);
    	for(int i = 0; i < columns; i++) // for every column, add an empty Array List to the button references
    	{
    		ArrayList<PixelButton> temp = new ArrayList<PixelButton>();
    		getbuttonReferences().add(temp);
    	}
	}
    
    //**GETTERS AND SETTERS**//
    private JPanel getbuttonLayout()
    {
    	return buttonLayout;
    }
    
    private JPanel getColorSliders()
    {
    	return colorSliders;
    }
    private JPanel getlastUsedColors()
    {
    	return lastUsedColors;
    }
    private JSlider getredSlider()
    {
    	return redSlider;
    }
    private JSlider getgreenSlider()
    {
    	return greenSlider;
    }
    private JSlider getblueSlider()
    {
    	return blueSlider;
    }
    private Icon getnewIcon()
    {
    	return newIcon;
    }
    private int getcurrentRed()
    {
    	return currentRed;
    }
	private void setcurrentRed(int value) 
	{
		currentRed = value;
	}
    private int getcurrentGreen()
    {
    	return currentGreen;
    }
	private void setcurrentGreen(int value) 
	{
		currentGreen = value;
	}
    private int getcurrentBlue()
    {
    	return currentBlue;
    }
	private void setcurrentBlue(int value) 
	{
		currentBlue = value;
	}
	private JTextField getcurrentColor()
	{
		return currentColor;
	}
	private JCheckBox getadvanced()
	{
		return advanced;
	}
	private ArrayList<ArrayList<PixelButton>> getbuttonReferences()
	{
		return buttonReferences;
	}
	private ArrayList<lastUsedButton> getLastUsedButtons()
	{
		return lastUsedButtons;
	}
	//**GETTERS AND SETTERS**//
	
	//Sets all the Color sliders.
    private void setColorSlider() 
    {
    	colorSliders = new JPanel(); //JPanel's go on the JFrame.
    	
    	JLabel colorPreview = new JLabel(); //This JLabel keeps track of the current color created using the sliders
    	colorPreview.setOpaque(true);
    	
  
    	currentColor = new JTextField(); //Initializes the JTextField which will be determined by individual color sliders.
    	getcurrentColor().setFont(new Font("Ariel", Font.PLAIN, 30));
    	getcurrentColor().setHorizontalAlignment(JTextField.CENTER);
    	
    	JButton createFile = new JButton("Create File"); // creates a button that will be used to create the file.
    	createFile.setFont(new Font("Ariel", Font.PLAIN, 20));
    	createFile.addActionListener(new ActionListener() //this action listener opens the JFilechooser
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser bitmapSave = new JFileChooser();
                bitmapSave.setCurrentDirectory(new File("/Users/Quade Kayle/Pictures")); //set directory that opens when JFileChooser opens
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "bmp");//sets a Filter for bmp images
                bitmapSave.addChoosableFileFilter(filter); 
                int retrival = bitmapSave.showSaveDialog(null); //makes an int called retrieval to check whatever file you select/create is approved.
                if (retrival == JFileChooser.APPROVE_OPTION) 
                {
                    try 
                    {
                    
                    	getnewIcon().createBitmap(bitmapSave.getSelectedFile().getPath()); //creates a bitmap based on selected file's path
                    	
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
            	
                }
            }});
        
    	//Creates Hashtable which will add labels to the sliders
    	Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
    	labelTable.put( new Integer( 0 ), new JLabel("0") );
    	labelTable.put( new Integer( 255 ), new JLabel("255") );
    
        getredSlider().addChangeListener(new ChangeListener()  //gives the red slider a listener to work with.
        {
            public void stateChanged(ChangeEvent e) 
            {
              setcurrentRed(getredSlider().getValue()); // sets the current working red color based on the red slider value
              colorPreview.setBackground(new Color(getcurrentRed(), getcurrentGreen(), getcurrentBlue())); //sets the background of the color previewer
              getcurrentColor().setText("" + ((JSlider) e.getSource()).getValue());//Sets currentColorBox with the numeric value of the slider
              Toolkit.getDefaultToolkit().beep();
              
            }
          }
        );
    	getredSlider().setLabelTable(labelTable); // adds label table to the slider
    	
        getgreenSlider().addChangeListener(new ChangeListener() 
        {
            public void stateChanged(ChangeEvent e) 
            {
              setcurrentGreen(getgreenSlider().getValue());
              colorPreview.setBackground(new Color(getcurrentRed(), getcurrentGreen(), getcurrentBlue()));
              getcurrentColor().setText("" + ((JSlider) e.getSource()).getValue());
              Toolkit.getDefaultToolkit().beep();
            
            }
          }
        );
    	getgreenSlider().setLabelTable(labelTable);
    	
    	

        getblueSlider().addChangeListener(new ChangeListener() 
        {
            public void stateChanged(ChangeEvent e) 
            {
              setcurrentBlue(getblueSlider().getValue());
              colorPreview.setBackground(new Color(getcurrentRed(), getcurrentGreen(), getcurrentBlue()));
              getcurrentColor().setText("" + ((JSlider) e.getSource()).getValue());
              Toolkit.getDefaultToolkit().beep();
            }
        }
        );
    	getblueSlider().setLabelTable(labelTable);
    	
    	//Gives the sliders a label that looks pretty.
    	JLabel sliderLabel = new JLabel("Color Sliders");
    	sliderLabel.setFont(new Font("Ariel", Font.PLAIN, 30));
    	sliderLabel.setHorizontalAlignment(JLabel.CENTER);
    	
      	advanced = new JCheckBox("Advanced");
      	
    	//adds everything that is needed for the color slider grid.
    	getColorSliders().setLayout(new GridLayout(9,1));
    	getColorSliders().add(sliderLabel);
    	getColorSliders().add(getcurrentColor());
    	getColorSliders().add(getredSlider());
    	getColorSliders().add(getgreenSlider());
    	getColorSliders().add(getblueSlider());
    	getColorSliders().add(colorPreview);
    	getColorSliders().add(createFile);
    	getColorSliders().add(getadvanced());
    	
    	
    	getredSlider().setPaintLabels(true);
    	getgreenSlider().setPaintLabels(true);
    	getblueSlider().setPaintLabels(true);
    	
    	getContentPane().add(getColorSliders(), BorderLayout.EAST);//Adds the color sliders to the overall JFRAME
    	
}
    //Adds in the last used color buttons
    private void setlastUsedColors()
    {
    	lastUsedColors = new JPanel();
    	getlastUsedColors().setLayout(new GridLayout(1,5));
    	getlastUsedColors().setSize(100,200);
    	
    	//adds in a button for 5 new buttons. This can be changed.
    	for(int i = 0; i < 5; i++)
    	{
    		getLastUsedButtons().add(new lastUsedButton(255,255,255)); //adds a default lastUsedButton of White
    		getLastUsedButtons().get(i).addActionListener(new ActionListener() //gives each button the same action listener.
            {
        		public void actionPerformed(ActionEvent e)
        		{
        	        setSliders(((lastUsedButton)e.getSource()).getRed(),((lastUsedButton)e.getSource()).getGreen(),((lastUsedButton)e.getSource()).getBlue());
        	    }
            });
    		getlastUsedColors().add(getLastUsedButtons().get(i)); //adds the buttons to the array.
    	}
    	add(getlastUsedColors(), BorderLayout.SOUTH); // adds the jpanel to the JFrame
        
    }
    //Set each Slider based on an RGB Value
	private void setSliders(int r, int g, int b) 
	{
		getredSlider().setValue(r);
		getgreenSlider().setValue(g);
		getblueSlider().setValue(b);
	}
	//Adds in the ButtonLayout to the JFrame
	private void setButtonLayout(int rows, int columns)
    {
		buttonLayout = new JPanel();
		getbuttonLayout().setLayout(new GridLayout(rows, columns)); // creates a new grid Layout which is created according to how many rows and columns you wanted
    	for(int j = getnewIcon().getPixels().get(0).size() - 1 ; j >= 0; j--) //gets the size of the Icon Height
    	{
    		for(int i = getnewIcon().getPixels().get(j).size() - 1; i >= 0; i--)//gets the size of the Icon Width
    		{
    			addJButton(getnewIcon().getPixels().get(j).get(i), i, j); // adds a Jbutton at the same spot where a certain pixel is.
    		}
    	}
    	getbuttonLayout().setVisible(true);
    	add(getbuttonLayout(), BorderLayout.CENTER);
    }
	
	//Adds a JButton object to the buttonLayout Jpanel. this block of code is freaking huge.
	private void addJButton(Pixel pixel, int x, int y) 
	{
		PixelButton temp = new PixelButton(x, y); // Initializes a temporary PixelButton object
		temp.addActionListener(new ActionListener() // gives this PixelButton object a listener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
              	if(getadvanced().isSelected() == false) //this is for the advanced checkbox.
            	{
                    int x = ((PixelButton)e.getSource()).getXCoor();
                    int y = ((PixelButton)e.getSource()).getYCoor();
                    
                    ((PixelButton)e.getSource()).setBackground(new Color(getcurrentRed(), getcurrentGreen(), getcurrentBlue())); // sets the button pressed to the current color
                    getnewIcon().setPixel(y, x, getcurrentRed(), getcurrentGreen(), getcurrentBlue());//sets the pixel pertaining to the pixel button pressed
                    checkAndSetRecentColors(); //checks if the recent color is the same, and or sets recent colors
                    Toolkit.getDefaultToolkit().beep();
            	}
            	else if(getadvanced().isSelected() == true)
            	{
            		JFrame advancedDialogs = new JFrame("Advanced Dialog"); // created a new JFrame to pop up if advanced dialogbox is checked when a button is clicked
            		JTabbedPane tabs = new JTabbedPane(); // creates a tabbed pane to add to the JFrame
            		
            		JPanel rowsandcolumns = new JPanel(); // created a JPanel which will go in the JTabbedPane
            		
            		rowsandcolumns.setLayout(new GridLayout(3,2));
            		rowsandcolumns.add(new JLabel("Rows: "));
            		JTextField rowsToGo = new JTextField(5);
            		rowsToGo.setText("Enter A Number");
            		rowsandcolumns.add(rowsToGo);
            		rowsandcolumns.add(new JLabel("Columns: "));
            		JTextField columnsToGo = new JTextField(5);
            		columnsToGo.setText("Enter A Number");
            		rowsandcolumns.add(columnsToGo);
            		JButton cancel = new JButton("CANCEL"); // if pressed, it exits the advanced dialog options
            		cancel.addActionListener(new ActionListener()
            		{
           			  @Override
           			  public void actionPerformed(ActionEvent e1)
           			  {
           				advancedDialogs.dispose();
           			  }
            		});
            		JButton ok = new JButton("OK"); // will then add based on the text written in.
            		ok.addActionListener(new ActionListener()
             		  {
             			  @Override
             			  public void actionPerformed(ActionEvent e1)
             			  {
             				 int x = ((PixelButton)e.getSource()).getXCoor();
                             int y = ((PixelButton)e.getSource()).getYCoor();
                             int numToTheRight = Integer.parseInt(rowsToGo.getText());
                             int numDown= Integer.parseInt(columnsToGo.getText());
                             ((PixelButton)e.getSource()).setBackground(new Color(getcurrentRed(), getcurrentGreen(), getcurrentBlue()));
                             for(int i = y, c1 = 0; c1 < numDown; i--, c1++) // needed to have multiple things in for loop because I have weird pixels
                             {
                     			for(int j = x,  c2 = 0; c2 < numToTheRight; j--, c2++)
                     			{
                     				if(i >=0 && j >=0)
                     				{
                     					getnewIcon().setPixel(i, j, getcurrentRed(), getcurrentGreen(), getcurrentBlue());
                         				getbuttonReferences().get(i).get(j).setBackground(new Color(getcurrentRed(), getcurrentGreen(), getcurrentBlue()));
                     				} 
                     			}
                     		}
                     		
                     		checkAndSetRecentColors();
                     		advancedDialogs.dispose();
             			  }
             			  
             		  });
             		  
            		
            		 JButton OpenBitmap = new JButton("Add Bitmap"); // adds a Bitmap if pressed
             		 OpenBitmap.addActionListener(new ActionListener()

             		  {
             			  @Override
             			  public void actionPerformed(ActionEvent e2)
             			  {
             				  int x = ((PixelButton)e.getSource()).getXCoor();
             				  int y = ((PixelButton)e.getSource()).getYCoor();
             				  JFileChooser bitmapOpen = new JFileChooser();
             				  bitmapOpen.setCurrentDirectory(new File("/Users/Quade Kayle/Pictures"));
             				  FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "bmp");
             				  bitmapOpen.addChoosableFileFilter(filter);
             				  int retrival = bitmapOpen.showOpenDialog(null);
             				  BufferedImage bmp = null; 
             				  if (retrival == JFileChooser.APPROVE_OPTION) 
             				  {
             					  try 
             					  {
             						  bmp = ImageIO.read(new File(bitmapOpen.getSelectedFile().toPath().toString())); // writes in a buffered image from the selectedfile
             					  }
             					  catch (Exception ex)
             					  {
             						  ex.printStackTrace();
             					  }
             					  Image scaled = bmp.getScaledInstance(500, 500, Image.SCALE_DEFAULT); // creates a scaled image of bitmap Image
             					  ImageIcon icon = new ImageIcon(scaled);
             					  JLabel label = new JLabel(icon, JLabel.CENTER);
             					  
             					  //Checks if you want to add the bitmap based on the preview that will show up
             					  int option = JOptionPane.showConfirmDialog(advancedDialogs, label, "Do you want to add this to your bitmap?", JOptionPane.OK_CANCEL_OPTION);
             					  if(option == JOptionPane.OK_OPTION)
             					  {
             						  int sX = x;
             						  int sY = y;
             						  for(int i = 0; i < bmp.getHeight(); i++)
             						  {
             							  for(int j = 0; j < bmp.getWidth(); j++)
             							  { 	
                               				if(sX >=0 && sY >=0)
                               				{
             								  getbuttonReferences().get(sY).get(sX).setBackground(new Color(bmp.getRGB(j, i)));
             								  getnewIcon().setPixel(sY, sX, new Color(bmp.getRGB(j,i)));
             								  sX--;
                               				}
             							  }
             							  sX = x;
             							  sY--;
             						  }
             						  advancedDialogs.dispose();
             					  }
             					  else if(option == JOptionPane.CANCEL_OPTION)
             					  {
             						  advancedDialogs.dispose();
             					  }
             				  }
             			  }
             		  });

             		 rowsandcolumns.add(cancel);
             		 rowsandcolumns.add(ok);
             		 tabs.addTab("Row and Column Chooser",null,rowsandcolumns, "Choose How Many Rows and Columns to fill with selected Color");
             		 tabs.addTab("Bitmap Opener", null,OpenBitmap , "Choose a Bitmap to add from the selected pixel");
             		 advancedDialogs.add(tabs);
             		 advancedDialogs.setSize(300, 200);
             		 advancedDialogs.setLocation(400, 200);
             		 advancedDialogs.setResizable(false);
             		 advancedDialogs.setVisible(true);
        }}});
		
		temp.setBackground(new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue()));
		getbuttonReferences().get(y).add(0,temp);
		getbuttonLayout().add(temp);
	}
	
	private void checkAndSetRecentColors()
	{
		Color mostrecent = new Color(getcurrentRed(), getcurrentGreen() ,getcurrentBlue());
        if(!(getLastUsedButtons().get(0).getColors().equals(mostrecent)))
        {
        	for(int i = 4; i > 0; i--)
        	{
            	getLastUsedButtons().get(i).setBackground(getLastUsedButtons().get(i-1).getColors());
            	getLastUsedButtons().get(i).setColors(getLastUsedButtons().get(i-1).getColors());
        	}
        	getLastUsedButtons().get(0).setBackground(new Color(getcurrentRed(), getcurrentGreen(), getcurrentBlue()));
        	getLastUsedButtons().get(0).setColors(new Color(getcurrentRed(), getcurrentGreen(), getcurrentBlue()));
        }
	}
}

