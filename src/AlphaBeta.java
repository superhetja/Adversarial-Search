import support_classes.*;
import java.util.Queue;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.lang.RuntimeException;

public class AlphaBeta implements Search{
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
    long time_padding = 1000, start_time;
    int max_time;

    public AlphaBeta(Environment env, Heuristic heuristic, int max_time)
    {
        this.env = env;
        this.her = heuristic;
        her.init(env);
        this.max_time = max_time;
    }
    
    public Action doSearch(State state, boolean is_white)
    {
        start_time = System.currentTimeMillis();
        Node root = new Node(null, state);
        int depth = 1;
        while(true){
            try{
                alphaBeta(root, depth, -Integer.MAX_VALUE, Integer.MAX_VALUE);
            } catch(RuntimeException e) {
                break;
            }
            depth++;
        }
        return root.bestAction;
        
    }
    private int alphaBeta(Node node, int depth, int alpha, int beta) {
        int value, tmpval;
        Node child;
        Action action;
        Iterator<Action> moves = env.legalMoves(node.state, node.state.whites_turn);
        if (max_time-1<(System.currentTimeMillis()-start_time)/ 1000F) {
            throw new RuntimeException("Out of time");
        }
        if ((env.isTerminalState(node.state)|(depth==0))){ //Don't go too deep
            return her.eval(node.state);
        }
        if (node.state.whites_turn){ // maximizing
            value = -Integer.MAX_VALUE;
            while(moves.hasNext()){
                action=moves.next();
                child= node.expand(action);
                tmpval = alphaBeta(child, depth-1, alpha, beta);

                if ( tmpval>alpha){
                    alpha = tmpval;
                    node.UpdateBestAction(action);
                }
                if (alpha>=beta) {
                    break;
                }
                
            }
        } else { // minimizing
            value = Integer.MAX_VALUE;
            while(moves.hasNext()){
                action=moves.next();
                child= node.expand(action);
                tmpval = alphaBeta(child, depth-1, alpha, beta);
                if ( tmpval<beta){
                    beta = tmpval;
                    node.UpdateBestAction(action);
                }
                if (alpha>=beta) {
                    break;
                }
            }
        }
        return value;
    }

    public static void main(String[] args){
        Environment env = new Environment(3, 5);
        Search s = new AlphaBeta(env, new SimpleHeuristics(), 5);
        long time = System.currentTimeMillis();
        Action ret = s.doSearch(env.getCurrentState(), true);
        System.out.println((time-System.currentTimeMillis())/1000F);
        while (!env.isTerminalState(env.getCurrentState())){
            System.out.println(env.getCurrentState().toString());
            System.out.println("Best Action: "+ret);
            env.updateState(ret);
            ret = s.doSearch(env.getCurrentState(), true);
        }

    }
}
