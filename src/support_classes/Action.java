package support_classes;

/**
 * Type Action
 * Helper function for working with actions for the pawns.
 */
public class Action {

    /**
     * the from - to coordiantes of the action
     */
    public int x1, y1, x2, y2;

    /**
     * Action init with four integers.
     * @param x1    from x coordinate
     * @param y1    from y coordinate
     * @param x2    to x coordinate
     * @param y2    to y coordinate
     */
    public Action(int x1, int  y1, int x2, int y2)
    {
        this.x1 = x1; this.y1 = y1;
        this.x2 = x2; this.y2 = y2;
    }

    /**
     * Action init with int[]
     * @param coords    list of four integers [x1, y1, x2, y2]
     */
    public Action(int[] coords)
    {
        this.x1 = coords[0]; this.y1 = coords[1];
        this.x2 = coords[2]; this.y2 = coords[3];
    }

    /**
     * Returns the from coordinates in int[]
     * @return  int[] of [x1,y1]
     */
    public int[] start(){return new int[]{x1,y1};}

    /**
     * Return the to coordinates in int[]
     * @return  int[] of [x2,y2]
     */
    public int[] end(){return new int[]{x2,y2};}

    /**
     * Return a true if the move is forward move
     * else false
     * 
     * @return x1 == x2 ? true : false
     */
    public boolean isForwardMove(){
        return x1 == x2;
    }


    /**
     * Returns true if the move is diagonale left move
     * else false
     * 
     * @return (y1< y2 && x1 > x2) || (y1 > y2 && x1 < x2) ? true : false
     */
    public boolean isDiagonalLeft(){
        return (y1< y2 && x1 > x2) || (y1 > y2 && x1 < x2);
    }


    /**
     * Returns true if the move is diagonale left move
     * else false
     * 
     * @return (y1 < y2 && x1 < x2) || (y1 > y2 && x1 > x2) ? true : false
     */
    public boolean isDiagonalRight(){
        return (y1 < y2 && x1 < x2) || (y1 > y2 && x1 > x2);
    }

    /**
     * Returns true if the actions is a white pawn action
     * else false
     * 
     * @return  y1 < y2 ? true : false
     */
    public boolean isWhite(){
        return y1 < y2;
    }

    /**
     * A string representation of the type Action
     */
    public String toString()
    {
        return "(move " + x1 + " " + y1 + " " + x2 + " " + y2 + ")";
    }

}
