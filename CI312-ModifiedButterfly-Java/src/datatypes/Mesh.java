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
		this.weightedValue = weight;
		
		this.calulateNewVertices();
		
		// now we have the new points, build the mesh
		Mesh subdividedMesh = this.buildNewMesh();
		
		subdividedMesh.calculateWingingFaces();
		return subdividedMesh;
	}


	/**
	 * Creates a new mesh from the control net
	 * @return the new mesh
	 */
	private Mesh buildNewMesh()
	{
		Mesh mesh = new Mesh("Butterfly Mesh"); // the subdivided mesh
		
		// build the new faces from the new points
		for (int i = 0; i < faces.size(); i++)
		{	
			//	the order in which the faces should be built
			//	065, 127, 834, 678
			int[][] buildOrder = new int[4][3];
			buildOrder[0][0] = 0; buildOrder[0][1] = 6; buildOrder[0][2] = 5; // 1
			buildOrder[1][0] = 1; buildOrder[1][1] = 2; buildOrder[1][2] = 7; // 2
			buildOrder[2][0] = 8; buildOrder[2][1] = 3; buildOrder[2][2] = 4; // 3
			buildOrder[3][0] = 6; buildOrder[3][1] = 7; buildOrder[3][2] = 8; // 4
			
			for (int k = 0; k < 4; k++)
			{
				byte[] edgeDirection = new byte[3];
				Edge[] newEdges = new Edge[3]; 
				
				for (int l = 0; l < newEdges.length; l++)
				{
					// uses Face.subdivide to get the 9 new edges
					newEdges[l] = this.faces.get(i).subdivide().get(buildOrder[k][l]);
					Edge tempEdge = mesh.getEdge(newEdges[l]);
					
					if(tempEdge != null)
					{
						newEdges[l] = tempEdge;
						edgeDirection[l] = (byte)1;				
					} 
					else edgeDirection[l] = (byte)0;
				}		
				mesh.addFace(new Face(newEdges[0], newEdges[1], newEdges[2], edgeDirection, ""));		
			}
		}
		return mesh;
	}

	/**
	 * Calculates all of the control points for each edge in the model
	 * 
	 * Not carried out particularly neatly - had issues with b2
	 * TODO Mesh.calculateControlPoints: re-factor into a cleaner loop if poss
	 */
	private HashMap<String,Vertex> calculateControlPoints(Face face, Edge edge)
	{		
		HashMap<String,Vertex> controlPoints = new HashMap<String,Vertex>();	

		Edge currentEdge = edge;
		Face currentFace = null; 
		Face previousFace = currentEdge.getWingedFaces()[0];
		Vertex a1, a2; // to save looking up repeatedly in the HashMap

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

		currentEdge = edge; 
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

		return controlPoints;
	}
	
	/**
	 * Works out the new point for each edge based
	 * on its control points. Stores the new point 
	 * in the edge
	 */
	private void calulateNewVertices()
	{
		// work out the new points
		for (int i = 0; i < this.faces.size(); i++)
		{
			Face face = this.faces.get(i);
			
			for (int j = 0; j < face.getEdges().size(); j++)
			{
				Edge edge = face.getEdges().get(j);
				
				if(face.getEdgeDirections()[j] != '1') // only calculate point once for each edge
				{
					HashMap<String,Vertex> controlPoints = this.calculateControlPoints(face,edge);
					edge.calculateNewPoint(controlPoints, this.weightedValue);
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
		System.out.println("Mesh.calculateWingingFaces: " + this.faces.size());
		
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
	
	private Edge getEdge(Vertex v1, Vertex v2)
	{		
		for (int i = 0; i < this.faces.size(); i++)
		{
			Edge e = this.faces.get(i).getEdge(v1, v2);
			if(e != null) return e;
		}
		return null;
	}
	// can also pass an edge to getEdge
	private Edge getEdge(Edge edge) { return this.getEdge(edge.getVertices().get(0), edge.getVertices().get(1)); }
	
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
	
	/** 
	 * Just prints out a list of the control points
	 * @param controlPoints
	 */
	private void printControlPoints(HashMap<String, Vertex> controlPoints)
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
	
	// public getters/setters
	public ArrayList<Face> getFaces() { return this.faces; }
}
