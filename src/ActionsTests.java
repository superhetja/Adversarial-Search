import support_classes.*;

import java.util.*;

public class EnvironmentTests {
    Environment env;
    int x;
    int y;

    public EnvironmentTests(int x,int y){
        this.x = x;
        this.y = y;
        this.env = new Environment(x,y);
    }

    public void reset(){
        this.env = new Environment(x,y);
    }
    
    public void compareMoves(int x,int y,Boolean color, ArrayList<Action> actions){
        var stateMoves = new ArrayList<Action>();
        var keyExists = false;
        // compares parameter actions with actions stored in state
        if (color){
            keyExists = env.getCurrentState().whiteMap.containsKey(new Pawn(x,y,true));
            stateMoves = env.getCurrentState().whiteMap.get(new Pawn(x,y,true));
        } else{
            keyExists = env.getCurrentState().blackMap.containsKey(new Pawn(x,y,false));
            stateMoves = env.getCurrentState().blackMap.get(new Pawn(x,y,false));
        }
        if ((actions.size()==0)&&(stateMoves==null)){
            if (keyExists) System.out.println("Pawn (" + x + "," + y +") passes test.");
        }else{
        var boolContents = stateMoves.containsAll(actions);
        var boolLength = (actions.size() == stateMoves.size());
        if (boolContents&&boolLength) System.out.println("Pawn (" + x + "," + y +") passes test.");
        else{
            System.out.println("Pawn (" + x + "," + y +") does NOT pass test.");
            System.out.println("Expected: " + actions);
            System.out.println("Actual value stored: " + stateMoves);
        }
        }
    }

    public void testWhiteTake(){
        System.out.println("\nWhite take test: Move white pawn at (2, 2) --> (3, 3)");
        System.out.println("White Pawns");

        var moves12 = new ArrayList<Action>();
        moves12.add(new Action(1,2,1,3));
        this.compareMoves(1, 2, true, moves12);

        var moves33 = new ArrayList<Action>();
        moves33.add(new Action(3,3,4,4));
        this.compareMoves(3, 3, true, moves33);

        var moves21 = new ArrayList<Action>();
        moves21.add(new Action(2,1,2,2));
        this.compareMoves(2, 1, true, moves21);

        var moves31 = new ArrayList<Action>();
        moves31.add(new Action(3,1,3,2));
        this.compareMoves(3, 1, true, moves31);

        var moves42 = new ArrayList<Action>();
        moves42.add(new Action(4,2,4,3));
        this.compareMoves(4, 2, true, moves42);

        var moves41 = new ArrayList<Action>();
        this.compareMoves(4, 1, true, moves41);

        var moves11 = new ArrayList<Action>();
        this.compareMoves(1, 1, true, moves11);

        System.out.println("\nBlack Pawns");

        var moves14 = new ArrayList<Action>();
        moves14.add(new Action(1,4,1,3));
        this.compareMoves(1, 4, false, moves14);

        var moves25 = new ArrayList<Action>();
        moves25.add(new Action(2,5,2,4));
        this.compareMoves(2, 5, false, moves25);

        var moves44 = new ArrayList<Action>();
        moves44.add(new Action(4,4,4,3));
        moves44.add(new Action(4,4,3,3));
        this.compareMoves(4, 4, false, moves44);

        var moves35 = new ArrayList<Action>();
        this.compareMoves(3, 5, false, moves35);

        var moves45 = new ArrayList<Action>();
        this.compareMoves(4, 5, false, moves45);

        var moves34 = new ArrayList<Action>();
        this.compareMoves(3, 4, false, moves34);
    }

    public void testBlackTake(){
        System.out.println("\nBlack take test: Move black pawn at (2, 4) --> (3, 3)");
        System.out.println("White Pawns");

        var moves12 = new ArrayList<Action>();
        moves12.add(new Action(1,2,1,3));
        this.compareMoves(1, 2, true, moves12);

        var moves22 = new ArrayList<Action>();
        moves22.add(new Action(2,2,2,3));
        moves22.add(new Action(2,2,3,3));
        this.compareMoves(2, 2, true, moves22);

        var moves31 = new ArrayList<Action>();
        moves31.add(new Action(3,1,3,2));
        this.compareMoves(3, 1, true, moves31);

        var moves42 = new ArrayList<Action>();
        moves42.add(new Action(4,2,4,3));
        moves42.add(new Action(4,2,3,3));
        this.compareMoves(4, 2, true, moves42);


        System.out.println("\nBlack Pawns");

        var moves25 = new ArrayList<Action>();
        moves25.add(new Action(2,5,2,4));
        this.compareMoves(2, 5, false, moves25);

        var moves33 = new ArrayList<Action>();
        moves33.add(new Action(3,3,4,2));
        moves33.add(new Action(3,3,3,2));
        moves33.add(new Action(3,3,2,2));
        this.compareMoves(3, 3, false, moves33);

        var moves44 = new ArrayList<Action>();
        moves44.add(new Action(4,4,4,3));
        this.compareMoves(4, 4, false, moves44);
    }

    public void testForward(){
        
        System.out.println("Forward test: move white pawn at (3, 2) --> (3, 3)\n");
        System.out.println("White Pawns");

        var moves22 = new ArrayList<Action>();
        moves22.add(new Action(2,2,2,3));
        this.compareMoves(2, 2, true, moves22);

        var moves33 = new ArrayList<Action>();
        moves33.add(new Action(3,3,4,4));
        moves33.add(new Action(3,3,2,4));
        this.compareMoves(3, 3, true, moves33);

        var moves12 = new ArrayList<Action>();
        moves12.add(new Action(1,2,1,3));
        this.compareMoves(1, 2, true, moves12);
        
        var moves42 = new ArrayList<Action>();
        moves42.add(new Action(4,2,4,3));
        this.compareMoves(4, 2, true, moves42);

        System.out.println("\nBlack Pawns");

        var moves14 = new ArrayList<Action>();
        moves14.add(new Action(1,4,1,3));
        this.compareMoves(1, 4, false, moves14);

        var moves24 = new ArrayList<Action>();
        moves24.add(new Action(2,4,2,3));
        moves24.add(new Action(2,4,3,3));
        this.compareMoves(2, 4, false, moves24);

        var moves43 = new ArrayList<Action>();
        this.compareMoves(4, 3, false, moves43);
        
        var moves44 = new ArrayList<Action>();
        moves44.add(new Action(4,4,4,3));
        moves44.add(new Action(4,4,3,3));
        this.compareMoves(4, 4, false, moves44);

    }

    public static void main(String[] args){
        EnvironmentTests tests = new EnvironmentTests(4,5);
        Environment env = tests.env;

        // PLAN - ARE CORRECT ACTIONS UPDATING IN AFFECTED PAWNS?

        // white pawn forward
        env.updateState(new Action(3,2,3,3));
        tests.testForward();
        System.out.println(env.getCurrentState().toString());


        // black pawn takes white
        env.updateState(new Action(2,4,3,3));
        tests.testBlackTake();
        System.out.println(env.getCurrentState().toString());

        // white pawn takes black
        env.updateState(new Action(2,2,3,3));
        tests.testWhiteTake();
        System.out.println(env.getCurrentState().toString());
        
    }
}
