import support_classes.*;
import java.util.Iterator;

public class MinMax implements Search{
    Environment env;
    Heuristic her;
    Pruning pruning;
    public MinMax(Environment env, Heuristic heuristic)
    {
        this.env = env;
        this.her = heuristic;
    }
    /*
    @Override
    public void init(Environment env, Heuristic heuristic, Pruning pruning) {
        this.env = env;
        this.her = heuristic;
        this.pruning = pruning;
    }*/
    public boolean isTerminal(State state) {   
        Iterator<Pawn> white_pawns = state.whitePawns.iterator();
        Iterator<Pawn> black_pawns = state.blackPawns.iterator();
        Pawn tmpPawn;
        boolean tie=true; // Tie if whos turn it is has nolegal moves
        
        while(white_pawns.hasNext()){
            tmpPawn = white_pawns.next();
            if (tmpPawn.y==this.env.getHeigth()){
                return true; // White won
            }
            if (state.whites_turn){ // If it's your turn and you have leagal move its not a tie
                tie = tmpPawn.moves.size()!= 0? false: tie;
            }
        }
        while(black_pawns.hasNext()){
            tmpPawn = black_pawns.next();
            if (tmpPawn.y==1){
                return true; // black won
            }
            if (!state.whites_turn){ // If it's your turn and you have leagal move it's not a tie
                tie = tmpPawn.moves.size()!= 0? false: tie;
            }
        }
        return tie;
    }
    //isTerminal has options: local function, Environment function, state function.

    public Action doSearch(State state, boolean color)
    {
        int score = 1<<31;//negative infinity
        if (!color)
            score = ~score;
        Action action;
        Action best = null;
        int ret;
        int depth = 2;
        Iterator<Action> actions;
        if (isTerminal(state))//state.is_terminal();  env.is_termialn(state) local is_termal(state), herusic.is_termal(state)
        //state.get_next(action)→ state
            return null;
        try{
            while (true)
            {
                actions = env.legalMoves(state, color);
                while (actions.hasNext())
                {
                    action = actions.next();
                    ret = rec_search(env.getNextState(state, action), depth, !color);
                    if (ret>score)
                    {
                        best = action;
                        score = ret;
                    }
                }
                depth++;
            }
        }
        catch(Exception e)
        {
            return best;
        }
    }

    private int rec_search(State state, int depth, boolean color)
    {
        /*
        if(checkfor_timeup(fjdkslaj))
            throw;
        */
        if((depth == 0) || isTerminal(state))
        {
            return her.eval(state);
        }
        int ret=0;
        int best = color?1<<31:~(1<<31);
        Action action;
        Iterator<Action> actions = env.legalMoves(state, color);
        while (actions.hasNext())
        {
            action = actions.next();
            ret = rec_search(env.getNextState(state, action), depth-1, !color);
            if(ret<best ^ color)
                best = ret;
        }
        return ret;
    }
}
