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
	private static ArrayList<Face> faces = new ArrayList<Face>();
	
	/**
	 * Constructor
	 * @param _name id of mesh
	 */
	public Mesh(String _name)
	{
		System.out.println("Mesh.Mesh");		
		this.name = _name; 
	}
	
	/**
	 * Adds a face to the mesh object
	 * @param faceToAdd
	 */
	public void addFace(Face faceToAdd)
	{
		faces.add(faceToAdd);
	}
	
	/**
	 * Subdivides the cube using the Catmull-Clark subdivision scheme
	 * @return the subdivided mesh
	 */
	public Mesh subdivide()
	{		
		System.out.println("Mesh.subdivide");
		
		ArrayList<Vertex> vertexPoints = new ArrayList<Vertex>();  
		Mesh newMesh = new Mesh("Catmull-Clark Mesh");
		
		// 
		// calculate the face points
		// 			_ _ _ 
		//			 !			!
		//			!         !
		//       !  X   X  !
		//        !   ^   !
		//          !!!!!
		//
		// ÁERROR! ÁERROR! ÁERROR!
		//
		for (int i = 0; i < faces.size(); i++) faces.get(i).calculateFacePoint();
		
		// calculate the edge points
		this.calculateEdgePoints();	
		
		// get the vertex points
		for (int i = 0; i < faces.size(); i++) 
		{
			for (int j = 0; j < faces.get(j).getVertices().size(); j++) 
			{
				Vertex vertexPoint = Face.getVertexPoint(faces.get(i).getVertices().get(j));
				if(!vertexPoints.contains(vertexPoint)) vertexPoints.add(vertexPoint); // check if already calculated
			}
		}	
		
		// we have the points, create the new faces
		for (int i = 0; i < faces.size(); i++) 
		{
			System.out.println("faces.get(i): " + faces.get(i));
			
			ArrayList<Face> newFaces = faces.get(i).createNewFaces();
			for (int j = 0; j < newFaces.size(); j++) newMesh.addFace(newFaces.get(j)); // add new face to newMesh 
		}
		
		return newMesh;
	}

	/**
	 * Loops through each edge calculating the edge points
	 * also works out the vertex valence as we go
	 */
	private void calculateEdgePoints()
	{
		for (int i = 0; i < faces.size(); i++) 
		{
			Face face = faces.get(i);
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
		System.out.println("Mesh.calculateWingingFaces: f:" + faces.size());
		
		for (int i = 0; i < faces.size(); i++)
		{
			Face currentFace = faces.get(i);
			
			for (int j = 0; j < currentFace.getEdges().size(); j++)
			{
				Edge currentEdge = currentFace.getEdges().get(j);
				
				// loop through faces again, looking for other face that contains 'edge'
				for (int k = 0; k < faces.size(); k++)
				{
					Face comparisonFace = faces.get(k);
					
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
		System.out.println("Mesh.draw: " + this.name);
		
		for (int i = 0; i < faces.size(); i++)
		{
			Face face = faces.get(i);
			
			gl.glBegin(GL2.GL_TRIANGLES);
			
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
		for (int i = 0; i < faces.size(); i++)
		{
			System.out.println("Face " + (i+1) + ": ");
			faces.get(i).print();
		}
	}
	
	// public getters/setters
	
	public static Edge getEdge(Edge e)
	{
		for (int i = 0; i < faces.size(); i++) return faces.get(i).getEdge(e);
		return null;
	}
	
	public ArrayList<Face> getFaces() { return faces; }
}
