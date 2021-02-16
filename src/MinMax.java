import support_classes.*;
import java.util.Queue;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.lang.RuntimeException;

public class MinMax implements Search{
    private class Node
    {
        boolean is_terminal;
        boolean maxing;
        Action action;
        int value;
        Node parent;
        State state;
        boolean color;
        public Node(Action action,Node parent,int value, boolean is_terminal, State state,boolean color)
        {
            this.action = action;
            this.parent = parent;
            this.value = value;
            this.is_terminal = is_terminal;
            this.state = state;
            this.color = color;
        }
        public Iterator<Node> expand = new Iterator<Node>(){
            Iterator<Action> actions = env.legalMoves(state, color);
            public boolean hasNext()
            {
                boolean check = actions.hasNext();
                if(!check)
                    this.state = null;//save space
                return check;
            }
            public Node next()
            {
                Action act = actions.next();
                State nxt = env.getNextState(state, act);
                return new Node(act, this, her.eval(nxt), env.isTerminalState(nxt), nxt,!color);
            }
        };
    }
    Environment env;
    Heuristic her;
    Pruning pruning;
    long starting;
    Date clock = new Date();
    long time_padding = 1000;
    int max_time;
    Queue<MinMax.Node> frontier = new LinkedList<MinMax.Node>();
    public MinMax(Environment env, Heuristic heuristic, int max_time)
    {
        this.env = env;
        this.her = heuristic;
        her.init(env);
        this.max_time = max_time;
    }
    
    public Action doSearch(State state)
    {

        return new Action(0,0,0,0);
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
        Search s = new MinMax(env, new SimpleHeuristics(), 50000);
        Action ret = s.doSearch(env.getCurrentState(),true);
        System.out.println("this thing: "+ret);

    }
}
