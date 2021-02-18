import support_classes.*;

import java.security.KeyStore.TrustedCertificateEntry;
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
    

    public static void main(String[] args){
        EnvironmentTests tests = new EnvironmentTests(4,5);
        Environment env = tests.env;

        // PLAN - ARE CORRECT LEGAL MOVES IN AVAILABLE ACTIONS?
        
        // can a pawn successfully move forward?
        System.out.println("Forward test: Pawn at (3, 2) --> (3, 3)");
        env.updateState(new Action(3,2,3,3)); // no pawn ahead
        System.out.println(env.getCurrentState().toString());
        // get white pawn available values
        var testforwardWhiteActions = new ArrayList<Action>();
        testforwardWhiteActions.add(new Action(2,2,2,3));
        // make hard coded pawn hashmap with correct actions

        var white_actions = env.getCurrentState().whiteMap.values();//containsValue(testforwardWhiteActions);
        System.out.println("\nVALUES: " + white_actions + testforwardWhiteActions);


        /*
        // can a pawn successfully move DIAGONAL LEFT
        System.out.println("\nDiagonal LEFT test: Pawn at (3, 3) --> (2, 4)");
        env.updateState(new Action(3,3,2,4));
        System.out.println(env.getCurrentState().toString());

        // can a while pawn successfully move DIAGONAL RIGHT
        System.out.println("\nDiagonal RIGHT test: Pawn at (2, 4) --> (3, 5)");
        env.updateState(new Action(2,4,3,5));
        System.out.println(env.getCurrentState().toString());
        */
    }
}
