public class StringMachine extends Turing {
	public State delta( State q, int i ) {
		if( i >= t.length() ) {
			System.out.println("index " + i + " not in string of length " + t.length() + " in state " + q );
			return State.REJECT;
		}
		char c = t.charAt( i );
		System.out.println("index " + i + ", char " + c + ", state " + q );
		if( !this.inAlphabet( c ) ) {
			System.out.println("character " + c + " not in alphabet");
			return State.REJECT;
		}
		if( q == State.START ) {
			if( c == 'x' ) {
				this.t = this.replaceAt( t, i, this.nullChar );
				return delta( State.SEARCH_Y, i+1 );
			} else {
				return State.REJECT;
			}
		} else if( q == State.SEARCH_Y ) {
			if( c == 'y' ) {
				this.t = this.replaceAt( t, i, this.nullChar );
				return delta( State.SEARCH_X, 0 );
			} else if( ( ( c == 'x' ) || c == this.nullChar ) && ( i != t.length() - 1 ) ) {
				return delta( State.SEARCH_Y, i+1 );
			} else {
				return delta( State.NO_Y_LEFT, i-1 );
			}
		} else if( q == State.SEARCH_X ) {
			if( c == 'x' ) {
				this.t = this.replaceAt( t, i, this.nullChar );
				return delta( State.SEARCH_Y, i+1 );
			} else if( c == this.nullChar && ( i != t.length() - 1 ) ) {
				return delta( State.SEARCH_X, i+1 );
			} else {
				return delta( State.NO_X_LEFT, 0 );
			}
		} else if( q == State.NO_Y_LEFT ) {
			if( c == this.nullChar && ( i != 0 ) ) {
				return delta( State.NO_Y_LEFT, i-1 );
			} else if( c == 'x' ) {
				return State.REJECT;
			} else if( i == 0 ) {
				return State.ACCEPT;
			} else {
				return State.HALT;
			}
		} else if( q == State.NO_X_LEFT ) {
			if( c == this.nullChar && ( i != t.length() - 1 ) ) {
				return delta( State.NO_X_LEFT, i+1 );
			} else if( c == 'y' ) {
				return State.REJECT;
			} else if( i == t.length() - 1 ) {
				return State.ACCEPT;
			} else {
				return State.HALT;
			}
		} else {
			return State.HALT;
		}
	}
	
	public String replaceAt( String t, int i, char r ) {
		if( i == t.length() - 1 ) {
			return t.substring( 0, i ) + r; 
		} else if( i == 0 ) {
			return r + t.substring( 1, t.length() );
		} else {
			return t.substring( 0, i ) + r + t.substring( i+1, t.length() );
		}
	}
	
	public boolean inAlphabet( char c ) {
		return ( c == 'x' || c == 'y' || c == this.nullChar );
	}
}
