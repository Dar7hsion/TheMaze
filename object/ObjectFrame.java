package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
/* CLASS HOLDS THE DRAW METHOD FOR THE OBJECT CLASS, THE ENEMY AND BULLET CLASSES BOTH EXTEND THE OBJECTFRAME 
 * CLASS, THIS CLASS ALSO HOLDS SOME OF THE ATTIBUTES UNIVERSAL TO ALL OBJECTS IN THIS GAME*/
public class ObjectFrame 
{
	public BufferedImage image;															//HOLDS THE IMAGE OF A GIVEN OBJECT
	public String name;																	//HOLDS THE NAME IN OF THE OBJECT IN THE FORM OF A STRING
	public int health = 1;																//HOLDS AND SETS THE OBJECT HEALTH TO 1 
	public int speed = 1;																//HOLDS AND SETS THE OBJECT SPEED TO 1
	public int pickUpIndex = 0;															//THIS IS COUNTER USED TO TELL WHEN ITS OK TO PICK AN OBJECT UP
	public boolean collisionOn, collisionOn_X, collisionOn_Y;							//HOLDS VALUES USED TO HOLD TELL IF THE OBJECT HAS COLLIDED WITH A NOTHER OBJECT
	public boolean collision = false;													//SETS THE DEFAULT VALUE OF COLLISION TO FALSE, THIS DETERMINES IF A OBJECT CAN TOCH ANOTHER OBJECT OR JUST PASS THOUGHT IT
	public int worldX, worldY, index;													//HOLDS THE WORLD X AND Y VALUES THE OBJECTS LOCATION AND THE INDEX VALUE THAT IS THE OBJECTS LOCATION IN THE OBJ[] ARRAY
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);							//SETS THE DEFAULT SOLIDAREA OF AN OBJECT, (0,0,48,48) SETS THE AREA TO TH WHOLE OBJECT
	public int solidAreaDefaultX = 0;													//SETS THE DEFAULT SOLID AREA X TO 0
	public int solidAreaDefaultY = 0;												//SETS THE DEFAULT SOLID AREA Y TO 0
	public double slope_Y;																//HOLDS THE Y VOLOCITY FOR BULLET OBJECT
	public double slope_X;																//HOLDS THE X VOLOCITY FOR BULLET OBJECT
	
	
	/* DRAW METHOD FOR ALL OBJECTS, OBJECT CLASSES EXTEND OBJECTFRAME*/
	public void draw(Graphics2D g2, GamePanel gp)
	{
		//SETS THE X AND Y SCREEN LOCATIONS IN RELATION TO THE PLAYERS X AND Y SCREEN LOCATION
		int screenX = worldX - gp.P1.worldX + gp.P1.imageScreenX;
		int screenY = worldY - gp.P1.worldY + gp.P1.imageScreenY;
		
		//IF THE OBJECT IS NOT IN THE GAME WINDOW DO NOT PAINT OBJECT
		if(worldX + gp.tileSize > gp.P1.worldX - gp.P1.imageScreenX &&
		   worldX - gp.tileSize < gp.P1.worldX + gp.P1.imageScreenX &&
		   worldY + gp.tileSize > gp.P1.worldY - gp.P1.imageScreenY &&
		   worldY - gp.tileSize < gp.P1.worldY + gp.P1.imageScreenY)
		{
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // DRAW, METHOD IS CALLED FROM THE GAMEPANEL REPAINT METHOD
		}	
	}

	public void updateLocation() {
		// TODO Auto-generated method stub
		
	}	
}
