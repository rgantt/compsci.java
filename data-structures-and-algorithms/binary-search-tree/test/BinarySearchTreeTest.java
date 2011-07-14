import junit.framework.*;
import java.util.*;

public class BinarySearchTreeTest extends TestCase {
	BinarySearchTree<Integer> bstI;
	BinarySearchTree<String> bstS;
	ArrayList<TreeNode<Integer>> fixtureInts;
	ArrayList<TreeNode<String>> fixtureStrings;
	
	public BinarySearchTreeTest( String param ) {
		super( param );
	}
	
	private void loadStrings() {
		bstS = new BinarySearchTree<String>();
		fixtureStrings = new ArrayList<TreeNode<String>>();
		fixtureStrings.add( new TreeNode<String>("alissa") );
		fixtureStrings.add( new TreeNode<String>("ryan") );
		fixtureStrings.add( new TreeNode<String>("david") );
		fixtureStrings.add( new TreeNode<String>("mary") );
		for( TreeNode<String> s : fixtureStrings ) {
			bstS.add( s );
		}
	}
	
	private void loadInts() {
		bstI = new BinarySearchTree<Integer>();
		fixtureInts = new ArrayList<TreeNode<Integer>>();
		fixtureInts.add( new TreeNode<Integer>(5) );
		fixtureInts.add( new TreeNode<Integer>(2) );
		fixtureInts.add( new TreeNode<Integer>(6) );
		fixtureInts.add( new TreeNode<Integer>(1) );
		for( TreeNode<Integer> v : fixtureInts ) {
			bstI.add( v );
		}		
	}
	
	public void tearDown() {
		bstI = null;
		bstS = null;
	}
	
	public void testTreeWalkIntegers() {
		loadInts();
		// walkInOrder should just give us a "naturally"-ordered list
		assertEquals( "1, 2, 5, 6, ", bstI.walkInOrder( bstI.getRoot() ) );
		assertEquals( "1, 2, 6, 5, ", bstI.walkPostOrder( bstI.getRoot() ) );
		assertEquals( "5, 1, 2, 6, ", bstI.walkPreOrder( bstI.getRoot() ) );
	}
	
	public void testTreeWalkStrings() {
		loadStrings();
		// walkInOrder should give us an alphabetical listing
		assertEquals( "alissa, david, mary, ryan, ", bstS.walkInOrder( bstS.getRoot() ) );
		assertEquals( "david, mary, ryan, alissa, ", bstS.walkPostOrder( bstS.getRoot() ) );
		assertEquals( "alissa, david, mary, ryan, ", bstS.walkPreOrder( bstS.getRoot() ) );
	}
	
	public void testSearchExistentNonExistent() {
		loadInts();
		// should return our search value if it's found
		assertEquals( new Integer(6), bstI.search( bstI.getRoot(), 6 ).getKey() );
		// should return null for non-existent nodes
		assertEquals( null, bstI.search( bstI.getRoot(), 42 ) );
	}
	
	public static void main( String[] args ) {
		junit.textui.TestRunner.run( BinarySearchTreeTest.class );
	}
}
