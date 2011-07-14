/**
 * Implementation of the HashTable datastructure.
 * 
 * Uses open addressing to handle hash collisions.
 * 
 * @author rgantt
 */
public class HashTable {
	/**
	 * Class-level constants that are set per lab instructions
	 */
	private final double RESIZE_THRESHOLD = 0.80;
	private final int INITIAL_CAPACITY = 5;
	
	/**
	 * Capacity of the current table
	 */
	private int capacity = INITIAL_CAPACITY;
	
	/**
	 * The number of elements that currently exist in the HashTable
	 */
	private int allocated = 0;
	
	/**
	 * Internal representation of T, the table
	 */
	private Entry[] table = new Entry[ INITIAL_CAPACITY ];
	
	/**
	 * Represents a deleted node
	 */
	private Entry deleted = new Entry();
	
	/**
	 * Internal representation of a key-value pair.
	 * 
	 * When an Entry is added to the HashTable, the key will
	 * be hashed, and the value of the internal table will
	 * point to an instance of the Entry class, which includes
	 * information about both the key (non-hashed) and its value.
	 * 
	 * @author rgantt
	 */
	public class Entry {
		private int key;
		private String value;
		
		// Constructor for initializing a "deleted" value
		Entry() {}
		
		Entry( int key, String value ) {
			this.key = key;
			this.value = value;
		}
		
		public int getKey() {
			return this.key;
		}
		
		public String getData() {
			return this.value;
		}
	}
	
	/**
	 * Allows insertion of an integer key, string value pair without the
	 * use of an instance of the HashTable.Entry class.
	 * 
	 * @param key
	 * @param value
	 */
	public void insert( int key, String value ) {
		this.insert( new Entry( key, value ) );
	}
	
	/**
	 * Insertion with open addressing.
	 * 
	 * If the table index h(k) is null, assign it; otherwise, probe
	 * linearly (+1) using appropriate modulus until an opening is found,
	 * then insert the Entry into it.
	 * 
	 * This method resizes the table if the load factor exceeds the
	 * designated threshold.
	 * 
	 * @param object
	 */
	public void insert( Entry object ) {
		int i = 0, j;
		do {
			j = this.hash( object.getKey(), i );
			if( this.table[ j ] == null || this.table[ j ] == this.deleted ) {
				this.table[ j ] = object;
				this.allocated++;
				break;
			}
			i++;
		} while( i != this.capacity );
		if( this.loadFactor() >= RESIZE_THRESHOLD ) {
			this.resize();
		}
	}
	
	/**
	 * Determines whether an entry with a specific key value exists
	 * in the HashTable.
	 * 
	 * Searches by checking the value at the true hashed-value of key,
	 * then proceeds in a linear probing order, finally giving up at
	 * the first null key in the internal table. 
	 * 
	 * @param key
	 * @return Entry the entry, if it is in the HashTable, null otherwise
	 */
	public Entry search( int key ) {
		int i = 0, j;
		do {
			j = this.hash( key, i );
			if( this.table[ j ].getKey() == key ) {
				return this.table[ j ];
			}
			i++;
		} while( this.table[ j ] != null || i != this.capacity );
		return null;
	}
	
	/**
	 * Remove an element from the HashTable by replacing it with
	 * a null-initialized version of the internal data
	 * representation, Entry.
	 * 
	 * @param key
	 * @return Entry the deleted Entry
	 */
	public Entry delete( int key ) {
		Entry entry = null;
		int i = 0, j;
		do {
			j = this.hash( key, i );
			if( this.table[ j ] != null && this.table[ j ].getKey() == key ) {
				entry = this.table[ j ];
				this.table[ j ] = this.deleted;
				this.allocated--;
				return entry;
			}
			i++;
		} while( this.table[ j ] != null || i != this.capacity );
		return null;
	}
	
	/**
	 * Prints out a string representation of the hash table.
	 * 
	 * @return String A string representation of the hash table
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("HashTable:\n");
		for( int i = 0; i < this.table.length; i++ ) {
			sb.append( "index " + i + ": " );
			if( this.table[ i ] != null ) {
				sb.append( this.table[ i ].getKey() + " =>" + this.table[ i ].getData() + "\n" );
			} else { 
				sb.append( "NULL\n" );
			}
		}
		return sb.toString();
	}
	
	/**
	 * Doubles the capacity of the internal table. Ensures
	 * that entries which contain the "deleted" element are
	 * not rehashed into the new table.
	 */
	private void resize() {
		this.capacity *= 2;
		Entry[] oldTable = this.table;
		this.table = new Entry[ this.capacity ];
		this.allocated = 0;
		
		for( int i = 0; i < oldTable.length; i++ ) {
			if( oldTable[ i ] != null && oldTable[ i ] != this.deleted ) {
				this.insert( oldTable[ i ] );
			}
		}
		oldTable = null;
	}
	
	/**
	 * Calculates the ratio of used spots to total spots
	 * 
	 * @return float The load factor of the hash table
	 */
	private float loadFactor() {
		return (float) allocated/capacity;
	}
	
	/**
	 * Computes a simple hash of the integer key which is
	 * equal to the modulus of the key and the size of the
	 * internal table.
	 * 
	 * @param key The key to hash
	 * @param iterations The magnitude of the linear probe index
	 * @return int The modulus of the key with the size of the table, pushed forward by the magnitude of the probe
	 */
	private int hash( int key, int iterations ) {
		return ( ( key + iterations ) % capacity );
	}
}
