import java.util.*;

public class Driver {
	private static RedBlackTree rb;
	private static final int INDENT = 4;

	public static void main( String[] args ) {
		rb = new RedBlackTree();
		Scanner scan = new Scanner( System.in );
		int start = 0;
		boolean ans;
		do {
			System.out.println("Add what? (-1 to finish)");
			start = scan.nextInt();
			scan.nextLine();
			if( start > 0 )
				ans = rb.add( start );
			else
				ans = false;
			if(!ans)
				System.out.println( start + " not added" );
			else
				System.out.println( rb.walkInOrder( rb.getRoot() ) );
		} while( start != -1 );
		//int[] vars = { 82, 75, 98, 60, 79, 80, 65, 62 };
		//for( int i : vars ) {
		//	rb.add( i );
		//}
		System.out.println( rb.walkInOrder( rb.getRoot() ) );
		printHelper( rb.getRoot(), 1 );
		do {
			System.out.println("Remove what? (-1 to finish)");
			start = scan.nextInt();
			scan.nextLine();
			if( start > 0 ) {
				ans = rb.delete( start );
				System.out.println( rb.walkInOrder( rb.getRoot() ) );
				printHelper( rb.getRoot(), 0 );
			}
		} while( start != -1 );

		System.out.println("Goodbye");
	}

	private static void printHelper( Node n, int indent ) {
		if ( n == null ) {
			System.out.println("Empty tree!");
			return;
		}
		if ( n.getRight() != null ) {
			printHelper( n.getRight(), indent + INDENT );
		}
		for ( int i = 0; i < indent; i++ )
			System.out.print(" ");
		if (!n.isRed)
			System.out.println( n.getItem() );
		else
			System.out.println( "<" + n.getItem() + ">" );
		if ( n.getLeft() != null ) {
			printHelper( n.getLeft(), indent + INDENT );
		}
	}
}