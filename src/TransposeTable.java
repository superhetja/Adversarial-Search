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
        int key = state.blackPawns.hashCode();

        int value = state.whitePawns.hashCode();

        statesBucket.put(key, value);

        System.out.println("K/V stored as: " + key + ", " + value);
    }

    public boolean containsState(State state){
        // returns true/false if state is in collection
        int key = state.blackPawns.hashCode();
        int value = state.whitePawns.hashCode();
        if (statesBucket.get(key) == null) return false;  // key not found
        if (statesBucket.get(key) == value) return true;  // k/v pair matches
        
        // will have to account for collisions later
        System.out.println("Collision!");
        return false;  // key/value pair doesn't match. Collision?
    }

    /*
    Not needed anymore
    public static int makeHashIndex(int hash){
        //creates a hash bucket index [0..13]
        hash = hash % 13;
        return hash;
    }
    */


    public static void main(String[] args){
        
        State newState = new State(5,5);
        State otherState = new State(4,4);
        TransposeTable stateTable = new TransposeTable();
        stateTable.storeState(newState);
        System.out.println(stateTable.containsState(newState));  // must return true
        System.out.println(stateTable.containsState(otherState));  // must return false

    }
    
}
