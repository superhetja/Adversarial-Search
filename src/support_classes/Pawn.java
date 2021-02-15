package support_classes;

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
        this.moves = new HashSet<Action>();
    }

    public Pawn(int x, int y, boolean is_white)
    {
        this.x = x;
        this.y = y;
        this.is_white = is_white;
        this.color = is_white?"white":"black";
        this.moves = new HashSet<Action>();
    }

    public boolean equals(Object o) {
		Pawn p = (Pawn)o;
		return p.x == x && p.y == y;
	}
	
	public int hashCode() {
		return x ^ y + x*31 + y*53;
	}

	public Object clone() {
        Pawn clone = new Pawn(x,y,is_white);
        clone.moves = new HashSet<Action>();
        for(Action act: this.moves)
            clone.moves.add(act);
        return clone;
	}

	public String toString() {
		return "Pawn at (" + x + ", " + y + ")"+moves;
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
            this.x++;
        }
    }
    public void takeLeft(){
        if (this.color.equals("white")){
            this.y++;
            this.x--;
        } else {
            this.y--;
            this.x--;
        }
    }
    public void updateLeagalMoves(State s){
        
        moves = new HashSet<Action>();
        int shift = is_white?1:-1;
        if(s.getPawn(x, y+shift)==null)//go forward
            moves.add(new Action(x,y,x,y+shift));
        if(s.checkBlack(x+1, y+shift)&&is_white || s.checkWhite(x+1, y+shift)&&!is_white)
            moves.add(new Action(x,y,x+1,y+shift));
        if(s.checkBlack(x-1, y+shift)&&is_white || s.checkWhite(x-1, y+shift)&&!is_white)
            moves.add(new Action(x,y,x-1,y+shift));
    }
        /*
        HashSet<Action> tmp_moves = new HashSet<Action>();
        if (is_white) {
            if (!s.blackPawns.contains(new Pawn(x, y+1, "black"))){
                if (!s.whitePawns.contains(new Pawn(x, y+1, "white"))){
                    // move forvard
                    tmp_moves.add(new Action(x, y, x, y+1));
                }
            } else if (s.blackPawns.contains(new Pawn(x+1, y+1, "black"))){
                // take right
                tmp_moves.add(new Action(x, y, x+1, y+1));
            } else if (s.blackPawns.contains(new Pawn(x-1, y+1, "black"))){
                // take left
                tmp_moves.add(new Action(x, y, x-1, y+1));
            }
        } else {
            if (!s.whitePawns.contains(new Pawn(x, y-1, "white"))){
                if (!s.blackPawns.contains(new Pawn(x, y+1, "black"))){
                    // move forvard
                    tmp_moves.add(new Action(x, y, x, y-1));
                }
            } else if (s.whitePawns.contains(new Pawn(x+1, y-1, "white"))){
                // take right
                tmp_moves.add(new Action(x, y, x+1, y-1));
            } else if (s.whitePawns.contains(new Pawn(x-1, y-1, "white"))){
                // take left
                tmp_moves.add(new Action(x, y, x-1, y-1));
            }
        }
        this.moves= tmp_moves;
        */

    public static void main(String[] args){
        var myPawn = new Pawn(1,3,"white");
        System.out.println(myPawn);
        myPawn.takeLeft();
        myPawn.moveForward();
        var pawnClone = myPawn.clone();
        System.out.println(pawnClone.equals(myPawn));
        myPawn.moves.add(new Action(1,2,3,4));
        System.out.println(myPawn);
        System.out.println(myPawn.clone());
    }

}
