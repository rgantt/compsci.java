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
		//only check the square at ns 2, 5, 6, 7, & 8
		if( n == 2 ) {
			if( A[0] + A[1] + A[2] == this.ROW_SUM ) {
				return true;
			} else {
				return false;
			}
		} else if ( n == 5 ) {
			if( A[3] + A[4] + A[5] == this.ROW_SUM ) {
				return true;
			} else {
				return false;
			}
		} else if( n == 6 ) {
			if( A[0] + A[3] + A[6] == this.ROW_SUM ) {
				return true;
			} else {
				return false;
			}
		} else if( n == 7 ) {
			if( A[1] + A[4] + A[7] == this.ROW_SUM ) {
				return true;
			} else {
				return false;
			}
		} else if( n == 8 ) {
			if( A[2] + A[5] + A[8] == this.ROW_SUM && A[0] + A[4] + A[8] == this.ROW_SUM ) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
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
		MagicSquare ms = new MagicSquare();
		ms.nextSquare( 0 );
	}
}
