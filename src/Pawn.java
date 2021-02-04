public class Pawn implements Cloneable {

    private int x,y;

    public Pawn(int x, int y){
        this.x = x;
        this.y = y;

    }

    public boolean equals(Object o) {
		Pawn p = (Pawn)o;
		return p.x == x && p.y == y;
	}
	
	public int hashCode() {
		return x ^ y + x*31 + y*53;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) { return null; }
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
    
}
