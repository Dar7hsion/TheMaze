package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GUI 
{
	GamePanel gp;
	Font arial_40, arial_80B;
	public boolean messageOn = false;
	public String message = "";
	//int messageCounter = 0;
	//public boolean gameFinished = false;
	int xMod = 3;
	
	public GUI(GamePanel gp)
	{
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);

	}
	
	public void showMessage(String text)
	{
		message = text;
		messageOn = true;
	}
	public void draw(Graphics2D g2)
	{
		if(gp.lose == true)
		{
			g2.setFont(arial_40);
			g2.setColor(Color.RED);
			
			String text;
			int textLength;
			int x, y;
			
			
			text = "Death!!!!";
			g2.drawString(text, 74, 65);
			
			text = "Time Lived: " + gp.time;
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.windowWidth/2 - textLength/xMod;
			y = gp.windowHeight/2 + gp.tileSize*4;
			g2.drawString(text, x, y);	
			
			gp.gameThread = null;
			
			
		}
		else
		{
			g2.setFont(arial_40);
			g2.setColor(Color.white);
			
			String text;
			
			text = "Time alive " + gp.time;
			g2.drawString(text, 74, 105);

		}
	}
}
