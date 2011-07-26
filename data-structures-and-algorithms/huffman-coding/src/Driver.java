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
			byte[] bytes = new byte[ (int)fileLength ];
			
			int offset = 0;
			int numRead = 0;
			while( offset < bytes.length && ( numRead = s.read( bytes, offset, bytes.length-offset ) ) >= 0 ) {
				offset += numRead;
			}
			s.close();
			
			StringBuffer input = new StringBuffer("");
			for( int i = 0; i < bytes.length; i++ ) {
				Character b = (char)bytes[i];
				input.append(b);
				if( hashTable.containsKey( b ) ) {
					Integer val = hashTable.remove( b );
					hashTable.put( b, val+1 );
				} else {
					hashTable.put( b, 1 );
				}
			}
			System.out.print( input.toString() );
			
			ArrayList<CharObject> c = new ArrayList<CharObject>();
			for( Enumeration<Character> chars = hashTable.keys(); chars.hasMoreElements(); ) {
				Character ne = chars.nextElement();
				CharObject co = new CharObject( ne, hashTable.get( ne ) );
				c.add( co );
			}
			
			Huffman h = new Huffman();
			HuffmanNode hn = h.huffmanTree( c );
			Hashtable<Character,String> hash = new Hashtable<Character,String>();
			HuffmanNode ht = h.huffmanCodes( hn, hash, new StringBuffer("") );
			//printHelper( ht, 1 );
			System.out.println( encodeFile( "output/encoded.txt", hash ) );
			System.out.println( decodeFile( "output/encoded.txt", hash ) );
		} catch( IOException e ) {
			System.out.println( e.getMessage() );
		}
	}
	
	private static String encodeFile( String filename, Hashtable<Character,String> hash ) throws IOException {
		BufferedWriter out = new BufferedWriter( new FileWriter( filename ) );
		
		File file = new File("fixtures/input_file.txt");
		FileInputStream s = new FileInputStream( new File("fixtures/input_file.txt") );
		long fileLength = file.length();
		byte[] bytes = new byte[ (int)fileLength ];
		
		int offset = 0;
		int numRead = 0;
		while( offset < bytes.length && ( numRead = s.read( bytes, offset, bytes.length-offset ) ) >= 0 ) {
			offset += numRead;
		}
		s.close();
		
		StringBuffer str = new StringBuffer("");
		for( int i = 0; i < bytes.length; i++ ) {
			Character b = (char)bytes[i];
			str.append( hash.get( b ) );
		}
		out.write( str.toString() );
		out.close();
		return str.toString();
	}
	
	private static String decodeFile( String filename, Hashtable<Character,String> hash ) throws IOException {
		File file = new File( filename );
		FileInputStream s = new FileInputStream( file );
		long fileLength = file.length();
		byte[] bytes = new byte[ (int)fileLength ];
		
		int offset = 0;
		int numRead = 0;
		while( offset < bytes.length && ( numRead = s.read( bytes, offset, bytes.length-offset ) ) >= 0 ) {
			offset += numRead;
		}
		s.close();
		
		Hashtable<String,Character> reverse = new Hashtable<String,Character>();
		for( Character c : hash.keySet() ) {
			reverse.put( hash.get( c ), c );
		}
		
		StringBuffer decoded = new StringBuffer("");
		int pos = 0;
		while( pos < bytes.length ) {
			StringBuffer key = new StringBuffer("");
			while( !reverse.containsKey( key.toString() ) ) {
				key.append( (char)bytes[pos] );
				pos++;
			}
			decoded.append( reverse.get( key.toString() ) );
		}
		return decoded.toString();
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
		System.out.println( "<" + n.label + ":" + n.freq + " ("+n.code+")>" );
		if ( n.left != null ) {
			printHelper( n.left, indent + INDENT );
		}
	}
}
