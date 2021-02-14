//import jdk.vm.ci.common.InitTimer;
import java.util.Iterator;
import support_classes.*;
public interface Heuristic {
    private Environment env;
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
        Iterator<Pawn> white_pawns = state.whitePawns.iterator();
        Iterator<Pawn> black_pawns = state.blackPawns.iterator();
        int abp= this.env.getHeigth(), awp= 1; //  Most advanced black/white pawn
        boolean tie=true; // Tie if whos turn it is has nolegal moves
        while(white_pawns.hasNext()){
            Pawn tmpPawn = white_pawns.next();
            awp = {tmpPawn.y>awp? tmpPawn.y: awp};
            if (state.whites_turn){
                tie = {tmpPawn.moves.size()!= 0? false: tie}
            }
        }
        while(black_pawns.hasNext()){
            Pawn tmpPawn = black_pawns.next();
            abp = {tmpPawn.y<abp? tmpPawn.y: abp};
            if (!state.whites_turn){
                tie = {tmpPawn.moves.size()!= 0? false: tie}
            }
        }
        if tie{
            return 0;
        } else if (awp== this.env.getHeigth()){
            return 100;
        } else if (abp == 1) {
            return -100;
        } else {
            return (abp -1)-(this.env.getHeigth()-awp)
        }
        


    }
}
