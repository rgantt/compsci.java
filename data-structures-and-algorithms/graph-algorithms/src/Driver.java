import java.io.*;
import java.util.*;

public class Driver {
	public static void main( String[] args ) {
		System.out.println("BFS:\n");
		
		GraphAlgorithms ga = new GraphAlgorithms( parseMatrix("fixtures/adjacenyFixture.txt") );
		ga.BFS( 0 );
		
		System.out.println("\nDFS:\n");
		
		ga.setMatrix( parseMatrix("fixtures/adjacenyFixture.txt") );
		ga.DFS();
		
		System.out.println("\nPrim's:\n");
		
		ga.setMatrix( parseMatrix("fixtures/floydWarshall.txt") );
		printMatrix( ga.getMatrix() );
		ga.primsMST( 0 );
		
		System.out.println("\nDijkstra's:\n");
		
		ga.setMatrix( parseMatrix("fixtures/dijkstras.txt") );
		printMatrix( ga.getMatrix() );
		ga.dijkstras( 0 );
		
		System.out.println("\nWarshall reachability:\n");
		
		ga.setMatrix( parseMatrix("fixtures/classExample.txt") );
		printMatrix( ga.getMatrix() );
		printMatrix( ga.warshall() );
		
		System.out.println("\nWarshall reachability:\n");
		
		ga.setMatrix( parseMatrix("fixtures/adjacenyFixture.txt") );
		printMatrix( ga.getMatrix() );
		printMatrix( ga.warshall() );
		
		System.out.println("\nFloyd-Warshall distance:\n");
		
		ga.setMatrix( parseMatrix("fixtures/floydWarshall.txt") );
		printMatrix( ga.getMatrix() );
		printMatrix( ga.floyd_warshall() );
	}
	
	public static ArrayList<ArrayList<Integer>> parseMatrix( String filename ) {
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();
		try {
			Scanner scanner = new Scanner( new File( filename ) );
			while( scanner.hasNextLine() ) {
				ArrayList<Integer> line = new ArrayList<Integer>();
				for( char c : ((String)scanner.nextLine()).toCharArray() ) {
					int v;
					if( c == 'x' ) {
						v = 0;
					} else if( c == 'n' ) {
						// would be nice to have something not in domain
						v = 999999;
					} else {
						v = Integer.parseInt( String.valueOf( c ) );
					}
					line.add( v );
				}
				matrix.add( line );
			}
		} catch( IOException e ) {
			System.out.println( e.getMessage() );
		}
		return matrix;
	}
	
	public static void printMatrix( ArrayList<ArrayList<Integer>> matrix ) {
		for( ArrayList<Integer> rows : matrix ) {
			for( int cell : rows ) {
				System.out.print( cell + " " );
			}
			System.out.println("");
		}
	}
}
