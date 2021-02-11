package support_classes;
import support_classes.position;
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
    public Map<Integer, HashMap<Integer, Pawn>>whiteMap, blackMap;
    


    
    public State() {
        whitePawns = new HashSet<Pawn>();
        whiteMap = new HashMap<Integer, HashMap<Integer, Pawn>>();
        blackPawns = new HashSet<Pawn>();
        blackMap = new HashMap<Integer, HashMap<Integer, Pawn>>();
        pawns = new HashSet<Pawn>();
    }


    public State(int width, int height){
        whitePawns = new HashSet<Pawn>(); //spurning hvort það þurfi að setja initial capacity to width*2
        blackPawns = new HashSet<Pawn>();
        pawns = new HashSet<Pawn>(); // er ekki búin að ákveða hvort ég ætla að nota
        Pawn pawn;
        //TODO: set action list to pawns
        for (int x = 1; x <= width; x++) {
            for(int y=1; y <= 2; y++ ){
                pawn = new Pawn(x,y,"white");
                whitePawns.add(pawn);
                whiteMap.put(x, new  HashMap<Integer, Pawn>());
                whiteMap.get(x).put(y, pawn);
                pawns.add(pawn);

                pawn = new Pawn(x,height-y+1,"black");
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
            cloned.whiteMap = (HashMap<Integer, HashMap<Integer, Pawn>>)whiteMap.clone();
            cloned.blackPawns = (HashSet<Pawn>)blackPawns.clone();
            cloned.blackMap = (HashMap<Integer, HashMap<Integer, Pawn>>)blackMap.clone();
            cloned.pawns = (HashSet<Pawn>)pawns.clone();
        } catch (CloneNotSupportedException e) { e.printStackTrace(); System.exit(-1); cloned=null; }
        return cloned;
    }
    
    public String toString() {
        return "State{#whitepawns: " + whitePawns.size() + " whitePawns: "+ whitePawns.toString() + "#blackpawns: " + blackPawns.size()  + " blackPawns: "+ blackPawns.toString() +"}";
    }
}
