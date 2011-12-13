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
	public void addFace(Face _faceToAdd)
	{
		this.faces.add(_faceToAdd);
	}
	
	/**
	 * Subdivides the cube using the Catmull-Clark subdivision scheme
	 * @return the subdivided mesh
	 */
	public Mesh subdivide()
	{						
		System.out.println("Mesh.subdivide: " + this.name);
		
		for (int i = 0; i < this.faces.size(); i++) this.faces.get(i).calculateFacePoint();
		
		this.calculateEdgePoints();	
		
		for (int i = 0; i < this.faces.size(); i++) this.faces.get(i).calculateVertexPoints();

		Mesh subdividedMesh = this.createNewFaces();
		subdividedMesh.calculateWingingFaces();
		return subdividedMesh;
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
					
					// store references to the winged faces
					if(comparisonFace != currentFace) 
					{
						if(comparisonFace.getEdge(currentEdge) != null)
							currentEdge.addWingedFaces(currentFace, comparisonFace);
					}
				}
			}
		}
	}
	
	/**
	 * Build a list of new faces using the
	 * edge, vertex and face points
	 * @return the new mesh
	 */
	private Mesh createNewFaces()
	{				
		Mesh mesh = new Mesh("Subdivided Cube");
		
		for (int i = 0; i < this.faces.size(); i++)
		{
			Face face = this.faces.get(i);
			ArrayList<Vertex> newVertices = face.getNewVertices(); // the new vertex points
									
			for (int j = 0; j < newVertices.size(); j++)
			{
				Vertex oldVertexPoint = face.getVertices().get(j);
				Vertex newVertexPoint = newVertices.get(j);
							
				Vertex edge1Point = null, edge2Point = null;
				Edge e1, e2, e3, e4;

				// get the incident edges which are in this face
				for (int k = 0; k < oldVertexPoint.getIncidentEdges().size(); k++)
				{
					Edge incidentEdge = face.getEdge(oldVertexPoint.getIncidentEdges().get(k));
					if (incidentEdge != null)
					{
						if (edge1Point == null) edge1Point = incidentEdge.getEdgePoint();
						else edge2Point = incidentEdge.getEdgePoint();
					}
				}

				// create the four edges using the points
				e1 = new Edge(newVertexPoint, edge1Point);
				e2 = new Edge(edge1Point, face.getFacePoint());
				e3 = new Edge(face.getFacePoint(), edge2Point);
				e4 = new Edge(edge2Point, newVertexPoint);

				//
				//          _ _ _ 
				//        |       |
				//       |         |
				//       |  X   X  |
				//        |   ^   |
				//          |||||
				//
				//  ÁSCHOOLBOY ERROR ALERT!
				//
				// Note to self: edgeDir has 4 bytes
				//
				
				byte ed1, ed2, ed3, ed4;
				
				/*
				 * find which edges already exist, and set 
				 * their direction to '1'
				 */
				
				if(mesh.getEdgeDirection(e1) != '#') 
				{
					e1 = e1.getInvert();
					ed1 = 1;
				} else ed1 = 0;
				
				if(mesh.getEdgeDirection(e2) != '#') 
				{
					e2 = e2.getInvert();
					ed2 = 1;
				} else ed2 = 0;
				
				if(mesh.getEdgeDirection(e3) != '#') 
				{
					e3 = e3.getInvert();
					ed3 = 1;
				} else ed3 = 0;
				
				if(mesh.getEdgeDirection(e4) != '#') 
				{
					e4 = e4.getInvert();
					ed4 = 1;
				} else ed4 = 0;
				
				/*
				 * Hack:----------------------------------
				 * the following faces need to be declared 
				 * backwards; can't see a general pattern 
				 * that could be applied during recursion
				 * ---------------------------------------
				 * Front  |	  2 3 4 
				 * Back   |	  2 3 4 
				 * Left   | 1     4
				 * Right  |	1     4
				 * Top 	 | 1   3   
				 * Bottom | 1   3  
				 */
				
				byte[] edgeDir;
				boolean declareFaceBackwards = false;
				
				switch(i)
				{
					case 0:
					case 1:
						if(j == 1 || j == 2 || j == 3) declareFaceBackwards = true;
						break;
					case 2:
					case 3:
						if(j == 0 || j == 2) declareFaceBackwards = true;
						break;
					case 4:
					case 5:
						if(j == 1 || j == 3) declareFaceBackwards = true;
						break;
				}
				
				if(!declareFaceBackwards)
				{
					edgeDir = new byte[] {ed1, ed2, ed3, ed4};
					mesh.addFace(new Face(e1, e2, e3, e4, edgeDir));
					System.out.println("edgeDir[" + edgeDir[0] + "," + edgeDir[1] + "," + edgeDir[2] + "," + edgeDir[3] + "]");
				}
				else
				{
					edgeDir = new byte[] {ed4, ed3, ed2, ed1};
					mesh.addFace(new Face(e4, e3, e2, e1, edgeDir));						
					System.out.println("edgeDir[" + edgeDir[3] + "," + edgeDir[2] + "," + edgeDir[1] + "," + edgeDir[0] + "]"); 
				}
	
			}
		}
		return mesh;
	}

	/**
	 * Handles drawing the mesh to screen
	 * @param _gl a reference to the GL2 object
	 */
	public void draw(GL2 _gl)
	{					
		for (int i = 0; i < this.faces.size(); i++)
		{			
			Face face = this.faces.get(i);
			
			if(face.getVertices().size() == 3) _gl.glBegin(GL2.GL_TRIANGLES);
			else if(face.getVertices().size() == 4) _gl.glBegin(GL2.GL_QUADS);
						
			_gl.glColor3ub(face.getColour()[0], face.getColour()[1], face.getColour()[2]);
			face.draw(_gl);
			
			_gl.glEnd();
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
	
	/**
	 * Gets the edge direction of the passed edge
	 * @param the edge to check
	 * @return the direction, or a null character #
	 */
	public byte getEdgeDirection(Edge _e)
	{				
		for (int k = 0; k < this.faces.size(); k++)
		{
			Face comparisonFace = this.faces.get(k);
			
			for (int l = 0; l < comparisonFace.getEdges().size(); l++)
			{
				Edge comparisonEdge = comparisonFace.getEdges().get(l);

				if(
						_e.getVertices().get(0).getX() == comparisonEdge.getVertices().get(0).getX() &&
						_e.getVertices().get(0).getY() == comparisonEdge.getVertices().get(0).getY() &&
						_e.getVertices().get(0).getZ() == comparisonEdge.getVertices().get(0).getZ() &&
						_e.getVertices().get(1).getX() == comparisonEdge.getVertices().get(1).getX() &&
						_e.getVertices().get(1).getY() == comparisonEdge.getVertices().get(1).getY() &&
						_e.getVertices().get(1).getZ() == comparisonEdge.getVertices().get(1).getZ()
					)
				{
					return comparisonFace.getEdgeDirections()[l];
				}		
				else if(
						_e.getVertices().get(0).getX() == comparisonEdge.getVertices().get(1).getX() &&
						_e.getVertices().get(0).getY() == comparisonEdge.getVertices().get(1).getY() &&
						_e.getVertices().get(0).getZ() == comparisonEdge.getVertices().get(1).getZ() &&
						_e.getVertices().get(1).getX() == comparisonEdge.getVertices().get(0).getX() &&
						_e.getVertices().get(1).getY() == comparisonEdge.getVertices().get(0).getY() &&
						_e.getVertices().get(1).getZ() == comparisonEdge.getVertices().get(0).getZ()
						)
				{
					return comparisonFace.getEdgeDirections()[l];
				}
			}
		}
		return '#';
	}
	
	public ArrayList<Face> getFaces() { return this.faces; }
}
