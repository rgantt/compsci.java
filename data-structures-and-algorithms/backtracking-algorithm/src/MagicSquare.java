public class MagicSquare {
	final public int ROW_SUM = 15;
	private int[] A = {0,0,0,0,0,0,0,0,0};
	private int[] B = {0,0,0,0,0,0,0,0,0,0};
	
	public void nextSquare( int n ) {
		for( int i = 0; i < B.length; i++ ) {
			if( B[i] == 0 ) {
				A[n] = i;
				B[i] = 1;
				if( test( n ) ) {
					if( n == 8 ) {
						printSolution();
					} else {
						nextSquare( n + 1 );
					}
				}
				A[n] = 0;
				B[i] = 0;
			}
		}
	}
	
	private boolean test( int n ) {
		switch( n ) {
			case 2:
				return checkRow( 2 );
			case 5:
				return checkRow( 5 );
			case 6:
				return ( checkColumn( 6 ) && checkNegDiagonal() );
			case 7:
				return checkColumn( 7 );
			case 8:
				return ( checkColumn( 8 ) && checkRow( 8 ) && checkPosDiagonal() );
			default:
				return true;
		}
	}
	
	private boolean checkRow( int n ) {
		return ( A[n-2] + A[n-1] + A[n] == this.ROW_SUM );
	}
	
	private boolean checkColumn( int n ) {
		return ( A[n-6] + A[n-3] + A[n] == this.ROW_SUM );
	}
	
	private boolean checkNegDiagonal() {
		return ( A[2] + A[4] + A[6] == this.ROW_SUM );
	}
	
	private boolean checkPosDiagonal() {
		return ( A[0] + A[4] + A[8] == this.ROW_SUM );
	}
	
	private void printSolution() {
		for( int i = 0; i < A.length; i++ ) {
			System.out.print( A[i] + " " );
			if( ( i + 1 ) % 3 == 0 ) {
				System.out.println("");
			}
		}
		System.out.println("");
	}
	
	public static void main( String[] args ) {
		new MagicSquare().nextSquare(0);
	}
}
