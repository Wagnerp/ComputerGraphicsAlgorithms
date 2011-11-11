package datatypes;

import java.util.ArrayList;
import java.util.HashMap;

import utils.Utils;

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
	// the faces which wing this edge 
	private Face[] wingedFaces = new Face[2];
	
	private String id = "";
	
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

	/**
	 * Calculates the new point from the control points
	 * @param a HashMap of the control points
	 * @return the new point
	 */
	public Vertex getNewPoint(HashMap controlPoints)
	{
		System.out.println("Edge.getNewPoint");

		Vertex newPoint = new Vertex(0, 0, 0);
		
		// loop through the HashMap using the algorithm 
		// using the weighings to build up the new point... 
		
		return newPoint;
	}
	
	/**
	 * Adds two faces to the winged faces list
	 * @param the faces to add
	 */
	public void addWingedFaces(Face face1, Face face2)
	{
		// make sure we're not just trying to add the same faces in reverse order
		if(wingedFaces[0] != face2 && wingedFaces[1] != face1 && wingedFaces[0] != face1 && wingedFaces[1] != face2) 
		{
			//System.out.println(face1.getId() + " wings " + face2.getId());
			wingedFaces[0] = face1;
			wingedFaces[1] = face2;
		}
		//else System.out.println(face1.getId() + " wings " + face2.getId() + ": Trying to add the same winged faces, faces not added");
	}
	
	/**
	 * Check to see whether the passed vertex is in this edge
	 * @param vertex to check for
	 * @return Boolean as appropriate
	 */
	public boolean contains(Vertex vertex)
	{
		for (int i = 0; i < this.vertices.size(); i++)
		{
			Vertex v = this.vertices.get(i);
			if(v == vertex) return true;
		}
		return false;
	}
	
	/**
	 * Prints out each vertex
	 */
	public void print()
	{
		System.out.println("Vertex 1: ");
		vertices.get(0).print();
		
		System.out.println("Vertex 2: ");
		vertices.get(1).print();
	}
	
	// public getters/setters
	public ArrayList<Vertex> getVertices() { return vertices; }
	public Face[] getWingedFaces() { return wingedFaces; }
	// returns the inverted version of this edge
	public Edge getInvert()
	{
		return new Edge(this.vertices.get(1), this.vertices.get(0));
	}
}
