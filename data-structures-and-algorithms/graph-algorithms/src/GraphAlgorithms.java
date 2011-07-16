import java.util.*;

public class GraphAlgorithms {
	private MatrixGraph graph;
	
	GraphAlgorithms( ArrayList<ArrayList<Integer>> matrix ) {
		this.graph = new MatrixGraph( matrix.size() );
		this.graph.setMatrix( matrix );
	}
	
	public void setMatrix( ArrayList<ArrayList<Integer>> m ) {
		this.graph.setMatrix( m );
	}
	
	public void setMatrix( int[][] m ) {
		this.graph.setMatrix( m );
	}
	
	public ArrayList<ArrayList<Integer>> getMatrix() {
		return this.graph.getMatrix();
	}
	
	public void dijkstras( int root ) {
		final int[] distance = new int[ this.graph.vertexCount ];
		final String[] path = new String[ this.graph.vertexCount ];
				
		path[0] = "0";
		distance[0] = 0;
		for( int i = 1; i < this.graph.vertexCount; i++ ){
			distance[i] = 10;
		}
		
		//use a priority queue with dijkstras, rewrote compare method to use distance in heap as comparison, rather than node number
		PriorityQueue<Integer> matrixQueue = new PriorityQueue<Integer>( this.graph.vertexCount, new Comparator<Integer>(){
			public int compare( Integer i, Integer j ) {
				if( distance[ i ] > 9 ) {
					return 1;
				} else if( distance[ j ] > 9 ) {
					return -1;
				} else if( distance[ i ] < distance[ j ] ) {
					return -1;
				} else if( distance[ i ] > distance[ j ] ) {
					return 1;
				} else {
					return 0;
				}
			}
		});		
		
		boolean[] enqueued = new boolean[ this.graph.vertexCount ];
		matrixQueue.add( root );
		enqueued[ root ] = true;
		
		while( matrixQueue.peek() != null ) {	
			System.out.println( "Visiting " + matrixQueue.peek() );
			for( int j = 0; j < this.graph.vertexCount; j++ ) {
				if( ( this.graph.alist.get( matrixQueue.peek() ).get(j) > 0 ) && enqueued[ j ] == false ) {
					matrixQueue.add(j);
					enqueued[ j ] = true;
					if( ( distance[ j ] > 9 ) || ( this.graph.alist.get( matrixQueue.peek() ).get(j) < this.graph.alist.get( matrixQueue.peek() ).get(j) + distance[ j ] ) ) {
						System.out.println("Adding " + this.graph.alist.get( matrixQueue.peek() ).get(j) + " to " + j );
						distance[ j ] = this.graph.alist.get( matrixQueue.peek() ).get(j) + distance[ matrixQueue.peek() ];
						path[ j ] = path[ matrixQueue.peek() ] + ", " + j;
					}
				}
			}
			matrixQueue.remove();
		}
		
		System.out.println("Node\tDist.\tPath");
		for( int d = 0; d < this.graph.vertexCount; d++ ) {
			System.out.println( d + "\t" + distance[d] + "\t" + path[d] );
		}
	}
	
