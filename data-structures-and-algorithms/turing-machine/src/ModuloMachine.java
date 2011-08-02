
public class ModuloMachine extends Turing {
	private int[] alphabet = { -1, 0, 1 };
	private int nullChar = -1;
	
	public ModuloMachine( String tape ) {
		super( tape );
	}
	
	/**
	 * Move from left to right, overwriting each entry with
	 * the current difference between the number of odd and even "on"
	 * bits. When out of input, check whether the current
	 * difference is divisible by 3. If so, accept. If not, reject.
	 */
	public State delta( State q, int i ) {
		int c = Character.getNumericValue( this.t.charAt( i ) );
		System.out.println("delta on " + c + ", i = " + i + " - " + q );
		if( c == this.nullChar && q == State.START ) {
			return delta( q, i+1 );
		}
		if( q == State.START ) {
			return delta( State.DIFF_0, i );
		}
		// Not "strictly" Turing... will refactor later since this whole thing blows anyway
		if( i == this.t.length() - 2 ) {
			System.out.println("Last character in string!");
			if( ( i + 1 ) % 2 == 0 ) {
				if( q == State.DIFF_0 ) {
					if( c == 0 ) {
						return State.ACCEPT;
					}
					return State.REJECT;
				} else if( q == State.DIFF_PLUS_2 ) {
					if( c == 1 ) {
						return State.ACCEPT;
					}
					return State.REJECT;
				} else if( q == State.DIFF_PLUS_1 ) {
					return State.REJECT;
				} else if( q == State.DIFF_MINUS_1 ) {
					if( c == 1 ) {
						return State.ACCEPT;
					}
					return State.REJECT;
				} else if( q == State.DIFF_MINUS_2 ) {
					return State.REJECT;
				} else {
					return State.ERROR;
				}
			} else {
				if( q == State.DIFF_0 ) {
					if( c == 0 ) {
						return State.ACCEPT;
					}
					return State.REJECT;
				} else if( q == State.DIFF_PLUS_2 ) {
					System.out.println("+2!");
					if( c == 1 ) {
						return State.ACCEPT;
					}
					return State.REJECT;
				} else if( q == State.DIFF_PLUS_1 ) {
					if( c == 1 ) {
						return State.ACCEPT;
					}
					return State.REJECT;
				} else if( q == State.DIFF_MINUS_1 ) {
					return State.REJECT;
				} else if( q == State.DIFF_MINUS_2 ) {
					return State.REJECT;
				} else {
					return State.ERROR;
				}
			}
		}
		
		if( ( i + 1 ) % 2 == 0 ) {
			if( q == State.DIFF_0 ) {
				if( c  == 1 ) {
					return delta( State.DIFF_PLUS_1, i+1 );
				} else if( c == 0 ) {
					return delta( State.DIFF_0, i+1 );
				} else {
					return State.ERROR;
				}
			} else if( q == State.DIFF_PLUS_2 ) {
				if( c  == 1 ) {
					return delta( State.DIFF_0, i+1 );
				} else if( c == 0 ) {
					return delta( State.DIFF_PLUS_2, i+1 );
				} else {
					return State.ERROR;
				}
			} else if( q == State.DIFF_PLUS_1 ) {
				if( c  == 1 ) {
					return delta( State.DIFF_PLUS_2, i+1 );
				} else if( c == 0 ) {
					return delta( State.DIFF_PLUS_1, i+1 );
				} else {
					return State.ERROR;
				}
			} else if( q == State.DIFF_MINUS_1 ) {
				if( c  == 1 ) {
					return delta( State.DIFF_0, i+1 );
				} else if( c == 0 ) {
					return delta( State.DIFF_MINUS_1, i+1 );
				} else {
					return State.ERROR;
				}
			} else if( q == State.DIFF_MINUS_2 ) {
				if( c  == 1 ) {
					return delta( State.DIFF_0, i+1 );
				} else if( c == 0 ) {
					return delta( State.DIFF_MINUS_2, i+1 );
				} else {
					return State.ERROR;
				}
			} else {
				return State.ERROR;
			}
		} else {
			if( q == State.DIFF_0 ) {
				if( c  == 1 ) {
					return delta( State.DIFF_MINUS_1, i+1 );
				} else if( c == 0 ) {
					return delta( State.DIFF_0, i+1 );
				} else {
					return State.ERROR;
				}
			} else if( q == State.DIFF_PLUS_2 ) {
				if( c  == 1 ) {
					return delta( State.DIFF_PLUS_1, i+1 );
				} else if( c == 0 ) {
					return delta( State.DIFF_PLUS_2, i+1 );
				} else {
					return State.ERROR;
				}
			} else if( q == State.DIFF_PLUS_1 ) {
				if( c  == 1 ) {
					return delta( State.DIFF_0, i+1 );
				} else if( c == 0 ) {
					return delta( State.DIFF_PLUS_1, i+1 );
				} else {
					return State.ERROR;
				}
			} else if( q == State.DIFF_MINUS_1 ) {
				if( c  == 1 ) {
					return delta( State.DIFF_MINUS_2, i+1 );
				} else if( c == 0 ) {
					return delta( State.DIFF_MINUS_1, i+1 );
				} else {
					return State.ERROR;
				}
			} else if( q == State.DIFF_MINUS_2 ) {
				if( c  == 1 ) {
					return delta( State.DIFF_MINUS_1, i+1 );
				} else if( c == 0 ) {
					return delta( State.DIFF_MINUS_2, i+1 );
				} else {
					return State.ERROR;
				}
			} else {
				return State.ERROR;
			}
		}
	}
	
	protected boolean inAlphabet( int i ) {
		int c = Character.getNumericValue( this.t.charAt( i ) );
		for( int a : this.alphabet ) {
			if( c == a ) {
				return true;
			}
		}
		System.out.println("character " + c + " not in alphabet");
		return false;
	}
}
