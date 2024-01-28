package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import ai.PathFinder;
import background.TileSetter;
import object.ObjectFrame;
import player.Player;

public class GamePanel extends JPanel implements Runnable
{
	/*The gamePanel is the most important class all other objects are derived or instantiated from gamePanel or are instantiated 
	 * from a class that was instantiated form gamePanel, gamePanel hold the default window and world/map dimensions
	 * gamePanel is used and ran as a Thread, using a loop controlled with a delta variable method to control frame rate 
	 * methods listed in the calls and "startGameThread()", "run()", "update()", "setGameThread()" and "paintComponent(Graphics g)"
	 * 
	 * NOTES: IN DRAW WINDOW (0, 0) IS IN UPPER LEFT HAND CORNER ALL LOCATION VALUES ARE IN REPECT THIS STARTING VALUE
	 **/
	
	//Game values
	public boolean lose = false;
	public int time = 0;
	public int gameTimer = 0;
	
	//Scales the Tiles 
	final int originalTileSize = 16;										// Sets the base tile size to 16 as in 16x16 pixels 								
	final int scale = 3; 													// Value used to scale up the tile size	
	public int tileSize = originalTileSize * scale;							// The scaled value for tile size 48x48, without this the size would be very small
	
	//sets window Dominations 
	public final int windowCol = 16; 										// Width of the WINDOW in columns, just a number not true distance 								
	public final int windowRow = 12; 										// Height of the WINDOW in rows, just a number not true distance 
	public final int windowWidth = tileSize * windowCol; 					// This is the WINDOW's true Width representing a true distance, the product of tileSize and windowCol
	public final int windowHeight = tileSize * windowRow;					// This is the WINDOW's true height representing a true distance, the product of tileSize and windowRow
	
	//Sets world Dominations
	public final int worldCol = 50;											// Width of the MAP in columns, just a number not true distance
	public final int worldRow = 50;											// Height of the MAP in rows, just a number not true distance
	public final int worldWidth = tileSize * worldCol;						// This is the MAP's true Width representing a true distance, the product of tileSize and worldCol
	public final int worldHeight = tileSize * worldRow;						// This is the MAP's true Height representing a true distance, the product of tileSize and worldRow
	
	int FPS = 60;															// This the the number of frames per second the game will reach, used in the run() method
		
	//Class Instantiations 
	public TileSetter TS = new TileSetter(this);							// TileStter "TS" is an object the handles the map creation, takes input from a text doc and converts to a a playable map
	public MouseListener ML = new MouseListener(this);						// MouseLististener "ML" is an object thats only job is to take X and Y locations of mouse clicks and pass the values to bullet class	
	public KeyHandler KH = new KeyHandler();								// KeyHandler "KH" is an object thats only job is to take key inputs and convert them to strings and pass the values back the layer class																
	public Collision CO = new Collision(this);								// Collision "CO" is an object that takes location values from different objects and compares them to other objects and if the values are true pass back a true boolean value 
	public ObjectFrame obj[] = new ObjectFrame[2000];						// ObjectFrame "obj[]" is an object array of size 2000, will hold enemy and bullet objects 
	public Player P1 = new Player(this, KH, obj);							// Player "P1" this is the aviator the object the player will control
	public ObjectSetter OS = new ObjectSetter(this, P1, ML);				// ObjectSetter "OS" is an object the is used to place objects in the obj[] array 
	public PathFinder PF = new PathFinder(this);							// PathFinder "PF" is an object used by the enemy objects to calculate the correct path to the player
	public GUI gui = new GUI(this);
	
	Thread gameThread;               										// Creates a Thread called gameThread that will be use in the startGameThread() method to instantiate the Thread, the thread is gamePanel 
	
	
	//GamePanel constructor 
	public GamePanel()		
	{
		this.setPreferredSize(new Dimension(windowWidth, windowHeight));  	// Sets the windows dimensions used by the pack() method used in the main class, windowWidth, windowHeight from above are passed as variables 
		this.setBackground(Color.black);									// Sets the window background color to black
		this.setDoubleBuffered(true);										// Process of drawing graphics into an off-screen image buffer and then copying the contents of the buffer to the screen all at once, when set to true this is enabled 
		this.addKeyListener(KH);											// Adds a specific instance of keyListner, in this case KH
		this.setFocusable(true);											// Indicates if the component can be focused or not, which is determined by the boolean parameter
	}
	
