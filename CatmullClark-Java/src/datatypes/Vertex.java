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
	 * @param _xCoord
	 * @param _yCoord
	 * @param _zCoord
	 */
	public Vertex(float _xCoord, float _yCoord, float _zCoord)
	{
		x = _xCoord;
		y = _yCoord;
		z = _zCoord;
	}
	
	/**
	 * Draws the vertex to the passed GL2 object
	 * @param _gl
	 */
	public void draw(GL2 _gl)
	{
		_gl.glVertex3f(x, y, z);
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
	public boolean addEdge(Edge _e)
	{
		// check that the edge isn't already stored
		for (int i = 0; i < this.incidentEdges.size(); i++)
			if(!_e.contains(this) || 
					_e == this.incidentEdges.get(i) || 
					_e == this.incidentEdges.get(i).getInvert()) 
				return false;
	
		this.incidentEdges.add(_e);
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
	public static Vertex add(Vertex _v1, Vertex _v2)
	{
		return new Vertex((_v1.getX()+_v2.getX()), (_v1.getY()+_v2.getY()), (_v1.getZ()+_v2.getZ()));
	}
	// allows the user to pass a single value to add instead of a vertex
	public static Vertex add(Vertex v, float toAdd) { return add(v, new Vertex(toAdd,toAdd,toAdd)); }
	
	/**
	 * Multiplies the vertex by the multiplicand
	 * @param the vertex
	 * @param _multiplicand
	 * @return the new vertex
	 */
	public static Vertex multiply(Vertex _vertex, double _multiplicand)
	{
		return new Vertex((float)(_vertex.getX()*_multiplicand), (float)(_vertex.getY()*_multiplicand), (float)(_vertex.getZ()*_multiplicand));
	}
	/**
	 * Divides the vertex by the param
	 * @param the vertex
	 * @param number to divide
	 * @return the new vertex
	 */
	public static Vertex divide(Vertex _vertex, double _divisor)
	{
		return new Vertex((float)(_vertex.getX()/_divisor), (float)(_vertex.getY()/_divisor), (float)(_vertex.getZ()/_divisor));
	}
}
