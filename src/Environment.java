import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import support_classes.*;
/**
 * Creates a board and places pawns down
 */
public class Environment {



    private State currentState;
    private int width, height;
    // I think it may be a good idea to store legalMoves since they are never that many and can save time of looping to find them.
    //private LinkedList<Pawn> legalMovesWhite, legalMovesBlack; 

    public Environment(int width, int height){
        this.width = width;
        this.height = height;
        this.currentState = new State(width, height);

    }
    public int getHeigth() {
        return this.height;
    }

    public State getCurrentState(){
        return this.currentState;
    }

    /*
        returns an iterator for all the legal actions in a state given the next teams moves.
        Iterator<Action> actions = Environment.legalMoves(state,color);
        while actions.hasNext()
        {
            Action action = actions.next();
            do something with the action
        }
    */
    public Iterator<Action> legalMoves(State state, boolean color){
        Iterator<Action> iterrer = new Iterator<Action>(){
            Iterator<Pawn> pawns = color?state.whitePawns.iterator():state.blackPawns.iterator();
            
            Iterator<Action> actions = null;

            public boolean hasNext()
            {
                return pawns.hasNext() || actions.hasNext();
            }

            public Action next()
            {
                System.out.println(actions);
                if (actions == null){
                    System.out.println("actions null");
                    actions = pawns.next().moves.iterator();}
                if (!actions.hasNext()){
                    System.out.println("actions don't have next");
                    actions = pawns.next().moves.iterator();
                }
                System.out.println(actions);
                return actions.next();
            }
        };
        return iterrer;
    }

    public void updateState(Action lastMove){
        System.out.println("Before update: " + this.currentState);
        this.currentState = getNextState(this.currentState, lastMove);
        System.out.println("After update: " + this.currentState);
    }


    /*
        return a copy of the given state 
    */
    public State getNextState (State state, Action lastMove) {
        return state;
        /*State new_state = (State)state.clone();
        boolean color = state.whiteMap.containsKey(lastMove.x1);
        if (color)
            color = color && state.whiteMap.get(lastMove.x1).containsKey(lastMove.y1);
        if (!color)//it's not white, let's check if it's black (just to be safe)
        {
            if (!state.blackMap.containsKey(lastMove.x1)){
                //throw Exception //was not a legal move.
            }
            if (!state.blackMap.get(lastMove.x1).containsKey(lastMove.y1)){
                //throw Exception //was not a legal move.
            }
        }

        new_state.whitePawns.remove(new Pawn(lastMove.x1, lastMove.y1));
        Pawn new_pawn = new Pawn(lastMove.x2, lastMove.y2, "white");
        new_state.whitePawns.add(new_pawn);
        if(new_state.blackPawns.contains(new_pawn)){
            new_state.blackPawns.remove(new_pawn);
        }*/
    }
    public static void main(String[] args){
        var env = new Environment(4,4);
        System.out.println(env.currentState);
        Iterator<Action> actions = env.legalMoves(env.currentState,true);
        while (actions.hasNext())
        {
            Action action = actions.next();
            System.out.println(action);
        }
    }
}
