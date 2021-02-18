package support_classes;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

//import jdk.internal.net.http.common.Pair;


/**
 * State keeps track of pawns position
 * Fills in the board on initialization
 */
public class State2 implements Cloneable {

    //public Map<Integer, HashMap<Integer, Pawn>>whiteMap, blackMap;//map.get(x).get(y) -> pawn.
    public boolean whites_turn; // How's turn is it?
    public HashMap<Pawn, ArrayList<Action>> whiteMap, blackMap;
    /**
     * only for printing
     */
    public int width,height;
    
    public State2() {
        whiteMap = new HashMap<Pawn, ArrayList<Action>>();
        blackMap = new HashMap<Pawn, ArrayList<Action>>();
    }


    public State2(int width, int height){
        whiteMap = new HashMap<Pawn, ArrayList<Action>>();
        blackMap = new HashMap<Pawn, ArrayList<Action>>();
        this.width = width;
        this.height = height;
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
                    else //forward actiosn
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

    public void delete_pawn(Pawn p)
    {
        whiteMap.remove(p);
        blackMap.remove(p);
    }

    public void add_pawn(Pawn p, ArrayList<Action> a){
        HashMap<Pawn, ArrayList<Action>> map = p.is_white? whiteMap : blackMap;
        map.put(p,a);
    }

    public void delete_pawn(int x, int y, boolean is_white)
    {
        delete_pawn(new Pawn(x,y,is_white));
    }
    

    @SuppressWarnings("unchecked") 
    public State2 clone(){
        State2 cloned = new State2();
        cloned.whiteMap = copy(whiteMap);
        cloned.blackMap = copy(blackMap);
        cloned.width = width;
        cloned.height = height;
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
        var someState= new State2(5,6);
        System.out.println(someState);
        Pawn oldPawn = new Pawn(2,2,true);
        Pawn newPawn = new Pawn(2,3,true);
        ArrayList<Action> a = new ArrayList<Action>();
        a.add(new Action(2,3,2,4));
        someState.add_pawn(newPawn, a);
        someState.delete_pawn(oldPawn);
        System.out.println(someState);
        ArrayList<Action> k = someState.getPawnAction(2,1);
        System.out.println(k);
        k.add(new Action(2,1,2,2));
        System.out.println(someState);
    }
}
