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
	 * Subdivides the cube using the Catmull-Clark subdivision scheme
	 * @return the subdivided mesh
	 */
	public Mesh subdivide()
	{		
		// 1. Loop through each face calculating face points
		for (int i = 0; i < this.faces.size(); i++) this.faces.get(i).calculateFacePoint();
		
		// 2. Loop through each edge calculating edge points
		// also, work out the vertex valence as we go...
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
		
		// 3. Calculate vertex points
		//
		// Formula:
		// 
		// 		( avg. of all adj. face points )
		// 							+
		// 2 * ( avg. of all edge points of incident edges )
		// 							+
		// 		vertex * ( vertex valence - 3 )
		// 							/
		// 					vertex valence
		//
		for (int i = 0; i < this.faces.size(); i++) 
		{	
			Face face = this.faces.get(i);
			for (int j = 0; j < face.getEdges().size(); j++) 
			{				
				for (int k = 0; k < face.getVertices().size(); k++)
				{			
					Vertex vertex = face.getVertices().get(k); 				// current vertex
					Vertex fpAvg = new Vertex(0,0,0); 							// average of adjacent face points
					Vertex epAvg = new Vertex(0,0,0); 							// average of adjacent edge points
					Vertex vertValenceSum = new Vertex(0,0,0);				// vertex*(vertex valence - 3)
					Vertex vertexPoint = new Vertex(0,0,0); 					// new vertex
					int vertValence = vertex.getIncidentEdges().size();	// current vertex's valence
					
					for (int l = 0; l < vertValence; l++)
					{
						Edge edge = vertex.getIncidentEdges().get(l);
						Vertex.add(edge.getWingedFaces()[0].getFacePoint(), fpAvg);	
						Vertex.add(edge.getEdgePoint(), epAvg);
					}
					
					fpAvg = Vertex.divide(fpAvg, vertValence);
					
					epAvg = Vertex.divide(epAvg, vertValence);
					epAvg = Vertex.multiply(epAvg, 2);
					
					vertValenceSum = Vertex.multiply(vertex, (vertValence-3));
					
					vertexPoint = Vertex.add(vertexPoint, fpAvg);
					vertexPoint = Vertex.add(vertexPoint, epAvg);
					vertexPoint = Vertex.add(vertexPoint, vertValenceSum);
					vertexPoint = Vertex.divide(vertexPoint, vertValence);
				}
			}
		}		
		
		// 4. Connect the new face points to the new edge points
		
		// 5. Connect each vertex point to each new edge point
		
		return null;
	}
	
	/**
	 * Loops through the mesh and calculates 
	 * which faces wing which edges
	 */
	public void calculateWingingFaces()
	{		
		System.out.println("Mesh.calculateWingingFaces: f:" + this.faces.size());
		
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
	
	// public getters/setters
	public ArrayList<Face> getFaces() { return this.faces; }
}
