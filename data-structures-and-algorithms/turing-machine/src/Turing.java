
abstract public class Turing {
	/**
	 * Enumerations blow
	 */
	public enum State {
		START, ACCEPT, REJECT, ERROR, HALT,
		SEARCH_Y, SEARCH_X, NO_X_LEFT, NO_Y_LEFT,
		DIFF_0, DIFF_PLUS_1, DIFF_PLUS_2, DIFF_MINUS_1, DIFF_MINUS_2
	};
	
	protected final char nullChar = ';';
	protected State q;
	protected String t;
	
	public Turing( String tape ) {
		this.q = State.START;
		this.t = this.nullChar + tape + this.nullChar;
	}
	
	public State execute() {
		while( !this.finished() ) {
			this.q = this.delta( this.q, 0 );
		}
		return this.q;
	}

	protected boolean finished() {
		return ( this.q == State.ACCEPT || this.q == State.REJECT || this.q == State.ERROR );
	}
	
	protected void replace( int i, char r ) {
		String n = new String("");
		if( i == this.t.length() - 1 ) {
			n = this.t.substring( 0, i ) + r; 
		} else if( i == 0 ) {
			n = r + this.t.substring( 1, this.t.length() );
		} else {
			n = this.t.substring( 0, i ) + r + this.t.substring( i+1, this.t.length() );
		}
		this.t = n;
	}
	
	abstract protected State delta( State q, int i );
	abstract protected boolean inAlphabet( int c );
}
