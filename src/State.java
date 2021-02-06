import java.util.HashSet;


/**
 * State keeps track of pawns position
 * Fills in the board on initialization
 */
public class State implements Cloneable {

    public HashSet<Pawn> whitePawns, blackPawns, pawns;

    public State() {
        whitePawns = new HashSet<Pawn>();
        blackPawns = new HashSet<Pawn>();
        pawns = new HashSet<Pawn>();
    }


    public State(int width, int height){
        whitePawns = new HashSet<Pawn>(); //spurning hvort það þurfi að setja initial capacity to width*2
        blackPawns = new HashSet<Pawn>();
        pawns = new HashSet<Pawn>(); // er ekki búin að ákveða hvort ég ætla að nota

        for (int x = 1; x <= width; x++) {
            for(int y=1; y <= 2; y++ ){
                whitePawns.add(new Pawn(x,y,"white"));
                pawns.add(new Pawn(x,y,"white"));

                blackPawns.add(new Pawn(x,height-y+1,"black"));
                pawns.add(new Pawn(x,height-y+1,"black"));
                
            }
        }
        
    }
    
    @SuppressWarnings("unchecked") 
    public State clone(){
        State cloned;
        try {
            cloned = (State)super.clone();
            cloned.whitePawns = (HashSet<Pawn>)whitePawns.clone();
            cloned.blackPawns = (HashSet<Pawn>)blackPawns.clone();
            cloned.pawns = (HashSet<Pawn>)pawns.clone();
        } catch (CloneNotSupportedException e) { e.printStackTrace(); System.exit(-1); cloned=null; }
        return cloned;
    }
    
    public String toString() {
        return "State{#whitepawns: " + whitePawns.size() + " whitePawns: "+ whitePawns.toString() + "#blackpawns: " + blackPawns.size()  + " blackPawns: "+ blackPawns.toString() +"}";
    }
}
