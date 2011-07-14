/**
 * Represents a node in a tree. Holds any kind of object whose class
 * implements the Comparable interface.
 * 
 * @author ryan.gantt
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class TreeNode<T extends Comparable> {
	private T key;
	private TreeNode<T> parent = null;
	private TreeNode<T> left = null;
	private TreeNode<T> right = null;
	
	TreeNode( T key ) {
		this.key = key;
	}
	
	public TreeNode<T> getLeft() {
		return left;
	}
	
	public void setLeft( TreeNode<T> left ) {
		this.left = left;
	}
	
	public TreeNode<T> getRight() {
		return right;
	}
	
	public void setRight( TreeNode<T> right ) {
		this.right = right;
	}
	
	public TreeNode<T> getParent() {
		return parent;
	}
	
	public void setParent( TreeNode<T> parent ) {
		this.parent = parent;
	}
	
	public T getKey() {
		return key;
	}
}
