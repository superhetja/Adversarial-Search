import java.util.HashSet;

/**
 * A Pawn.
 */
public class Pawn implements Cloneable {

    //TODO: geyma legal moves í pawn

    public int x,y;
    public String color;
    public HashSet<Action> moves;
    public boolean is_white;

    public Pawn(int x, int y, String color){
        this.x = x;
        this.y = y;
        this.color = color;
        is_white = color.equals("white");
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
		return "Pawn at (" + x + ", " + y + ")";
    }
    
    public void moveForward() {
        if (color.equals("white")){
            this.y++;
        } else {
            this.y--;
        }
    }

    public void takeRight(){
        if (this.color.equals("white")){
            this.y++;
            this.x++;
        } else {
            this.y--;
            this.x--;
        }
    }
    public void takeLeft(){
        if (this.color.equals("white")){
            this.y++;
            this.x--;
        } else {
            this.y--;
            this.x++;
        }
    }
    
}
