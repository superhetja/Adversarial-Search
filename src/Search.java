import support_classes.*;

public interface Search {
    //public void init(Environment env, Heuristic heuristic, Pruning pruning);
    public Action doSearch(State state);
/*    Environment env;
    String color;

    public Search(Environment env, String color) {
        this.state = state;

    }

    public String doSearch(){
        HashSet<Pawn> white, black;
        
        white = state.whitePawns;
        black = state.blackPawns;

        for(Pawn p : white.iterator()){
            new_pawn = p.clone();
            new_pawn.moveForward();
            if (!white.contains(new_pawn) && !black.contains(new_pawn)) {
                return "(move " + p.x + " " + p.y + " " + new_pawn.x + " " + new_pawn.y + ")";
            }
            System.out.println("SOME PAWN IN THE WAY!! "+ p + " "+new_pawn );
        }



    }

*/
}