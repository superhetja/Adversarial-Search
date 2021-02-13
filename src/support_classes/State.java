package support_classes;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;


import javax.swing.text.Position;

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
        pawns = new HashSet<Pawn>(); // er ekki búin að ákveða hvort ég ætla að nota
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
                whiteMap.put(x, new  HashMap<Integer, Pawn>());//for ever x hashmap for y.  {x: {}}
                whiteMap.get(x).put(y, pawn);                                             //{x: {y:pawn}}
                pawns.add(pawn);

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
                blackMap.put(x, new  HashMap<Integer, Pawn>());
                blackMap.get(x).put(height-y+1, pawn);
                pawns.add(pawn);
                
            }
        }
        
    }
    
    @SuppressWarnings("unchecked") 
    public State clone(){
        State cloned;
        try {
            cloned = (State)super.clone();
            cloned.whitePawns = (HashSet<Pawn>)whitePawns.clone();
            //cloned.whiteMap = (HashMap<Integer, HashMap<Integer, Pawn>>)whiteMap.clone();
            cloned.blackPawns = (HashSet<Pawn>)blackPawns.clone();
            //cloned.blackMap = (HashMap<Integer, HashMap<Integer, Pawn>>)blackMap.clone();
            cloned.pawns = (HashSet<Pawn>)pawns.clone();
        } catch (CloneNotSupportedException e) { e.printStackTrace(); System.exit(-1); cloned=null; }
        return cloned;
    }
    
    public String toString() {
        return "State{#whitepawns: " + whitePawns.size() + " whitePawns: "+ whitePawns.toString() + "#blackpawns: " + blackPawns.size()  + " blackPawns: "+ blackPawns.toString() +"}";
    }
}
