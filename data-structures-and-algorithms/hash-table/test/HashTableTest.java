import junit.framework.TestCase;

public class HashTableTest extends TestCase {
	HashTable ht;
	
	public HashTableTest( String param ) {
		super( param );
	}
	
	public void setUp() {
		ht = new HashTable();
	}
	
	public void tearDown() {
		ht = null;
	}
	
	public void testSimpleTreeAddition() {
		int[] k = { 8, 5, 12, 3, 2, 1 };
		String[] v = { "Eight", "Five", "Twelve", "Three", "Two", "One" };
		
		for( int i = 0; i < k.length; i++ ) {
			ht.insert( k[i], v[i] );
		}
		
		assertTrue( ht.search(5) instanceof HashTable.Entry );
	}
	
	public static void main( String[] args ) {
		junit.textui.TestRunner.run( HashTableTest.class );
	}
}
