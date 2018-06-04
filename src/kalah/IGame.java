package kalah;

/**
 *
 * @author Eugene Fong (efon103)
 */
public interface IGame {

    public void next(int command);
    public int move(int command);
    public int nextTurn();
    public int currentTurn();
    public boolean endGame();
    public GameBoard board();
    public void gameOver();
}
