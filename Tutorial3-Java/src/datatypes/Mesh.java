package datatypes;

// Java imports
import java.util.ArrayList;
import javax.media.opengl.GL2;

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
	 * Array for colours to simplify the display process
	 */
	public Colour[] rbgColours = 
	{ 
		new Colour((byte)227, (byte)59, (byte)101),  // pink
		new Colour((byte)182, (byte)59, (byte)227),  // purple
		new Colour((byte)6, (byte)105, (byte)13),    // dark green
		new Colour((byte)227, (byte)143, (byte)59),  // orange
		new Colour((byte)59, (byte)227, (byte)104),  // green
		new Colour((byte)59, (byte)149, (byte)227),  // blue
		new Colour((byte)59, (byte)62, (byte)227),   // dark blue
		new Colour((byte)227, (byte)213, (byte)59),  // yellow
		new Colour((byte)227, (byte)76, (byte)59),   // red
		new Colour((byte)105, (byte)6, (byte)6),     // maroon
		new Colour((byte)255, (byte)176, (byte)176), // peach
		new Colour((byte)219, (byte)176, (byte)255)  // lilac
	};
	
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
			gl.glBegin(GL2.GL_TRIANGLES);
			
			gl.glColor3ub(this.rbgColours[i].red, this.rbgColours[i].green, this.rbgColours[i].blue);
			this.faces.get(i).draw(gl);
			
			gl.glEnd();
		}
	}
	
	public ArrayList<Face> getFaces() { return this.faces; }
}
