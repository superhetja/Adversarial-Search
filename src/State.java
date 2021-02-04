import java.util.HashSet;


/**
 * State keeps track of pawns position
 * Fills in the board on initialization
 */
public class State {

    private HashSet<Pawn> whitePawns, blackPawns;

    public State(int width, int height){
        whitePawns = new HashSet<Pawn>();
        blackPawns = new HashSet<Pawn>();

        for (int x = 1; x <= width; x++) {
            for(int y=1; y <= 2; y++ ){
                whitePawns.add(new Pawn(x,y));
                blackPawns.add(new Pawn(x,height-y+1));
            }
        }
    }

    
}
