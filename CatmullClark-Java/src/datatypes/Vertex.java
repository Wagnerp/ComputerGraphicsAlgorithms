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
	
	// the edges which contain this vertex
	private ArrayList<Edge> incidentEdges = new ArrayList<Edge>();

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
	 * Draws the vertex to the passed GL2 object
	 * @param gl
	 */
	public void draw(GL2 gl)
	{
		gl.glVertex3f(x, y, z);
	}
	
	/**
	 * Prints out the vertex's (x,y,z) coords
	 */
	public void print()
	{
		System.out.println("(x:" + x + " y:" + y + " z:" + z + ")");
	}
	
	/**
	 * Adds the passed edge to the edges list
	 * @param edge to add
	 * @return whether the edge has been added
	 */
	public boolean addEdge(Edge edge)
	{
		// check that the edge isn't already stored
		for (int i = 0; i < this.incidentEdges.size(); i++)
			if(!edge.contains(this) || 
					edge == this.incidentEdges.get(i) || 
					edge == this.incidentEdges.get(i).getInvert()) 
				return false;
	
		this.incidentEdges.add(edge);
		return true;
	}
	
	/**
	 * Getters for the coords
	 * @return the coord (float) 
	 */
	public float getX() { return this.x; }
	public float getY() { return this.y; }
	public float getZ() { return this.z; }
	public ArrayList<Edge> getIncidentEdges() { return this.incidentEdges; }
	
	/**
	 * Static methods
	 */
	
	/**
	 *  Adds the passed vertices
	 * @param the product
	 */
	public static Vertex add(Vertex v1, Vertex v2)
	{
		return new Vertex((v1.getX()+v2.getX()), (v1.getY()+v2.getY()), (v1.getZ()+v2.getZ()));
	}
	// allows the user to pass a single value to add instead of a vertex
	public static Vertex add(Vertex v, float toAdd) { return add(v, new Vertex(toAdd,toAdd,toAdd)); }
	
	/**
	 * Multiplies the vertex by the multiplicand
	 * @param the vertex
	 * @param multiplicand
	 * @return the new vertex
	 */
	public static Vertex multiply(Vertex v, double multiplicand)
	{
		return new Vertex((float)(v.getX()*multiplicand), (float)(v.getY()*multiplicand), (float)(v.getZ()*multiplicand));
	}
	/**
	 * Divides the vertex by the param
	 * @param the vertex
	 * @param number to divide
	 * @return the new vertex
	 */
	public static Vertex divide(Vertex v, double divide)
	{
		return new Vertex((float)(v.getX()/divide), (float)(v.getY()/divide), (float)(v.getZ()/divide));
	}
}
