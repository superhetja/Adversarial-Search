import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;

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
                if(actions == null)
                    actions = pawns.next().moves.iterator();
                while (!actions.hasNext()&&pawns.hasNext())
                {
                    actions = pawns.next().moves.iterator();
                }
                return actions.hasNext();
            }

            public Action next()
            {
                return actions.next();
            }
        };
        return iterrer;
    }
    public boolean isTerminalState(State state) {   
        Iterator<Pawn> white_pawns = state.whitePawns.iterator();
        Iterator<Pawn> black_pawns = state.blackPawns.iterator();
        Pawn tmpPawn;
        boolean tie=true; // Tie if whos turn it is has nolegal moves
        
        while(white_pawns.hasNext()){
            tmpPawn = white_pawns.next();
            if (tmpPawn.y==getHeigth()){
                return true; // White won
            }
            if (state.whites_turn){ // If it's your turn and you have leagal move its not a tie
                tie = tmpPawn.moves.size()!= 0? false: tie;
            }
        }
        while(black_pawns.hasNext()){
            tmpPawn = black_pawns.next();
            if (tmpPawn.y==1){
                return true; // black won
            }
            if (!state.whites_turn){ // If it's your turn and you have leagal move it's not a tie
                tie = tmpPawn.moves.size()!= 0? false: tie;
            }
        }
        return tie;
    }
    //isTerminal has options: local function, Environment function, state function.

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
        State newState = state;
        Pawn currentPawn = state.getPawn(lastMove.x1, lastMove.y1); // getting current pawn
        boolean color = currentPawn.is_white;  // lastMove type Action
        if(lastMove.x1!=lastMove.x2)//killer move
        {
            newState.delete_pawn(lastMove.x2, lastMove.y2);
        }
        if (color){ // Is the player white?
            // check move legality, for white: current move >= lastmove (but xy1 != xy2 because that's not a move at all)
            newState.delete_pawn(currentPawn);
            // if move is forward, invalid if any pawn in the way.
            if ((currentPawn.x == lastMove.x2)&&(currentPawn.y + 1 == lastMove.y2)){
                Pawn otherPawn = state.getPawn(lastMove.x2, lastMove.y2);

                if (otherPawn == null){
                    // No pawns at target location, move is acceptable
                    System.out.println("move is acceptable");
                    // we update the state to move currentPawn forward
                    currentPawn.moveForward();
                    currentPawn.updateLeagalMoves(state);
                    newState.add_pawn(currentPawn);

                    // update the white pawns affected by this action
                    List<Integer> list_x=Arrays.asList(0);
                    List<Integer> list_y=Arrays.asList(-1);
                    Pawn cpawn;
                    for (int i =0; i<1;i++){
                        if (state.checkWhite(lastMove.x1+list_x.get(i), lastMove.y1+list_y.get(i))) {
                            cpawn= newState.getPawn(lastMove.x1+list_x.get(i), lastMove.y1+list_y.get(i));
                            newState.delete_pawn(cpawn);
                            cpawn.updateLeagalMoves(newState);
                            newState.add_pawn(cpawn);
                        }
                    }
                    
                    // Update the black pawns affected by this action
                    list_x=Arrays.asList(-1,0,1,-1,1);
                    list_y=Arrays.asList(1,1,1,0,0);
                    for (int i =0; i<5;i++){
                        if (state.checkBlack(lastMove.x2+list_x.get(i), lastMove.y2+list_y.get(i))) {
                            cpawn= newState.getPawn(lastMove.x2+list_x.get(i), lastMove.y2+list_y.get(i));
                            newState.delete_pawn(cpawn);
                            cpawn.updateLeagalMoves(newState);
                            newState.add_pawn(cpawn);
                        }
                    }
                    

                }
                // out here, otherPawn/whitePawn != null, move is invalid.
            }
            // else if move is diagonal RIGHT, require a BLACK pawn
            else if ((currentPawn.x + 1 == lastMove.x2) && (currentPawn.y + 1 == lastMove.y2)){
                System.out.println("diagonal RIGHT");
                Pawn otherPawn = (Pawn)state.blackMap.get(lastMove.x2).get(lastMove.y2);
                System.out.println("Is there a pawn? " + otherPawn);
                // we update the state to move currentPawn to the right
                currentPawn.takeRight();
                currentPawn.updateLeagalMoves(state);
                newState.add_pawn(currentPawn);

                // update the white pawns affected by this action
                List<Integer> list_x=Arrays.asList(0,2);
                List<Integer> list_y=Arrays.asList(-1,0);
                Pawn cpawn;
                for (int i =0; i<2;i++){
                    if (state.checkWhite(lastMove.x1+list_x.get(i), lastMove.y1+list_y.get(i))) {
                        cpawn= newState.getPawn(lastMove.x1+list_x.get(i), lastMove.y1+list_y.get(i));
                        newState.delete_pawn(cpawn);
                        cpawn.updateLeagalMoves(newState);
                        newState.add_pawn(cpawn);
                    }
                }
                
                // Update the black pawns affected by this action
                list_x=Arrays.asList(-1,1,-1,-2);
                list_y=Arrays.asList(1,1,0,0);
                for (int i =0; i<4;i++){
                    if (state.checkBlack(lastMove.x2+list_x.get(i), lastMove.y2+list_y.get(i))) {
                        cpawn= newState.getPawn(lastMove.x2+list_x.get(i), lastMove.y2+list_y.get(i));
                        newState.delete_pawn(cpawn);
                        cpawn.updateLeagalMoves(newState);
                        newState.add_pawn(cpawn);
                    }
                }

            }
            // else if move is diagonal LEFT, require a BLACK pawn
            else if ((currentPawn.x == lastMove.x2 - 1) && (currentPawn.y == lastMove.y2 -1)){
                System.out.println("diagonal RIGHT");
                Pawn otherPawn = (Pawn)state.blackMap.get(lastMove.x2).get(lastMove.y2);
                System.out.println("Is there a pawn? " + otherPawn);

                 // we update the state to move currentPawn to the left
                 currentPawn.takeLeft();
                 currentPawn.updateLeagalMoves(state);
                 newState.add_pawn(currentPawn);
 
                 // update the white pawns affected by this action
                 List<Integer> list_x=Arrays.asList(0,-2);
                 List<Integer> list_y=Arrays.asList(-1,0);
                 Pawn cpawn;
                 for (int i =0; i<2;i++){
                     if (state.checkWhite(lastMove.x1+list_x.get(i), lastMove.y1+list_y.get(i))) {
                        cpawn= newState.getPawn(lastMove.x1+list_x.get(i), lastMove.y1+list_y.get(i));
                        newState.delete_pawn(cpawn);
                        cpawn.updateLeagalMoves(newState);
                        newState.add_pawn(cpawn);
                     }
                 }
                 
                 // Update the black pawns affected by this action
                 list_x=Arrays.asList(-1,1,1,2);
                 list_y=Arrays.asList(1,1,0,0);
                 for (int i =0; i<4;i++){
                     if (state.checkBlack(lastMove.x2+list_x.get(i), lastMove.y2+list_y.get(i))) {
                        cpawn= newState.getPawn(lastMove.x2+list_x.get(i), lastMove.y2+list_y.get(i));
                        newState.delete_pawn(cpawn);
                        cpawn.updateLeagalMoves(newState);
                        newState.add_pawn(cpawn);
                     }
                 }
            }

            
        } else { // player is black
            // Delete the old paw
            newState.delete_pawn(currentPawn);
            // if move is forward, invalid if any pawn in the way.
            if ((currentPawn.x == lastMove.x2)&&(currentPawn.y - 1 == lastMove.y2)){
                Pawn otherPawn = state.getPawn(lastMove.x2, lastMove.y2);

                if (otherPawn == null){
                    // No pawns at target location, move is acceptable
                    System.out.println("move is acceptable");
                    // we update the state to move currentPawn forward
                    currentPawn.moveForward();
                    currentPawn.updateLeagalMoves(state);
                    newState.add_pawn(currentPawn);

                    // update the black pawns affected by this action
                    List<Integer> list_x=Arrays.asList(0);
                    List<Integer> list_y=Arrays.asList(1);
                    Pawn cpawn;
                    for (int i =0; i<1;i++){
                        if (state.checkBlack(lastMove.x1+list_x.get(i), lastMove.y1+list_y.get(i))) {
                            cpawn= newState.getPawn(lastMove.x1+list_x.get(i), lastMove.y1+list_y.get(i));
                            newState.delete_pawn(cpawn);
                            cpawn.updateLeagalMoves(newState);
                            newState.add_pawn(cpawn);
                        }
                    }
                    
                    // Update the white pawns affected by this action
                    list_x=Arrays.asList(-1,0,1,-1,1);
                    list_y=Arrays.asList(-1,-1,-1,0,0);
                    for (int i =0; i<5;i++){
                        if (state.checkWhite(lastMove.x2+list_x.get(i), lastMove.y2+list_y.get(i))) {
                            cpawn= newState.getPawn(lastMove.x2+list_x.get(i), lastMove.y2+list_y.get(i));
                            newState.delete_pawn(cpawn);
                            cpawn.updateLeagalMoves(newState);
                            newState.add_pawn(cpawn);
                        }
                    }
                    

                }
                // out here, otherPawn/whitePawn != null, move is invalid.
            }
            // else if move is diagonal RIGHT, require a BLACK pawn
            else if ((currentPawn.x + 1 == lastMove.x2) && (currentPawn.y - 1 == lastMove.y2)){
                System.out.println("diagonal RIGHT");
                Pawn otherPawn = (Pawn)state.blackMap.get(lastMove.x2).get(lastMove.y2);
                System.out.println("Is there a pawn? " + otherPawn);
                // we update the state to move currentPawn to the right
                currentPawn.takeRight();
                currentPawn.updateLeagalMoves(state);
                newState.add_pawn(currentPawn);

                // update the black pawns affected by this action
                List<Integer> list_x=Arrays.asList(0,2);
                List<Integer> list_y=Arrays.asList(1,0);
                Pawn cpawn;
                for (int i =0; i<2;i++){
                    if (state.checkBlack(lastMove.x1+list_x.get(i), lastMove.y1+list_y.get(i))) {
                        cpawn= newState.getPawn(lastMove.x1+list_x.get(i), lastMove.y1+list_y.get(i));
                        newState.delete_pawn(cpawn);
                        cpawn.updateLeagalMoves(newState);
                        newState.add_pawn(cpawn);
                    }
                }
                
                // Update the white pawns affected by this action
                list_x=Arrays.asList(-1,1,-1,-2);
                list_y=Arrays.asList(-1,-1,0,0);
                for (int i =0; i<4;i++){
                    if (state.checkWhite(lastMove.x2+list_x.get(i), lastMove.y2+list_y.get(i))) {
                        cpawn= newState.getPawn(lastMove.x2+list_x.get(i), lastMove.y2+list_y.get(i));
                        newState.delete_pawn(cpawn);
                        cpawn.updateLeagalMoves(newState);
                        newState.add_pawn(cpawn);
                    }
                }

            }
            // else if move is diagonal LEFT, require a BLACK pawn
            else if ((currentPawn.x == lastMove.x2 - 1) && (currentPawn.y == lastMove.y2 -1)){
                System.out.println("diagonal RIGHT");
                Pawn otherPawn = (Pawn)state.blackMap.get(lastMove.x2).get(lastMove.y2);
                System.out.println("Is there a pawn? " + otherPawn);

                 // we update the state to move currentPawn to the left
                 currentPawn.takeLeft();
                 currentPawn.updateLeagalMoves(state);
                 newState.add_pawn(currentPawn);
 
                 // update the black pawns affected by this action
                 List<Integer> list_x=Arrays.asList(0,-2);
                 List<Integer> list_y=Arrays.asList(1,0);
                 Pawn cpawn;
                 for (int i =0; i<2;i++){
                     if (state.checkBlack(lastMove.x1+list_x.get(i), lastMove.y1+list_y.get(i))) {
                        cpawn= newState.getPawn(lastMove.x1+list_x.get(i), lastMove.y1+list_y.get(i));
                        newState.delete_pawn(cpawn);
                        cpawn.updateLeagalMoves(newState);
                        newState.add_pawn(cpawn);
                     }
                 }
                 
                 // Update the white pawns affected by this action
                 list_x=Arrays.asList(-1,1,1,2);
                 list_y=Arrays.asList(-1,-1,0,0);
                 for (int i =0; i<4;i++){
                     if (state.checkWhite(lastMove.x2+list_x.get(i), lastMove.y2+list_y.get(i))) {
                        cpawn= newState.getPawn(lastMove.x2+list_x.get(i), lastMove.y2+list_y.get(i));
                        newState.delete_pawn(cpawn);
                        cpawn.updateLeagalMoves(newState);
                        newState.add_pawn(cpawn);
                     }
                 }
            }


        }  
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
        /*Action myAction = new Action(1,2,2,3); // should eat pawn
        env.updateState(myAction);
        myAction= new Action(1, 4, 2, 3);
        env.updateState(myAction);*/
        Iterator<Action> act = env.legalMoves(env.getCurrentState(), true);
        while(act.hasNext())
        {
            System.out.println(act.next());
        }

        //System.out.println(nextState);

    }
}
