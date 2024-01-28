package player;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import object.ObjectFrame;


/* PLAYER OBJECT, THIS IS THE OBJECT THE THE PLAYER WILL CONTROL*/
public class Player 
{
	GamePanel gp;															// Players saved instance of gamePanel
	KeyHandler keyH;														// Players saved instance of keyHandler
	
	public BufferedImage player, hit, death;								// HOLDS THE IMAGES USED TO DISPLAY THE PLAYER
	public int imageWidth,imageHeigth;										// HOLDS THE HEIGHT AND WIDTH DIMENTIONS OF THE PLAYERS IMAGES 
	public int imageScreenX, imageScreenY;									// THIS VALUE WILL BE USED TO DRAW THE PLAYER ON THE SCREEN.
	
	public Rectangle solidArea;												// THE RECTANGLE USED WITH COLLISION, ALL VALUES CONECTED TO SOLID AREA ARE RELATED TO THIS RECTANGLE
	public int solidAreaDefultX, solidAreaDefultY;							// SAVES A SET OF SOLID AREA DEFULT VALUES FOR X AND Y, USED IN THE COLLISION CLASS
	public int worldX, worldY;												// PLAYER'S LOCATION ON THE MAP, NOTE THAT (0,0) IS IN THE UPPER LEFT CORNER
	public boolean collisionOn = false;										// BOOLEAN STATMENT, WILL BE SET TO TRUE BY COLLISION CLASS WHEN THE SPACE THE PLAYER IS ABOUT TO BEEN IS LISTED AS SOLID
	
	public String direction;												// STRINGS DOWNS THE DIRECTION THE PLAYER IS FACING, USED INT HE UPDATE METHOD
	public int speed;														// HOLDS THE VALUE OF SPEED 
	
	
	//PLAYER constructor
	public Player(GamePanel gp, KeyHandler keyH, ObjectFrame obj[])
	{
		this.gp = gp;														// Sets the passed in gp value to this.gp
		this.keyH = keyH;													// Sets the passed in keyH value to this.keyH
		solidArea = new Rectangle();										// Instantiation of the rectangle used for collision 
		setDefaultValues();													// Calls the setDefaultValues() method
 		getPlayerImage();													// Calls the getPlayerImage() method
		
	}
	
	
	/* this method is used to set all the default values for the player, 
	 * they are pulled to there own method for organization */
	public void setDefaultValues()
	{
		int SD = 2;
		int SDWH = 4;
		solidAreaDefultX = solidArea.x;										// SAVES A COPY OF SOLID AREA X VALUE FOR USE IN THE COLLISION CLASS 
		solidAreaDefultY = solidArea.y;										// SAVES A COPY OF SOLID AREA Y VALUE FOR USE IN THE COLLISION CLASS
		imageScreenX = gp.windowWidth/2 - (gp.tileSize/2);					// SETS THE VALUES OF IMAGESCREENX TO A POINT IN THE CENTER X VALUE OF THE WINDOW
		imageScreenY = gp.windowHeight/2 - (gp.tileSize/2);					// SETS THE VALUES OF IMAGESCREENY TO A POINT IN THE CENTER Y VALUE OF THE WINDOW
		imageWidth = gp.tileSize;											// SETS IMAGE WIDTH TO TILESIZE, BY DEFAULT 48
		imageHeigth = gp.tileSize;											// SETS IMAGE HEIGHT TO TILESIZE, BY DEFAULT 48
		worldX = gp.tileSize * 2;											// SETS PALYER STARTING LOCATION X 2 TIMES TILESIZE, 96
		worldY = gp.tileSize * 2;											// SETS PALYER STARTING LOCATION Y 2 TIMES TILESIZE, 96
		solidArea.x = SD;													// SETS THE X LOCATION OF THE SOLID AREA INSIDE THE PALYER IMAGE, FROM UPPER LEFT CONER
		solidArea.y = SD;													// SETS THE Y LOCATION OF THE SOLID AREA INSIDE THE PALYER IMAGE, FROM UPPER LEFT CONER
		solidArea.width = gp.tileSize - SDWH;												// SETS THE WIDTH FOR THE SOLID AREA
		solidArea.height = gp.tileSize - SDWH;												// SETS THE HEIGHT FOR THE SOLID AREA
		speed = 3;															// SETS PLAYERS STARTING SPEED VALUE
		direction = "down";													// SETS PLAYERS STARTING DIRECTION VALUE, THE DIRECTION HE IS FACING 
	}
	
