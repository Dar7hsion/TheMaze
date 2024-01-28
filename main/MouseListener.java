package main;
import java.awt.event.MouseEvent;

import player.Player;


//CLASS TAKES MOUSE INPUT IN THE FORM OF X AND Y LOCATION UPON MOUSEPRESSED
public class MouseListener implements java.awt.event.MouseListener 
{
	GamePanel gp; // PASS IN GAMEPANEL SO THE OBJECT SETTER METHODS CAN BE USED 
	
	
	public MouseListener(GamePanel gp)
	{
		this.gp = gp;
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{

	
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		double mouse_x = e.getX(); // X LOCATION UPON MOUSEPRESSED
		double mouse_y = e.getY(); // Y LOCATION UPON MOUSEPRESSED
		gp.OS.setObjects(mouse_x, mouse_y); // PASSES THE X AND Y VALUES TO THE OBJECT SETTERS SETOBJECT() METHOD
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
	
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{

	}
}
