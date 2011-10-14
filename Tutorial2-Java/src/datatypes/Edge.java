package datatypes;

public class Edge
{
	// an array of the vertices, clockwise
	public Vertex[] vertices = new Vertex[2];
	private Boolean checked = false;
	
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
	
	public Edge invert()
	{
		return new Edge(this.vertices[1], this.vertices[0]);
	}
	
	public void setChecked()
	{
		if(!this.checked) this.checked = true;
		else System.out.println("Error: edge has already been checked");
	}
	
	public Boolean getChecked() { return this.checked; }
}
