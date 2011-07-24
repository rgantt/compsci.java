import java.util.*;

public class Driver {
	public static final int INDENT = 4;
	
	public static void main( String[] args ) {
		ArrayList<CharObject> c = new ArrayList<CharObject>();
		c.add( new CharObject( 'a', 45 ) );
		c.add( new CharObject( 'b', 13 ) );
		c.add( new CharObject( 'c', 12 ) );
		c.add( new CharObject( 'd', 16 ) );
		c.add( new CharObject( 'e', 9 ) );
		c.add( new CharObject( 'f', 5 ) );
		
		Huffman h = new Huffman();
		HuffmanNode hn = h.encodeHuffman( c );
		printHelper( hn, 1 );
	}
	
	private static void printHelper( HuffmanNode n, int indent ) {
		if ( n == null ) {
			System.out.println("Empty tree!");
			return;
		}
		if ( n.right != null ) {
			printHelper( n.right, indent + INDENT );
		}
		for ( int i = 0; i < indent; i++ )
			System.out.print(" ");
		if( new Character( n.label ).equals( null ) ) {
			System.out.println( "<" + n.freq + ">" );
		} else {
			System.out.println( "<" + n.label + ":" + n.freq + ">" );
		}
		if ( n.left != null ) {
			printHelper( n.left, indent + INDENT );
		}
	}
}
