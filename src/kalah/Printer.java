package kalah;
import com.qualitascorpus.testsupport.IO;

/**
 *
 * @author Eugene Fong (efon103)
 */
public class Printer implements IPrinter{
    
    IO io;
    
    public Printer(IO ioParam){
        io = ioParam;
    }
    
    @Override
    public void printGameBoard(IGame game){
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6[" + game.board().draw(13) + "] | 5[" + game.board().draw(12) +
                    "] | 4[" + game.board().draw(11) + "] | 3[" + game.board().draw(10) +
                    "] | 2[" + game.board().draw(9) + "] | 1[" + game.board().draw(8) +
                    "] | " + game.board().draw(0) + " |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("| " + game.board().draw(7) + " | 1[" + game.board().draw(1) +
                    "] | 2[" + game.board().draw(2) +  "] | 3[" + game.board().draw(3) +
                    "] | 4[" + game.board().draw(4) + "] | 5[" + game.board().draw(5) +
                    "] | 6[" + game.board().draw(6) + "] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }
    
    @Override
    public void printEndGame(IGame game){
        io.println("\tplayer 1:" + game.board().getPlayer(1));
        io.println("\tplayer 2:" + game.board().getPlayer(2));
        if (game.board().getPlayer(1) > game.board().getPlayer(2)){
            io.println("Player 1 wins!");
        } else if (game.board().getPlayer(1) < game.board().getPlayer(2)){
            io.println("Player 2 wins!");
        } else {
            io.println("A tie!");
        }
    }
}
