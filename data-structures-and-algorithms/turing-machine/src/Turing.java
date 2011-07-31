
abstract public class Turing {
	public enum State {
		START, SEARCH_Y, SEARCH_X, NO_X_LEFT, NO_Y_LEFT, ACCEPT, REJECT, HALT
	};
	protected final char nullChar = '#';
	protected State q;
	protected String t;
	
	public Turing() {
		this.q = State.START;
	}
	
	public State execute( String t ) {
		this.t = t;
		while( !this.finished() ) {
			this.q = this.delta( this.q, 0 );
		}
		return this.q;
	}

	public boolean finished() {
		return ( this.q == State.ACCEPT || this.q == State.REJECT || this.q == State.HALT );
	}
	
	abstract protected State delta( State q, int i );
}
