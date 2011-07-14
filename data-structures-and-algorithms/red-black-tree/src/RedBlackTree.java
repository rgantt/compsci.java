public class RedBlackTree {
	private boolean fixup;
	private boolean addReturn;
	private Node root;

	RedBlackTree() {
		root = null;
		fixup = false;
	}
	
	public Node getRoot() {
		return root;
	}

	public boolean add( int item ) {
		if ( root == null ) {
			root = new Node( item );
			root.isRed = false; // root is black.
			return true;
		} else {
			root = add( root, item );
			root.isRed = false; // root is always black.
			return addReturn;
		}
	}

	private Node add(Node localRoot, int item) {
		if (localRoot.getItem() == item) {
			// item already in the tree.
			addReturn = false;
			return localRoot;
		}

		else if (item < localRoot.getItem()) {
			// item < localRoot.data.
			if (localRoot.getLeft() == null) {
				// Create new left child.
				Node child = new Node( item );
				child.setParent( localRoot );
				localRoot.setLeft( child );
				addReturn = true;
				return localRoot;
			}

			else { // Need to search.
				// Check for two red children, swap colors if found.
				moveBlackDown(localRoot);
				// Recursively add on the left.
				localRoot.setLeft(add(localRoot.getLeft(), item));

				// See whether the left child is now red
				if (localRoot.getLeft().isRed) {

					if (localRoot.getLeft().getLeft() != null
							&& ( localRoot.getLeft().getLeft()).isRed) {
						// Left-left grandchild is also red.

						// Single rotation is necessary.
						localRoot.getLeft().isRed = false;
						localRoot.isRed = true;
						return rotateRight(localRoot);
					}
					else if (localRoot.getLeft().getRight() != null
							&& (localRoot.getLeft().getRight()).isRed) {
						// Left-right grandchild is also red.
						// Double rotation is necessary.
						localRoot.setLeft(rotateLeft(localRoot.getLeft()));
						localRoot.getLeft().isRed = false;
						localRoot.isRed = true;
						return rotateRight(localRoot);
					}
				}
				return localRoot;
			}
		}

		else {
			// item > localRoot.data
			if (localRoot.getRight() == null) {
				// create new right child
				Node child = new Node( item );
				child.setParent( localRoot );
				localRoot.setRight( child );
				addReturn = true;
				return localRoot;
			}
			else { // need to search
				// check for two red children swap colors
				moveBlackDown(localRoot);
				// recursively insert on the right
				localRoot.setRight(add(localRoot.getRight(), item));
				// see if the right child is now red
				if (localRoot.getRight().isRed) {
					if (localRoot.getRight().getRight() != null
							&& ( localRoot.getRight().getRight()).isRed) {
						// right-right grandchild is also red
						// single rotate is necessary
						localRoot.getRight().isRed = false;
						localRoot.isRed = true;
						return rotateLeft(localRoot);
					}
					else if (localRoot.getRight().getLeft() != null
							&& (localRoot.getRight().getLeft().isRed)) {
						// left-right grandchild is also red
						// double rotate is necessary
						localRoot.setRight(rotateRight(localRoot.getRight()));
						localRoot.getRight().isRed = false;
						localRoot.isRed = true;
						return rotateLeft(localRoot);
					}
				}
				return localRoot;
			}

		}
	}
	  
	private void moveBlackDown( Node localRoot ) {
		// see if both children are red
		if (localRoot.getLeft() != null && localRoot.getRight() != null && localRoot.getLeft().isRed && localRoot.getRight().isRed ) {
			//make them black and myself red
			localRoot.getLeft().isRed = false;
			localRoot.getRight().isRed = false;
			localRoot.isRed = true;
		}
	}

	private Node rotateRight( Node root ) {
		System.out.println("Rotating Right");
		Node temp = root.getLeft();
		transplant( root, temp );
		root.setLeft( temp.getRight() );
		if( temp.getRight() != null )
			temp.getRight().setParent( root );
		temp.setRight( root );
		root.setParent( temp );
		return temp;
	}
	
	private Node rotateLeft( Node localRoot ) {
		System.out.println("Rotating Left");
		Node temp = localRoot.getRight();
		transplant( localRoot, temp );
		localRoot.setRight( temp.getLeft() );
		if( temp.getLeft() != null )
			temp.getLeft().setParent( localRoot );
		temp.setLeft( localRoot );
		localRoot.setParent( temp );
		return temp;
	}
	
	/**
	 * Recursive method that returns the node if found, otherwise:
	 * 1. Searches the left subtree of x if k is less than x.key
	 * 2. Searches the right subtree of x if k is greater than x.key
	 * 
	 * @param x The parent of the subtree to search
	 * @param k The int value to find
	 * @return Node The item, if found, null otherwise
	 */
	public Node search( Node x, int k ) {
		if( x == null || x.getItem() == k ) {
			return x;
		}
		if( x.getItem() < k ) {
			return search( x.getRight(), k );
		} else {
			return search( x.getLeft(), k );
		}
	}
	
	public boolean delete( int item ) {
		Node z = search( root, item );
		if( z == null ) {
			return false;
		} else {
			delete( z );
			return true;
		}
	}
	
	public void delete( Node n ) {
	    if ( n.getLeft() != null && n.getRight() != null ) {
	        // hard move-up the predecessor and delete it instead
	        Node pre = maximum( n.getLeft() );
	        // have to hard-copy the value, since we're assigning by reference
	        n.setItem( pre.getItem() );
	        n = pre;
	    }
	    Node child = ( n.getRight() == null ) ? n.getLeft() : n.getRight();
	    if ( !n.isRed ) {
	        n.isRed = ( child != null && child.isRed ? true : false );
	    	deleteCase1( n );
	    }
	    transplant( n, child );
	    if( root != null )
	    	root.isRed = false;
	}
	
	// n is the root, no need to continue
	private void deleteCase1( Node n ) {
	    if ( n.getParent() == null )
	        return;
	    else
	        deleteCase2( n );
	}
	
	// case where n has a red sibling; swap colors and rotate about n's parent
	private void deleteCase2( Node n ) {
		Node sibling;
		if( n == n.getParent().getLeft() ) {
			sibling = n.getParent().getRight();
		} else {
			sibling = n.getParent().getLeft();
		}
	    if ( sibling != null && sibling.isRed ) {
	        n.getParent().isRed = true;
	        sibling.isRed = false;
	        if ( n == n.getParent().getLeft() )
	            rotateLeft( n.getParent() );
	        else
	            rotateRight( n.getParent() );
	    }
	    deleteCase3( n );
	}
	
	// n's parent, siblings, nephews all black; paint sibling red
	private void deleteCase3( Node n ) {
		Node sibling;
		if( n == n.getParent().getLeft() ) {
			sibling = n.getParent().getRight();
		} else {
			sibling = n.getParent().getLeft();
		}
	    if ( !n.getParent().isRed 
	    		&& ( sibling == null || !sibling.isRed ) 
	    		&& ( sibling == null || ( sibling.getLeft() != null && !sibling.getLeft().isRed ) ) 
	    		&& ( sibling == null || ( sibling.getRight() != null && !sibling.getRight().isRed ) ) ) {
	        //sibling.isRed = true;
	    	n.isRed = true;
	        // case simplified, move some nodes
	        deleteCase1( n.getParent() );
	    }
	    else
	        deleteCase4( n );
	}
	
	// siblings/nephews black, parent red. swap sibling/parent's colors
	private void deleteCase4( Node n ) {
		Node sibling;
		if( n == n.getParent().getLeft() ) {
			sibling = n.getParent().getRight();
		} else {
			sibling = n.getParent().getLeft();
		}
		if( n.getParent().isRed 
				&& ( sibling == null || !sibling.isRed ) 
				&& ( sibling.getLeft() == null || !sibling.getLeft().isRed ) 
				&& ( sibling.getRight() == null || !sibling.getRight().isRed ) ) {
	        sibling.isRed = true;
	        n.getParent().isRed = false;
	    }
	    else
	        deleteCase5( n );
	}
	
	// sibling black, one nephew red, one nephew black; exchange colors of sibling
	// and nephew, rotate in same direction of exchange about sibling (and mirror)
	private void deleteCase5( Node n ) {
		Node sibling;
		if( n == n.getParent().getLeft() ) {
			sibling = n.getParent().getRight();
		} else {
			sibling = n.getParent().getLeft();
		}
	    if ( ( n == n.getParent().getLeft() ) 
	    		&& ( sibling != null && !sibling.isRed ) 
	    		&& ( sibling.getLeft() != null  && sibling.getLeft().isRed ) 
	    		&& ( sibling.getRight() != null && !sibling.getRight().isRed ) ) { 
	        sibling.isRed = true;
	        sibling.getLeft().isRed = false;
	        rotateRight( sibling );
	    } else if ( ( n == n.getParent().getRight() ) 
	    		&& ( sibling != null && !sibling.isRed ) 
	    		&& ( sibling.getRight() != null && sibling.getRight().isRed ) 
	    		&& ( sibling.getLeft() != null && !sibling.getLeft().isRed ) ) {
	        sibling.isRed = true;
	        sibling.getRight().isRed = false;
	        rotateLeft( sibling );
	    }
	    deleteCase6( n );
	}
	
	// sibling is black, nephew is red, n left child. exchange colors of parents and
	// sibling, make nephew black, rotate left about parent (and mirror)
	private void deleteCase6( Node n ) {
		Node sibling;
		if( n == n.getParent().getLeft() ) {
			sibling = n.getParent().getRight();
		} else {
			sibling = n.getParent().getLeft();
		}
		if( sibling != null )
			sibling.isRed = ( n.getParent().isRed ? true : false );
		n.getParent().isRed = false;
	    if ( n == n.getParent().getLeft() ) {
	    	if( sibling != null && sibling.getRight() != null )
	    		sibling.getRight().isRed = false;
	        rotateLeft( n.getParent() );
	    } else {
	    	if( sibling != null && sibling.getLeft() != null )
	    		sibling.getLeft().isRed = false;
	        rotateRight( n.getParent() );
	    }
	}
	
	public void deleteFixup( Node x ) {
		while( x != root && !x.isRed && x.getParent() != null && x.getParent() != null ) {
			if( x == x.getParent().getLeft() ) {
				fixupLeft( x );
			} else {
				fixupRight( x );
			}
			x = x.getParent();
		}
		x.isRed = false;
	}
	
	/**
	 * Replaces Node u with Node v in the tree, ensuring
	 * that v inherits u's parent
	 * 
	 * @param u Node to replace
	 * @param v Node to use in u's place
	 */
	private void transplant( Node u, Node v ) {
		// check whether u.p is the sentinel, implying u is the root
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
	 * @return Node The leaf with the smallest key value
	 */
	private Node minimum( Node x ) {
		if( x.getLeft() == null ) {
			return x;
		}
		return minimum( x.getLeft() );
	}
	
	private Node maximum( Node x ) {
		if( x.getRight() == null ) {
			return x;
		}
		return maximum( x.getRight() );
	}
	
	private Node fixupRight( Node localroot ) {
		if( localroot == null ) return localroot;
		if( localroot.isRed ) {
			localroot.isRed = false;
			if( localroot.getLeft().getRight() != null && localroot.getLeft().getRight().isRed ) {
				localroot.setLeft( rotateLeft( localroot.getLeft() ) );
				fixup = false;
				return rotateRight( localroot );
			} else if( localroot.getLeft().getLeft() != null && localroot.getLeft().getLeft().isRed ) {
				localroot.getLeft().getLeft().isRed = false;
				fixup = false;
				return rotateRight( localroot );
			} else {
				// leftchild is a leaf, or has two black children
				localroot.getLeft().isRed = true;
				fixup = false;
				return localroot;
			}
		} else {
			// local root is black
			if(	localroot.getLeft().isRed ) {
				localroot.isRed = true;
				localroot.getLeft().isRed = false;
				localroot.setLeft( rotateLeft( localroot.getLeft() ) );
				Node temp = rotateRight( localroot );
				return rotateRight( temp );
			} else {
				localroot.getLeft().isRed = true;
				return localroot;
			}
		}
	}
	
	private Node fixupLeft( Node localroot ) {
		if( localroot == null ) return localroot;
		if( localroot.isRed ) {
			localroot.isRed = false;
			if( localroot.getRight().getLeft() != null && localroot.getRight().getLeft().isRed ) {
				localroot.setRight( rotateRight( localroot.getRight() ) );
				fixup = false;
				return rotateLeft( localroot );
			} else if( localroot.getRight().getRight() != null && localroot.getRight().getRight().isRed ) {
				localroot.getRight().getRight().isRed = false;
				fixup = false;
				return rotateLeft( localroot );
			} else {
				// leftchild is a leaf, or has two black children
				localroot.getRight().isRed = true;
				fixup = false;
				return localroot;
			}
		} else {
			if( localroot.getRight().isRed ) {
				localroot.isRed = true;
				localroot.getRight().isRed = false;
				localroot.setRight( rotateRight( localroot.getRight() ) );
				Node temp = rotateLeft( localroot );
				return rotateLeft( temp );
			} else {
				localroot.getRight().isRed = true;
				return localroot;
			}
		} 
	}
	
	public String walkInOrder( Node x ) {
		StringBuffer sb = new StringBuffer("");
		if( x != null && x != null ) {
			sb.append( walkInOrder( x.getLeft() ) );
			sb.append( printNode( x ) );
			sb.append( walkInOrder( x.getRight() ) );
		}
		return sb.toString();
	}
	
	public String printNode( Node x ) {
		StringBuffer sb = new StringBuffer();
		String color;
		if( x.isRed ) {
			color = "red";
		} else {
			color = "black";
		}
		sb.append( x.getItem() + " ( " + x + " <-- [" + x.getParent() + "], " + x.getLeft() + ", " + x.getRight() + ") [" + color + "]\n" );
		return sb.toString();
	}
}