	public void primsMST( int root )
	{
		//prim's variables
		final int[] parent;			//store the parent (node coming we're coming from)
		final int[] minDistance;	//store the minimum distance between nodes
		
		//populate the prim's fields, and populate the min distance with all infinity (-1)
		parent = new int[this.graph.vertexCount];
		minDistance = new int[this.graph.vertexCount];
		minDistance[0] = 0;
		for(int i = 1; i < this.graph.vertexCount; i++) {
			minDistance[i] = -1;
		}
		
		int[][] minSpan = new int[this.graph.vertexCount][this.graph.vertexCount];	//store the minimum spanning tree output - starts with all 0s
		for( int i = 0; i < this.graph.vertexCount; i++ ) {
			for( int j = 0; j < this.graph.vertexCount; j++ ) {
				minSpan[i][j] = 0;
			}
		}
		
		//use a priority queue with prims, rewrote compare method to use distance in heap
		PriorityQueue<Integer> matrixQueue = new PriorityQueue<Integer>( this.graph.vertexCount, new Comparator<Integer>(){
			public int compare( Integer i, Integer j ) {
				if( minDistance[i] == -1 ) {
					return 1;
				} else if( minDistance[j] == -1 ) {
					return -1;
				} else if( minDistance[i] < minDistance[j] ) {
					return -1;
				} else if( minDistance[i] > minDistance[j] ) {
					return 1;
				} else {
					return 0;
				}
			}
		});
		
		boolean[] known = new boolean[this.graph.vertexCount];	//store whether the edge to that node is known
		boolean[] visited = new boolean[this.graph.vertexCount];	//store whether the node has been visited
		
		matrixQueue.add(root);
		visited[root] = true;
		
		while( matrixQueue.peek() != null ) {	
			known[matrixQueue.peek()] = true;

			for ( int j = 0; j < this.graph.vertexCount; j++ ) {
				if( ( this.graph.alist.get( matrixQueue.peek() ).get(j) > 0 ) && ( visited[j] == false ) && ( known[j] == false ) && ( matrixQueue.peek() !=j ) ) {					
					if( ( minDistance[j] == -1 ) || ( this.graph.alist.get( matrixQueue.peek() ).get(j) < minDistance[j] ) ) {
						minDistance[j] = this.graph.alist.get( matrixQueue.peek() ).get(j);
						parent[j] = matrixQueue.peek();
					}
					matrixQueue.add(j);
				}
			}
			if( matrixQueue.peek() != root ) {
				minSpan[ parent[ matrixQueue.peek() ] ][ matrixQueue.peek() ] = 1;
				minSpan[ matrixQueue.peek() ][ parent[ matrixQueue.peek() ] ] = 1;
			}
			visited[ matrixQueue.peek() ] = true;
			matrixQueue.remove();
		}
		
		System.out.println("Node\tDist.\tPath");
		for( int d = 0; d < this.graph.vertexCount; d++ ){
			System.out.println( d + "\t" + minDistance[d] + "\t" + parent[d] );
		}
		System.out.println("Minimum Spanning Tree");
		for( int i = 0; i < this.graph.vertexCount; i++ ) {
			for( int j = 0; j < this.graph.vertexCount; j++ ) {
				System.out.print( minSpan[i][j] + " " );
			}
			System.out.println("");
		}
	}
	
	public void BFS( int s ) {
		Queue<Vertex> q = new LinkedList<Vertex>();
		q.add( this.graph.vertices[s][s] );
		while( q.peek() != null ) {
			Vertex u = q.remove();
			for( Vertex v : this.graph.adjacentTo( u ) ) {
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
		for( int i = 0; i < this.graph.alist.size(); i++ ) {
			Vertex v = this.graph.vertices[i][i];
			if( v.color == -1 ) {
				DFS_visit( v );
			}
		}
	}
	
	public void DFS_visit( Vertex u ) {
		u.color = 0;
		for( Vertex v : this.graph.adjacentTo( u ) ) {
			if( v.color == -1 ) {
				DFS_visit( v );
			}
		}
		System.out.println( "Visited " + u.x );
		u.color = 1;
	}
	
	public MatrixGraph warshall() {
		int[][] adj = this.graph.matrix;
		for( int i = 0; i < this.graph.matrix.length; i++ ) {
			for( int j = 0; j < this.graph.matrix.length; j++ ) {
				for( int k = 0; k < this.graph.matrix.length; k++ ) {
					if( adj[j][i] == 0 ) {
						adj[j][k] = ( ( adj[j][i] == 1 && adj[i][k] == 1 ) ? 1 : 0 );
					}
				}
			}
		}
		return new MatrixGraph( adj );
	}
	
	public MatrixGraph floyd_warshall() {
		int[][] adj = this.graph.matrix;
		for( int i = 0; i < this.graph.matrix.length; i++ ) {
			for( int j = 0; j < this.graph.matrix.length; j++ ) {
				for( int k = 0; k < this.graph.matrix.length; k++ ) {
					adj[j][k] = Math.min( adj[j][i], adj[j][i] + adj[i][k] );
				}
			}
		}
		return new MatrixGraph( adj );
	}
}
