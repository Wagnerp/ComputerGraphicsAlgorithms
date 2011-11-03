package datatypes;

import java.util.ArrayList;
import javax.media.opengl.GL2;

import utils.Utils;

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
	private ArrayList<Vertex> midPoints = new ArrayList<Vertex>(); 
	
	private byte[] colour = new byte[3];
	private byte[] edgeDirection = new byte[3];
			
	/**
	 * Constructor
	 * @param _edge1
	 * @param _edge2
	 * @param _edge3
	 */
	public Face(Edge _edge1, Edge _edge2, Edge _edge3, byte[] edgeDir)
	{
		this.edges.add(_edge1);
		this.edges.add(_edge2);
		this.edges.add(_edge3);
		
		// randomly generate a colour
		this.colour[0] = (byte)(Utils.generateRandomNumber(254)+1);
		this.colour[1] = (byte)(Utils.generateRandomNumber(254)+1);
		this.colour[2] = (byte)(Utils.generateRandomNumber(254)+1);
		
		this.edgeDirection = edgeDir;		
	}
	
	public ArrayList<Vertex> subdivide()
	{		
		for (int i = 0; i < this.edges.size(); i++)
		{	
			if(this.edgeDirection[i] == 0) midPoints.add(this.edges.get(i).getHalfwayPoint());
		}
		
		// get control points...
		
		return midPoints;
	}
	
	public void draw(GL2 gl)
	{	
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
			System.out.println("Edge " + i+1 + ": ");
			this.edges.get(i).print();
		}
	}
	
	public ArrayList<Edge> getEdges() { return this.edges; }
	public ArrayList<Vertex> getMidPoints() { return this.midPoints; }
	public byte[] getColour() { return this.colour; }
}
