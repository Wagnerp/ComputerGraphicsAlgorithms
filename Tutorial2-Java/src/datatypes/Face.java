package datatypes;

import java.util.ArrayList;

/**
 * A simple object to store face information
 * So far handles tris and quads
 *
 * @author Tom
 * @version 0.1
 * @history 13.10.2011: Created class
 */

public class Face
{
	// the edges in the face
	public ArrayList<Edge> edges = new ArrayList<Edge>();
	
	// used to specify face type
	public enum PolygonType { TRIANGLE, QUAD }
	public PolygonType faceType;
			
	/**
	 * Constructor for quads
	 * @param _edge1
	 * @param _edge2
	 * @param _edge3
	 * @param _edge4
	 */
	public Face(Edge _edge1, Edge _edge2, Edge _edge3, Edge _edge4)
	{
		this.edges.add(_edge1);
		this.edges.add(_edge2);
		this.edges.add(_edge3);
		this.edges.add(_edge4);
		
		this.faceType = PolygonType.QUAD;
	}
	
	/**
	 * Constructor for triangles
	 * @param _edge1
	 * @param _edge2
	 * @param _edge3
	 */
	public Face(Edge _edge1, Edge _edge2, Edge _edge3)
	{
		this.edges.add(_edge1);
		this.edges.add(_edge2);
		this.edges.add(_edge3);
		
		this.faceType = PolygonType.TRIANGLE;
	}
	
	/**
	 * Prints each edge
	 */
	public void print()
	{
		System.out.println("Edge 1: ");
		edges.get(0).print();
		
		System.out.println("Edge 2: ");
		edges.get(1).print();
		
		System.out.println("Edge 3: ");
		edges.get(2).print();
		
		if(this.faceType == PolygonType.QUAD)
		{
			System.out.println("Edge 4: ");
			edges.get(3).print();
		}
	}
}
