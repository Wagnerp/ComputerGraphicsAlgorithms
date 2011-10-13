package datatypes;

import javax.media.opengl.GL2;

/**
 * A simple object to store face information.
 * Faces are triangular rather than quad
 *
 * @author Tom
 * @version 0.1
 * @history 13.10.2011: Created class
 */

public class Face
{
	Edge[] edges = new Edge[4];
	
	Boolean[] edgeDirection = { true, true, true, true };
			
	public Face(Edge _edge1, Edge _edge2, Edge _edge3, Edge _edge4, Boolean[] _edgeDirection)
	{
		this.edges[0] = _edge1;
		this.edges[1] = _edge2;
		this.edges[2] = _edge3;
		this.edges[3] = _edge4;
		
		if(_edgeDirection.length == 4)
		{
			this.edgeDirection = _edgeDirection;
		}
		else System.out.println("Invalid edge direction passed");
	}
	
	public void print()
	{
		System.out.println("Edge 1: ");
		edges[0].print();
		
		System.out.println("Edge 2: ");
		edges[1].print();
		
		System.out.println("Edge 3: ");
		edges[2].print();
		
		System.out.println("Edge 4: ");
		edges[3].print();
	}
}
