package support_classes;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

//import jdk.internal.net.http.common.Pair;


/**
 * State keeps track of pawns position
 * Fills in the board on initialization
 */
public class State implements Cloneable {

    public HashSet<Pawn> whitePawns, blackPawns, pawns;
    public Map<Integer, HashMap<Integer, Pawn>>whiteMap, blackMap;//map.get(x).get(y) -> pawn.
    
    public State() {
        whitePawns = new HashSet<Pawn>();
        whiteMap = new HashMap<Integer, HashMap<Integer, Pawn>>();
        blackPawns = new HashSet<Pawn>();
        blackMap = new HashMap<Integer, HashMap<Integer, Pawn>>();
        pawns = new HashSet<Pawn>();
    }


    public State(int width, int height){
        whitePawns = new HashSet<Pawn>(); //spurning hvort það þurfi að setja initial capacity to width*2
        whiteMap = new HashMap<Integer, HashMap<Integer, Pawn>>();
        blackPawns = new HashSet<Pawn>();
        blackMap = new HashMap<Integer, HashMap<Integer, Pawn>>();
        this.pawns = new HashSet<Pawn>(); // er ekki búin að ákveða hvort ég ætla að nota
        Pawn pawn;

        //TODO: set action list to pawns
        for (int x = 1; x <= width; x++) {
            for(int y=1; y <= 2; y++ ){
                pawn = new Pawn(x,y,"white");
                if(y==2)
                {
                    if(height==4)//diagonal kill actions
                    {
                        if(x<width)
                        {
                            pawn.moves.add(new Action(x,y,x+1,y+1));
                        }
                        if(x>1)
                        {
                            pawn.moves.add(new Action(x,y,x-1,y+1));
                        }
                    }
                    else //forward actiosn
                    {
                        pawn.moves.add(new Action(x,y,x,y+1));
                    }
                }
                whitePawns.add(pawn);
                if(!whiteMap.containsKey(x))
                    whiteMap.put(x, new  HashMap<Integer, Pawn>());//for ever x hashmap for y.  {x: {}}
                whiteMap.get(x).put(y, pawn);                                             //{x: {y:pawn}}
                this.pawns.add(pawn);

                pawn = new Pawn(x,height-y+1,"black");
                if(y==2)
                {
                    if(height==4)//diagonal kill actions
                    {
                        if(x<width)
                        {
                            pawn.moves.add(new Action(x,y,x+1,height-y));
                        }
                        if(x>1)
                        {
                            pawn.moves.add(new Action(x,y,x-1,height-y));
                        }
                    }
                    else //forward actiosn
                    {
                        pawn.moves.add(new Action(x,y,x,height-y));
                    }
                }
                blackPawns.add(pawn);
                if (!blackMap.containsKey(x))
                    blackMap.put(x, new  HashMap<Integer, Pawn>());
                blackMap.get(x).put(height-y+1, pawn);
                this.pawns.add(pawn);
                
            }
        }
        
    }
    
    @SuppressWarnings("unchecked") 
    public State clone(){
        State cloned = new State();
        Iterator<Pawn> ps = this.pawns.iterator();
        Pawn p;
        while (ps.hasNext())
        {
            p= ps.next();
            p = new Pawn(p.x,p.y,p.is_white);
            cloned.pawns.add(p);
            if(p.is_white)
            {
                cloned.whitePawns.add(p);
                if(!cloned.whiteMap.containsKey(p.x))
                    cloned.whiteMap.put(p.x, new HashMap<Integer, Pawn>());
                cloned.whiteMap.get(p.x).put(p.y,p);
            }
            else
            {
                cloned.blackPawns.add(p);
                if(!cloned.blackMap.containsKey(p.x))
                    cloned.blackMap.put(p.x, new HashMap<Integer, Pawn>());
                cloned.blackMap.get(p.x).put(p.y,p);
            }
            
        }
        return cloned;
    }
    
    public String toString() {
        return "State{#whitepawns: " + whitePawns.size() + " whitePawns: "+ whitePawns.toString() + "#blackpawns: " + blackPawns.size()  + " blackPawns: "+ blackPawns.toString() + " whitemap: "+ whiteMap.toString()+" blackMap: "+blackMap.toString()+"}";
    }
    public static void main(String[] args){
        var someState= new State(5,5);
        System.out.println(someState);

        State other = someState.clone();
        other.blackMap.get(2).get(5).y=-1;
        System.out.println(someState);
        System.out.println(other);
        System.out.println(other.equals(someState));
    }
}
