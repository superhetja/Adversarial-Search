package support_classes;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;


/**
 * State keeps track of pawns position
 * Fills in the board on initialization
 */
public class State implements Cloneable {

    //public Map<Integer, HashMap<Integer, Pawn>>whiteMap, blackMap;//map.get(x).get(y) -> pawn.
    public boolean whites_turn; // How's turn is it?
    public HashMap<Pawn, ArrayList<Action>> whiteMap, blackMap;
    /**
     * only for printing
     */
    public int width,height;
    
    public State() {
        whiteMap = new HashMap<Pawn, ArrayList<Action>>();
        blackMap = new HashMap<Pawn, ArrayList<Action>>();
    }


    public State(int width, int height){
        whiteMap = new HashMap<Pawn, ArrayList<Action>>();
        blackMap = new HashMap<Pawn, ArrayList<Action>>();
        this.width = width;
        this.height = height;
        whites_turn = true;
        Pawn wPawn,bPawn;
        for (int x = 1; x <= width; x++) {
            for(int y=1; y <= 2; y++ ){
                int bY = height+1-y;
                wPawn = new Pawn(x,y,true);
                bPawn = new Pawn(x, bY, false);
                whiteMap.put(wPawn, new ArrayList<Action>());
                blackMap.put(bPawn, new ArrayList<Action>());
                
                if(y==2)
                {
                    if(height==4)//diagonal kill actions
                    {
                        if(x<width)
                        {
                            whiteMap.get(wPawn).add(new Action(x,y,x+1,y+1));
                            blackMap.get(bPawn).add(new Action(x,bY, x+1, bY-1));
                        }
                        if(x>1)
                        {
                            whiteMap.get(wPawn).add(new Action(x,y,x+1,y-1));
                            blackMap.get(bPawn).add(new Action(x,bY,x-1,bY-1));
                        }
                    }
                    else //forward actions
                    {
                        whiteMap.get(wPawn).add(new Action(x,y, x, y+1));
                        blackMap.get(bPawn).add(new Action(x,bY, x, bY-1));
                    }
                }
            }
        }
        
    }




    public boolean checkWhite(int x, int y)
    {
        return whiteMap.containsKey(new Pawn(x,y,true));
    }

    public boolean checkBlack(int x, int y)
    {
        return blackMap.containsKey(new Pawn(x,y,false));
    }

    public ArrayList<Action> getPawnAction(int x, int y)
    {
        if (checkWhite(x, y))
            return whiteMap.get(new Pawn(x,y,true));
        else if (checkBlack(x, y))
            return blackMap.get(new Pawn(x,y,false));
        else return null;
    }

    public void delete_pawn(Pawn p) {
        HashMap<Pawn, ArrayList<Action>> map = p.is_white? whiteMap : blackMap;
        int pawn_shift = p.is_white? 1 : -1;

        ArrayList<Action> tmp_action;

        for (int x = -1; x <= 1 ; x++) {
            for (int y = -1; y <= 1; y++) {
                tmp_action = getPawnAction(p.x+x, p.y+y);

                if(tmp_action != null){
                    //if in same line and same color than that pawn can now move forward..
                    if(x == 0 && (p.is_white? checkWhite(p.x+x, p.y+y): checkBlack(p.x+x, p.y+y))) {
                        tmp_action.add(new Action(p.x+x, p.y+y, p.x, p.y));
                    }
                    else if (x != 0 && y != 0 && (p.is_white? checkBlack(p.x+x, p.y+y): checkWhite(p.x+x, p.y+y))) {
                        Iterator<Action> itr = tmp_action.iterator();
                        while(itr.hasNext()) {
                            Action move = itr.next();
                            if(move.x2 == p.x && move.y2 == p.y && (move.isDiagonalLeft() || move.isDiagonalRight() )) {
                                itr.remove();
                            }

                        }
                    }


                }
            }
        }

        map.remove(p);
    }


