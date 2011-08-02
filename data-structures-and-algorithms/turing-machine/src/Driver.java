public class Driver {
	public static void main( String[] args ) {
		Turing sm = new StringMachine("xxxyyy");
		Turing.State stringState = sm.execute();
		
		if( stringState == Turing.State.ACCEPT ) {
			System.out.println("The input was accepted!");
		} else if( stringState == Turing.State.REJECT ) {
			System.out.println("The input was rejected");
		} else if( stringState == Turing.State.ERROR ) {
			System.out.println("The machine halted without acceptance or rejection");
		}
		
		String[] tests = {
				"0000", "11", "110", "1001", "1100", "1111", 
				"10010", "10101", "11000", "11011", "11110", 
				"100001", "100100", "100111", "101010"
		};
		Turing mod;
		
		for( String test : tests ) {
			mod = new ModuloMachine( test );
			System.out.println( mod.execute() );
		}
	}
}
