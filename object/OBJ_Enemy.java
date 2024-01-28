package object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import ai.Node;
import main.GamePanel;

public class OBJ_Enemy extends ObjectFrame
{
	GamePanel gp;
	
	public static int ObstacleCount = 1;
	//public int mouseX, mouseY;
	//public double slope_X, slope_Y;
	public boolean onPath = false;
	public String direction;
	public int lifeTimer = 0;
	public static int DEATHCOUNT = 2;
	
	public OBJ_Enemy(int mouseX, int mouseY, GamePanel gp)
	{
		//this.mouseX = mouseX;
		//this.mouseY = mouseY;
		this.gp = gp;
		this.solidAreaDefaultX = solidArea.x;
		this.solidAreaDefaultY = solidArea.y;
		setDefaultValues();
		
		try
		{
			image = ImageIO.read(getClass().getResourceAsStream("/objects/sphere_Red.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void setDefaultValues()
	{
		int SD = 2;
		int SDWH = 4;
		name = "Enemy";
		health = 3;
		speed = 1;
		collision = true;
		solidArea = new Rectangle();
		solidArea.x = SD;
		solidArea.y = SD;
		solidArea.width = gp.tileSize - SDWH;
		solidArea.height = gp.tileSize - SDWH;
		pickUpIndex = 101;
		direction = "down";
	}
	
	public void searchPath(int goalCol, int goalRow)
	{
		int startCol = (worldX + solidArea.x)/gp.tileSize;
		int startRow = (worldY + solidArea.y)/gp.tileSize;
		gp.PF.setNodes(startCol, startRow, goalCol, goalRow);
		
		if(gp.PF.search() == true)
		{
			//NEXT WORLDX AND WORLDY
			int nextX = gp.PF.pathList.get(0).col * gp.tileSize;
			int nextY = gp.PF.pathList.get(0).row * gp.tileSize;
			int enLeftX = worldX + solidArea.x;
			int enRightX = worldX + solidArea.x + solidArea.width;
			int enTopY = worldY + solidArea.y;
			int enBottomY = worldY + solidArea.y +solidArea.height;
			
			gp.CO.collisionTvE(this);
			if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize && collisionOn == false) 
			{
				direction = "up";
			}
			else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize && collisionOn == false)
			{
				direction = "down";
			}
			else if(enTopY >= nextY &&  enBottomY < nextY + gp.tileSize && collisionOn == false)
			{
				//LEFT OR RIGHT
				if(enLeftX > nextX)
				{
					direction = "left";
				}
				if(enLeftX < nextX)
				{
					direction = "right";
				}
			}
			else if(enTopY > nextY && enLeftX > nextX)
			{
				//UP OR LEFT
				direction = "up";
				gp.CO.collisionTvE(this);
				if(collisionOn == true)
				{
					
					direction = "left";
				}
			}
			else if(enTopY > nextY && enLeftX < nextX)
			{
				//UP OR RIGHT
				direction = "up";
				gp.CO.collisionTvE(this);
				if(collisionOn == true)
				{
					direction = "right";
				}
			}
			else if(enTopY < nextY && enLeftX > nextX)
			{
				//DOWN OR LEFT
				direction = "down";
				gp.CO.collisionTvE(this);
				if(collisionOn == true)
				{
					direction = "left";
				}
			}
			else if(enTopY < nextY && enLeftX < nextX)
			{
				//DOWN OR RIGHT
				direction = "down";
				gp.CO.collisionTvE(this);
				if(collisionOn == true)
				{
					direction = "right";
				}
			}
			//IF REACHES THE GOAL STOP THE SEARCH
			int nextCol = gp.PF.pathList.get(0).col;
			int nextRow = gp.PF.pathList.get(0).row;
			if(nextCol == goalCol && nextRow ==goalRow)
			{
				onPath = false;
			}
			collisionOn = false;
		}
	}
	public void updateSpeed()
	{
		if(this.speed < 5)
		{
			lifeTimer++;
			if(lifeTimer > 900)
			{
				lifeTimer = 0;
				speed++;
			}
		}
	}
	
	public void updateLocation()
	{
		updateSpeed();
		searchPath(((gp.P1.worldX + gp.P1.solidArea.x)/gp.tileSize),((gp.P1.worldY + gp.P1.solidArea.y)/gp.tileSize));

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
