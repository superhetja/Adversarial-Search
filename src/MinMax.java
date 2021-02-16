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
        /*
        boolean is_terminal;
        boolean maxing;
        Action action;
        int value;
        Node parent;
        State state;
        boolean color;
        int depth_searched;
        public Node(Action action,Node parent,int value, boolean is_terminal, State state, int depth)
        {
            this.action = action;
            this.parent = parent;
            this.value = value;
            this.is_terminal = is_terminal;
            this.state = state;
            this.color = color;
            this.depth_searched = depth;
        }
        private Iterator<Node> expand = new Iterator<Node>(){
            Iterator<Action> actions = env.legalMoves(state, color);
            public boolean hasNext()
            {
                if (is_terminal)
                    return false;
                boolean check = actions.hasNext();
                //if(!check)
                //    value = ;
                return check;
            }
            public Node next()
            {
                Action act = actions.next();
                State nxt = env.getNextState(state, act);
                return new Node(act, null, her.eval(nxt), env.isTerminalState(nxt), nxt,depth_searched+1);
            }
        };

       public Node expand(Action action)
       {
            if(!expand.hasNext())
                return null;
            Node ret = expand.next();
            ret.parent = this;
            return ret;
       }

       public void Propigate()
       {
           if (parent != null)
           {
                if(parent.depth_searched<this.depth_searched)
                {
                    parent.depth_searched = this.depth_searched;
                    parent.value = this.value;
                }
                else
                {
                    parent.value = (this.value<parent.value)^parent.maxing?this.value:parent.value;
                }
           }
       }*/
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
        
        Iterator<Action> actions = env.legalMoves(state, state.whites_turn);
        Action bestAction= null;
        int bestValue=0, tmpValue;
        Action action;
        State tmpState;
        while(actions.hasNext()){
            action= actions.next();
            tmpState = env.getNextState(state, action);
            tmpValue = minmax(new Node(action, null, 0, tmpState), 1);
            if(bestValue<tmpValue)
            {
                bestAction = action;
                bestValue = tmpValue;
            }
        }

        return bestAction;
        // HashSet<Node> first_moves = new HashSet<Node>();
        // Iterator<Action> actions = env.legalMoves(state, state.whites_turn);
        // Action act;
        // Node curr;
        // State nxt;
        // while(actions.hasNext())
        // {
        //     act = actions.next();
        //     nxt = env.getNextState(state, act);
        //     curr = new Node(act,null,0, env.isTerminalState(nxt),nxt,1);
        //     first_moves.add(curr);
        //     frontier.add(curr);
        // }
        // //do searches using the frontier

        // Action bestAction = null;
        // int bestValue=0;
        // Iterator<Node> iter = first_moves.iterator();
        // while(iter.hasNext())
        // {
        //     curr = iter.next();
        //     if(bestAction==null)
        //     {
        //         bestAction = curr.action;
        //         bestValue = curr.value;
        //     }
        //     else if(bestValue<curr.value)
        //     {
                
        //         bestAction = curr.action;
        //         bestValue = curr.value;
        //     }
        // }
        // return bestAction;
    }
    private int minmax(Node node, int depth) {
        int value, tmpval;
        Node child;
        Iterator<Action> moves = env.legalMoves(node.state, node.state.whites_turn);
        if ((depth==0)|(env.isTerminalState(node.state)|(depth>10))){ //Don't go too deep
            return her.eval(node.state);
        }
        if (depth%2==0){ // maximizing
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
        Action ret = s.doSearch(env.getCurrentState());
        System.out.println("this thing: "+ret);

    }
}
