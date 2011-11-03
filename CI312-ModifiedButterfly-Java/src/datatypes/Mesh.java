package datatypes;

// Java imports
import java.util.ArrayList;
import javax.media.opengl.GL2;

// Java Library imports
import utils.Utils;

/**
 * A class to represent a 3D mesh model
 * Stores the faces that make the model
 * Handles both tri and quad faces
 *
 * @author Tom
 * @version 0.1
 * @history 13.10.2011: Created class
 */

public class Mesh
{
	// an identifier for the mesh
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
	
	public Mesh subdivide()
	{
		System.out.println("Mesh.subdivide: There are " + this.faces.size() + " faces");
		
		Mesh newMesh = new Mesh("New Mesh");
		
		// limit to first two faces for now
//		for (int i = 0; i < this.faces.size(); i++)
		for (int i = 0; i < 2; i++)
		{
			this.faces.get(i).subdivide();
		}
		
		return newMesh;
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
