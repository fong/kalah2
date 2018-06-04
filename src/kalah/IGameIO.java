package kalah;

/**
 *
 * @author Eugene Fong (efon103)
 */
public interface IGameIO {
    public void tick();
    public void draw();
    public boolean running();
    public int input();
}
