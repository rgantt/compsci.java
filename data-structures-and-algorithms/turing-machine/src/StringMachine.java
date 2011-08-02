public class StringMachine extends Turing {
	private char[] alphabet = { '#', 'x', 'y', this.nullChar };
	
	public StringMachine( String tape ) {
		super( tape );
	}
	
	public State delta( State q, int i ) {
		if( !this.inAlphabet( i ) ) {
			return State.REJECT;
		}
		
		char c = t.charAt( i );
		//System.out.println("index " + i + ", char " + c + ", state " + q );
		if( q == State.START ) {
			if( c == 'x' ) {
				this.replace( i, '#' );
				return delta( State.SEARCH_Y, i+1 );
			} else if ( c == this.nullChar ) {
				return delta( State.SEARCH_X, i+1 );
			} else {
				return State.REJECT;
			}
		} else if( q == State.SEARCH_Y ) {
			if( c == 'y' ) {
				this.replace( i, '#' );
				return delta( State.SEARCH_X, i-1 );
			} else if( c == '#' || c == 'x' ) {
				return delta( State.SEARCH_Y, i+1 );
			} else {
				return delta( State.NO_Y_LEFT, i-1 );
			}
		} else if( q == State.SEARCH_X ) {
			if( c == 'x' ) {
				this.replace( i, '#' );
				return delta( State.SEARCH_Y, i+1 );
			} else if( c == '#' ) {
				return delta( State.SEARCH_X, i-1 );
			} else {
				return delta( State.NO_X_LEFT, i+1 );
			}
		} else if( q == State.NO_Y_LEFT ) {
			if( c == '#' ) {
				return delta( State.NO_Y_LEFT, i-1 );
			} else if( c == 'x' ) {
				return State.REJECT;
			} else if( i == 0 ) {
				return State.ACCEPT;
			} else {
				return State.ERROR;
			}
		} else if( q == State.NO_X_LEFT ) {
			if( c == this.nullChar ) {
				return State.ACCEPT;
			} else if( c == '#' ) {
				return delta( State.NO_X_LEFT, i+1 );
			} else if( c == 'y' || c == 'x' ) {
				return State.REJECT;
			} else {
				return State.ERROR;
			}
		} else {
			return State.ERROR;
		}
	}
	
	public boolean inAlphabet( int index ) {
		char c = this.t.charAt( index );
		for( char a : this.alphabet ) {
			if( a == c ) {
				return true;
			}
		}
		System.out.println("character " + c + " not in alphabet");
		return false;
	}
}
