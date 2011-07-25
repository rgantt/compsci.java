
public class HuffmanNode implements Comparable {
	public char label;
	public int freq;
	public HuffmanNode left;
	public HuffmanNode right;
	public String code;
	
	public HuffmanNode( HuffmanNode left, HuffmanNode right, CharObject c ) {
		this.left = left;
		this.right = right;
		this.label = c.letter;
		this.freq = c.freq;
	}
	
	public HuffmanNode(){}
	
	public int compareTo( Object o ) {
		if( o instanceof HuffmanNode ) {
			if( ((HuffmanNode) o).freq > this.freq ) {
				return -1;
			} else if( ((HuffmanNode) o).freq < this.freq ) {
				return 1;
			} else {
				return 0;
			}
		}
		return -2;
	}
}
