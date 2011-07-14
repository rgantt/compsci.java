/**
 * A binary search tree is an acyclic graph that satisfies the binary-search-tree
 * property:
 * 
 * 	Let x be a node in a binary search tree. If y is a node in the left subtree
 * 	of x, then y.key <= x.key. If y is a node in the right subtree of x, then
 * 	y.key >= x.key. (Cormen et al 2009)
 * 
 * This implementation of the BST data structure holds any data type which implements
 * the Comparable interface; however, a particular instance only holds objects
 * of a _certain_ Comparable type (parameter T).
 * 
 * @author ryan.gantt
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class BinarySearchTree<T extends Comparable> {
	private TreeNode<T> root = null;
	
	/**
	 * Returns the root node. Useful for starting a tree walk from the root, if
	 * a reference is needed.
	 * 
	 * @return TreeNode<T> The root node of the BST
	 */
	public TreeNode<T> getRoot() {
		return root;
	}
	
	/**
	 * Traverse downward through the tree, keeping each node as a tentative parent
	 * for the node we'll be inserting.
	 * 
	 * Once we find a leaf (or appropriate node with only one child), compare it 
	 * with the new node and insert it to the left if smaller, to the right 
	 * if bigger. Assign the previous leaf as the new node's parent.
	 * 
	 * Suppressing compile-time warning that happens when we must cast the return
	 * from getKey() into the Comparable data type contained in our tree.
	 */
	@SuppressWarnings("unchecked")
	public void add( TreeNode<T> z ) {
		TreeNode<T> x = root;
		TreeNode<T> y = null;
		while( x != null ) {
			y = x;
			if( z.getKey().compareTo( x.getKey() ) < 0 ) {
				x = x.getLeft();
			} else {
				x = x.getRight();
			}
		}
		z.setParent( y );
		if( y == null ) {
			root = z;
		} else if ( z.getKey().compareTo( y.getKey() ) < 0 ) {
			y.setLeft( z );
		} else {
			y.setRight( z );
		}
	}
	
	/**
	 * Recursive method that returns the node if found, otherwise:
	 * 1. Searches the left subtree of x if k is less than x.key
	 * 2. Searches the right subtree of x if k is greater than x.key
	 * 
	 * @param x The parent of the subtree to search
	 * @param k The comparable value to find
	 * @return TreeNode<T> The item, if found, null otherwise
	 */
	@SuppressWarnings("unchecked")
	public TreeNode<T> search( TreeNode<T> x, Comparable<T> k ) {
		if( x == null || x.getKey().compareTo( k ) == 0 ) {
			return x;
		}
		if( k.compareTo( x.getKey() ) < 0 ) {
			return search( x.getLeft(), k );
		} else {
			return search( x.getRight(), k );
		}
	}
	
	/**
	 * Remove a node from the BST. The deletion is simple if we are removing
	 * a leaf or a node with only one child.
	 * 
	 * In the case of two children of z, we have to do a bit more work: detach 
	 * z's smallest right-hand node, attach the rest of z's right subtree to it,
	 * then replace z with this newly-assembled subtree
	 * 
	 * @param z The TreeNode<T> to remove from the BST
	 */
	public void delete( TreeNode<T> z ) {
		if( z.getLeft() == null ) {
			// if z only has a right child (or no children), bring the child up
			// and remove reference to z
			transplant( z, z.getRight() );
		} else if( z.getRight() == null ) {
			// if z only has a left child, bring the child up
			// and remove reference to z
			transplant( z, z.getLeft() );
		} else {
			// if z has both left and right children
			// find minimum value to the right of z
			TreeNode<T> y = minimum( z.getRight() );
			// 
			if( y.getParent() != z ) {
				// remove y temporarily, replaced with its right subtree
				transplant( y, y.getRight() );
				// attach z's right subtree to y as its new right subtree
				y.setRight( z.getRight() );
				// hard-attach y as parent to its new right subtree
				y.getRight().setParent( y );
			}
			// move minimum right-value up to take z's place
			transplant( z, y ); // 
			// add z's old child as y's new child
			y.setLeft( z.getLeft() );
			// hard-replace y's parent with z's old parent
			y.getLeft().setParent( y );
		}
	}
	
	/**
	 * Replaces TreeNode<T> u for TreeNode<T> v in the tree, ensuring
	 * that v inherits u's parent
	 * 
	 * @param u TreeNode<T> to replace
	 * @param v TreeNode<T> to use in u's place
	 */
	private void transplant( TreeNode<T> u, TreeNode<T> v ) {
		if( u.getParent() == null ) {
			// if u is the root node
			root = v;
		} else if( u == u.getParent().getLeft() ) {
			// if u is its parent's left node
			u.getParent().setLeft( v );
		} else {
			// if u is its parent's right node
			u.getParent().setRight( v );
		}
		// make sure that v only has one parent
		if( v != null ) {
			v.setParent( u.getParent() );
		}
	}
	
	/**
	 * Recurses down the left branch of a subtree until it reaches a dead end
	 * and then returns (guaranteed to give the minimum element of the tree
	 * by the binary-search-property).
	 * 
	 * @param x Node with which to start
	 * @return TreeNode<T> The leaf with the smallest key value
	 */
	private TreeNode<T> minimum( TreeNode<T> x ) {
		if( x.getLeft() == null ) {
			return x;
		} else {
			return minimum( x.getLeft() );
		}
	}
	
	public String walkInOrder( TreeNode<T> x ) {
		StringBuffer sb = new StringBuffer();
		if( x != null ) {
			sb.append( walkInOrder( x.getLeft() ) );
			sb.append( x.getKey() + ", " );
			sb.append( walkInOrder( x.getRight() ) );
		}
		return sb.toString();
	}
	
	public String walkPreOrder( TreeNode<T> x ) {
		StringBuffer sb = new StringBuffer();
		if( x != null ) {
			sb.append( x.getKey() + ", " );
			sb.append( walkInOrder( x.getLeft() ) );
			sb.append( walkInOrder( x.getRight() ) );
		}
		return sb.toString();
	}
	
	public String walkPostOrder( TreeNode<T> x ) {
		StringBuffer sb = new StringBuffer();
		if( x != null ) {
			sb.append( walkInOrder( x.getLeft() ) );
			sb.append( walkInOrder( x.getRight() ) );
			sb.append( x.getKey() + ", " );
		}
		return sb.toString();
	}
}
