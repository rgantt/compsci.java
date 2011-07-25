import java.util.ArrayList;

import junit.framework.*;

public class HuffmanTest extends TestCase {
	public HuffmanTest( String param ) {
		super( param );
	}
	
	public void testCorrectEncodingTree() {
		ArrayList<CharObject> c = new ArrayList<CharObject>();
		c.add( new CharObject( 'a', 45 ) );
		c.add( new CharObject( 'b', 13 ) );
		c.add( new CharObject( 'c', 12 ) );
		c.add( new CharObject( 'd', 16 ) );
		c.add( new CharObject( 'e', 9 ) );
		c.add( new CharObject( 'f', 5 ) );
		
		Huffman h = new Huffman();
		HuffmanNode hn = h.encodeHuffman( c );
		
		// make some assertions about the tree here
	}
	
	public static void main( String[] args ) {
		junit.textui.TestRunner.run( HuffmanTest.class );
	}
}
