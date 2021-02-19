import java.util.Iterator;
import support_classes.*;
public class SimpleHeuristics implements Heuristic {
	/**
	 * reference to the environment to be able to figure out positions of obstacles
	 */
	private Environment env;

    /**
	 * @param solvingAgent
	 */
	SimpleHeuristics() {
	}

    public void init(Environment env) {
        this.env = env;
    }


    public int eval(State state) {
        /*
            SIMPLE HEURISTIC:
                returns:
                • 100, if white won the game
                • 0, for a draw
                • -100, if white lost the game
                • distance of most advanced black pawn to row 1 - distance of most advanced white pawn
                    to row H, for non-terminal states
            Time complexety: O(number of pawns)
        
        */
        Iterator<Pawn> white_pawns = state.whiteMap.keySet().iterator();
        Iterator<Pawn> black_pawns = state.blackMap.keySet().iterator();
        int abp= this.env.getHeigth(), awp= 1; //  Most advanced black/white pawn
        Pawn pawn;

        // Find most advanced white pawn
        while(white_pawns.hasNext()){
            pawn= white_pawns.next();
            if(pawn.y >awp){
                awp= pawn.y;
            }
        }
        // Find most advanced blackpawn
        while(black_pawns.hasNext()){
            pawn= black_pawns.next();
            if(pawn.y <abp){
                abp= pawn.y;
            }
        }

        //check for tie
        // Tie if whos turn it is has nolegal moves

        Iterator<Action> moves = env.legalMoves(state, state.whites_turn);
        if (!moves.hasNext()){
            return 0;
        }
        if (awp== this.env.getHeigth()){
            return 100;
        } else if (abp == 1) {
            return -100;
        } else {
            return (abp -1)-(this.env.getHeigth()-awp);
        }
        


    }
}