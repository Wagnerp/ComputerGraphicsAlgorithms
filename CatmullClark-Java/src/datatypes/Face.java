package datatypes;

import java.util.ArrayList;
import java.util.Random;

import javax.media.opengl.GL2;

/**
 * A simple object to store face information
 * So far handles tris and quads
 *
 *	TODO: Face: Move getters/setters into alphabetical order
 * TODO: Face: Move colours into an ArrayList
 * TODO: Face.Face: Come up with more elegant way to deal with tris/quads 
 *
 * @author Tom
 * @version 0.1
 * @history 13.10.2011: Created class
 */ 

public class Face
{
	// the edges in the face
	private ArrayList<Edge> edges = new ArrayList<Edge>();
  /* the vertices in the face
	* (populated by getVertices(), to avoid unnecessary loopage) */
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	// the RGB value of the face's colour
	private byte[] colour;
	// holds the direction of each edge
	private byte[] edgeDirection;
	private Vertex facePoint;
	
	private String id = "";
			
	/**
	 * Constructor - tri
	 * @param _edge1
	 * @param _edge2
	 * @param _edge3
	 */
	public Face(Edge _edge1, Edge _edge2, Edge _edge3, byte[] edgeDir, String _id)
	{		
		this.edges.add(_edge1);
		this.edges.add(_edge2);
		this.edges.add(_edge3);
		
		// randomly generate a colour
		Random r = new Random();
		this.colour = new byte[3];
		this.colour[0] = (byte)(r.nextInt(254)+1);
		this.colour[1] = (byte)(r.nextInt(254)+1);
		this.colour[2] = (byte)(r.nextInt(254)+1);
		
		this.edgeDirection = edgeDir;		
		this.id = _id;
	}
	
	/**
	 * Constructor - quad
	 * @param _edge1
	 * @param _edge2
	 * @param _edge3
	 * @param edgeDir
	 * @param _id
	 */
	public Face(Edge _edge1, Edge _edge2, Edge _edge3, Edge _edge4, byte[] edgeDir, String _id)
	{		
		this.edges.add(_edge1);
		this.edges.add(_edge2);
		this.edges.add(_edge3);
		this.edges.add(_edge4);
		
		// randomly generate a colour
		Random r = new Random();
		this.colour = new byte[4];
		this.colour[0] = (byte)(r.nextInt(254)+1);
		this.colour[1] = (byte)(r.nextInt(254)+1);
		this.colour[2] = (byte)(r.nextInt(254)+1);
		this.colour[3] = (byte)(r.nextInt(254)+1);
		
		this.edgeDirection = edgeDir;		
		this.id = _id;
	}
	
	/**
	 * Draws each edge
	 * @param gl
	 */
	public void draw(GL2 gl)
	{				
		// draw in an anti-clockwise fashion 
		this.edges.get(0).getVertices().get(edgeDirection[0]).draw(gl);		
		if(this.edges.size() == 4) this.edges.get(3).getVertices().get(edgeDirection[3]).draw(gl);
		this.edges.get(2).getVertices().get(edgeDirection[2]).draw(gl);
		this.edges.get(1).getVertices().get(edgeDirection[1]).draw(gl);
	}
	
	/**
	 * Prints each edge
	 */
	public void print()
	{
		for (int i = 0; i < this.edges.size(); i++)
		{
			System.out.println("Edge " + (i+1) + ": ");
			this.edges.get(i).print();
		}
	}
	
	public void calculateFacePoint()
	{
		ArrayList<Vertex> faceVertices = this.getVertices();		
		Vertex sum = new Vertex(0,0,0);
		
		// sum up the vertices
		for (int j = 0; j < faceVertices.size(); j++) sum = Vertex.add(faceVertices.get(j), sum);
		
		// get the average
		this.facePoint = Vertex.divide(sum, faceVertices.size());
	}
	
	/**
	 * Build a list of new faces using the
	 * edge, vertex and face points
	 * @return list of new faces (one face per vertex)
	 */
	public ArrayList<Face> createNewFaces()
	{				
		ArrayList<Face> newFaces = new ArrayList<Face>(); 
		
		for (int i = 0; i < this.getVertices().size(); i++)
		{			
			Vertex vertexPoint = this.getVertices().get(i);
			Vertex edge1Point = null, edge2Point = null;
			Edge e1, e2, e3, e4;
			byte[] edgeDir = new byte[3]; 

			for (int j = 0; j < vertexPoint.getIncidentEdges().size(); j++)
			{
				Edge incidentEdge = this.getEdge(vertexPoint.getIncidentEdges().get(j));
				if(incidentEdge != null) 
				{
					if(edge1Point == null) edge1Point = incidentEdge.getEdgePoint();
					else edge2Point = incidentEdge.getEdgePoint();
				}  
			}	
			
			e1 = new Edge(vertexPoint, edge1Point, null);
			e2 = new Edge(edge1Point, this.facePoint, null);
			e3 = new Edge(this.facePoint, edge2Point, null);
			e4 = new Edge(edge2Point, vertexPoint, null);
			
			//
			// 			_ _ _ 
			//			 !			!
			//			!         !
			//       !  X   X  !
			//        !   ^   !
			//          !!!!!
			//
			//  ÁSCHOOLBOY ERROR ALERT!
			//
			// Note to self: edgeDir has 4 bytes
			//
			edgeDir = new byte[] {0,0,0,0};
			
			newFaces.add(new Face(e1, e2, e3, e4, edgeDir, ""));			
		}
		
		return newFaces;
	}
	
