/**
 * Creates a board and places pawns down
 */
public class Environment {



    private State currentState;
    private int width, height;

    public Environment(int width, int height){
        this.width = width;
        this.height = height;
        this.currentState = new State(width, height);

    }

    
}
