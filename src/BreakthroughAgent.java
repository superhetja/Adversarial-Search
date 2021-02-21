import support_classes.*;

public class BreakthroughAgent implements Agent{

    /**
     * Time in secound we have for each move.
     */
    private int playclock;

    /**
     * The color we are playing
     */
    private boolean color; 

    /**
     * Used to determine if we have to search for action
     * or send back "noob" so the other player can play.
     */
    private boolean myTurn;

    /**
     * The game's environment
     */
    private Environment env; 


    // TODO: not really sure
    /**
     * Searching something....
     * 
     */
    private Search searching;

    /**
     * Called once before the first action.
     * Initializes the agent.
     * 
     * @param   role        the role it has to play, "white" or "black"
     * @param   width       width of the board playing
     * @param   height      height of the board playing
     * @param   playclock   number of seconds after which nextAction must return
     */
    public void init(String role, int width, int height, int playclock){
        this.playclock = playclock;
        color = role.equals("white");
        myTurn = !color; // changed in nextAction before each search 
        this.env = new Environment(width, height);
        searching = new AlphaBeta(env, new ProductHeuristic(color),this.playclock);
    }

    
    /**
     * Called every time there has to be a new move
     * @param   lastMove    int list containing last move (x1 y1 x2 y2)
     */
    public String nextAction(int[] lastMove) {

        /* the first time the agent is called last move is null */
        if (lastMove != null) {
            Action lastAction = new Action(lastMove);
            System.out.println("he did: "+lastAction);
            env.updateState(lastAction);
        }
        /* determine if it is our move or theirs */
        myTurn = !myTurn;
        if ( !myTurn ) { return "noob"; }
        String ret = searching.doSearch(env.getCurrentState(), color).toString(); // Returns a Action ("(move " + x1 + " " + y1 + " " + x2 + " " + y2 + ")")
        System.out.println("we are doing: "+ret);
        return ret;
    }

    
    /**
     * Called when the game is over or match is aborted.
     */
	@Override
	public void cleanup() {
		// TODO: cleanup so that the agent is ready for the next match

	}
    
}
