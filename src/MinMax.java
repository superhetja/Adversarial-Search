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
        Action bestAction;
        Node parent;
        State state;
        public Node(Node parent, State state)
        {
            this.bestAction = null;
            this.parent = parent;
            this.state = state;
        }
        public void UpdateBestAction(Action a){
            this.bestAction= a;
        }
        
        public Node expand(Action action)
        {   
            State s = env.getNextState(this.state, action);
            return new Node(this, s);
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
        Action bestAction= null;
        int bestValue= state.whites_turn?-Integer.MAX_VALUE:Integer.MAX_VALUE; 
        int tmpValue;
        Action action;
        State tmpState;
        Node root = new Node(null, state);
    	minmax(root, 5);
        return root.bestAction;
    }
    private int minmax(Node node, int depth) {
        int value, tmpval;
        Node child;
        Action action;
        Iterator<Action> moves = env.legalMoves(node.state, node.state.whites_turn);
        if ((env.isTerminalState(node.state)|(depth==0))){ //Don't go too deep
            return her.eval(node.state);
        }
        if ((depth%2==0)&& node.state.whites_turn){ // maximizing
            value = -Integer.MAX_VALUE;
            while(moves.hasNext()){
                action=moves.next();
                child= node.expand(action);
                tmpval = minmax(child, depth-1);
                if ( tmpval>value){
                    value = tmpval;
                    node.UpdateBestAction(action);
                }
                
            }
        } else { // minimizing
            value = Integer.MAX_VALUE;
            while(moves.hasNext()){
                action=moves.next();
                child= node.expand(action);
                tmpval = minmax(child, depth-1);
                if ( tmpval<value){
                    value = tmpval;
                    node.UpdateBestAction(action);
                }
            }
        }
        return value;
    }

    public static void main(String[] args){
        Environment env = new Environment(4, 4);
        Search s = new MinMax(env, new SimpleHeuristics(), 50000);
        Action ret = s.doSearch(env.getCurrentState(), true);
        System.out.println("Best Action: "+ret);
        env.updateState(ret);
        ret = s.doSearch(env.getCurrentState(), true);
        System.out.println("Best Action: "+ret);
        env.updateState(ret);
        ret = s.doSearch(env.getCurrentState(), true);
        System.out.println("Best Action: "+ret);

    }
}
