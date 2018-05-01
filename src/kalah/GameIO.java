package kalah;

import com.qualitascorpus.testsupport.IO;

/**
 *
 * @author Eugene Fong (efon103)
 */
public class GameIO {
    public boolean running;
    
    static int boardSize = 14;
    static int halfBoard = boardSize/2;
                
    boolean lastTurn = false;

    String prompt;
    Game game;

    public GameIO(){
        game = new Game();
    }
    
    public void tick(IO io){
        int command;
        
        while(true) {
            this.draw(io); 
            prompt = "Player P" + game.currentTurn +
                     "'s turn - Specify house number or 'q' to quit: ";
            command = parseInput(io);

            if (command > 0){
                game.next(command);
            } else {
                io.println("Game over");
                this.draw(io);
                prompt = "";
                running = false;
                break;
            }
        } 
        
        if (game.endGame){
            game.clearBoard();
            io.println("\tplayer 1:" + game.get(0));
            io.println("\tplayer 2:" + game.get(halfBoard));
            if (game.get(0) > game.get(halfBoard)){
                io.println("Player 1 wins!");
            } else if (game.get(0) < game.get(halfBoard)){
                io.println("Player 2 wins!");
            } else {
                io.println("A tie!");
            }
            running = false;
        }
    }
    
    public void draw(IO io){
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6[" + game.draw(13) + "] | 5[" + game.draw(12) +
                    "] | 4[" + game.draw(11) + "] | 3[" + game.draw(10) +
                    "] | 2[" + game.draw(9) + "] | 1[" + game.draw(8) +
                    "] | " + game.draw(0) + " |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("| " + game.draw(7) + " | 1[" + game.draw(1) +
                    "] | 2[" + game.draw(2) +  "] | 3[" + game.draw(3) +
                    "] | 4[" + game.draw(4) + "] | 5[" + game.draw(5) +
                    "] | 6[" + game.draw(6) + "] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }
    
    private int parseInput(IO io){
        String input;
        int house;
        
        while (true) {
            if (!"".equals(prompt) && !game.endGame){
                input = io.readFromKeyboard(prompt);

                try {
                    house = Integer.parseInt(input);

                    if ((house < 0) || (house > 6)){
                        prompt = "Bad input: ";
                    } else if (game.nextTurn == 2) {
                        if (game.get(house+halfBoard) == 0){
                            io.println("House is empty. Move again.");
                            this.draw(io);
                        } else {
                            return house;
                        }
                    } else if (game.nextTurn == 1) {
                        if (game.get(house) == 0){
                            io.println("House is empty. Move again.");
                            this.draw(io);
                        } else {
                            return house;
                        }
                    } 
                } catch (NumberFormatException e){
                    if (!"q".equals(input)){
                    } else {
                        return 0;
                    }
                }
            } else return 0;
        }
    }
}