	//Triggers the game thread when called from the main class
	public void startGameThread()		
	{
		gameThread = new Thread(this); 										// Uses the gmaeThread Thread variable to instantiate the gamePanel into the thread
		gameThread.start();													// start() is used to call the run() method
 	}

	/* run() method is used for timing of the game, as long as the thread is still running the while loop will continue, 
	 * on a preset timed interval the game will call the update() and repaint() methods, 
	 * at the game core all other classes and methods will be called from either the update() or repaint() methods.*/
	
	public void run()		
	{
		double drawInterval = 1000000000/FPS; 								// Sets the double value that will be used as the draw interval, has to be in nanoseconds so 1000000000 nanoseconds is equal to 1 second so 1/60 means that every (1/60) of a seconds a new loop will run. 
		double delta = 0;													// Delta is the value we compare to see if its time to update and draw it will be a value between 0 and 1 if over one update and draw													
		long lastTime = System.nanoTime();									// Used to find the time that has elapsed from the last ran loop, value is in nanoseconds 
		long currentTime;	  												// Used to save the current time in nanoseconds 
		
		/* This loop is the core engine, gameThread is the the class gamePanel instantiated as a Thread
		 * We must set limits to the frame rate, if left uncheck the Thread would run on the CPU as fast as the computer could process it
		 * this will run for the life of the game, this method of engine works on a delta variable
		 * first a draw interval must be set, in this case 1000000000/FPS, FPS = 60, 1000000000/60 = 16666666.6667 
		 * this number 16666666.6667 represents the time between each frame if we set FPS to 60 per second
		 * When the loop runs the current time is logged into the "current time variable(nanoseconds)"
		 * we then set delta equal to its self plus the "currentTime" minus the "lastTime" divided by the "drawInerval"
		 * "currentTime" minus the "lastTime" will give you the amount of time that has passed from the last time we ran the loop
		 * we use divide by the "drawInterval to get proportion value "delta"
		 * the value of "delta" is compared to 1 in the if statement the closer to the correct time interval the large the delta value will be 
		 * once the "delta" value is over 1 the if statement will run triggering the update() and repaint() methods
		 * the last step is to decrement delta by 1, we must decrement by 1 and not reset due to the fact that delta could have been over 1 at 
		 * the time and if we rest we could cause miss timed frame rate*/
		while(gameThread != null) 
		{
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1)
			{
				gameTimer++;
				if(gameTimer == 60)
				{
					gameTimer = 0;
					time++;
				}
				update();
				repaint();
				delta--;
			}	
		}
	}
	
	/* This method is used to call all other update methods in the program, other players 
	 * and objects have update methods that must be called before the draw method of each frame
	 * runs through the obj[] and for every int i it checks if its null if not it calls its update method of the object
	 * players updated method is simply called */
	public void update() 
	{	
		for(int i = 0; i < obj.length; i++)
		{
			if(obj[i] != null)
			{
				obj[i].updateLocation();
			}
		}
		P1.update();
	}
	
	// This is what is used to end the game for what ever reason, this method sets the thread to null ending the run() method loop
	public void setGameThread()
	{
		gameThread = null;
	}
	
	/* Similar to the update method the paintComponent() method calls the call methods of other classes like the 
	 * update() method call draw methods use into this method, */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);										// Makes sure the background color will show up around gamePanel draw methods 
		Graphics g2 = (Graphics2D)g; 									// Abstract class that lets use draw, extends the Graphics class 
		TS.draw((Graphics2D) g2);										// Call the draw method of the tile setter object, builds background
		
		for(int i = 0; i < obj.length; i++)								// Loop is used to run the obj[] array and for every int i it checks to see if its null and if not it calls the objects draw method
		{
			if(obj[i] != null)
			{
				obj[i].draw((Graphics2D)g2, this);
			}
		}
		P1.draw((Graphics2D) g2);										// Call the draw method of the player object 
		gui.draw((Graphics2D)g2);
		g2.dispose();													// Component method call, disposes of the graphic data after the draw 
	}
}
