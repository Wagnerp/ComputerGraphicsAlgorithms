package datatypes;

public class Vertex
{
	public float x;
	public float y;
	public float z;	

	public Vertex(float xCoord, float yCoord, float zCoord)
	{
		x = xCoord;
		y = yCoord;
		z = zCoord;
	}
	
	public void print()
	{
		System.out.println("x: " + x + " y: " + y + " z: " + z);
	}
}
