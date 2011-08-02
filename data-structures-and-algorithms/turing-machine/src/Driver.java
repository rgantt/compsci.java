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
		
		Turing mod = new ModuloMachine("101010000");
		Turing.State modState = mod.execute();
		
		if( modState == Turing.State.ACCEPT ) {
			System.out.println("The input was accepted!");
		} else if( modState == Turing.State.REJECT ) {
			System.out.println("The input was rejected");
		} else if( modState == Turing.State.ERROR ) {
			System.out.println("The machine halted without acceptance or rejection");
		}
	}
}
