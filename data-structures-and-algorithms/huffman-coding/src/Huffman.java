import java.util.*;

public class Huffman {
	public HuffmanNode huffmanTree( ArrayList<CharObject> C ) {
		int n = C.size();
		PriorityQueue<HuffmanNode> Q = new PriorityQueue<HuffmanNode>( n );
		for( CharObject c : C ) {
			Q.add( new HuffmanNode( null, null, c ) );
		}
		for( int i = 0; i < n-1; i++ ) {
			HuffmanNode z = new HuffmanNode();
			z.left = Q.poll();
			z.right = Q.poll();
			z.freq = z.left.freq + z.right.freq;
			Q.add( z );
		}
		return Q.poll();
	}
	
	public HuffmanNode huffmanCodes( HuffmanNode node, Hashtable<Character,String> h, StringBuffer code ) {
		if( node.label != '\u0000' ) {
			h.put( node.label, code.toString() );
		}
		
		if( node.left != null ) {
			code.append("0");
			node.left.code = code.toString();
			huffmanCodes( node.left, h, code );
			code.deleteCharAt( code.length()-1 );
		}
		
		if( node.right != null ) {
			code.append("1");
			node.right.code = code.toString();
			huffmanCodes( node.right, h, code );
			code.deleteCharAt( code.length()-1 );
		}
		
		return node;
	}
}