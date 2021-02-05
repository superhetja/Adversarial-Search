import java.util.List;
import java.util.LinkedList;
/**
 * Creates a board and places pawns down
 */
public class Environment {



    private State currentState;
    private int width, height;
    // I think it may be a good idea to store legalMoves since they are never that many and can save time of looping to find them.
    private LinkedList<Pawn> legalMovesWhite, legalMovesBlack; 

    public Environment(int width, int height){
        this.width = width;
        this.height = height;
        this.currentState = new State(width, height);

    }

    public State getCurrentState(){
        return this.currentState;
    }

    public List<Pawn> legalMoves(State state){
        List<Pawn> moves = new LinkedList<Pawn>();

        
        return moves;
    }

    public void updateState(int[] lastMove, String color) {
        if (color.equals("white")) {
            currentState.whitePawns.remove(new Pawn(lastMove[0], lastMove[1]));
            currentState.whitePawns.add(new Pawn(lastMove[2], lastMove[3]));
            
        }else {
            currentState.blackPawns.remove(new Pawn(lastMove[0], lastMove[1]));
            currentState.blackPawns.add(new Pawn(lastMove[2], lastMove[3]));

        }
        //currentState.pawns.remove(new Pawn(lastMove[0], lastMove[1], color));
        //currentState.pawns.add(new Pawn(lastMove[2], lastMove[3],color));
    }

    
}
