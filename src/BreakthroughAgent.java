import java.util.Random;
public class BreakthroughAgent implements Agent{

    private Random random = new Random();

    private String role;
    private int playclock;
    private boolean myTurn;
    private int width, height;

    public void init(String role, int width, int height, int playclock){
        this.role = role;
        this.playclock = playclock;
        myTurn = role.equals("white");
        this.width = width;
        this.height = height;
    }

    public String nextAction(int[] lastMove) {
        if(lastMove != null) {
            int x1 = lastMove[0], y1 = lastMove[1], x2 = lastMove[2], y2 = lastMove[3];
            String roleOfLastPlayer;
            if (myTurn && role.equals("white") || !myTurn && role.equals("black")) {
                roleOfLastPlayer = "white";
            } else {
                roleOfLastPlayer = "black";
            }
            System.out.println(roleOfLastPlayer + " moved from " + x1 + ", " + y1 + " to " + x2 + ", " + y2);
        }


        myTurn = !myTurn;
        if(myTurn){
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
            return "(move " + x1 + " " + y1 + " " + x2 + " " + y2 + ")";
        } else {
            return "noop";
        }
    }

    public void cleanup() {

    }
    
}
