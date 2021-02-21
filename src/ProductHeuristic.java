import support_classes.*;
import java.util.Iterator;

public class ProductHeuristic implements Heuristic {
    boolean color;
    Environment env;
    public ProductHeuristic(boolean color)
    {
        this.color = color;
    }
    public void init(Environment env)
    {
        this.env = env;
    }
    public int eval(State state)
    {
        int w_b = 1;
        int b_b = env.getHeigth();
        Pawn tempPawn;
        Iterator<Pawn> kek = state.whiteMap.keySet().iterator();
        while(kek.hasNext())
        {
            tempPawn = kek.next();
            if(w_b<tempPawn.y)
                w_b = tempPawn.y;
        }
        kek = state.blackMap.keySet().iterator();
        while(kek.hasNext())
        {
            tempPawn = kek.next();
            if(b_b>tempPawn.y)
                b_b = tempPawn.y;
        }
        if(w_b==env.getHeigth())
            return color?100:-100;
        if(b_b==1)
            return color?-100:100;
        int ret = b_b-w_b+state.whiteMap.size()-state.blackMap.size();
        return color?ret:-ret;
    }
}
