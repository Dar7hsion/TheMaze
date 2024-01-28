package main;

import object.OBJ_Bullet;
import object.OBJ_Enemy;
import player.Player;


public class Collision
{
	GamePanel gp;
	
	public Collision(GamePanel gp)//Passes the GamePanel object 
	{
		this.gp = gp;
	}
	
	
	public void collisionTvP(Player player)//Passes in the player object tile vs player
	{
		int entityLeftWorldX = player.worldX + player.solidArea.x;//player in world location X value + location of solid area in side on the left
		int entityRightWorldX = player.worldX + player.solidArea.x + player.solidArea.width;//player in world location X value + location of solid area + the solid area width in side to the Right
		int entityTopWorldY = player.worldY + player.solidArea.y;//player in world location Y value + location of solid area in side at the top
		int entityBottomWorldY = player.worldY + player.solidArea.y + player.solidArea.height;//player in world location Y value + location of solid area + the solid area height in side to the bottom
		int entityLeftCol = entityLeftWorldX/gp.tileSize; 
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		int tileNum1, tileNum2;
		
		switch(player.direction)
		{
			case "up":
				entityTopRow = (entityTopWorldY - player.speed)/gp.tileSize;
				tileNum1 = gp.TS.tileMapLocation[entityLeftCol][entityTopRow];
				tileNum2 = gp.TS.tileMapLocation[entityRightCol][entityTopRow];
				if(gp.TS.tileImageArray[tileNum1].collision_On == true || gp.TS.tileImageArray[tileNum2].collision_On == true)
				{
					player.collisionOn = true;
				}
				break;
				
			case "down":
				entityBottomRow = (entityBottomWorldY + player.speed)/gp.tileSize;//note -
				tileNum1 = gp.TS.tileMapLocation[entityLeftCol][entityBottomRow];
				tileNum2 = gp.TS.tileMapLocation[entityRightCol][entityBottomRow];
				if(gp.TS.tileImageArray[tileNum1].collision_On == true || gp.TS.tileImageArray[tileNum2].collision_On == true)
				{
					player.collisionOn = true;
				}
				break;
				
			case "left":
				entityLeftCol = (entityLeftWorldX - player.speed)/gp.tileSize;
				tileNum1 = gp.TS.tileMapLocation[entityLeftCol][entityTopRow];
				tileNum2 = gp.TS.tileMapLocation[entityLeftCol][entityBottomRow];
				if(gp.TS.tileImageArray[tileNum1].collision_On == true || gp.TS.tileImageArray[tileNum2].collision_On == true)
				{
					player.collisionOn = true;
				}
				break;
				
			case "right":
				entityRightCol = (entityRightWorldX + player.speed)/gp.tileSize;
				tileNum1 = gp.TS.tileMapLocation[entityRightCol][entityTopRow];
				tileNum2 = gp.TS.tileMapLocation[entityRightCol][entityBottomRow];
				if(gp.TS.tileImageArray[tileNum1].collision_On == true || gp.TS.tileImageArray[tileNum2].collision_On == true)
				{
					player.collisionOn = true;
				}
				break;
				
		}
	}	
	
	public void collisionTvB(OBJ_Bullet Bullet)//tile vs bullet
	{
		int entityLeftWorldX = Bullet.worldX + Bullet.solidArea.x;
		int entityRightWorldX = Bullet.worldX + Bullet.solidArea.x + Bullet.solidArea.width;
		int entityTopWorldY = Bullet.worldY + Bullet.solidArea.y;
		int entityBottomWorldY = Bullet.worldY + Bullet.solidArea.y + Bullet.solidArea.height;
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		int tileNum1, tileNum2;
		
		if(Bullet.slope_X < 0)
		{
			entityLeftCol = (entityLeftWorldX - Bullet.speed)/gp.tileSize;
			tileNum1 = gp.TS.tileMapLocation[entityLeftCol][entityTopRow];
			tileNum2 = gp.TS.tileMapLocation[entityLeftCol][entityBottomRow];
			if(gp.TS.tileImageArray[tileNum1].collision_On == true || gp.TS.tileImageArray[tileNum2].collision_On == true)
			{
				Bullet.collisionOn_X = true;
			}
		}
		
		if(Bullet.slope_X > 0)
		{
			entityRightCol = (entityRightWorldX + Bullet.speed)/gp.tileSize;
			tileNum1 = gp.TS.tileMapLocation[entityRightCol][entityTopRow];
			tileNum2 = gp.TS.tileMapLocation[entityRightCol][entityBottomRow];
			if(gp.TS.tileImageArray[tileNum1].collision_On == true || gp.TS.tileImageArray[tileNum2].collision_On == true)
			{
				Bullet.collisionOn_X = true;
			}
		}
		
		if(Bullet.slope_Y < 0)
		{
			entityTopRow = (entityTopWorldY - Bullet.speed)/gp.tileSize;
			tileNum1 = gp.TS.tileMapLocation[entityLeftCol][entityTopRow];
			tileNum2 = gp.TS.tileMapLocation[entityRightCol][entityTopRow];
			if(gp.TS.tileImageArray[tileNum1].collision_On == true && gp.TS.tileImageArray[tileNum2].collision_On == true)
			{
				Bullet.collisionOn_Y = true;
			}
		}
		
		if(Bullet.slope_Y > 0)
		{
			entityBottomRow = (entityBottomWorldY + Bullet.speed)/gp.tileSize;
			tileNum1 = gp.TS.tileMapLocation[entityLeftCol][entityBottomRow];
			tileNum2 = gp.TS.tileMapLocation[entityRightCol][entityBottomRow];
			if(gp.TS.tileImageArray[tileNum1].collision_On == true && gp.TS.tileImageArray[tileNum2].collision_On == true)
			{
				Bullet.collisionOn_Y = true;
			}			
		}		
	}
	
