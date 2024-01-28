package background;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import ai.Node;
import main.GamePanel;


/* This class is used the build the "TS" object its methods are used build the background of the game*/
public class TileSetter
{
	GamePanel gp;															// This is the gamePanel object passed in from gamePanel during instantiation 
	public Tile[] tileImageArray;											// Tile[] is an object array of Tile objects, Tiles hold two values an image and a boolean value
	public int tileMapLocation[][];											// tileMapLocation[][] is a multidimensional int array, this will hold location values col and row 
	
	// TileSetter constructor, pass in gamePanel
	public TileSetter(GamePanel gp)
	{
		this.gp = gp;														// Saves an instance of gamePanel to "this" object as "gp"
		tileImageArray = new Tile[10];										// instantiation of tileImageArray to build a Tile[] of size 10 
		tileMapLocation = new int[gp.worldCol] [gp.worldRow];				// instantiation of tileMapLocation to build a int[][]of size gp.worldCol and gp.worldRow, 50x50
		getTileImage();														// Calls the getTileImage() method, just sets the values of the tiles in the Tile[], col/row location and solid value
		loadMap("/maps/NEWMAPTEST.txt");										// Calls the loadMaps() method, the input is a string the holds a file past to the correct text file in the "resouce_folder"
	}
	
	//This method sets the Tile[] values
	public void getTileImage()
	{
		int a,b; // lets use change the index values by changing one number
		a = 4; // will be used as an index
		b = 0; // will be used as an index
		try
		{
			tileImageArray[b] = new Tile(); // instantiation of a new elements in the array
			tileImageArray[b].img = ImageIO.read(getClass().getResourceAsStream("/tiles/floor.png"));//sets image

			tileImageArray[a] = new Tile(); // instantiation of a new elements in the array
			tileImageArray[a].img = ImageIO.read(getClass().getResourceAsStream("/tiles/Wall.png"));//sets image
			tileImageArray[a].collision_On = true; //sets tiles numbered 4 as solid 
		}
		catch(IOException e) // try catch is needed in case .img file is not at the end of the file path listed in the getResouceAsStream method
		{
			e.printStackTrace();
		}
	}

	/* This method is used to read a given text file, the argument taken by this method is the string given in the constructor of this class
	 * try catch is needed here for the same reason as the .img files listed above, if the text file is not at the location listed in the file path we would get an error*/
	public void loadMap(String filePath)
	{
		try
		{
			InputStream is = getClass().getResourceAsStream(filePath);			// This instantiation of a InputStream object, this converts the text file into a stream of information in bits
			BufferedReader br = new BufferedReader(new InputStreamReader(is));	// This instantiation of a BufferedReader object takes in the inputStream
			
			int col = 0; //  used to keep track of current col
			int row = 0; //	 used to keep track of current row												
			
			/*while we stay with in the 50x50 size limit for every location of tileMapLocation[col][row], we take in a string "line" 
			 * given to use by the "br" BufferedReader the text document is a grid of 50x50 numbers each number will be read and pass 
			 * indiviualy to the string"line" based on what col and row value are currently listed we add the line number to the tileMapLocation[][]
			 * and move to the next number, the col's and row's are used as index's . */
			while(col < gp.worldCol && row < gp.worldRow)
			{
				String line = br.readLine();
				
				while(col < gp.worldCol)
				{
					String numbers[] = line.split("	"); //THIS IS A TAB NOT A SPACE, COMPACTABLE WITH COPY FROM EXCEL 
					int num = Integer.parseInt(numbers[col]);
					tileMapLocation[col][row] = num;
					col++;	
				}
				if(col == gp.worldCol)
				{
					col = 0;
					row++;
				}
			}
			br.close();
		}
		catch(Exception e){}
	}
	
	/* This is one of the draw methods called in the gamePanel repaint method, we use the filled tileMapLocation[][],
	 *  to find the correct tile for each location*/
	public void draw(Graphics2D g2)
	{	
		int worldCol = 0; //  used to keep track of current col
		int worldRow = 0; //  used to keep track of current row

		while(worldCol < gp.worldCol && worldRow < gp.worldRow)
		{
			int tileNum = tileMapLocation[worldCol][worldRow];								// Sets tileNum to the value of the int array at worldCol, worldRow, 0 or 4
			int worldX = worldCol * gp.tileSize;											// scales to the true world location x value
			int worldY = worldRow * gp.tileSize;											// scales to the true world location y value
			int screenX = worldX - gp.P1.worldX + gp.P1.imageScreenX;						// finds tiles location x in relation to the player
			int screenY = worldY - gp.P1.worldY + gp.P1.imageScreenY;						// finds tiles location y in relation to the player
			
			if(worldX + gp.tileSize > gp.P1.worldX - gp.P1.imageScreenX &&					// This cool check to see if the tile is within the view area of the window if not it wont draw, save resources 
			   worldX - gp.tileSize < gp.P1.worldX + gp.P1.imageScreenX &&
			   worldY + gp.tileSize > gp.P1.worldY - gp.P1.imageScreenY &&
			   worldY - gp.tileSize < gp.P1.worldY + gp.P1.imageScreenY)
			{
			g2.drawImage(tileImageArray[tileNum].img, screenX, screenY, gp.tileSize, gp.tileSize, null);// draws the tile, this process is repeated 50x50 times 
			}
			
			worldCol++;

			if(worldCol == gp.worldCol)
			{
				worldCol = 0;
				worldRow++;
			}
		}
	}
	
}
