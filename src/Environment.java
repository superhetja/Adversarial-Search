import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;

import support_classes.*;

/**
 * Creates a board and places pawns down
 */
public class Environment {


    /**
     * Current state of the game
     */
    private State currentState;


    /**
     * Size the board.
     */
    private int width, height;


    /**
     * Init function
     * @param width
     * @param height
     */
    public Environment(int width, int height){
        this.width = width;
        this.height = height;
        this.currentState = new State(this.width, this.height);
    }



    /**
     * Access to the boards height.. 
     * TODO: Not sure where it is used
     * @return height of the board
     */
    public int getHeigth() {
        return this.height;
    }


    /**
     * Returns current state
     * @return current state
     */
    public State getCurrentState(){
        return this.currentState;
    }

    /**
     * Finds all legal moves of given state for given color.
     * @param   state   class State to search in
     * @param   color   boolean color to search for
     * @return  Iterator of Actions.
     */
    public Iterator<Action> legalMoves(State state, boolean color){
        Iterator<Action> iterrer = new Iterator<Action>(){
            Iterator<ArrayList<Action>> actList = color? state.whiteMap.values().iterator() : state.blackMap.values().iterator();
            Iterator<Action> actions = actList.next().iterator();
            public boolean hasNext()
            {
                while (!actions.hasNext() && actList.hasNext()) {
                    actions = actList.next().iterator();
                }
                return actions.hasNext();

            }

            public Action next() {
                return actions.next();
            }
        };
        return iterrer;
    }
    


    /**
     * Tells if it is a terminals state or not.
     * @param state
     * @return  
     */
    public boolean isTerminalState(State state) { 
        // if it is whites turn then black just did so we need to check wether that move but him to the end.
        Iterator<Pawn> pawns = state.whites_turn ? state.blackMap.keySet().iterator() : state.whiteMap.keySet().iterator();
        int edge = state.whites_turn? 0 : height;

        while(pawns.hasNext()){
            if(pawns.next().y == edge){
                return true;
            }
        }

        //check for tie
        // Tie if whos turn it is has nolegal moves

        Iterator<Action> moves = legalMoves(state, state.whites_turn);
        if (!moves.hasNext()){
            return true;
        }
        return false;
    }
    

    /**
     * updates the current state based on last Move
     * @param lastMove
     */
    public void updateState(Action lastMove){
        this.currentState = getNextState(this.currentState, lastMove);
    }

    public State getNextState(State state, Action lastMove) {
        State new_state = state.clone();
        new_state.whites_turn = !new_state.whites_turn;
        if (!lastMove.isForwardMove()){
            new_state.delete_pawn(new Pawn(lastMove.x2, lastMove.y2, !lastMove.isWhite()));
        }
        new_state.delete_pawn(new Pawn(lastMove.x1, lastMove.y1, lastMove.isWhite()));
        new_state.add_pawn(new Pawn(lastMove.x2, lastMove.y2, lastMove.isWhite()));
        return new_state;
    }


    /**
     * String representation of the current state.
     */
    public String toString() {
        return this.currentState.toString();    
    }
    public static void main(String[] args){
        /*
        long startTime = System.nanoTime();
        methodToTime();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        */

        var env = new Environment(4,4);
        System.out.println(env);
        
        env.updateState(new Action(2,2,1,3));

        System.out.println(env);
        
        //env.updateState(new Action(5,5,5,4));
        
        System.out.println(env);
        System.out.println(env.isTerminalState(env.getCurrentState()));

        /*

        System.out.println("Start timer!");
        long startTime = System.nanoTime();
        Boolean lm = env.isTerminalState(env.getCurrentState());
        long endTime = System.nanoTime();

*/
        //System.out.println(env.currentState);
        /*
        Iterator<Action> actions = env.legalMoves(env.currentState,true);
        while (actions.hasNext())
        {
            Action action = actions.next();
            System.out.println(action);
        }*/
        /*
        // TESTING FUNCTION getNextState(state, action);
        Action myAction = new Action(1,2,2,3); // should eat pawn
        env.updateState(myAction);
        myAction= new Action(1, 4, 2, 3);
        env.updateState(myAction);

        //System.out.println(nextState);
        */
    }
}
