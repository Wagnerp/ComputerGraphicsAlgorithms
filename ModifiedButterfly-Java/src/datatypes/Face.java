package datatypes;

import java.util.ArrayList;
import java.util.Random;

import javax.media.opengl.GL2;

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
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	// the RGB value of the face's colour
	private byte[] colour = new byte[3];
	// holds the direction of each edge
	private byte[] edgeDirection = new byte[3];
	
	private String id = "";
			
	/**
	 * Constructor
	 * @param _edge1
	 * @param _edge2
	 * @param _edge3
	 */
	public Face(Edge _edge1, Edge _edge2, Edge _edge3, byte[] edgeDir, String _id)
	{		
		this.edges.add(_edge1);
		this.edges.add(_edge2);
		this.edges.add(_edge3);
		
		// randomly generate a colour
		Random r = new Random();
		this.colour[0] = (byte)(r.nextInt(254)+1);
		this.colour[1] = (byte)(r.nextInt(254)+1);
		this.colour[2] = (byte)(r.nextInt(254)+1);
		
		this.edgeDirection = edgeDir;		
		this.id = _id;
	}
	
	/**
	 * Splits the edges of the face face into 9 new edges and build new faces
	 * @return ArrayList of the new faces
	 * TODO Face.subdivide: re-factor
	 */
	public Face[] subdivide()
	{ 			
	  // split up the existing edges
		
		Face[] newFaces = new Face[4]; 
		ArrayList<Edge> newEdges = new ArrayList<Edge>(); 
		Vertex[] midPoints = new Vertex[3]; 

		for (int i = 0; i < this.edges.size(); i++)
		{			
			Edge e = this.edges.get(i); 
						
			Vertex v1 = e.getVertices().get(0); 
			Vertex v2 = e.getVertices().get(1); 
			Vertex midPoint = e.getMidPoint();
			
			midPoints[i] = midPoint;
			
			if (this.edgeDirection[i] == 0)
			{
				newEdges.add(new Edge(v1, midPoint));
				newEdges.add(new Edge(midPoint, v2));
			}
			else 
			{
				newEdges.add(new Edge(midPoint, v2));
				newEdges.add(new Edge(v1, midPoint));
			}
		}

		// add the new edges
		newEdges.add(new Edge(midPoints[0], midPoints[1]));
		newEdges.add(new Edge(midPoints[1], midPoints[2]));
		newEdges.add(new Edge(midPoints[2], midPoints[0]));
		
		

		// build the faces
		byte[] edgeDir1 = new byte[3];
		edgeDir1[0] = this.edgeDirection[0];
		edgeDir1[1] = (byte)1;
		edgeDir1[2] = this.edgeDirection[2];
		newFaces[0] = new Face(newEdges.get(0), newEdges.get(8), newEdges.get(5), edgeDir1, this.getId()+"_1");

		byte[] edgeDir2 = new byte[3];
		edgeDir2[0] = this.edgeDirection[0];
		edgeDir2[1] = this.edgeDirection[1];
		edgeDir2[2] = (byte)1;
		newFaces[1] = new Face(newEdges.get(1), newEdges.get(2), newEdges.get(6), edgeDir2, this.getId()+"_2");
		
		byte[] edgeDir3 = new byte[3];
		edgeDir3[0] = (byte)1;
		edgeDir3[1] = this.edgeDirection[1];
		edgeDir3[2] = this.edgeDirection[2];
		newFaces[2] = new Face(newEdges.get(7), newEdges.get(3), newEdges.get(4), edgeDir3, this.getId()+"_3");
		
		byte[] edgeDir4 = new byte[3];
		edgeDir4[0] = (byte)0;
		edgeDir4[1] = (byte)0;
		edgeDir4[2] = (byte)0;
		newFaces[3] = new Face(newEdges.get(6), newEdges.get(7), newEdges.get(8), edgeDir4, this.getId()+"_4");
				
		return newFaces;
	}
	
	/**
	 * Draws each edge
	 * @param gl
	 */
	public void draw(GL2 gl)
	{				
		// draw in an anti-clockwise fashion 
		this.edges.get(0).getVertices().get(edgeDirection[0]).draw(gl);		
		this.edges.get(2).getVertices().get(edgeDirection[2]).draw(gl);
		this.edges.get(1).getVertices().get(edgeDirection[1]).draw(gl);
	}
	
	/**
	 * Prints each edge
	 */
	public void print()
	{
		for (int i = 0; i < this.edges.size(); i++)
		{
			System.out.println("Edge " + (i+1) + ": ");
			this.edges.get(i).print();
		}
	}
	
	//	public getters/setters
	/**
	 * Returns the vertex of the face which isn't in the passed edge
	 * @param edge
	 * @return the vertex
	 */
	public Vertex getPoint(Edge edge)
	{		
		for (int i = 0; i < this.edges.size(); i++) 
		{			
			Edge e = this.edges.get(i);
			
			Vertex edgeV1 = edge.getVertices().get(0);
			Vertex edgeV2 = edge.getVertices().get(1);
			
			if(!e.equals(edge))
			{	
				Vertex v1 = e.getVertices().get(0);
				Vertex v2 = e.getVertices().get(1);
				
				if(v2 == edgeV1 || v2 == edgeV2) return v1;
				else if(v1 == edgeV1 || v1 == edgeV2) return v2;
			}
		}
		
		return null;
	}
	/**
	 * Gets the edge from it's vertices
	 * @param v1, v2 the vertices
	 * @return the edge
	 */
	public Edge getEdge(Vertex v1, Vertex v2) 
	{ 
		for (int i = 0; i < this.edges.size(); i++)
		{
			Edge e = this.edges.get(i); 
			if(e.contains(v1) && e.contains(v2)) return e; 
		} 
		return null; 
	}
	public Edge getEdge(Edge e) { return this.getEdge(e.getVertices().get(0), e.getVertices().get(1)); }
	/**
	 * Returns the edges other than the passed edge
	 * @param edge we don't want returned
	 * @return the edges
	 */
	public ArrayList<Edge> getEdges(Edge edge) 
	{ 
		ArrayList<Edge> otherEdges = new ArrayList<Edge>();
		
		for (int i = 0; i < this.edges.size(); i++)
		{
			Edge e = this.edges.get(i);
			if(e != edge) otherEdges.add(e); 
		}
		
		return otherEdges; 
	}
	public ArrayList<Edge> getEdges() { return this.edges; }
	public String getId() { return this.id; }
	public byte[] getColour() { return this.colour; }
	public byte[] getEdgeDirections() { return this.edgeDirection; }
}
