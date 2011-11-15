package datatypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	 * Adds two faces to the winged faces list
	 * @param the faces to add
	 */
	public void addWingedFaces(Face face1, Face face2)
	{
		// check we're not trying to add same faces in reverse order
		if(wingedFaces[0] != face2 && wingedFaces[1] != face1 && wingedFaces[0] != face1 && wingedFaces[1] != face2) 
		{
			wingedFaces[0] = face1;
			wingedFaces[1] = face2;
		}
	}

	/**
	 * Calculates the new point from the control points
	 * @param a HashMap of the control points
	 * @return the new point
	 */
	public Vertex calculateNewPoint(HashMap<String, Vertex> controlPoints, float weightedValue)
	{
		Vertex newPoint = new Vertex(0,0,0);

		for (Map.Entry<String, Vertex> entry : controlPoints.entrySet()) 
		{			
			String key = entry.getKey();
			Vertex controlPoint = entry.getValue();
						
			if(key.charAt(0) == 'a')
			{
				controlPoint = Vertex.multiply(controlPoint, 0.5);				
				controlPoint = Vertex.add(controlPoint, (weightedValue*-1));
			}
			else if(key.charAt(0) == 'b')
			{
				controlPoint = (Vertex.multiply(controlPoint, 0.125));
				controlPoint = Vertex.add(controlPoint, (weightedValue*2));
			}
			else if(key.charAt(0) == 'c')
			{
				controlPoint = (Vertex.multiply(controlPoint, -0.0625));
				controlPoint = Vertex.add(controlPoint, (weightedValue*-1));
			}
			else if(key.charAt(0) == 'd') continue; // not using modified butterfly
			
			newPoint = Vertex.add(newPoint, controlPoint, true);
		}
		
		System.out.print("Edge.calculateNewPoint: ");
		newPoint.print();
		return newPoint;
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
	/**
	 * Inverts the vertices of this edge
	 * @return the inverted edge
	 */
	public Edge getInvert()
	{
		return new Edge(this.vertices.get(1), this.vertices.get(0));
	}
}
