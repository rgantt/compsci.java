import java.util.*;
import java.io.*;

public class Driver {
	public static final int INDENT = 4;
	
	public static void main( String[] args ) {
		// Read in a file, generate an ArrayList of CharObject-s to pass to Huffman
		Hashtable<Character,Integer> hashTable = new Hashtable<Character,Integer>();
		try {
			File file = new File("fixtures/input_file.txt");
			FileInputStream s = new FileInputStream( file );
			long fileLength = file.length();
			byte[] bytes = new byte[(int)fileLength];
			
			System.out.println("Success in opening fixture");
			int offset = 0;
			int numRead = 0;
			while( offset < bytes.length && ( numRead = s.read( bytes, offset, bytes.length-offset ) ) >= 0 ) {
				System.out.println("Increasing offset by " + numRead );
				offset += numRead;
			}
			s.close();
			
			for( int i = 0; i < bytes.length; i++ ) {
				Character b = (char)bytes[i];
				System.out.println("Messing with " + b );
				if( hashTable.containsKey( b ) ) {
					Integer val = hashTable.remove( b );
					hashTable.put( b, val+1 );
				} else {
					hashTable.put( b, 1 );
				}
			}
			
			ArrayList<CharObject> c = new ArrayList<CharObject>();
			for( Enumeration<Character> chars = hashTable.keys(); chars.hasMoreElements(); ) {
				Character ne = chars.nextElement();
				CharObject co = new CharObject( ne, hashTable.get( ne ) );
				c.add( co );
			}
			
			Huffman h = new Huffman();
			HuffmanNode hn = h.huffmanTree( c );
			HuffmanNode ht = h.huffmanCodes( hn, new StringBuffer("") );
			printHelper( ht, 1 );
		} catch( IOException e ) {
			System.out.println( e.getMessage() );
		}
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
			System.out.println( "<" + n.freq + " ("+n.code+")>" );
		} else {
			System.out.println( "<" + n.label + ":" + n.freq + " ("+n.code+")>" );
		}
		if ( n.left != null ) {
			printHelper( n.left, indent + INDENT );
		}
	}
}