	public void collisionTvE(OBJ_Enemy obs)//tile vs enemy
	{
		int entityLeftWorldX = obs.worldX + obs.solidArea.x;
		int entityRightWorldX = obs.worldX + obs.solidArea.x + obs.solidArea.width;
		int entityTopWorldY = obs.worldY + obs.solidArea.y;
		int entityBottomWorldY = obs.worldY + obs.solidArea.y + obs.solidArea.height;
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		int tileNum1, tileNum2;
		
		
		switch(obs.direction)
		{
			case "up":
					entityLeftCol = (entityLeftWorldX - obs.speed)/gp.tileSize;
					tileNum1 = gp.TS.tileMapLocation[entityLeftCol][entityTopRow];
					tileNum2 = gp.TS.tileMapLocation[entityRightCol][entityTopRow];
					if(gp.TS.tileImageArray[tileNum1].collision_On == true || gp.TS.tileImageArray[tileNum2].collision_On == true)
					{
						obs.collisionOn = true;
					}

				break;
				
			case "down":
					entityRightCol = (entityRightWorldX + obs.speed)/gp.tileSize;
					tileNum1 = gp.TS.tileMapLocation[entityLeftCol][entityBottomRow];
					tileNum2 = gp.TS.tileMapLocation[entityRightCol][entityBottomRow];
					if(gp.TS.tileImageArray[tileNum1].collision_On == true || gp.TS.tileImageArray[tileNum2].collision_On == true)
					{
						obs.collisionOn = true;
					}

				break;
				
			case "left":
					entityTopRow = (entityTopWorldY - obs.speed)/gp.tileSize;
					tileNum1 = gp.TS.tileMapLocation[entityLeftCol][entityTopRow];
					tileNum2 = gp.TS.tileMapLocation[entityLeftCol][entityBottomRow];
					if(gp.TS.tileImageArray[tileNum1].collision_On == true && gp.TS.tileImageArray[tileNum2].collision_On == true)
					{
						obs.collisionOn = true;
					}

				break;
				
			case "right":
					entityBottomRow = (entityBottomWorldY + obs.speed)/gp.tileSize;
					tileNum1 = gp.TS.tileMapLocation[entityRightCol][entityTopRow];
					tileNum2 = gp.TS.tileMapLocation[entityRightCol][entityBottomRow];
					if(gp.TS.tileImageArray[tileNum1].collision_On == true && gp.TS.tileImageArray[tileNum2].collision_On == true)
					{
						obs.collisionOn = true;
					}			
				
				break;
				
		}	
		
	}	
	
	public int collisionOvP(Player player2, boolean player)//object vs player
		{
			int index = 1999;
			
			for(int i = 0; i < gp.obj.length; i++)
			{
				if(gp.obj[i] != null)
				{
					player2.solidArea.x = player2.worldX + player2.solidArea.x;
					player2.solidArea.y = player2.worldY + player2.solidArea.y;
					
					gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
					gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
					
					switch(player2.direction)
					{
					case "up":
						player2.solidArea.y -= player2.speed;
						if(player2.solidArea.intersects(gp.obj[i].solidArea))
						{
							if(gp.obj[i].collision == true)
							{
								player2.collisionOn = true;
							}
							if(player == true)
							{
								index = i;
							}
						}
						break;
					case "down":
						player2.solidArea.y += player2.speed;
						if(player2.solidArea.intersects(gp.obj[i].solidArea))
						{
							if(gp.obj[i].collision == true)
							{
								player2.collisionOn = true;
							}
							if(player == true)
							{
								index = i;
							}
						}
						break;
					case "left":
						player2.solidArea.x -= player2.speed;
						if(player2.solidArea.intersects(gp.obj[i].solidArea))
						{
							if(gp.obj[i].collision == true)
							{
								player2.collisionOn = true;
							}
							if(player == true)
							{
								index = i;
							}
						}
						break;
					case "right":
						player2.solidArea.x += player2.speed;
						if(player2.solidArea.intersects(gp.obj[i].solidArea))
						{
							if(gp.obj[i].collision == true)
							{
								player2.collisionOn = true;
							}
							if(player == true)
							{
								index = i;
							}
						}
						break;
					}
					
					player2.solidArea.x = player2.solidAreaDefultX;
					player2.solidArea.y = player2.solidAreaDefultY;
					
					gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
					gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
				}
			}
			return index;
		}
	
