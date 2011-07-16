import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Matrix {
	public int matrix[][];
	
	public static void print( ArrayList<ArrayList<Integer>> matrix ) {
		for( ArrayList<Integer> rows : matrix ) {
			for( int cell : rows ) {
				System.out.print( cell + " " );
			}
			System.out.println("");
		}
	}
	
	public static void print( int[][] matrix ) {
		for( int i = 0; i < matrix.length; i++ ) {
			for( int j = 0; j < matrix[ i ].length; j++ ) {
				System.out.print( matrix[ i ][ j ] + " " );
			}
			System.out.println("");
		}
	}
	
	public static void print( MatrixGraph g ) {
		print( g.matrix );
	}
	
	public static ArrayList<ArrayList<Integer>> input( String filename ) {
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();
		try {
			Scanner scanner = new Scanner( new File( filename ) );
			while( scanner.hasNextLine() ) {
				ArrayList<Integer> line = new ArrayList<Integer>();
				for( char c : ((String)scanner.nextLine()).toCharArray() ) {
					int v;
					if( c == 'x' ) {
						v = 0;
					} else if( c == 'n' ) {
						// would be nice to have something not in domain
						v = 999999;
					} else {
						v = Integer.parseInt( String.valueOf( c ) );
					}
					line.add( v );
				}
				matrix.add( line );
			}
		} catch( IOException e ) {
			System.out.println( e.getMessage() );
		}
		return matrix;
	}
	
	public static int[][] input( String file, int size ) {
		String infilename = file;
		int[][] matrix = new int[size][size];
		try	{
			FileReader infile = new FileReader(infilename);
			Scanner in = new Scanner(infile);
			 
			while( in.hasNextInt() ) {
				for( int i = 0; i < size; i++ ) {
					for( int j = 0; j < size; j++ ) {
						matrix[i][j] = in.nextInt();
					}
				}
			}
		 } catch(FileNotFoundException exception) {
			 System.out.println("cannot find file!");
		 }
		 return matrix;
	}
}