import support_classes.*;

import java.util.Date;
import java.util.Iterator;
import java.lang.RuntimeException;

public class MinMax implements Search{
    Environment env;
    Heuristic her;
    Pruning pruning;
    long starting;
    Date clock = new Date();
    long time_padding = 1000;
    int max_time;
    public MinMax(Environment env, Heuristic heuristic, int max_time)
    {
        this.env = env;
        this.her = heuristic;
        her.init(env);
        this.max_time = max_time;
    }
    /*
    @Override
    public void init(Environment env, Heuristic heuristic, Pruning pruning) {
        this.env = env;
        this.her = heuristic;
        this.pruning = pruning;
    }*/
    //maximaze for the color (is_white)
    public Action doSearch(State state, boolean color)
    {
        int score = 1<<31;//negative infinity
        Action action;
        Action best = null;
        int ret;
        int depth = 2;
        Iterator<Action> actions;
        starting = clock.getTime();
        if (env.isTerminalState(state))//state.is_terminal();  env.is_termialn(state) local is_termal(state), herusic.is_termal(state)
        //state.get_next(action)→ state
            return null;
        try{
            while (true)
            {
                actions = env.legalMoves(state, color);
                System.out.println("is it true? "+actions.hasNext());
                while (actions.hasNext())
                {
                    System.out.println("here");
                    action = actions.next();
                    System.out.println("Do this: " +action);
                    ret = rec_search(env.getNextState(state, action), depth-1, false, !color);
                    System.out.println("scored: "+ret);
                    if (ret>score)
                    {
                        best = action;
                        score = ret;
                    }
                }
                depth++;
            }
        }
        catch(RuntimeException e)
        {
            return best;
        }
    }

    private int rec_search(State state, int depth, boolean maximizing, boolean color)
    {
        System.out.println(clock.getTime()-starting);
        if(clock.getTime()-starting+time_padding>max_time)
            throw new RuntimeException();
        
        if((depth == 0) || env.isTerminalState(state))
        {
            return her.eval(state);
        }
        int ret=0;
        int best = maximizing?~(1<<31):1<<31;//best or worst for minimizing
        Action action;
        Iterator<Action> actions = env.legalMoves(state, color);
        System.out.println(actions.hasNext());
        while (actions.hasNext())
        {
            action = actions.next();
            ret = rec_search(env.getNextState(state, action), depth-1, !maximizing, !color);
            if((ret<best) ^ maximizing)
                best = ret;
        }
        return ret;
    }
    private int fastlog_2(int num)
    {
        int ret = 0;
        while (num>1)
        {
            num=num>>1;
        }
        return ret;
    }

    public static void main(String[] args){
        Environment env = new Environment(4, 4);
        Search s = new MinMax(env, new SimpleHeuristics(), 5000);
        Action ret = s.doSearch(env.getCurrentState(),true);
        System.out.println("this thing: "+ret);

    }
}
