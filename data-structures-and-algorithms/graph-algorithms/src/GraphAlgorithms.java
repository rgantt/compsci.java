import java.util.*;

public class GraphAlgorithms {
	private ArrayList<ArrayList<Integer>> matrix;
	private Vertex[][] vertices;
	
	//prim's variables
	private int[] paParent;			//store the parent (node coming we're coming from)
	private int[] paMinDistance;	//store the minimum distance between nodes
	
	int[] daDistance;
	String[] daPath;
	
	private int size = 0;
	
	private class Vertex {
		public int x;
		public int y;
		public int color;
		public int value = 0;
		
		Vertex( int v, int x, int y, int color ) {
			this.value = v;
			this.x = x;
			this.y = y;
			this.color = color;
		}
		
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append( "x: " + this.x + "\n" );
			sb.append( "y: " + this.y + "\n" );
			sb.append( "c: " + this.color + "\n" ) ;
			sb.append( "val: " + this.value + "\n\n" );
			return sb.toString();
		}
	}
	
	GraphAlgorithms( ArrayList<ArrayList<Integer>> matrix ) {
		this.setMatrix( matrix );
		
		//populate the prim's fields, and populate the min distance with all infinity (-1)
		paParent = new int[size];
		paMinDistance = new int[size];
		paMinDistance[0] = 0;
		for(int i = 1; i < size; i++)
		{
			paMinDistance[i] = -1;
		}
	}
	
	public void setMatrix( ArrayList<ArrayList<Integer>> matrix ) {
		this.matrix = matrix;
		this.vertices = new Vertex[matrix.size()][matrix.size()];
		for( int i = 0; i < matrix.size(); i++ ) {
			for( int j = 0; j < matrix.get(i).size(); j++ ) {
				vertices[i][j] = new Vertex( matrix.get(i).get(j), i, j, -1 );
			}
		}
		this.size = this.matrix.size();
	}
	
	public ArrayList<ArrayList<Integer>> getMatrix() {
		return this.matrix;
	}
	
	public ArrayList<Vertex> adjacentTo( Vertex vertex ) {
		ArrayList<Vertex> a = new ArrayList<Vertex>();
		for( int i = 0; i < matrix.get( vertex.x ).size(); i++ ) {
			if( ( matrix.get(vertex.x).get(i) > 0 ) && matrix.get(vertex.x).get(i) < 10  ) {
				a.add( vertices[i][i] );
			}
		}
		return a;
	}
	
	public int weight( Vertex u, Vertex v ) {
		//System.out.println( "w(" + u.x + "," + v.x + ") = " + vertices[ u.x ][ v.x ].value );
		return vertices[ u.x ][ v.x ].value;
	}
	
	public void dijkstras( int root ) {
		// set all the dijkstra's fields, populate the distance table from the weighted matrix
		this.daDistance = new int[ size ];
		this.daPath = new String[ size ];
				
		daPath[0] = "0";
		daDistance[0] = 0;
		for( int i = 1; i < size; i++ ){
			daDistance[i] = 10;
		}
		
		//use a priority queue with dijkstras, rewrote compare method to use distance in heap as comparison, rather than node number
		PriorityQueue<Integer> matrixQueue = new PriorityQueue<Integer>( size, new Comparator<Integer>(){
			public int compare( Integer i, Integer j ) {
				if( daDistance[ i ] > 9 ) {
					return 1;
				} else if( daDistance[ j ] > 9 ) {
					return -1;
				} else if( daDistance[ i ] < daDistance[ j ] ) {
					return -1;
				} else if( daDistance[ i ] > daDistance[ j ] ) {
					return 1;
				} else {
					return 0;
				}
			}
		});		
		
		boolean[] enqueued = new boolean[ size ];
		matrixQueue.add( root );
		enqueued[ root ] = true;
		
		while( matrixQueue.peek() != null ) {	
			System.out.println( "Visiting " + matrixQueue.peek() );
			for( int j = 0; j < size; j++ ) {
				if( ( matrix.get( matrixQueue.peek() ).get(j) > 0 ) && enqueued[ j ] == false ) {
					matrixQueue.add(j);
					enqueued[ j ] = true;
					if( ( daDistance[ j ] > 9 ) || ( matrix.get( matrixQueue.peek() ).get(j) < matrix.get( matrixQueue.peek() ).get(j) + daDistance[ j ] ) ) {
						System.out.println("Adding " + matrix.get( matrixQueue.peek() ).get(j) + " to " + j );
						daDistance[ j ] = matrix.get( matrixQueue.peek() ).get(j) + daDistance[ matrixQueue.peek() ];
						daPath[ j ] = daPath[ matrixQueue.peek() ] + ", " + j;
					}
				}
			}
			matrixQueue.remove();
		}
		
		System.out.println("Node\tDist.\tPath");
		for( int d = 0; d < size; d++ ) {
			System.out.println( d + "\t" + daDistance[d] + "\t" + daPath[d] );
		}
	}
	
	public void primsMST(int root)
	{
		int[][] minSpan = new int[size][size];	//store the minimum spanning tree output - starts with all 0s
		for(int i = 0; i<size; i++)
		{
			for(int j = 0; j<size; j++)
			{
				minSpan[i][j] = 0;
			}
		}
		
		//use a priority queue with prims, rewrote compare method to use distance in heap
		PriorityQueue<Integer> matrixQueue = new PriorityQueue<Integer>(size, new Comparator<Integer>(){
			public int compare(Integer i, Integer j)
			{
				if(paMinDistance[i]==-1)
				{
					return 1;
				}
				else if(paMinDistance[j]==-1)
				{
					return -1;
				}
				else if(paMinDistance[i]<paMinDistance[j])
				{
					return -1;
				}
				else if(paMinDistance[i]>paMinDistance[j])
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}});
		
		boolean[] known = new boolean[size];	//store whether the edge to that node is known
		boolean[] visited = new boolean[size];	//store whether the node has been visited
		
		matrixQueue.add(root);
		visited[root] = true;
		
		while(matrixQueue.peek() != null)
		{	
			known[matrixQueue.peek()] = true;

			for (int j=0; j< size; j++)
			{
			
				if(matrix.get(matrixQueue.peek()).get(j)>0 && visited[j]==false && known[j]==false && matrixQueue.peek()!=j)
				{
					
					if(paMinDistance[j]==-1 || matrix.get(matrixQueue.peek()).get(j)< paMinDistance[j])
					{
						paMinDistance[j] = matrix.get(matrixQueue.peek()).get(j);
						paParent[j] = matrixQueue.peek();
					}
					matrixQueue.add(j);
				}
				
			}
			if(matrixQueue.peek() != root)
			{
				minSpan[paParent[matrixQueue.peek()]][matrixQueue.peek()] = 1;
				minSpan[matrixQueue.peek()][paParent[matrixQueue.peek()]] = 1;
			}
			visited[matrixQueue.peek()] = true;
			matrixQueue.remove();
		}
		
		System.out.println("Node        Distance    Path");
		for(int d = 0; d < size; d++)
		{
			System.out.println(d + "           " + paMinDistance[d] + "           " + paParent[d]);
		}
		System.out.println("Minimum Spanning Tree");
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				System.out.print(minSpan[i][j] + " ");
			}
			System.out.println("");
		}
		
		
	}
	
	public void BFS( int s ) {
		Queue<Vertex> q = new LinkedList<Vertex>();
		q.add( vertices[s][s] );
		while( q.peek() != null ) {
			Vertex u = q.remove();
			for( Vertex v : adjacentTo( u ) ) {
				if( v.color == -1 ) {
					v.color = 0;
					q.add( v );
				}
			}
			System.out.println( "visited " + u.x );
			u.color = 1;
		}
	}
	
	public void DFS() {
		for( int i = 0; i < matrix.size(); i++ ) {
			Vertex v = vertices[i][i];
			if( v.color == -1 ) {
				DFS_visit( v );
			}
		}
	}
	
	public void DFS_visit( Vertex u ) {
		u.color = 0;
		for( Vertex v : adjacentTo( u ) ) {
			if( v.color == -1 ) {
				DFS_visit( v );
			}
		}
		System.out.println( "Visited " + u.x );
		u.color = 1;
	}
	
	public ArrayList<ArrayList<Integer>> warshall() {
		ArrayList<ArrayList<Integer>> adj = matrix;
		for( int i = 0; i < matrix.size(); i++ ) {
			for( int j = 0; j < matrix.size(); j++ ) {
				for( int k = 0; k < matrix.size(); k++ ) {
					if( adj.get( j ).get( k ) == 0 ) {
						adj.get( j ).set( k, ( adj.get( j ).get( i ) == 1 && adj.get( i ).get( k ) == 1 ) ? 1 : 0 );
					}
				}
			}
		}
		return adj;
	}
	
	public ArrayList<ArrayList<Integer>> floyd_warshall() {
		ArrayList<ArrayList<Integer>> adj = matrix;
		for( int k = 0; k < matrix.size(); k++ ) {
			for( int i = 0; i < matrix.size(); i++ ) {
				for( int j = 0; j < matrix.size(); j++ ) {
					adj.get(i).set( j, Math.min( adj.get(i).get(j), adj.get(i).get(k) + adj.get(k).get(j) ) );
				}
			}
		}
		return adj;
	}
}
