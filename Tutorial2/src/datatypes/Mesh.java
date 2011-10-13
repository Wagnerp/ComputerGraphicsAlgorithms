package datatypes;

import java.util.ArrayList;

import javax.media.opengl.GL2;

public class Mesh
{
	String name = "";
	
	private ArrayList<Face> faces = new ArrayList<Face>();
	private Vertex[] colours = { new Vertex(0.0f,1.0f,0.0f), new Vertex(1.0f,0.5f,0.0f), new Vertex(1.0f,0.0f,0.0f), new Vertex(1.0f,1.0f,0.0f), new Vertex(0.0f,0.0f,1.0f), new Vertex(1.0f,0.0f,1.0f) };
	
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
			gl.glColor3f(colours[i].x, colours[i].y, colours[i].z);
			for (int j = 0; j < faces.get(i).edges.length; j++)
			{			
				for (int k = 0; k < faces.get(i).edges[k].vertices.length; k++)
				{
					Vertex v = faces.get(i).edges[j].vertices[k];
					gl.glVertex3f(v.x, v.y, v.z);
				}
			}
		}
	}
}
