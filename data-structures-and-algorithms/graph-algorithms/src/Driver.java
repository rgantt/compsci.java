public class Driver {
	public static void main( String[] args ) {		
		System.out.println("BFS:\n");
		
		GraphAlgorithms ga = new GraphAlgorithms( Matrix.input("fixtures/adjacenyFixture.txt") );
		ga.setMatrix( Matrix.input( "fixtures/weightedMatrix.txt", 5 ) );
		ga.BFS( 0 );
		
		System.out.println("\nDFS:\n");
		
		ga.setMatrix( Matrix.input( "fixtures/weightedMatrix.txt", 5 ) );
		ga.DFS();
		
		System.out.println("\nPrim's:\n");
		
		ga.setMatrix( Matrix.input( "fixtures/weightedMatrix.txt", 5 ) );
		Matrix.print( ga.getMatrix() );
		ga.primsMST( 0 );
		
		System.out.println("\nDijkstra's:\n");
		
		// bummer that i need to specify the size ex ante
		ga.setMatrix( Matrix.input( "fixtures/weightedMatrix.txt", 5 ) );
		Matrix.print( ga.getMatrix() );
		ga.dijkstras( 0 );
		
		System.out.println("\nWarshall reachability:\n");
		
		ga.setMatrix( Matrix.input("fixtures/classExample.txt") );
		Matrix.print( ga.getMatrix() );
		Matrix.print( ga.warshall() );
		
		System.out.println("\nWarshall reachability:\n");
		
		ga.setMatrix( Matrix.input("fixtures/adjacenyFixture.txt") );
		Matrix.print( ga.getMatrix() );
		Matrix.print( ga.warshall() );
		
		System.out.println("\nFloyd-Warshall distance:\n");
		
		ga.setMatrix( Matrix.input("fixtures/floydWarshall.txt") );
		Matrix.print( ga.getMatrix() );
		Matrix.print( ga.floyd_warshall() );
	}
}
