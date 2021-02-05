import java.util.HashSet;


/**
 * State keeps track of pawns position
 * Fills in the board on initialization
 */
public class State {

    public HashSet<Pawn> whitePawns, blackPawns, pawns;

    public State(int width, int height){
        whitePawns = new HashSet<Pawn>(); //spurning hvort það þurfi að setja initial capacity to width*2
        blackPawns = new HashSet<Pawn>();
        pawns = new HashSet<Pawn>(); // er ekki búin að ákveða hvort ég ætla að nota

        for (int x = 1; x <= width; x++) {
            for(int y=1; y <= 2; y++ ){
                whitePawns.add(new Pawn(x,y,));
                pawns.add(new Pawn(x,y,"white"));

                blackPawns.add(new Pawn(x,height-y+1,));
                pawns.add(new Pawn(x,height-y+1,"black"));
                
            }
        }
    }

    
}
