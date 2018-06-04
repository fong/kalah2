package kalah;

/**
 *
 * @author Eugene Fong (efon103)
 */
public interface IGameBoard {
    public int get(int container);
    public int getPlayer(int player);
    public void clearBoard();
    public void addSeed(int container);
    public void addNSeeds(int container, int amount);
    public int removeAllSeeds(int container);
    public int numberOfSeeds(int container);
    public int checkEmpty();
    public String draw(int container);
}
