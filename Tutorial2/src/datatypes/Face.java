package datatypes;

import java.util.ArrayList;

/**
 * A simple object to store face information.
 *
 * @author Tom
 * @version 0.1
 * @history 13.10.2011: Created class
 */

public class Face
{
	public ArrayList<Edge> edges = new ArrayList<Edge>();
	public enum PolygonType { TRIANGLE, QUAD }
	public PolygonType faceType;
			
	// for quads
	public Face(Edge _edge1, Edge _edge2, Edge _edge3, Edge _edge4)
	{
		this.edges.add(_edge1);
		this.edges.add(_edge2);
		this.edges.add(_edge3);
		this.edges.add(_edge4);
		
		this.faceType = PolygonType.QUAD;
	}
	
	// for triangles
	public Face(Edge _edge1, Edge _edge2, Edge _edge3)
	{
		this.edges.add(_edge1);
		this.edges.add(_edge2);
		this.edges.add(_edge3);
		
		this.faceType = PolygonType.TRIANGLE;
	}
	
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
