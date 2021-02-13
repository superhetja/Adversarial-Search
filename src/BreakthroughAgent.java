import java.util.Random;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import support_classes.*;

public class BreakthroughAgent implements Agent{

    private Random random = new Random();

    private String role; // the name of this agent's role (white or black)
    private int playclock; // this is how much time (in seconds) we have before nextAction needs to return a move
    private boolean myTurn; // whether it is this agent's turn or not
    private boolean color;
    private int width, height; // dimensions of the board
    private Environment env; //our game environment

    private Search searching = new MinMax(env, new Heuristic(){
        public void init(Environment e){};
        public int eval(State s)
        {
            return 1;
        }
    });

    /*
		init(String role, int playclock) is called once before you have to select the first action. Use it to initialize the agent. role is either "white" or "black" and playclock is the number of seconds after which nextAction must return.
	*/
    public void init(String role, int width, int height, int playclock){
        this.role = role;
        this.playclock = playclock;
        myTurn = !role.equals("white"); // myTurn is change before getting nextMove.
        color = role.equals("white");
        this.width = width;
        this.height = height;
        // TODO: add your own initialization code here
        this.env = new Environment(width, height);
    }

    // lastMove is null the first time nextAction gets called (in the initial state)
    // otherwise it contains the coordinates x1,y1,x2,y2 of the move that the last player did
    public String nextAction(int[] lastMove) {
        Action lastAction = new Action(lastMove);
        env.updateState(lastAction);
        return searching.doSearch(env.getCurrentState(), color).toString();

        /*if(lastMove != null) {
            int x1 = lastMove[0], y1 = lastMove[1], x2 = lastMove[2], y2 = lastMove[3];
            String roleOfLastPlayer;
            if (myTurn && role.equals("white") || !myTurn && role.equals("black")) {
                roleOfLastPlayer = "white";
            } else {
                roleOfLastPlayer = "black";
            }
            // TODO: 1. update your internal world model according to the action that was just executed
            env.updateState(new Action(lastMove), roleOfLastPlayer);
            System.out.println(roleOfLastPlayer + " moved from " + x1 + ", " + y1 + " to " + x2 + ", " + y2);
            
        }

        // update turn (above that line it myTurn is still for the previous state)
        myTurn = !myTurn;
        if(myTurn){
            // TODO: 2. run alpha-beta search to determine the best move

			// Here we just construct a random move (that will most likely not even be possible),
			// this needs to be replaced with the actual best move.
            int x1, y1, x2,y2;
            x1 = random.nextInt(width)+1;
			x2 = x1 + random.nextInt(3)-1;
			if (role.equals("white")) {
				y1 = random.nextInt(height-1);
				y2 = y1 + 1;
			} else {
				y1 = random.nextInt(height-1)+2;
				y2 = y1 - 1;
            }
            System.out.println("About to do move..");
            
            HashMap<Pawn,Pawn> moves = env.legalMoves(env.getCurrentState());
            System.out.println(moves);
            Iterator it = moves.entrySet().iterator();
            while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Pawn from = (Pawn)pair.getKey();
            Pawn to = (Pawn)pair.getValue();
                return "(move " + from.x + " " + from.y + " " + to.x + " " + to.y + ")";
            }
            System.out.println("Out side of the for loop and did not return any value");
            System.out.println(moves);
            return "(move " + 2 + " " + 2 + " " + 2 + " " + 3 + ")";
            
            //return "(move " + x1 + " " + y1 + " " + x2 + " " + y2 + ")";
            //return "(move " + x1 + " " + y1 + " " + x2 + " " + y2 + ")";
        } else {
            return "noop";
        }*/
    }

	// is called when the game is over or the match is aborted
	@Override
	public void cleanup() {
		// TODO: cleanup so that the agent is ready for the next match
	}
    
}
