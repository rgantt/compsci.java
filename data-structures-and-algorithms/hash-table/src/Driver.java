import java.io.*;
import java.util.*;

public class Driver {
	public static void main( String[] args ) {		
		HashTable ht = readFromFile("fixtures/hunterexample.txt");
		
		ht.insert( 1, "Lloyd" );
		
		System.out.println( ht.toString() );
	}
	
	public static HashTable readFromFile( String filename ) {
		HashTable ht = new HashTable();
		try {
			Scanner scanner = new Scanner( new File( filename ) );
			while( scanner.hasNextLine() ) {
				ht.insert( scanner.nextInt(), scanner.nextLine() );
			}
		} catch( IOException e ) {
			System.out.println( e.getMessage() );
		}
		return ht;
	}
}
