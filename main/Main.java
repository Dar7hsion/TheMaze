package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) 
	{
		//This section the instantiation of the games window using JFrame
		//setting window setting, and starting the game thread methods 
		
		JFrame window = new JFrame();											//instantiation of window
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);					//set how the window will respond when the window is closed, application will close upon exit
		window.setResizable(false);												//set it the user can resize the window, set to false
		window.setTitle("Death Maze");										//sets the tile that will be shown at the top of the window
		GamePanel gamePanel = new GamePanel();									//instantiation the gamePaenl class the class from witch all other classes are called
		window.getContentPane().addMouseListener(new MouseListener(gamePanel));	//adds the mouse listener to the window
		window.add(gamePanel);													//Attaches gamePanel to the window 
		window.pack();															//sets window dimensions to a preset size listed in gamePanel 
		window.setLocationRelativeTo(null);										// sets default location when window pops up, when set to null centers the window by default 
		window.setVisible(true);												//sets the visibility, true the window can be see, false the window can do be see
		gamePanel.startGameThread();											//gamePanel thread is called to start thread, this method will instantiate the thread and then call the start method, 
																				//this triggers the run method in gamePanel
	}
}
