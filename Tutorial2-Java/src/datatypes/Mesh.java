package datatypes;

import java.util.ArrayList;

import javax.media.opengl.GL2;

/**
 * Stores the faces that make up a mesh model.
 * Handles drawing the points to a GL surface
 *
 * @author Tom
 * @version 0.1
 * @history 13.10.2011: Created class
 */

public class Mesh
{
	String name = "";
	
	private ArrayList<Face> faces = new ArrayList<Face>();
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
	
	public Mesh(String _name)
	{
		this.name = _name; 
	}
	
	public void addFace(Face faceToAdd)
	{
		this.faces.add(faceToAdd);
	}
	
	public void display(GL2 gl)
	{			
		for (int i = 0; i < faces.size(); i++)
		{
			if (faces.get(i).faceType == Face.PolygonType.TRIANGLE) gl.glBegin(GL2.GL_TRIANGLES);
			else gl.glBegin(GL2.GL_QUADS);
			
			// float-value colours
			//gl.glColor3f(colours[i].x, colours[i].y, colours[i].z);
			
			// RBG-value colours
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
