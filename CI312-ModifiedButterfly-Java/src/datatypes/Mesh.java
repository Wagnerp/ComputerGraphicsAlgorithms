package datatypes;

// Java imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.media.opengl.GL2;

// Java Library imports
import utils.Utils;

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
	
	public Mesh subdivide(double weight)
	{
		System.out.println("Mesh.subdivide using weighting of " + weight + ": There are " + this.faces.size() + " faces");
		
		// will be the new subdivided mesh
		Mesh newMesh = new Mesh("Butterfly Mesh");
		
		calculateControlPoints();
		
		return newMesh;
	}

	/**
	 * Default_method_description
	 */
	private void calculateControlPoints()
	{
		System.out.println("Mesh.calculateControlPoints");
		
		// create a HashMap for the control points
		HashMap<String,Vertex> controlPoints = new HashMap<String,Vertex>();
		controlPoints.put("a1", new Vertex());
		controlPoints.put("a2", new Vertex());
		controlPoints.put("b1", new Vertex());
		controlPoints.put("b2", new Vertex());
		controlPoints.put("c1", new Vertex());
		controlPoints.put("c2", new Vertex());
		controlPoints.put("c3", new Vertex());
		controlPoints.put("c4", new Vertex());
		controlPoints.put("d1", new Vertex());
		controlPoints.put("d2", new Vertex());		
		
		// loop through the faces
//		for (int i = 0; i < this.faces.size(); i++)
		for (int i = 0; i < 1; i++)
		{
			Face face = this.faces.get(i);
					
			// loop through the edges
//			for (int j = 0; j < face.getEdges().size(); j++)
			for (int j = 2; j < 3; j++)
			{
				Edge currentEdge = face.getEdges().get(j);
				Face currentFace = null; 
				Face previousFace = currentEdge.getWingedFaces()[0];				
				// to save looking up repeatedly in the HashMap
				Vertex a1, a2;
				
				System.out.println("Calculating control points for Face " + face.getId() + " edge" + j);
				
				// A POINTS ----------------> we already know the a points

				controlPoints.put("a1", currentEdge.getVertices().get(0));
				controlPoints.put("a2", currentEdge.getVertices().get(1));
				// also set the variables
				a1 = controlPoints.get("a1");
				a2 = controlPoints.get("a2");
				
				// B1 ---------------->
				
				controlPoints.put("b1", currentEdge.getWingedFaces()[0].getPoint(currentEdge));
				
				// C1 ---------------->
	
				// update vars
				currentEdge = previousFace.getEdge(a1, controlPoints.get("b1"));
				currentFace = (currentEdge.getWingedFaces()[0] != previousFace) ? currentEdge.getWingedFaces()[0] : currentEdge.getWingedFaces()[1];				
				previousFace = currentFace;
				controlPoints.put("c1", currentFace.getPoint(currentEdge));
				
				// D1 ---------------->				
				
				// update vars
				currentEdge = previousFace.getEdge(a1, controlPoints.get("c1"));
				currentFace = (currentEdge.getWingedFaces()[0] != previousFace) ? currentEdge.getWingedFaces()[0] : currentEdge.getWingedFaces()[1];				
				System.out.println("Current Face: " + previousFace.getId() + "-> Next face: " + currentFace.getId() + "...Current Edge: (" + currentEdge.getVertices().get(0).getX() + "," + currentEdge.getVertices().get(0).getY() + "," + currentEdge.getVertices().get(0).getZ() + ") - (" + currentEdge.getVertices().get(1).getX() + "," + currentEdge.getVertices().get(1).getY() + "," + currentEdge.getVertices().get(1).getZ() + ")");
				previousFace = currentFace;
				controlPoints.put("d1", currentFace.getPoint(currentEdge));
				
				// C2 ---------------->				
				
				// update vars
				currentEdge = previousFace.getEdge(a1, controlPoints.get("d1"));
				currentFace = (currentEdge.getWingedFaces()[0] != previousFace) ? currentEdge.getWingedFaces()[0] : currentEdge.getWingedFaces()[1];
				System.out.println("Current Face: " + previousFace.getId() + "-> Next face: " + currentFace.getId() + "...Current Edge: (" + currentEdge.getVertices().get(0).getX() + "," + currentEdge.getVertices().get(0).getY() + "," + currentEdge.getVertices().get(0).getZ() + ") - (" + currentEdge.getVertices().get(1).getX() + "," + currentEdge.getVertices().get(1).getY() + "," + currentEdge.getVertices().get(1).getZ() + ")");
				previousFace = currentFace;
				controlPoints.put("c2", currentFace.getPoint(currentEdge));
				
				// B2 ---------------->				
				
				currentEdge = face.getEdges().get(j); 
				previousFace = currentEdge.getWingedFaces()[1];	
				
				controlPoints.put("b2", currentEdge.getWingedFaces()[1].getPoint(currentEdge));
				
				// C3 ---------------->				
				
				// update vars
				currentEdge = previousFace.getEdge(a2, controlPoints.get("b2"));
				currentFace = (currentEdge.getWingedFaces()[0] != previousFace) ? currentEdge.getWingedFaces()[0] : currentEdge.getWingedFaces()[1];
				
				System.out.println("Current Face: " + previousFace.getId() + "-> Next face: " + currentFace.getId() + "...Current Edge: (" + currentEdge.getVertices().get(0).getX() + "," + currentEdge.getVertices().get(0).getY() + "," + currentEdge.getVertices().get(0).getZ() + ") - (" + currentEdge.getVertices().get(1).getX() + "," + currentEdge.getVertices().get(1).getY() + "," + currentEdge.getVertices().get(1).getZ() + ")");
				
				previousFace = currentFace;
				
				controlPoints.put("c3", currentFace.getPoint(currentEdge));
				
				// D2 ---------------->				
				
				// update vars
				currentEdge = previousFace.getEdge(a2, controlPoints.get("c3"));
				currentFace = (currentEdge.getWingedFaces()[0] != previousFace) ? currentEdge.getWingedFaces()[0] : currentEdge.getWingedFaces()[1];
				
				System.out.println("Current Face: " + previousFace.getId() + "-> Next face: " + currentFace.getId() + "...Current Edge: (" + currentEdge.getVertices().get(0).getX() + "," + currentEdge.getVertices().get(0).getY() + "," + currentEdge.getVertices().get(0).getZ() + ") - (" + currentEdge.getVertices().get(1).getX() + "," + currentEdge.getVertices().get(1).getY() + "," + currentEdge.getVertices().get(1).getZ() + ")");
				
				previousFace = currentFace;
				
				controlPoints.put("d2", currentFace.getPoint(currentEdge));
				
				// C4 ---------------->				
				
				// update vars
				currentEdge = previousFace.getEdge(a2, controlPoints.get("d2"));
				currentFace = (currentEdge.getWingedFaces()[0] != previousFace) ? currentEdge.getWingedFaces()[0] : currentEdge.getWingedFaces()[1];
				
				System.out.println("Current Face: " + previousFace.getId() + "-> Next face: " + currentFace.getId() + "...Current Edge: (" + currentEdge.getVertices().get(0).getX() + "," + currentEdge.getVertices().get(0).getY() + "," + currentEdge.getVertices().get(0).getZ() + ") - (" + currentEdge.getVertices().get(1).getX() + "," + currentEdge.getVertices().get(1).getY() + "," + currentEdge.getVertices().get(1).getZ() + ")");
				
				previousFace = currentFace;
				
				controlPoints.put("c4", currentFace.getPoint(currentEdge)); 
			}
		}
		
		// print out the control points
		System.out.println("Control Points:");
		System.out.print("a1: "); controlPoints.get("a1").print();
		System.out.print("a2: "); controlPoints.get("a2").print();
		System.out.println();
		System.out.print("b1: "); controlPoints.get("b1").print();
		System.out.print("b2: "); controlPoints.get("b2").print();
		System.out.println();
		System.out.print("c1: "); controlPoints.get("c1").print();
		System.out.print("c2: "); controlPoints.get("c2").print();
		System.out.print("c3: "); controlPoints.get("c3").print();
		System.out.print("c4: "); controlPoints.get("c4").print();
		System.out.println();
		System.out.print("d1: "); controlPoints.get("d1").print();
		System.out.print("d2: "); controlPoints.get("d2").print();
	}

	/**
	 * Loops through the mesh and calculates 
	 * which faces wing which edges
	 */
	public void calculateWingingFaces()
	{
		System.out.println("Mesh.calculateWingingFaces");
		
		// loop through the faces
		for (int i = 0; i < this.faces.size(); i++)
		{
			Face face = this.faces.get(i);
		
			// loop through the edges
			for (int j = 0; j < face.getEdges().size(); j++)
			{
				Edge edge = face.getEdges().get(j);
				
				// now loop through the faces again, looking for the other face that contains 'edge'
				for (int k = 0; k < this.faces.size(); k++)
				{
					Face face2 = this.faces.get(k);
					for (int l = 0; l < face2.getEdges().size(); l++)
					{
						Edge e = face2.getEdges().get(l);
						
						// add the two winging faces to 'edge' object
						if(edge.equals(e) && !face.equals(face2))
						{							
							edge.addWingedFaces(face, face2);
						}
					}
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
			
			gl.glBegin(GL2.GL_TRIANGLES);
			
			gl.glColor3ub(face.getColour()[0], face.getColour()[1], face.getColour()[2]);
			face.draw(gl);
			
			gl.glEnd();
		}
	}
	
	public ArrayList<Face> getFaces() { return this.faces; }
}