	public int collisionOvB(OBJ_Bullet Bullet, boolean Bullet_T)//object vs bullet
	{
		int index = 1999;
		
		for(int i = 0; i < gp.obj.length; i++)
		{
			if(gp.obj[i] != null && gp.obj[i].index != Bullet.index)
			{
				Bullet.solidArea.x = Bullet.worldX + Bullet.solidArea.x;
				Bullet.solidArea.y = Bullet.worldY + Bullet.solidArea.y;
				
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
				if(Bullet.slope_Y < 0)
				{
					Bullet.solidArea.y -= Bullet.speed;
					if(Bullet.solidArea.intersects(gp.obj[i].solidArea))
					{
						if(gp.obj[i].collision == true)
						{
							Bullet.collisionOn_Y = true;
						}
						if(Bullet_T == true)
						{
							index = i;
							
						}
					}
				}
				else
				{
					Bullet.solidArea.y += Bullet.speed;
					if(Bullet.solidArea.intersects(gp.obj[i].solidArea))
					{
						if(gp.obj[i].collision == true)
						{
							Bullet.collisionOn_Y = true;
						}
						if(Bullet_T == true)
						{
							index = i;
						}
					}
				}
				if(Bullet.slope_X < 0)
				{
					Bullet.solidArea.x -= Bullet.speed;
					if(Bullet.solidArea.intersects(gp.obj[i].solidArea))
					{
						if(gp.obj[i].collision == true)
						{
							Bullet.collisionOn_X = true;
						}
						if(Bullet_T == true)
						{
							index = i;
						}
					}
				}
				else
				{
					Bullet.solidArea.x += Bullet.speed;
					if(Bullet.solidArea.intersects(gp.obj[i].solidArea))
					{
						if(gp.obj[i].collision == true)
						{
							Bullet.collisionOn_X = true;
						}
						if(Bullet_T == true)
						{
							index = i;
						}
					}
				}	
					
				Bullet.solidArea.x = Bullet.solidAreaDefaultX;
				Bullet.solidArea.y = Bullet.solidAreaDefaultY;
				
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}
		}
		return index;
	}
		
		public int collisionOvE(OBJ_Enemy enemy, boolean Enemy_T)//object vs Enemy
		{
			int index = 1999;	
		
		for(int i = 0; i < gp.obj.length; i++)
		{
			if(gp.obj[i] != null && gp.obj[i].index != enemy.index)
			{
				enemy.solidArea.x = enemy.worldX + enemy.solidArea.x;
				enemy.solidArea.y = enemy.worldY + enemy.solidArea.y;
				
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
				if(enemy.slope_Y < 0)
				{
					enemy.solidArea.y -= enemy.speed;
					if(enemy.solidArea.intersects(gp.obj[i].solidArea))
					{
						if(gp.obj[i].collision == true)
						{
							enemy.collisionOn_Y = true;
						}
						if(Enemy_T == true)
						{
							index = i;
							
						}
					}
				}
				else
				{
					enemy.solidArea.y += enemy.speed;
					if(enemy.solidArea.intersects(gp.obj[i].solidArea))
					{
						if(gp.obj[i].collision == true)
						{
							enemy.collisionOn_Y = true;
						}
						if(Enemy_T == true)
						{
							index = i;
						}
					}
				}
				if(enemy.slope_X < 0)
				{
					enemy.solidArea.x -= enemy.speed;
					if(enemy.solidArea.intersects(gp.obj[i].solidArea))
					{
						if(gp.obj[i].collision == true)
						{
							enemy.collisionOn_X = true;
						}
						if(Enemy_T == true)
						{
							index = i;
						}
					}
				}
				else
				{
					enemy.solidArea.x += enemy.speed;
					if(enemy.solidArea.intersects(gp.obj[i].solidArea))
					{
						if(gp.obj[i].collision == true)
						{
							enemy.collisionOn_X = true;
						}
						if(Enemy_T == true)
						{
							index = i;
						}
					}
				}	
					
				enemy.solidArea.x = enemy.solidAreaDefaultX;
				enemy.solidArea.y = enemy.solidAreaDefaultY;
				
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}
		}
		return index;
	}
	
}
