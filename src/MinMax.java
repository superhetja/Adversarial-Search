import support_classes.*;
import java.util.Queue;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.lang.RuntimeException;

public class MinMax implements Search{
    private class Node
    {
        Action action;
        int value;
        Node parent;
        State state;
        public Node(Action action,Node parent,int value, State state)
        {
            this.action = action;
            this.parent = parent;
            this.value = value;
            this.state = state;
        }
        
        public Node expand(Action action)
        {   
            State s = env.getNextState(this.state, action);
            return new Node(action, this, her.eval(s), s);
        }

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
    
    public Action doSearch(State state, boolean is_white)
    {
        
        Iterator<Action> actions = env.legalMoves(state, state.whites_turn);
        Action bestAction= null;
        int bestValue= state.whites_turn?-Integer.MAX_VALUE:Integer.MAX_VALUE; 
        int tmpValue;
        Action action;
        State tmpState;
        while(actions.hasNext()){
            action= actions.next();
            tmpState = env.getNextState(state, action);
            tmpValue = minmax(new Node(action, null, 0, tmpState), 1);
            if (state.whites_turn) {
                if(bestValue<tmpValue)
                {
                    bestAction = action;
                    bestValue = tmpValue;
                }
            }
            else { // is blacks turn
                if(bestValue>tmpValue)
                {
                    bestAction = action;
                    bestValue = tmpValue;
                }
            }
        }

        return bestAction;
    }
    private int minmax(Node node, int depth) {
        int value, tmpval;
        Node child;
        Iterator<Action> moves = env.legalMoves(node.state, node.state.whites_turn);
        if ((env.isTerminalState(node.state)|(depth>5))){ //Don't go too deep
            return her.eval(node.state);
        }
        if ((depth%2==0)&& node.state.whites_turn){ // maximizing
            value = -Integer.MAX_VALUE;
            while(moves.hasNext()){
                child= node.expand(moves.next());
                tmpval = minmax(child, depth+1);
                value = tmpval>value?tmpval:value;
                
            }
        } else { // minimizing
            value = Integer.MAX_VALUE;
            while(moves.hasNext()){
                child= node.expand(moves.next());
                tmpval = minmax(child, depth+1);
                value = tmpval<value?tmpval:value;
            }
        }
        return value;
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
        Action ret = s.doSearch(env.getCurrentState(), true);
        System.out.println("Best Action: "+ret);

    }
}
