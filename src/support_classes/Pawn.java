package support_classes;

/**
 * Type Pawn.
 * Coordinate of a pawn on the board and helper functions
 */
public class Pawn implements Cloneable {


    /**
     * x and y coordinate of the Pawn
     */
    public int x,y;

    /**
     * true if the pawn is white else false.
     */
    public boolean is_white;

    //TODO: is this used?
    /**
     * Pawn init with color as string.
     * 
     * @param x     x coordinates of the pawn
     * @param y     y coordinates of the pawn
     * @param color string "white" || "black"
     */
    public Pawn(int x, int y, String color){
        this.x = x;
        this.y = y;
        is_white = color.equals("white");
    }

    /**
     * Pawn init.
     * @param x     x coordinates of the pawn
     * @param y     y coordinates of the pawn
     * @param is_white  true if the pawn is white else false
     */
    public Pawn(int x, int y, boolean is_white)
    {
        this.x = x;
        this.y = y;
        this.is_white = is_white;
    }

    /**
     * equals representation for the pawn
     * return true if xs' and ys' are the same for both Pawns 
     * else false.
     */
    public boolean equals(Object o) {
		Pawn p = (Pawn)o;
		return p.x == x && p.y == y;
	}
	
    /**
     * Hashing function for the pawn
     */
	public int hashCode() {
		return x ^ y + x*31 + y*53;
	}

    /**
     * Cloning function for the pawn
     */
	public Object clone() {
        Pawn clone = new Pawn(x,y,is_white);
        return clone;
	}

    /**
     * A string representation of the pawn
     */
	public String toString() {
		return "Pawn at (" + x + ", " + y + ")";
    }


    public static void main(String[] args){

    }

}
