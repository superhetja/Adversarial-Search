//import jdk.vm.ci.common.InitTimer;
import support_classes.*;
public interface Heuristic {
    public void init(Environment env);

    public int eval(State state);
}