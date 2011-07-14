public class Node {
	public boolean isRed;
	private int item;
	private Node left = null;
	private Node right = null;
	
	/**
	 * The immediate ascendent of this node in the tree
	 */
	private Node parent = null;

	Node( int a ) {
		item = a;
		isRed = true;
	}
	
	/**
	 * Null constructor
	 */
	Node() {
		isRed = true;
	}

	/**
	 * Getter on parent
	 * @return parent The current parent of the node
	 */
	public Node getParent() {
		return parent;
	}
	
	/**
	 * Setter on parent
	 * @param p The new parent of the node
	 */
	public void setParent( Node p ) {
		this.parent = p;
	}
	
	public int getItem() {
		return item;
	}
	
	public void setItem( int k ) {
		item = k;
	}

	public void setRight( Node i ) {
		right = i;
	}
	public void setLeft( Node i ) {
		left = i;
	}
	public Node getLeft() { 
		return left;
	}
	public Node getRight() {
		return right;
	} 
}