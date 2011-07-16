public class Vertex {
	public int x;
	public int y;
	public int color;
	public int value = 0;

	Vertex( int v, int x, int y, int color ) {
		this.value = v;
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "x: " + this.x + "\n" );
		sb.append( "y: " + this.y + "\n" );
		sb.append( "c: " + this.color + "\n" ) ;
		sb.append( "val: " + this.value + "\n\n" );
		return sb.toString();
	}
}