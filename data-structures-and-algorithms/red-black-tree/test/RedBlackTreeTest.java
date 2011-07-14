import junit.framework.TestCase;

public class RedBlackTreeTest extends TestCase {
	RedBlackTree rbt;
	
	public RedBlackTreeTest( String param ) {
		super( param );
	}
	
	public void setUp() {
		rbt = new RedBlackTree();
	}
	
	public void tearDown() {
		rbt = null;
	}
	
	public void testSimpleTreeAddition() {
		int[] f = { 8, 5, 12, 3, 2, 1 };
		for( int n : f ) {
			rbt.add( n );
		}
		assertEquals( 8, rbt.search( rbt.getRoot(), 12 ).getParent().getItem() );
		assertEquals( false, rbt.search( rbt.getRoot(), 12 ).isRed );
		
		assertEquals( 8, rbt.search( rbt.getRoot(), 3 ).getParent().getItem() );
		assertEquals( true, rbt.search( rbt.getRoot(), 3 ).isRed );
	}

	public void testSimpleTreeRemoval() {
		int[] f = { 8, 5, 12, 3, 2, 1 };
		for( int n : f ) {
			rbt.add( n );
		}
		
		rbt.delete( 5 );
		assertEquals( 12, rbt.search( rbt.getRoot(), 12 ).getItem() );
		assertEquals( null, rbt.search( rbt.getRoot(), 5 ) );
	}
	
	public static void main( String[] args ) {
		junit.textui.TestRunner.run( RedBlackTreeTest.class );
	}
}
