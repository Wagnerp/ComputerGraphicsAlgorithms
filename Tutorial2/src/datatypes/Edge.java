package datatypes;

public class Edge
{
	public Vertex[] vertices = new Vertex[2];
	
	public Edge(Vertex _startPoint, Vertex _endPoint)
	{
		this.vertices[0] = _startPoint;
		this.vertices[1] = _endPoint;
	}
	
	public void print()
	{
		System.out.println("Vertex 1: ");
		vertices[0].print();
		
		System.out.println("Vertex 2: ");
		vertices[1].print();
	}
	
	// invert edge??
}
