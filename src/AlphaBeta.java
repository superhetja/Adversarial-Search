import java.util.Iterator;
import java.util.Queue;
import support_classes.*;
import java.util.Date;
import java.time.Clock;

public class AlphaBeta implements Search{
    //constants
    private int start_alpha;//Negative Infinity
    private int start_beta;//Infinity
    //support objects
    private Environment env;
    private Heuristic heuristic;
    //time based parameters
    private long max_time;
    private long start_time;
    private long padding = 1000;

    private class OutOfTimeException extends RuntimeException
    {
        public OutOfTimeException(String kek)
        {super(kek);}
    }

    private class Ret
    {
        int val;
        boolean terminal;
        Action action;
        Action Best_act;
        public Ret( Action a,int v, boolean b)
        {val=v;terminal = b;action = a;}
        public void combine(Ret other, boolean maximizing)// this<other
        {
            if(maximizing==(this.val < other.val))
            {
                this.val = other.val;
                Best_act = other.action;
            }
            terminal &= other.terminal;
        }
        public void extract(Ret other, boolean maximizing)
        {
            if(maximizing==(this.val < other.val))
            {
                this.val = other.val;
                this.action = other.action;
            }
            terminal &= other.terminal;
        }
        public String toString()
        {
            String act = action == null? "null": action.toString();
            return act + val + terminal;
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

    public Action doSearch(State state, boolean is_white)
    {
        Ret anchor = null;
        int depth = 2;
        start_time = System.currentTimeMillis();
        try
        {
            while(true)
            {
                anchor = rec_search(state, null, depth, start_alpha, start_beta, true);
                if(anchor.terminal)//You've explored the howl search tree
                    break;
                depth++;
            }
        }
        catch (OutOfTimeException e)
        {
            System.out.println("out of time");
            //throw e;
        }
        catch (Exception e)
        {
            throw e;
        }
        if(anchor == null)
            return null;
        else
            return anchor.Best_act;
    }

    private Ret rec_search(State state, Action lastAction, int depth, int a, int b, boolean maximizing)
    {
        if(state==null)
            throw new NullPointerException("I can't have this");
        //System.out.println(env.isTerminalState(state));
        boolean terminal = env.isTerminalState(state);
        if (depth == 0 || terminal)
        {
            return new Ret(lastAction, heuristic.eval(state),terminal);
        }
        Ret value = new Ret(lastAction, maximizing?start_alpha:start_beta, true);
        Iterator<Action> actions = env.legalMoves(state, state.whites_turn);
        Action act;
        long now = System.currentTimeMillis();
        while(actions.hasNext())
        {
            if(max_time-padding<now-start_time)
            {
                throw new OutOfTimeException("out of time");
            }
                
            act = actions.next();
            value.combine(rec_search(env.getNextState(state,act), act ,depth-1, a, b, !maximizing), maximizing);
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
        Environment env = new Environment(4, 5);
        Search s = new AlphaBeta(env, new SimpleHeuristics(), 5000);
        long time = System.currentTimeMillis();
        Action ret = s.doSearch(env.getCurrentState(), true);
        if (ret == null)
        {
            System.out.println("fuck");
            return;
        }
        while (!env.isTerminalState(env.getCurrentState())){
            System.out.println(env.getCurrentState().toString());
            System.out.println("Best Action: "+ret);
            env.updateState(ret);
            ret = s.doSearch(env.getCurrentState(), true);
        }
        System.out.println(env.getCurrentState().toString());
        System.out.println(env.isTerminalState(env.getCurrentState()));
    }
}

