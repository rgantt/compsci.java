import java.util.*;
import java.io.*;

public class Driver {
	public static final int INDENT = 4;
	
	public static void main( String[] args ) {
		Hashtable<Character,Integer> hashTable = new Hashtable<Character,Integer>();
		try {
			ArrayList<Character> inChars = readCharsFromFile("fixtures/input_file.txt");
			StringBuilder input = new StringBuilder("");
			for( Character c : inChars ) {
				input.append(c);
				if( hashTable.containsKey(c) ) {
					Integer val = hashTable.remove(c);
					hashTable.put( c, val+1 );
				} else {
					hashTable.put( c, 1 );
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
			Hashtable<Character,String> codewords = new Hashtable<Character,String>();
			HuffmanNode ht = h.huffmanCodes( hn, codewords, new StringBuilder("") );
			printHelper( ht, 1 );
			System.out.println( encodeFile( "output/encoded.txt", codewords ) );
			System.out.println( decodeFile( "output/encoded.txt", codewords ) );
		} catch( IOException e ) {
			System.out.println( e.getMessage() );
		}
	}
	
	private static String encodeFile( String filename, Hashtable<Character,String> hash ) throws IOException {		
		ArrayList<Character> inChars = readCharsFromFile("fixtures/input_file.txt");
		PrintWriter out = new PrintWriter( new BufferedWriter( new FileWriter( filename ) ) );
		StringBuilder str = new StringBuilder( inChars.size() );
		for( Character b : inChars ) {
			str.append( hash.get( b ) );
			out.write( hash.get( b ) );
		}
		out.close();
		return str.toString();
	}
	
	private static String decodeFile( String filename, Hashtable<Character,String> hash ) throws IOException {
		ArrayList<Character> inChars = readCharsFromFile( filename );
		Hashtable<String,Character> reverse = new Hashtable<String,Character>();
		for( Character c : hash.keySet() ) {
			reverse.put( hash.get( c ), c );
		}
		StringBuilder decoded = new StringBuilder("");
		int pos = 0;
		while( pos < inChars.size() ) {
			StringBuilder key = new StringBuilder("");
			while( !reverse.containsKey( key.toString() ) ) {
				key.append( (char)inChars.get(pos) );
				pos++;
			}
			decoded.append( reverse.get( key.toString() ) );
		}
		return decoded.toString();
	}
	
	private static ArrayList<Character> readCharsFromFile( String filename ) throws IOException {
		FileInputStream s = new FileInputStream( new File( filename ) );
		ArrayList<Character> chars = new ArrayList<Character>();
		while( s.available() > 0 ) {
			chars.add( (char)s.read() );
		}
		s.close();
		return chars;
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
