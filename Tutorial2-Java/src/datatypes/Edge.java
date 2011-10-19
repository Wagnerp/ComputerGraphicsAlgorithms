package datatypes;

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
	public Vertex[] vertices = new Vertex[2];
	
	/**
	 * Constructor
	 * @param _startPoint
	 * @param _endPoint
	 */
	public Edge(Vertex _startPoint, Vertex _endPoint)
	{
		this.vertices[0] = _startPoint;
		this.vertices[1] = _endPoint;
	}
	
	/*
	 * Returns the inverted version of the edge
	 * e.g. if the edge is (v1,v2), would return (v2,v1).
	 */
	public Edge invert()
	{
		return new Edge(this.vertices[1], this.vertices[0]);
	}
	
	public void print()
	{
		System.out.println("Vertex 1: ");
		vertices[0].print();
		
		System.out.println("Vertex 2: ");
		vertices[1].print();
	}
}
