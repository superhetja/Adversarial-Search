public interface Pruning {
    public void init(Environment env);
    public boolean cut_off();
    public Pruning passing();
    public void backing(int ret);
}
