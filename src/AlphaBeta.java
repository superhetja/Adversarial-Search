import java.util.Iterator;

import support_classes.*;

public class AlphaBeta implements Search{
    private int start_alpha;//Negative Infinity
    private int start_beta;//Infinity
    private Environment env;
    private Heuristic heuristic;

    public  AlphaBeta(Environment env, Heuristic heuristic) {
        this.env = env;
        this.heuristic = heuristic;
    }
    
    public boolean isTerminal(State s)
    {
        return false;
    }

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
        if (isTerminal(state))
            return null;
        try{
            while (true)
            {
                actions = env.legalMoves(state, color);
                while (actions.hasNext())
                {
                    action = actions.next();
                    ret = rec_search(env.getNextState(state, action), depth, start_alpha, start_beta, !color);
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

    private int rec_search(State state, int depth, int a, int b, boolean color)
    {
        if((depth == 0) || isTerminal(state))
        {
            return heuristic.eval(state);
        }
        if (color)
        {
            int ret; int value = color?start_alpha:start_beta;
            Action action; Iterator<Action> actions = env.legalMoves(state, color);
            while (actions.hasNext())
            {
                action = actions.next();
                ret = rec_search(env.getNextState(state, action), depth-1,a,b, !color);
                value = value<ret?ret:value;
                a = a<ret?ret:a;
                if (a>=b)
                {
                    break;
                }
            }
            return value;
        }
        else
        {
            int ret; int value = color?start_alpha:start_beta;
            Action action; Iterator<Action> actions = env.legalMoves(state, color);
            while (actions.hasNext())
            {
                action = actions.next();
                ret = rec_search(env.getNextState(state, action), depth-1,a,b, !color);
                value = value<ret?value:ret;
                a = a<ret?a:ret;
                if (b<=a)
                {
                    break;
                }
            }
            return value;
        }
        
    }
}
/*
public class AlphaBeta implements Pruning{
    private int start_alpha;//Negative Infinity
    private int start_beta;//Infinity
    private Environment env;

    public  AlphaBeta(Environment env, Heuristic heuristic) {
        this.env = env;
        this.heuristic = heuristic;
    }
    public void init(Environment env)
    {
        this.env = env;
        start_alpha = 1<<31;//Negative Infinity
        start_beta = ~start_alpha;//Infinity
    }
    public boolean cut_off();
    public Pruning passing();
    public void backing();
    
}

*/