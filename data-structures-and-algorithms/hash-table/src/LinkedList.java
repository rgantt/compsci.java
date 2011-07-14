
public class LinkedList {
	protected Node<String> head = null;
	protected int length = 0;
	
	private class Node<T> {
		public Node<T> next = null;
		public T data;
		
		Node( T data ) {
			this.data = data;
		}
	}
	
	public String head() {
		return head.data;
	}
	
	public int length() {
		return length;
	}
	
	public void add( String data ) {
		Node<String> node = new Node<String>( data );
		if( this.head == null ) {
			this.head = node;
		} else {
			Node<String> current = head;
			while( current.next != null ) {
				current = current.next;
			}
			current.next = node;
		}
		this.length++;
	}
	
	public String index( int index ) throws ArrayIndexOutOfBoundsException {
		if( index > -1 && index < this.length ) {
			Node<String> current = this.head;
			int i = 0;
			while( i++ < index ) {
				current = current.next;
			}
			return current.data;
		} else {
			throw new ArrayIndexOutOfBoundsException("List index out of bounds");
		}
	}
	
	public String remove( int index ) throws ArrayIndexOutOfBoundsException {
		if( index > -1 && index < this.length ) {
			Node<String> current = this.head;
			int i = 0;
			if( index == 0 ) {
				this.head = current.next;
			} else {
				Node<String> previous = null;
				while( i++ < index ) {
					previous = current;
					current = current.next;
				}
				previous.next = current.next;
			}
			this.length--;
			return current.data;
		} else {
			throw new ArrayIndexOutOfBoundsException("List index out of bounds");
		}
	}
}
