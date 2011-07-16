public interface Graph {
	public boolean addEdge( Vertex i, Vertex j, int weight );
	public boolean addEdge( Vertex i, Vertex j );
	public boolean removeEdge( Vertex i, Vertex j );
	public boolean edgeBetween( Vertex i, Vertex j );
	public Vertex[] adjacentTo( Vertex i );
}