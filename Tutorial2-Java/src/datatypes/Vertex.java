package datatypes;

/**
 * A simple object to store vertex information.
 *
 * @author Tom
 * @version 0.1
 * @history 13.10.2011: Created class
 */

public class Vertex
{
	public float x;
	public float y;
	public float z;	

	/**
	 * Constructor
	 * @param xCoord
	 * @param yCoord
	 * @param zCoord
	 */
	public Vertex(float xCoord, float yCoord, float zCoord)
	{
		x = xCoord;
		y = yCoord;
		z = zCoord;
	}
	
	/**
	 * Prints out the vertex's (x,y,z) coords
	 */
	public void print()
	{
		System.out.println("x: " + x + " y: " + y + " z: " + z);
	}
}
