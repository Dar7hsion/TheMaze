package ai;

public class Node 
{
	Node parent;//HOLDS THE VALUe OF THE LAST CURRENT NODE
	public int col;//Used to keep track of col location
	public int row;//Used to keep track of row location
	int gCost;//GCOST THE DISTANCE BETWEEN THE CURRENT NODE AND THE START NODE
	int hCost;//HCOST THE DISTANCE FROM THE CURRENT NODE TO THE GOAL NODE
	int fCost;//FCOST THE TOTAL COST(G+H) OF THE NODE, "SUM"
	boolean solid, open, checked;// Used in the pathFinding class
	
	public Node(int col, int row)
	{
		this.col = col;
		this.row = row;
	}
}
