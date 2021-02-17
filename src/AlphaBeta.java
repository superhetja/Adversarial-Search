import java.util.Iterator;
import java.util.Queue;
import support_classes.*;
import java.util.Date;
import java.time.Clock;

public class AlphaBeta implements Search{
    private int start_alpha;//Negative Infinity
    private int start_beta;//Infinity
    private Environment env;
    private Heuristic heuristic;
    private long max_time;
    private long start_time;
    private long padding = 1000;

    private class Ret
    {
        int val;
        boolean terminal;
        Action action;
        public Ret( Action a,int v, boolean b)
        {val=v;terminal = b;action = a;}
        public Ret combine(Ret other, boolean maximizing)
        {
            if((val<other.val))
                return new Ret(action, val, terminal&&other.terminal);
            else
                return new Ret(other.action, other.val, terminal&&other.terminal);
        }
    }

    public  AlphaBeta(Environment env, Heuristic heuristic, long max_time) {
        this.env = env;
        this.heuristic = heuristic;
        this.heuristic.init(env);
        start_alpha = 1<<31;
        start_beta = ~start_alpha;
        this.max_time = max_time;
    }

    public Action doSearch(State state)
    {
        Ret anchor = new Ret(null, start_alpha, true);
        Ret ret;
        int depth = 2;
        start_time = System.currentTimeMillis();
        try
        {
            while(true)
            {
                ret = rec_search(state, null, depth, start_alpha, start_beta, true);
                if(ret.terminal)//You've explored the howl search tree
                {
                    return ret.action;
                }
                anchor = anchor.combine(ret, true);
                depth++;
                System.out.println(ret.action + " : " + ret.val);
            }
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        //return anchor.action;
    }

    private Ret rec_search(State state, Action lastAction, int depth, int a, int b, boolean maximizing)
    {
        if (depth == 0 || env.isTerminalState(state))
        {
            return new Ret(lastAction, heuristic.eval(state),true);
        }
        Ret value = new Ret(lastAction, maximizing?start_alpha:start_beta, true);
        Iterator<Action> actions = env.legalMoves(state, state.whites_turn);
        Action act;
        long now = System.currentTimeMillis();
        while(actions.hasNext())
        {
            if(max_time-padding<now-start_time)
            {
                throw new RuntimeException("out of time");
            }
                
            act = actions.next();
            value = value.combine(rec_search(env.getNextState(state,act), act ,depth-1, a, b, !maximizing), maximizing);
            if(maximizing)
                a = value.val;
            else
                b = value.val;
            if(a>=b)
                break;
        }
        return value;
    }
    public static void main(String[] args){
        Environment env = new Environment(4, 4);
        Search s = new AlphaBeta(env, new SimpleHeuristics(), (long)~(1<<63));
        Action ret = s.doSearch(env.getCurrentState());
        System.out.println("this thing: "+ret);
        
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