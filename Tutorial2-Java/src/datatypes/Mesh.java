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
	 * Arrays for colours to simplify the display process
	 */
	private Vertex[] colours = 
	{ 
		new Vertex(0.0f,1.0f,0.0f), // green
		new Vertex(1.0f,0.5f,0.0f), // orange
		new Vertex(1.0f,0.0f,0.0f), // red
		new Vertex(1.0f,1.0f,0.0f), // yellow 
		new Vertex(0.0f,0.0f,1.0f), // blue 
		new Vertex(1.0f,0.0f,1.0f)  // pink
	};
	private Colour[] rbgColours = 
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
	
	/**
	 * Handles drawing the mesh to screen
	 * @param gl a reference to the GL2 object
	 */
	public void display(GL2 gl)
	{			
		for (int i = 0; i < faces.size(); i++)
		{
			if (faces.get(i).faceType == Face.PolygonType.TRIANGLE) gl.glBegin(GL2.GL_TRIANGLES);
			else gl.glBegin(GL2.GL_QUADS);
			
			// use float-value colours
			//gl.glColor3f(colours[i].x, colours[i].y, colours[i].z);
			// use RBG-value colours
			gl.glColor3ub(rbgColours[i].red, rbgColours[i].green, rbgColours[i].blue);
			
			for (int j = 0; j < faces.get(i).edges.size(); j++)
			{			
				for (int k = 0; k < faces.get(i).edges.get(k).vertices.length; k++)
				{
					Vertex v = faces.get(i).edges.get(j).vertices[k];
					gl.glVertex3f(v.x, v.y, v.z);
				}
			}
			gl.glEnd();
		}
	}
}
