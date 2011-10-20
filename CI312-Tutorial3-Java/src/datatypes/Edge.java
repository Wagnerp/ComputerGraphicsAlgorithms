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
	
	public Vertex getHalfwayPoint()
	{
		float x = (vertices[0].x + vertices[1].x)/2;
		float y = (vertices[0].y + vertices[1].y)/2;
		float z = (vertices[0].z + vertices[1].z)/2;

		System.out.println("Halfway Point: x: " + x + " y: " + y + " z: " + z);
		
		return new Vertex(x, y, z);
	}
	
	public void print()
	{
		System.out.println("Vertex 1: ");
		vertices[0].print();
		
		System.out.println("Vertex 2: ");
		vertices[1].print();
	}
}
