import java.util.*;

public class MatrixGraph extends Matrix implements Graph {
    public ArrayList<ArrayList<Integer>> alist;
    public Vertex[][] vertices;
    public int vertexCount;

    MatrixGraph( int vertexCount ) {
    	this.vertexCount = vertexCount;
    	matrix = new int[vertexCount][vertexCount];
    }
    
    MatrixGraph( int[][] matrix ) {
    	this.vertexCount = matrix.length;
    	this.matrix = matrix;
    }
    
	/**
	 * Converts a 2-d integer array to a 2-d ArrayList
	 * 
	 * @param matrix
	 */
	public void setMatrix( int[][] matrix ) {
		this.matrix = matrix;
		int length = matrix[0].length;
		ArrayList<ArrayList<Integer>> m = new ArrayList<ArrayList<Integer>>();
		for( int i = 0; i < length; i++ ) {
			ArrayList<Integer> n = new ArrayList<Integer>();
			for( int j = 0; j < length; j++ ) {
				n.add( matrix[ i ][ j ] );
			}
			m.add( n );
		}
		setMatrix( m );
	}
	
	public void setMatrix( ArrayList<ArrayList<Integer>> matrix ) {
		this.alist = matrix;
		this.vertices = new Vertex[matrix.size()][matrix.size()];
		for( int i = 0; i < matrix.size(); i++ ) {
			for( int j = 0; j < matrix.get(i).size(); j++ ) {
				vertices[i][j] = new Vertex( matrix.get(i).get(j), i, j, -1 );
			}
		}
		this.vertexCount = this.matrix[0].length;
	}
	
	public ArrayList<ArrayList<Integer>> getMatrix() {
		return this.alist;
	}
	
	public int weight( Vertex u, Vertex v ) {
		return vertices[ u.x ][ v.x ].value;
	}
    
    public boolean addEdge( Vertex i, Vertex j ) {
    	return addEdge( i, j, 1 );
    }

    public boolean addEdge( Vertex i, Vertex j, int w ) {
          if ( i.x >= 0 && i.x < vertexCount && i.y > 0 && i.y < vertexCount ) {
        	  matrix[i.x][i.y] = w;
        	  matrix[i.y][i.x] = w;
        	  return true;
          } else {
        	  return false;
          }
    }

    public boolean removeEdge( Vertex i, Vertex j ) {
    	return this.removeEdge( i.x, i.y );
    }
    
    public boolean removeEdge( int i, int j ) {
          if (i >= 0 && i < vertexCount && j > 0 && j < vertexCount) {
        	  matrix[i][j] = 0;
        	  matrix[j][i] = 0;
        	  return true;
          } else {
        	  return false;
          }
    }

    public boolean isEdge( int i, int j ) {
          if (i >= 0 && i < vertexCount && j > 0 && j < vertexCount)
                return ( matrix[i][j] > 0 ? true : false );
          else
                return false;
    }
    
	public Vertex[] adjacentTo( Vertex vertex ) {
		ArrayList<Vertex> a = new ArrayList<Vertex>();
		for( int i = 0; i < matrix[ vertex.x ].length; i++ ) {
			if( ( vertices[ vertex.x ][ i ].value > 0 ) && ( vertices[ vertex.x ][ i ].value < Integer.MAX_VALUE ) ) {
				a.add( vertices[i][i] );
			}
		}
		Vertex[] v = new Vertex[ a.size() ];
		for( int i = 0; i < v.length; i++ ) {
			v[ i ] = a.get( i );
		}
		return v;
	}
	
	public boolean edgeBetween( Vertex i, Vertex j ) {
		return true;
	}
}