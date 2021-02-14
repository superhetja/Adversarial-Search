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
        this.currentState = new State(this.width, this.height);

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
        

        // executes action on clone state (gives what if)
        // given state and an Action x1, y1, x2, y2
        /*  3 things need to happen:
            *Any pawn in (x2,y2) needs to be removed from all storage (it got eaten)
            *whatever pawn that was in (x1,y1) must be moved to (x2,y2)
            *every pawn in affected coordinates must have their actions updated
        */
        State newState = (State)state.clone();
        Pawn p = state.getPawn(lastMove.x1, lastMove.y1); // getting current pawn
        boolean color = p.is_white;  // lastMove type Action
        if(lastMove.x1!=lastMove.x2)//killer move
        {
            newState.delete_pawn(lastMove.x2, lastMove.y2);
        }
        if (color){ // Is the player white?
            // check move legality, for white: current move >= lastmove (but xy1 != xy2 because that's not a move at all)
            
            // if move is forward, invalid if any pawn in the way.
            if ((currentPawn.x == lastMove.x2)&&(currentPawn.y + 1 == lastMove.y2)){
                Pawn otherPawn = (Pawn)state.blackMap.get(lastMove.x2).get(lastMove.y2);
                Pawn whitePawn = (Pawn)state.whiteMap.get(lastMove.x2).get(lastMove.y2);
                if (otherPawn == null || whitePawn == null){
                    // No pawns at target location, move is acceptable
                    System.out.println("move is acceptable");
                    // we update the state to replace currentPawn with newPawn in state.whiteMap
                }
                // out here, otherPawn/whitePawn != null, move is invalid.
            }
            // else if move is diagonal RIGHT, require a BLACK pawn
            else if ((currentPawn.x + 1 == lastMove.x2) && (currentPawn.y + 1 == lastMove.y2)){
                System.out.println("diagonal RIGHT");
                Pawn otherPawn = (Pawn)state.blackMap.get(lastMove.x2).get(lastMove.y2);
                System.out.println("Is there a pawn? " + otherPawn);
            }
            // else if move is diagonal LEFT, require a BLACK pawn
            else if ((currentPawn.x == lastMove.x2 - 1) && (currentPawn.y == lastMove.y2 -1)){
                System.out.println("diagonal RIGHT");
                Pawn otherPawn = (Pawn)state.blackMap.get(lastMove.x2).get(lastMove.y2);
                System.out.println("Is there a pawn? " + otherPawn);
            }

            
    }       

        /*
       


        if (color) // white player
            color = color && state.whiteMap.get(lastMove.x1).containsKey(lastMove.y1); // nested dictionaries, riiiiight
            system.out.println(color);
            // we want to know if the pawn in this square belongs to the current player.

        else if (!color) //it's not white, let's check if it's black (just to be safe)
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
        return newState;
    }
    public static void main(String[] args){
        var env = new Environment(4,4);
        //System.out.println(env.currentState);
        /*
        Iterator<Action> actions = env.legalMoves(env.currentState,true);
        while (actions.hasNext())
        {
            Action action = actions.next();
            System.out.println(action);
        }*/
        // TESTING FUNCTION getNextState(state, action);
        Action myAction = new Action(1,2,1,3); // should eat pawn
        State nextState = env.getNextState(env.currentState, myAction);

        //System.out.println(nextState);

    }
}
