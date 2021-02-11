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

    //isTerminal has options: local function, Environment function, state function.

    public Action doSearch(State state, boolean color)
    {
        int score = 1<<31;//negative infinity
        if (!color)
            score = ~score;
        Action action;
        Action best;
        int ret;
        int depth = 2;
        Iterator<Action> actions;
        if (isTerminal(state))
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
