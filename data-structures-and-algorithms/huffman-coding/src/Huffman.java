import java.util.*;

public class Huffman {
	public HuffmanNode encodeHuffman( ArrayList<CharObject> C ) {
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
}