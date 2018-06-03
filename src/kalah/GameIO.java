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
    Printer printer;

    public GameIO(IO io){
        game = new Game(size);
        printer = new Printer(io);
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
                running = false;
                break;
            }
        } 
        
        if (game.endGame){
            game.board.clearBoard();
            printer.printEndGame(game);
            running = false;
        }
    }
    
    public void draw(IO io){
        printer.printGameBoard(game);
    }
    
    private int keyboardInput(IO io){
        String input;
        int house;
        
        while (true) {
            if (!game.endGame){
                input = io.readFromKeyboard(prompt);

                try {
                    house = Integer.parseInt(input);

                    if ((house < 0) || (house > size/2-1)){
                        prompt = "Invalid input: ";
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