	//	public getters/setters
	
	/**
	 * Gets the edge from it's vertices
	 * @param v1, v2 the vertices
	 * @return the edge
	 */
	public Edge getEdge(Vertex v1, Vertex v2) 
	{ 
		for (int i = 0; i < this.edges.size(); i++)
		{
			Edge e = this.edges.get(i); 
			if(e.contains(v1) && e.contains(v2)) return e; 
		} 
		return null; 
	}
	
	/**
	 * Returns the edges other than the passed edge
	 * @param edge we don't want returned
	 * @return the edges
	 */
	public ArrayList<Edge> getEdges(Edge edge) 
	{ 
		ArrayList<Edge> otherEdges = new ArrayList<Edge>();
		
		for (int i = 0; i < this.edges.size(); i++)
		{
			Edge e = this.edges.get(i);
			if(e != edge) otherEdges.add(e); 
		}
		
		return otherEdges; 
	}
	
	/**
	 * Returns the vertex of the face which isn't in the passed edge
	 * @param edge
	 * @return the vertex
	 */
	public Vertex getPoint(Edge edge)
	{		
		for (int i = 0; i < this.edges.size(); i++) 
		{			
			Edge e = this.edges.get(i);
			
			Vertex edgeV1 = edge.getVertices().get(0);
			Vertex edgeV2 = edge.getVertices().get(1);
			
			if(!e.equals(edge))
			{	
				Vertex v1 = e.getVertices().get(0);
				Vertex v2 = e.getVertices().get(1);
				
				if(v2 == edgeV1 || v2 == edgeV2) return v1;
				else if(v1 == edgeV1 || v1 == edgeV2) return v2;
			}
		}
		
		return null;
	}
	
	public ArrayList<Vertex> getVertices() 
	{ 				
		if(this.vertices.size() < 1) System.out.println("Face.getVertices: Need to populate vertices");
		if(this.vertices.size() < 1) for (int i = 0; i < this.edges.size(); i++) this.vertices.add(this.edges.get(i).getVertices().get(this.edgeDirection[i]));
		return this.vertices; 
	}
	
	/**
	 * Uses the following formula to work out the 
	 * vertex points:
	 * 
	 *		 	(avg. of all adj. face points)
	 * 							+
	 *  2*(avg. of all edge points of incident edges)
	 * 							+
	 * 			vertex*(vertex valence - 3)
	 * 							/
	 * 					vertex valence
	 */
	public static Vertex getVertexPoint(Vertex oldVertexPoint)
	{						
		Vertex fpAvg = new Vertex(0,0,0); 							// average of adjacent face points
		Vertex epAvg = new Vertex(0,0,0); 							// average of adjacent edge midpoints
		Vertex vertValenceSum = new Vertex(0,0,0);				// vertex*(vertex valence - 3)
		Vertex vertexPoint = new Vertex(0,0,0); 					// new vertex
		int vertValence = oldVertexPoint.getIncidentEdges().size();	// current vertex's valence

		for (int l = 0; l < vertValence; l++)
		{
			Edge edge = oldVertexPoint.getIncidentEdges().get(l);
			Vertex.add(edge.getWingedFaces()[0].getFacePoint(), fpAvg);	
			Vertex.add(edge.getMidPoint(), epAvg);
		}

		//fpAvg = Vertex.divide(fpAvg, vertValence);

		//epAvg = Vertex.divide(epAvg, vertValence);
		epAvg = Vertex.multiply(epAvg, 2);

		vertValenceSum = Vertex.multiply(oldVertexPoint, (vertValence-3));

		// now add the individual parts and divide by the valence
		vertexPoint = Vertex.add(vertexPoint, fpAvg);
		vertexPoint = Vertex.add(vertexPoint, epAvg);
		vertexPoint = Vertex.add(vertexPoint, vertValenceSum);
		vertexPoint = Vertex.divide(vertexPoint, vertValence);

		return vertexPoint;
	}
	
	public byte[] getColour() { return this.colour; }
	public Edge getEdge(Edge e) { return this.getEdge(e.getVertices().get(0), e.getVertices().get(1)); }
	public ArrayList<Edge> getEdges() { return this.edges; }
	public byte[] getEdgeDirections() { return this.edgeDirection; }
	public Vertex getFacePoint() { return this.facePoint; }
	public String getId() { return this.id; }
}
