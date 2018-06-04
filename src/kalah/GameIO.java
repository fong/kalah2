package kalah;

import com.qualitascorpus.testsupport.IO;

/**
 * GameIO.java manages game tick and IO
 * @author Eugene Fong (efon103)
 */
public class GameIO implements IGameIO {
    public boolean running;
    public int size = 14; 
    boolean lastTurn = false;
    
    IO io;
    String prompt;
    IGame game;
    IPrinter printer;

    public GameIO(IO ioParam){
        io = ioParam;
        game = new Game(size);
        printer = new Printer(io);
        running = true;
    }
    
    @Override
    public void tick(){
        int command;
        
        while(true) {
            draw(); 
            prompt = "Player P" + game.currentTurn() +
                     "'s turn - Specify house number or 'q' to quit: ";
            command = keyboardInput();

            if (command > 0){
                game.next(command);
            } else {
                io.println("Game over");
                draw();
                running = false;
                break;
            }
        } 
        
        if (game.endGame()){
            game.board().clearBoard();
            printer.printEndGame(game);
            running = false;
        }
    }
    
    @Override
    public void draw(){
        printer.printGameBoard(game);
    }
    
    @Override
    public boolean running(){
        return running;
    }
    
    private int keyboardInput(){
        String input;
        int house;
        
        while (true) {
            if (!game.endGame()){
                input = io.readFromKeyboard(prompt);

                try {
                    house = Integer.parseInt(input);

                    if ((house < 0) || (house > size/2-1)){
                        prompt = "Invalid input: ";
                    } else if (game.nextTurn() == 2) {
                        if (game.board().get(house+size/2) == 0){
                            io.println("House is empty. Move again.");
                            draw();
                        } else {
                            return house;
                        }
                    } else if (game.nextTurn() == 1) {
                        if (game.board().get(house) == 0){
                            io.println("House is empty. Move again.");
                            draw();
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