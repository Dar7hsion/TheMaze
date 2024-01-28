package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/* THIS CLASS HANDLES THE KEY INPUTS FROM THE USER, THIS DATA IS TAKEN AND PASSED TO THE PLAYER CLASS*/
public class KeyHandler implements KeyListener
{
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	
	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}

	//SETS VALUES TO TRUE
	public void keyPressed(KeyEvent e) 
	{
		int code = e.getKeyCode();//STORES THE NUMBER CODE FOR A PRESSED KEY
		//IF "W" KEY IS PRESSED UPPRESSED = TRUE
		if(code == KeyEvent.VK_W)
		{
			upPressed = true;
		}
		//IF "W" KEY IS PRESSED DOWNPRESSED = TRUE
		if(code == KeyEvent.VK_S)
		{
			downPressed = true;
		}
		//IF "W" KEY IS PRESSED LEFTPRESSED = TRUE
		if(code == KeyEvent.VK_A)
		{
			leftPressed = true;
		}
		//IF "W" KEY IS PRESSED RIGHTPRESSED = TRUE
		if(code == KeyEvent.VK_D)
		{
			rightPressed = true;
		}
	}

	//RESTS VALUES BACK TO FALSE
	public void keyReleased(KeyEvent e) 
	{
		int code = e.getKeyCode();//STORES THE NUMBER CODE FOR A RELEASED KEY
		//IF "W" KEY IS PRESSED UPPRESSED = FALSE
		if(code == KeyEvent.VK_W)
		{
			upPressed = false;
		}
		//IF "W" KEY IS PRESSED DOWNPRESSED = FALSE
		if(code == KeyEvent.VK_S)
		{
			downPressed = false;
		}
		//IF "W" KEY IS PRESSED LEFTPRESSED = FALSE
		if(code == KeyEvent.VK_A)
		{
			leftPressed = false;
		}
		//IF "W" KEY IS PRESSED RIGHTPRESSED = FALSE
		if(code == KeyEvent.VK_D)
		{
			rightPressed = false;
		}
	}
}