	//PLAYER IMAGES ARE LISTED HERE, TRY CATCH IS NEEDED IN CASE THE IMAGE CAN NOT BE FOUND. 
	public void getPlayerImage()
	{
		try
		{
			player = ImageIO.read(getClass().getResourceAsStream("/player/sphere_Blue.png")); //STRING INPUT IS FILE PATH TO RESOURCE_FOLDER
			hit = ImageIO.read(getClass().getResourceAsStream("/player/sphere_Red2.png")); //STRING INPUT IS FILE PATH TO RESOURCE_FOLDER
		}catch(IOException e) {
		
			e.printStackTrace();
		}
	}
	
	//COLTROLS THE UPDATING OF PLAYER VALUES, THIS METHOD IS CALLED FROM THE UPDATE METHOD IN GAMEPANEL
	public void update()
	{
		//CONTROLS THE PICKING UP OF OBJECTS, BULLETS AND ENEMYS
		int objIndex = gp.CO.collisionOvP(this, true);
		if(gp.obj[objIndex] != null)
		{
			if(gp.obj[objIndex].pickUpIndex > 100)
			{
				pickUpObject(objIndex);
				gp.lose = true;
			}
			else
			{
				gp.obj[objIndex].pickUpIndex++;
			}
		}
		
		// SETS THE PLAYERS DIRECTION VALUEE BASED ON THE KEY LISTENER 
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true)
		{
			//CHECKS IF THE PLAYER IS ALIVE IF THE PLAYERS ISALIVE HE CAN MOVE NORMALLY 

			if(keyH.upPressed == true)
			{
				direction = "up";
			}
			else if(keyH.downPressed == true)
			{
				direction = "down";
			}
			else if(keyH.leftPressed ==true)
			{
				direction = "left";
			}
			else if(keyH.rightPressed ==true)
			{
				direction = "right";
			}
			
			/* BEFORE MOVEMENT CAN BE USED TO CHANGE LOCATION WE 
			 * MUST FIND OUT IF WE ARE RUNNING INTO A SOLID OBJECT*/
			
			collisionOn = false;// VALUE RESET
			
			gp.CO.collisionTvP(this);// COLLISION CHECK
			
			/* COLLISION VALUE PASSED IN AND COMPARIED, IF TRUE LOCTION WILL NOT CHANGE, 
			 * IF FALSE LOCTION IS CHANGED AS NORMAL IN RELATION TO SPEED */
			if(collisionOn == false) 
			{
				switch(direction)
				{
				case "up": 
					worldY -= speed; 
					break;
				case "down": 
					worldY += speed;
					break;
				case "left": 
					worldX -= speed;
					break;
				case "right": 
					worldX += speed;
					break;	
				}
			}
		}
	}
	
	/* METHOD IS USED TO PICK UP OBJECTS RUNS THE OBJ[] ARRAY LOOKING 
	 * FOR THE INDEX PASSED FROM THE COLLISION CLASS REMOVING IT*/
	public void pickUpObject(int i)
	{
		if(i != 1999)
		{
			String objectName = gp.obj[i].name;
			switch(objectName)
			{
			case "Bullet":
				gp.obj[i] = null;
				break;
				
			case "Enemy":
				gp.obj[i] = null;
				break;
			}	
		}
	}
	
	/* METHOD USED TO PAINT THE IMAGE TO THE WINDOW, 
	 * THIS IS CALLED IN THE DARW METHOD IN THE GAMEPANEL CLASS*/
	public void draw(Graphics2D g2)
	{
		BufferedImage image;
		if(collisionOn)
		{
			image = hit;
		}
		else
		{
			image = player;
		}
		//SEE THE DISCRIPTION AT THE TOP OF PAGE FOR VLAUES LIST HERE. 
		g2.drawImage(image, imageScreenX, imageScreenY, imageWidth, imageHeigth, null);
	}
}
