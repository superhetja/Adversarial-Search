import java.util.HashMap;
import java.util.HashSet;
/**
 * Creates a board and places pawns down
 */
public class Environment {



    private State currentState;
    private int width, height;
    // I think it may be a good idea to store legalMoves since they are never that many and can save time of looping to find them.
    //private LinkedList<Pawn> legalMovesWhite, legalMovesBlack; 

    public Environment(int width, int height){
        this.width = width;
        this.height = height;
        this.currentState = new State(width, height);

    }

    public State getCurrentState(){
        return this.currentState;
    }

    public HashMap<Pawn,Pawn> legalMoves(State state){
        HashMap<Pawn,Pawn> moves = new HashMap<>();
        HashSet<Pawn> white = state.whitePawns;
        HashSet<Pawn> black = state.blackPawns;
        for (Pawn p : white) {
            Pawn right_pawn = (Pawn)p.clone();
            right_pawn.takeRight();
            if (white.contains(right_pawn)) { moves.put(p, right_pawn);}
            Pawn left_pawn = (Pawn)p.clone();
            left_pawn.takeLeft();
            if (white.contains(left_pawn)) { moves.put(p, left_pawn);}
            Pawn forward_pawn = (Pawn)p.clone();
            forward_pawn.moveForward();
            if (!white.contains(forward_pawn) && !black.contains(forward_pawn)) {
                moves.put(p,forward_pawn);
            }
        }

    
        
        return moves;
    }

    public void updateState(int[] lastMove, String color){
        this.currentState = getNextState(this.currentState, lastMove, color);
    }

    public State getNextState (State state,int[] lastMove, String color) {
        State new_state = (State)state.clone();
        if (color.equals("white")) {
            new_state.whitePawns.remove(new Pawn(lastMove[0], lastMove[1]));
            Pawn new_pawn = new Pawn(lastMove[2], lastMove[3]);
            new_state.whitePawns.add(new_pawn);
            if(new_state.blackPawns.contains(new_pawn)){
                new_state.blackPawns.remove(new_pawn);
            }
            
        }else {
            new_state.blackPawns.remove(new Pawn(lastMove[0], lastMove[1]));
            new_state.blackPawns.add(new Pawn(lastMove[2], lastMove[3]));
            Pawn new_pawn = new Pawn(lastMove[2], lastMove[3]);
            if(new_state.whitePawns.contains(new_pawn)){
                new_state.whitePawns.remove(new_pawn);
            }

        }
        return new_state;
        //currentState.pawns.remove(new Pawn(lastMove[0], lastMove[1], color));
        //currentState.pawns.add(new Pawn(lastMove[2], lastMove[3],color));
    }

    
}
