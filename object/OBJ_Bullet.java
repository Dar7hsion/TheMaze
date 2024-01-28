package object;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_Bullet extends ObjectFrame// CHILD CLASS OR OBJECTFRAME
{
	GamePanel gp;																		// SAVED VALUE OF GAMEPANEL
	public double angle, mouse_x, mouse_y;												// DOUBLE VALUES WILL HOLD THE DATE NEEDED TO CALUCLATE THE RATE OF CHANGE IN THE X AND Y DIRECTIONS THAT THE BULLETS WILL USE TO UPDATE EACH FRAME
	public int location_current_X, location_current_Y, imageScreenX, imageScreenY;		// THE LOCATION WHERE THE BULLET SAVES ITS CURRENT MAP LOCATION AND ON SCREEN LOCATION 
	
	/* CONTRUCTOR OF THE BULLER OBJECT, VALUES PASSED IN FROM OBJECT SETTER OBJECT, X AND Y ON SCREEN LOCATION OF 
	 * MOUSE PRESSED, X AND Y LOCATION OF PLAYER'S CENTER AND PLAYER'S X AND Y OF SCREEN LOCATION THESE VALUES ARE 
	 * USED IN THE SETSLOPE() METHOD*/
	public OBJ_Bullet(double mouse_x, double mouse_y, int player_center_X, int player_center_Y, int imageScreenX, int imageScreenY, GamePanel gp)
	{
		setDefaultValues();											// CALLS THE SETDEFAULTVALUES() METHOD 
		this.gp = gp;												// Sets the passed in gp value to this.gp
		this.mouse_x = mouse_x;										// Sets the passed in mouse_x value to this.mouse_x
		this.mouse_y = mouse_y;										// Sets the passed in mouse_y value to this.mouse_y
		this.solidAreaDefaultX = solidArea.x;						// SETS THE DEFAULT SOLID AREA X
		this.solidAreaDefaultY = solidArea.y;						// SETS THE DEFAULT SOLID AREA Y
		this.imageScreenX = imageScreenX;							// THIS X VALUE WILL BE USED TO DRAW THE OBJECT ON THE SCREEN.
		this.imageScreenY = imageScreenY;							// THIS Y VALUE WILL BE USED TO DRAW THE OBJECT ON THE SCREEN.
		this.location_current_Y = player_center_Y;					// SETS THE STARTING Y LOCATION TO THE PLAYERS LOCATION 
		this.location_current_X = player_center_X;					// SETS THE STARTING X LOCATION TO THE PLAYERS LOCATION 
		setSlope(mouse_x, mouse_y, imageScreenX, imageScreenY);		// CALLS THE SETSLOPE METHOD
		
		//TRY/CATCH IS NEEDED JUST IN CAST THE FILE IS NOT AT THE LOCATION 
		try
		{
			image = ImageIO.read(getClass().getResourceAsStream("/objects/bullet.png"));//FILE PATH TO IMAGE FILE IS PASSED AS A STRING 
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//THIS METHOD IS USED TO HOLD ALLL THE DEFAULT VALUES FOR A BULLET OJECT
	public void setDefaultValues()
	{
		name = "Bullet";           	    // OBJECT NAME
		speed = 10;						// OBJECT SPEED
		collision = true;				// OBJECT IS SOLID AND CAN TOUCH OTHERR OBJECTS 
		solidArea = new Rectangle();	// RECTANGLE THAT WILL BE THE BULLETS SOLID AREA  
		solidArea.x = 16;				// WHERE THE SOLID AREA STARTS IN THE IMAGE, X VALUE
		solidArea.y = 16;				// WHERE THE SOLID AREA STARTS IN THE IMAGE, Y VALUE
		solidArea.width = 16;			// SOLID AREA WIDTH
		solidArea.height = 16;			// SOLID AREA HEIGHT
		collisionOn_X = false;			// THESE ARE USED IN THE COLLISION CALLS USED TO FIND THE CAHNGE IN X SLOPE
		collisionOn_Y = false;			// THESE ARE USED IN THE COLLISION CALLS USED TO FIND THE CAHNGE IN Y SLOPE
	}
	
	// SETS THE SLOPE X AND Y FOR THE BULLET USING THE PYTHAGOREAN THEOREM
	public void setSlope(double mouse_x, double mouse_y, int imageScreenX, int imageScreenY)
	{
		angle = Math.atan2(mouse_y - imageScreenY, mouse_x - imageScreenX);
		this.slope_X = speed*(Math.cos(angle));
		this.slope_Y = speed*(Math.sin(angle));
	}
	
	// UPDATE METHOD IS USED TO ADJUST THE WORLD LOCATION OF THE BULLET OBJECT, CALLED FROM THE GAMEPANEL CLASS UPDATED METHOD
	public void updateLocation()
	{
		//CHECKS TO SEE IF THE OBJECT IS READY TO BE PICKED UP
		pickUpIndex++;
		if(pickUpIndex > 10000)
		{
			gp.obj[this.index] = null;
		}
		
		//CHECKS COLLISION BETWEEN OBJECTS AND THE BULLET
		int objIndex = gp.CO.collisionOvB(this, true);
		
		pickUpObject(objIndex);
		
		//CHECKS THE COLLISION BETWEEN TILES AND THE BULLET 
		gp.CO.collisionTvB(this);
		
		if(collisionOn_Y == true)
		{
			this.slope_Y *= -1;
			collisionOn_Y = false;
			if(gp.obj[objIndex] != null)
			{
				gp.obj[objIndex].slope_Y *= -1;
				gp.obj[objIndex].collisionOn_Y = false;	
			}
		}
		if(collisionOn_X == true)
		{
			this.slope_X *= -1;
			collisionOn_X = false;
			if(gp.obj[objIndex] != null)
			{
				gp.obj[objIndex].slope_X *=-1;
				gp.obj[objIndex].collisionOn_X = false;
			}	
		}
		this.worldY = location_current_Y + (int)Math.round(slope_Y);
		this.worldX = location_current_X + (int)Math.round(slope_X);
		location_current_Y = worldY;
		location_current_X = worldX;
	}
	
	public void pickUpObject(int i)
	{
		if(i != 1999)
		{
			String objectName = gp.obj[i].name;
			switch(objectName)
			{
			case "Bullet":
				gp.obj[i] = null;
				gp.obj[this.index] = null;
				break;
			case "Enemy":
				gp.obj[i] = null;
				gp.obj[this.index] = null;
				for(int j = 0; j < OBJ_Enemy.DEATHCOUNT; j++)
				{
					gp.OS.setObjectsObstacle(gp.PF.findRandomOpenArea());
				}
				break;	
				
			}	
		}
	}
}
