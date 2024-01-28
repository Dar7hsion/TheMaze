package main;

import javax.swing.Spring;

import ai.Node;
import object.OBJ_Bullet;
import object.OBJ_Enemy;
import player.Player;



public class ObjectSetter 
{
	GamePanel gp;
	Player player;
	MouseListener ML;
	ObjectSetter(GamePanel gp, Player player, MouseListener ML)
	{
		this.gp = gp;
		this.player = player;
		this.ML = ML;
		
	}
	
	public void setObjectsTest(int x, int y, String t)
	{
		int mouse_x = x;
		int mouse_y = y;
		String type = t;
		int player_center_X = player.worldX;
		int player_center_Y = player.worldY;
		for(int i = 0; i <= 1999; i++)
		{
			if(gp.obj[i] == null)
			{
				switch(type)
				{
				case "bullet":
				gp.obj[i]= new OBJ_Bullet(mouse_x, mouse_y, player_center_X, player_center_Y, player.imageScreenX, player.imageScreenY, gp);
				gp.obj[i].worldX = player_center_X;
				gp.obj[i].worldY = player_center_Y;
				gp.obj[i].index = i;
				break;	
				
				case "enemy":
					
					
				break;
				
				}	
			}
		}
	}
	
	public void setObjects(double x, double y)
	{
		double mouse_x = x;
		double mouse_y = y;
		int player_center_X = player.worldX;
		int player_center_Y = player.worldY;
		for(int i = 0; i <= 1999; i++)
		{
			if(gp.obj[i] == null)
			{
				gp.obj[i]= new OBJ_Bullet(mouse_x, mouse_y, player_center_X, player_center_Y, player.imageScreenX, player.imageScreenY, gp);
				gp.obj[i].worldX = player_center_X;
				gp.obj[i].worldY = player_center_Y;
				gp.obj[i].index = i;
				break;
			}
		}
	}
	
	public void setObjectsObstacle(Node node)
	{
		
		int mouseX = node.col;
		int mouseY = node.row;
		
		for(int i = 0; i < OBJ_Enemy.ObstacleCount; i++)
		{
			for(int j = 0; j <= 1999; j++)
			{
				if(gp.obj[j] == null)
				{
					gp.obj[j]= new OBJ_Enemy(mouseX, mouseY, gp);
					gp.obj[j].worldX = mouseX * gp.tileSize;
					gp.obj[j].worldY = mouseY * gp.tileSize;
					gp.obj[j].index = j;
					break;
				}
			}
		}
	}
}
