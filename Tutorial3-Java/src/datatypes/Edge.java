package datatypes;

import java.util.ArrayList;

/**
 * A simple object to store edge information
 *
 * @author Tom
 * @version 0.1
 * @history 13.10.2011: Created class
 */

public class Edge
{
	// an array of the vertices, clockwise
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	
	/**
	 * Constructor
	 * @param _startPoint
	 * @param _endPoint
	 */
	public Edge(Vertex _startPoint, Vertex _endPoint)
	{
		this.vertices.add(_startPoint);
		this.vertices.add(_endPoint);
	}
	
	public Vertex getHalfwayPoint()
	{
		float x = (vertices.get(0).getX() + vertices.get(1).getX())/2;
		float y = (vertices.get(0).getY() + vertices.get(1).getY())/2;
		float z = (vertices.get(0).getZ() + vertices.get(1).getZ())/2;

		System.out.println("Halfway Point: x: " + x + " y: " + y + " z: " + z);
		
		return new Vertex(x, y, z);
	}
	
	public void print()
	{
		System.out.println("Vertex 1: ");
		vertices.get(0).print();
		
		System.out.println("Vertex 2: ");
		vertices.get(1).print();
	}
	
	public ArrayList<Vertex> getVertices() { return vertices; }
}