    public void add_pawn(Pawn p){
        HashMap<Pawn, ArrayList<Action>> map = p.is_white? whiteMap : blackMap;
        int pawn_shift = p.is_white? 1:-1;
        // we have:
        //  x-1,y+1,    x,y+1,  x+1,y+1
        //  x-1,y,      p(x,y)  x+1,y
        //  x-1,y-1,    x,y-1,  x+1,y-1
        ArrayList<Action> a = new ArrayList<Action>();
        ArrayList<Action> tmp_action;
        for (int x = -1; x <= 1; x++){
            for (int y = -1; y <= 1; y++) {
                tmp_action = getPawnAction(p.x+x, p.y+y);

                //there is no pawn here
                if(tmp_action == null){
                    // for the square in front of pawn that is about to be placed
                    // x needs to be 0 and y needs to be 1 for white -1 for black
                    // it that is empty then we can add it to the list
                    if(x == 0 && y == pawn_shift) {
                        a.add(new Action(p.x, p.y, p.x, p.y+pawn_shift));
                    }
                } else {
                    //if pawn in front of us then we need to remove its action if it is a forward move that end on your square
                    if (x == 0){
                        Iterator<Action> ji = tmp_action.iterator();
                        while(ji.hasNext()){
                            // ok if the move is forward move
                            // and the x2,y2 coordinates of the move are same as py then remove.
                            // i thing all of the condition except for y2 == y will always be true..
                            Action move = ji.next();
                            if(move.isForwardMove() && move.x2 == p.x && move.y2 == p.y) {
                                ji.remove();
                            }
                        }
                    } else if ( (y == pawn_shift) && (p.is_white? checkBlack(p.x+x, p.y+y): checkWhite(p.x+x, p.y+y)) ) {
                        // if not in same row, and is in front of me and is not in my team  then add move to me and that pawn
                        a.add(new Action(p.x,p.y, p.x+x, p.y+y));
                        tmp_action.add(new Action(p.x+x, p.y+y, p.x, p.y));

                    }
                    
                }
                
            }
        }
            
        map.put(p,a);
    }



    public void delete_pawn(int x, int y, boolean is_white)
    {
        delete_pawn(new Pawn(x,y,is_white));
    }
    

    @SuppressWarnings("unchecked") 
    public State clone(){
        State cloned = new State();
        cloned.whiteMap = copy(whiteMap);
        cloned.blackMap = copy(blackMap);
        cloned.width = width;
        cloned.height = height;
        cloned.whites_turn = whites_turn;
        return cloned;
    }
    
    public String toString() {
        String s = "State   x = white, o = black \n";
        s += "--".repeat(width)+ "-" + "\n";
        Pawn p;
        for (int y = height; y > 0; y--) {
            for (int x = 1; x <= width; x++){
                s += "|" + (checkWhite(x, y) ? "x" : checkBlack(x, y) ? "o" : " ");
            }
            s += "|" + "\n";
        }
        s +=  "-" + "--".repeat(width) + '\n';
        s += "White legal moves: " + whiteMap.values()+ "\n";
        s += "Black legal moves: " + blackMap.values()+ "\n";
        return s;
    }


    public static HashMap<Pawn, ArrayList<Action>> copy(HashMap<Pawn, ArrayList<Action>> original) {
        HashMap<Pawn, ArrayList<Action>> copy = (HashMap<Pawn, ArrayList<Action>>)original.clone();
        for (Map.Entry<Pawn, ArrayList<Action>> entry : original.entrySet())
        {
            copy.put( (Pawn)entry.getKey().clone(), (ArrayList<Action>) entry.getValue().clone() );
        }
        return copy;
    }
    public static void main(String[] args){
        var someState= new State(5,6);
        System.out.println(someState);
        Pawn oldPawn = new Pawn(2,2,true);
        Pawn newPawn = new Pawn(2,3,true);
        ArrayList<Action> a = new ArrayList<Action>();
        // should add [(move 2 1 2 2)]
        someState.delete_pawn(oldPawn);
        //should remove [(move 2 2 2 3)] and add [(move 2 3 2 4)]
        someState.add_pawn(newPawn);
        System.out.println(someState);
        oldPawn = new Pawn(3,5, false);
        newPawn = new Pawn(3,4, false);
        //should add [(move 3 6 3 5)]
        someState.delete_pawn(oldPawn);
        someState.add_pawn(newPawn);
        System.out.println(someState);
        
        oldPawn = new Pawn(2,3, true);
        newPawn = new Pawn(2,4, true);
        //Should remove (move 3 4 2 3)
        someState.delete_pawn(oldPawn);
        // Sould remove (move 2 5 2 4) add move (move 1 5 2 4) and add move(2 4 1 5)
        someState.add_pawn(newPawn);

    
 
        System.out.println(someState);

    }
}
