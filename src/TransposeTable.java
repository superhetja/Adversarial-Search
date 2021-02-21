import support_classes.*;
import java.util.*;
import java.util.HashMap;

public class TransposeTable {

    Map<Integer, Integer> statesBucket;// Hash array, contains map with hash key values

    public TransposeTable(){
        statesBucket = new HashMap<Integer, Integer>();
    }

    public void storeState(State state){
        // Hashes two halves of a state and stores it as
        // (k,v) in HashMap bucket.
        int key = state.blackMap.keySet().hashCode();
        int value = state.whiteMap.keySet().hashCode();

        statesBucket.put(key, value);

        System.out.println("K/V stored as: " + key + ", " + value);
    }

    public boolean containsState(State state){
        // returns true/false if state is in collection
        int key = state.blackMap.keySet().hashCode();
        int value = state.whiteMap.keySet().hashCode();
        if (statesBucket.get(key) == null) return false;  // key not found
        if (statesBucket.get(key) == value) return true;  // k/v pair matches
  
        // will have to account for collisions later
        System.out.println("Collision!");
        return false;  // key found, but value does not match. Collision?
    }

    public static void main(String[] args){

        State newState = new State(5,5);
        State copyState = new State(5,5);
        TransposeTable stateTable = new TransposeTable();
        //State copyState = newState.clone();
        stateTable.storeState(copyState);
        //stateTable.storeState(newState);
        System.out.println("Contains state: " + stateTable.containsState(newState));  // must return true
        //System.out.println(stateTable.containsState(copyState));  // must return false
        // I'm just making this comment to step on Siggi's lawn.

    }
    
}
