package datatypes;

// Java imports
import java.util.ArrayList;

import javax.media.opengl.GL2;

/**
 * A class to represent a 3D mesh model
 * Stores the faces that make the model
 *
 * @author Tom
 * @version 0.1
 * @history 13.10.2011: Created class
 */

public class Mesh
{
	// an identifier for the mesh, mainly for debugging
	String name = "";
	// The face objects that make up the mesh
	private ArrayList<Face> faces = new ArrayList<Face>();
	
	/**
	 * Constructor
	 * @param _name id of mesh
	 */
	public Mesh(String _name)
	{
		this.name = _name;
	}
	
	/**
	 * Adds a face to the mesh object
	 * @param faceToAdd
	 */
	public void addFace(Face faceToAdd)
	{
		this.faces.add(faceToAdd);
	}
	
	/**
	 * Subdivides the cube using the Catmull-Clark subdivision scheme
	 * @return the subdivided mesh
	 */
	public Mesh subdivide()
	{				
		ArrayList<Vertex> vertexPoints = new ArrayList<Vertex>();  
		Mesh newMesh = new Mesh("Subdivided Cube");
				
		// calculate the face points
		for (int i = 0; i < this.faces.size(); i++) this.faces.get(i).calculateFacePoint();
		
		// calculate the edge points
		this.calculateEdgePoints();	
		
		// get the vertex points
		for (int i = 0; i < this.faces.size(); i++) 
		{
			for (int j = 0; j < this.faces.get(j).getVertices().size(); j++) 
			{
				Vertex vertexPoint = Face.getVertexPoint(this.faces.get(i).getVertices().get(j));
				if(!vertexPoints.contains(vertexPoint)) vertexPoints.add(vertexPoint); // check if already calculated
			}
		}	
				
		// we have the points, create the new faces
		for (int k = 0; k < this.faces.size(); k++) 
		{
			ArrayList<Face> newFaces = this.faces.get(k).createNewFaces();
			
			for (int l = 0; l < newFaces.size(); l++) 
			{
				newMesh.addFace(newFaces.get(l)); // add new face to newMesh 
			}
			
		}
		
		return newMesh;
	}

	/**
	 * Loops through each edge calculating the edge points
	 * also works out the vertex valence as we go
	 */
	private void calculateEdgePoints()
	{		
		for (int i = 0; i < this.faces.size(); i++) 
		{
			Face face = this.faces.get(i);
			for (int j = 0; j < face.getEdges().size(); j++) 
			{
				Edge edge = face.getEdges().get(j); 
				
				if(face.getEdgeDirections()[j] != 1) // don't bother calculating twice
				{	
					edge.calculateEdgePoint();
					edge.addToVertexValence();
				}
			}
		}
	}
	
	/**
	 * Loops through the mesh and calculates 
	 * which faces wing which edges
	 */
	public void calculateWingingFaces()
	{				
		for (int i = 0; i < this.faces.size(); i++)
		{
			Face currentFace = this.faces.get(i);
			
			for (int j = 0; j < currentFace.getEdges().size(); j++)
			{
				Edge currentEdge = currentFace.getEdges().get(j);
				
				// loop through faces again, looking for other face that contains 'edge'
				for (int k = 0; k < this.faces.size(); k++)
				{
					Face comparisonFace = this.faces.get(k);
					
					if(comparisonFace.getEdge(currentEdge) != null && comparisonFace != currentFace) 
						currentEdge.addWingedFaces(currentFace, comparisonFace);
				}
			}
		}
	}

	/**
	 * Handles drawing the mesh to screen
	 * @param gl a reference to the GL2 object
	 */
	public void draw(GL2 gl)
	{					
		for (int i = 0; i < this.faces.size(); i++)
		{			
			Face face = this.faces.get(i);
			
			if(face.getVertices().size() == 3) gl.glBegin(GL2.GL_TRIANGLES);
			else if(face.getVertices().size() == 4) gl.glBegin(GL2.GL_QUADS);
						
			gl.glColor3ub(face.getColour()[0], face.getColour()[1], face.getColour()[2]);
			face.draw(gl);
			
			gl.glEnd();
		}
	}
	
	/**
	 * Prints the whole mesh
	 */
	public void print()
	{
		for (int i = 0; i < this.faces.size(); i++)
		{
			System.out.println("Face " + (i+1) + ": ");
			this.faces.get(i).print();
		}
	}
	
	// public getters/setters
	
	public Edge getEdge(Edge e)
	{
		for (int i = 0; i < this.faces.size(); i++) return this.faces.get(i).getEdge(e);
		return null;
	}
	
	public ArrayList<Face> getFaces() { return this.faces; }
}
