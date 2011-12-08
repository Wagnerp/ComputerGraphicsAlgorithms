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
		//Mesh newMesh = new Mesh("Subdivided Cube");
				
		// calculate the face points
		for (int i = 0; i < this.faces.size(); i++) this.faces.get(i).calculateFacePoint();
		
		// calculate the edge points
		this.calculateEdgePoints();	
		
		// get the vertex points
		for (int i = 0; i < this.faces.size(); i++) this.faces.get(i).calculateVertexPoints();

		return this.createNewFaces();		
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
	 * Build a list of new faces using the
	 * edge, vertex and face points
	 * @return list of new faces (one face per vertex)
	 */
	private Mesh createNewFaces()
	{				
		Mesh mesh = new Mesh("Subdivided Cube");
		
		for (int i = 0; i < this.faces.size(); i++)
		{
			Face face = this.faces.get(i);
			
			for (int j = 0; j < face.getNewVertices().size(); j++)
			{
//				Vertex vertexPoint = face.getNewVertices().get(j);
				Vertex vertexPoint = face.getVertices().get(j);
			
				// !!!!!				
				
				Vertex edge1Point = null, edge2Point = null;
				Edge e1, e2, e3, e4;

				for (int k = 0; k < vertexPoint.getIncidentEdges().size(); k++)
				{
					Edge incidentEdge = face.getEdge(vertexPoint.getIncidentEdges().get(k));
					if (incidentEdge != null)
					{
						if (edge1Point == null) edge1Point = incidentEdge.getEdgePoint();
						else edge2Point = incidentEdge.getEdgePoint();
					}
				}

				// create the four edges using the points
				e1 = new Edge(vertexPoint, edge1Point, null);
				e2 = new Edge(edge1Point, face.getFacePoint(), null);
				e3 = new Edge(face.getFacePoint(), edge2Point, null);
				e4 = new Edge(edge2Point, vertexPoint, null);

				//
				//          _ _ _ 
				//        !       !
				//       !         !
				//       !  X   X  !
				//        !   ^   !
				//          |||||
				//
				//  |SCHOOLBOY ERROR ALERT|
				//
				// Note to self: edgeDir has 4 bytes
				//
//				int ed1 = (mesh.getEdge(e1) == null) ? 0 : 1;
//				int ed2 = (mesh.getEdge(e2) == null) ? 0 : 1;
//				int ed3 = (mesh.getEdge(e3) == null) ? 0 : 1;
//				int ed4 = (mesh.getEdge(e4) == null) ? 0 : 1;
				
//				int ed1, ed2, ed3, ed4;
//				
//				if(mesh.getEdge(e1) == null) ed1 = 0;
//				else
//				{
//					ed1 = 1;
//					//System.out.print("ed1: "); e1.print();
//				}
//				if(mesh.getEdge(e2) == null) ed2 = 0;
//				else
//				{
//					ed2 = 1;
////					System.out.print("ed2: "); e2.print();
//				}
//				if(mesh.getEdge(e3) == null) ed3 = 0;
//				else
//				{
//					ed3 = 1;
////					System.out.print("ed3: "); e3.print();
//				}
//				if(mesh.getEdge(e4) == null) ed4 = 0;
//				else
//				{
//					ed4 = 1;
////					System.out.print("ed4: "); e4.print();
//				}
//				
//				byte[] edgeDir = new byte[] { (byte)ed1, (byte)ed2, (byte)ed3, (byte)ed4 };
//				
//				if(j == 0) 
//				{
//					edgeDir = new byte[] {0,0,0,0};
//				}
//				else if(j == 1) 
//				{
//					edgeDir = new byte[] {1,0,0,0};
//				}
//				else if(j == 2) 
//				{
//					edgeDir = new byte[] {1,1,0,1};
//				}
				
				byte[] edgeDir = new byte[] {0,0,0,0};
				
				System.out.println("edgeDir[" + edgeDir[0] + "," + edgeDir[1] + "," + edgeDir[2] + "," + edgeDir[3] + "] vertex valence: " + vertexPoint.getIncidentEdges().size()); 
				
				mesh.addFace(new Face(e1, e2, e3, e4, edgeDir, ""));
			}
		}
		return mesh;
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
