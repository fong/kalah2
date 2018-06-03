package kalah;

import com.qualitascorpus.testsupport.IO;

/**
 * GameIO.java manages game tick and IO
 * @author Eugene Fong (efon103)
 */
public class GameIO {
    public boolean running;
    public int size = 14;
                
    boolean lastTurn = false;

    String prompt;
    Game game;

    public GameIO(){
        game = new Game(size);
    }
    
    public void tick(IO io){
        int command;
        
        while(true) {
            this.draw(io); 
            prompt = "Player P" + game.currentTurn +
                     "'s turn - Specify house number or 'q' to quit: ";
            command = keyboardInput(io);

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
            game.board.clearBoard();
            io.println("\tplayer 1:" + game.board.getPlayer(1));
            io.println("\tplayer 2:" + game.board.getPlayer(2));
            if (game.board.getPlayer(1) > game.board.getPlayer(2)){
                io.println("Player 1 wins!");
            } else if (game.board.getPlayer(1) < game.board.getPlayer(2)){
                io.println("Player 2 wins!");
            } else {
                io.println("A tie!");
            }
            running = false;
        }
    }
    
    public void draw(IO io){
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6[" + game.board.draw(13) + "] | 5[" + game.board.draw(12) +
                    "] | 4[" + game.board.draw(11) + "] | 3[" + game.board.draw(10) +
                    "] | 2[" + game.board.draw(9) + "] | 1[" + game.board.draw(8) +
                    "] | " + game.board.draw(0) + " |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("| " + game.board.draw(7) + " | 1[" + game.board.draw(1) +
                    "] | 2[" + game.board.draw(2) +  "] | 3[" + game.board.draw(3) +
                    "] | 4[" + game.board.draw(4) + "] | 5[" + game.board.draw(5) +
                    "] | 6[" + game.board.draw(6) + "] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }
    
    private int keyboardInput(IO io){
        String input;
        int house;
        
        while (true) {
            if (!"".equals(prompt) && !game.endGame){
                input = io.readFromKeyboard(prompt);

                try {
                    house = Integer.parseInt(input);

                    if ((house < 0) || (house > size/2-1)){
                        prompt = "Bad input: ";
                    } else if (game.nextTurn == 2) {
                        if (game.board.get(house+size/2) == 0){
                            io.println("House is empty. Move again.");
                            this.draw(io);
                        } else {
                            return house;
                        }
                    } else if (game.nextTurn == 1) {
                        if (game.board.get(house) == 0){
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