package datatypes;

// Java imports
import java.util.ArrayList;
import java.util.HashMap;
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
	// whether or not to show certain debug messages
	private Boolean debug = false;
	// used when calculating new points
	private float weightedValue;
	
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
	 * Subdivides the cube using the modified butterfly scheme
	 * @param weighting of the control points
	 * @return the subdivided mesh
	 */
	public Mesh subdivide(float weight)
	{
		if(debug) System.out.println("Mesh.subdivide using weighting of " + weight + ": There are " + this.faces.size() + " faces");
		
		this.weightedValue = weight;
		
		// will be the new subdivided mesh
		Mesh newMesh = new Mesh("Butterfly Mesh");
		
		calculateControlPoints();
		
		return newMesh;
	}

	/**
	 * Calculates all of the control points for each edge in the model
	 * 
	 * Not carried out particularly neatly - had issues with b2
	 * TODO Mesh.calculateControlPoints: refactor into a cleaner loop
	 * TODO Mesh.calculateControlPoints: remove debug stuff
	 */
	private void calculateControlPoints()
	{		
		HashMap<String,Vertex> controlPoints = new HashMap<String,Vertex>();	
		
		for (int i = 0; i < this.faces.size(); i++)
		{
			Face face = this.faces.get(i);
					
			for (int j = 0; j < face.getEdges().size(); j++)
			{
				if(face.getEdgeDirections()[j] == '1') continue; 
				
				Edge currentEdge = face.getEdges().get(j);
				Face currentFace = null; 
				Face previousFace = currentEdge.getWingedFaces()[0];
				Vertex a1, a2; // to save looking up repeatedly in the HashMap
				
				if(debug) System.out.println("Calculating control points for Face " + face.getId() + " edge" + j);
				
				// A1/A2 -------------> we already know the a points

				controlPoints.put("a1", currentEdge.getVertices().get(0));
				controlPoints.put("a2", currentEdge.getVertices().get(1));
				// also set the variables
				a1 = controlPoints.get("a1");
				a2 = controlPoints.get("a2");
				
				// B1 ----------------> b1, c1, d1, c2 -> b2, c3, d2, c4 
				
				controlPoints.put("b1", currentEdge.getWingedFaces()[0].getPoint(currentEdge));
				
				// C1 ---------------->
				
				currentEdge = previousFace.getEdge(a1, controlPoints.get("b1"));
				currentFace = (currentEdge.getWingedFaces()[0] != previousFace) ? currentEdge.getWingedFaces()[0] : currentEdge.getWingedFaces()[1];				
				previousFace = currentFace;
				controlPoints.put("c1", currentFace.getPoint(currentEdge));
				
				// D1 ---------------->				
				
				currentEdge = previousFace.getEdge(a1, controlPoints.get("c1"));
				currentFace = (currentEdge.getWingedFaces()[0] != previousFace) ? currentEdge.getWingedFaces()[0] : currentEdge.getWingedFaces()[1];				
				previousFace = currentFace;
				controlPoints.put("d1", currentFace.getPoint(currentEdge));
				
				// C2 ---------------->				
				
				currentEdge = previousFace.getEdge(a1, controlPoints.get("d1"));
				currentFace = (currentEdge.getWingedFaces()[0] != previousFace) ? currentEdge.getWingedFaces()[0] : currentEdge.getWingedFaces()[1];
				previousFace = currentFace;
				controlPoints.put("c2", currentFace.getPoint(currentEdge));
				
				// B2 ---------------->				
				
				currentEdge = face.getEdges().get(j); 
				previousFace = currentEdge.getWingedFaces()[1];	
				controlPoints.put("b2", currentEdge.getWingedFaces()[1].getPoint(currentEdge));
				
				// C3 ---------------->				
				
				currentEdge = previousFace.getEdge(a2, controlPoints.get("b2"));
				currentFace = (currentEdge.getWingedFaces()[0] != previousFace) ? currentEdge.getWingedFaces()[0] : currentEdge.getWingedFaces()[1];
				previousFace = currentFace;
				controlPoints.put("c3", currentFace.getPoint(currentEdge));
				
				// D2 ---------------->				
				
				currentEdge = previousFace.getEdge(a2, controlPoints.get("c3"));
				currentFace = (currentEdge.getWingedFaces()[0] != previousFace) ? currentEdge.getWingedFaces()[0] : currentEdge.getWingedFaces()[1];
				previousFace = currentFace;
				controlPoints.put("d2", currentFace.getPoint(currentEdge));
				
				// C4 ---------------->				
				
				currentEdge = previousFace.getEdge(a2, controlPoints.get("d2"));
				currentFace = (currentEdge.getWingedFaces()[0] != previousFace) ? currentEdge.getWingedFaces()[0] : currentEdge.getWingedFaces()[1];
				previousFace = currentFace;
				controlPoints.put("c4", currentFace.getPoint(currentEdge)); 
				
				// print out the control points
				if(debug) 
				{
					System.out.println();
					System.out.println("Control points calculated...");
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
					System.out.println();
				}
				
				face.getEdges().get(j).calculateNewPoint(controlPoints, weightedValue);
			}
		}
	}
	
	/**
	 * Loops through the mesh and calculates 
	 * which faces wing which edges
	 */
	public void calculateWingingFaces()
	{
		if(debug) System.out.println("Mesh.calculateWingingFaces");
		
		for (int i = 0; i < this.faces.size(); i++)
		{
			Face face = this.faces.get(i);
		
			for (int j = 0; j < face.getEdges().size(); j++)
			{
				Edge edge = face.getEdges().get(j);
				
				// loop through faces again, looking for other face that contains 'edge'
				for (int k = 0; k < this.faces.size(); k++)
				{
					Face face2 = this.faces.get(k);
					for (int l = 0; l < face2.getEdges().size(); l++)
					{
						Edge e = face2.getEdges().get(l);
						
						// add the two winging faces to 'edge' object
						if(edge.equals(e) && !face.equals(face2))	edge.addWingedFaces(face, face2);
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
	
	// public getters/setters
	public ArrayList<Face> getFaces() { return this.faces; }
}
