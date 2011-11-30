package datatypes;

import java.util.ArrayList;

import javax.media.opengl.GL2;

/**
 * A simple object to store vertex information.
 *
 * @author Tom
 * @version 0.1
 * @history 13.10.2011: Created class
 */

public class Vertex
{
	private float x;
	private float y;
	private float z;
	
	public ArrayList<Edge> neighbouringEdges = new ArrayList<Edge>();

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
	
	public void draw(GL2 gl)
	{
		gl.glVertex3f(x, y, z);
	}
	
	/**
	 * Prints out the vertex's (x,y,z) coords
	 */
	public void print()
	{
		System.out.println("x: " + x + " y: " + y + " z: " + z);
	}
	
	/**
	 * Getters for the coords
	 * @return the coord (float) 
	 */
	public float getX() { return this.x; }
	public float getY() { return this.y; }
	public float getZ() { return this.z; }
}
