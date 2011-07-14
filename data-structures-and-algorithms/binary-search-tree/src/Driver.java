import java.util.Scanner;
import java.util.*;
import java.io.*;

public class Driver {
	public static void main( String[] args ) {
		boolean exit = false;
		int choice, count;
		while( exit == false ) {
			System.out.println("Please choose an option:\n1. Demonstrate\n2. Exit");
			choice = handleKeyboardInput( 10 );
			switch( choice ) {
				case 1:
					System.out.println("Integers will be generated randomly and added to the BST. How many would you like to add?");
					count = handleKeyboardInput( 50 );
					if( count == 0 ) {
						break;
					}
					ArrayList<TreeNode<Integer>> values = generateRandomValues( count );
					BinarySearchTree<Integer> bst = generateSearchTree( values );
					System.out.println( displayValuesAndTree( values, bst ) );
					System.out.println("Please enter a space-separate list of the keys of the nodes you wish to remove:");
					ArrayList<Integer> ints = handleListOfIntegers();
					for( Integer i : ints ) {
						bst = removeNode( bst, i );
					}
					System.out.println( displayTreeInOrder( bst ) + "\n" );
					break;
				case 2:
					System.out.println("Good-bye.");
					exit = true;
					break;
				default:
					break;
			}
		}
	}
	
	private static int handleKeyboardInput( int maxInt ) {
		Scanner keyboard = new Scanner( System.in );
		try {
			int next = keyboard.nextInt();
			if( next > maxInt ) {
				throw new Exception();
			}
			return next;
		} catch( Exception e ) {
			System.out.println("Please enter a valid integer value.");
			return 0;
		}
	}
	
	private static ArrayList<Integer> handleListOfIntegers() {
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);
		ArrayList<Integer> ints = new ArrayList<Integer>();
		try {
			String line = in.readLine();
			StringTokenizer s = new StringTokenizer( line );
			while( s.hasMoreTokens() ) {
				ints.add( Integer.parseInt( s.nextToken() ) );
			}
		} catch( Exception e ) {
			System.out.println("Input error, could not continue");
		}
		return ints;
	}
	
	private static ArrayList<TreeNode<Integer>> generateRandomValues( int elements ) {
		ArrayList<TreeNode<Integer>> values = new ArrayList<TreeNode<Integer>>();
		Random rng = new Random();
		for( int i = 0; i < elements; i++ ) {
			TreeNode<Integer> next;
			boolean found = true;
			while( found ) {
				next = new TreeNode<Integer>( rng.nextInt( 100 ) + 1 );
				found = false;
				for( TreeNode<Integer> v : values ) {
					if( v.getKey().compareTo( next.getKey() ) == 0 ) {
						found = true;
					}
				}
				if( !found ) {
					values.add( next );
				}
			}
		}
		return values;
	}
	
	private static String displayValuesAndTree( ArrayList<TreeNode<Integer>> values, BinarySearchTree<Integer> bst ) {
		StringBuffer sb = new StringBuffer(), sbElements = new StringBuffer();
		for( TreeNode<Integer> v : values ) {
			sbElements.append( v.getKey() + ", " );
		}
		sb.append( "Values added: " + sbElements.toString() + "\n" );
		sb.append( displayTreeInOrder( bst ) + "\n" );
		return sb.toString();
	}
	
	private static String displayTreeInOrder( BinarySearchTree<Integer> bst ) {
		StringBuffer sb = new StringBuffer();
		sb.append( "In order: " + bst.walkInOrder( bst.getRoot() ) + "\n" );
		return sb.toString();
	}
	
	private static BinarySearchTree<Integer> removeNode( BinarySearchTree<Integer> bst, int key ) {
		TreeNode<Integer> x = bst.search( bst.getRoot(), new Integer( key ) );
		if( x != null ) {
			bst.delete( x );
		} else {
			System.out.println("Node " + key + " does not exist in tree, could not remove.");
		}
		return bst;
	}
	
	private static BinarySearchTree<Integer> generateSearchTree( ArrayList<TreeNode<Integer>> values ) {
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		for( TreeNode<Integer> v : values ) {
			bst.add( v );
		}
		return bst;
	}
}