package kalah;

import com.qualitascorpus.testsupport.IO;

/**
 *
 * @author Eugene Fong (efon103)
 */
public class Game {
    public boolean running;
    
    int nextTurn = 2;
    int currentTurn = 1;
    int houseSelected;
    boolean lastTurn = false;
    boolean quit = false;
    boolean endGame = false;
    String prompt;
    SeedContainer[] board = new SeedContainer[14];

    public Game(){
        board[0] = new Store();
        board[7] = new Store();

        for (int i = 1; i < 7; i++){
            board[i] = new House(i);
            board[i+7] = new House(i);
        }
    }
    
    public void tick(IO io){
        int command;
        boolean quit = false;
        while (!endGame || !quit) {
            this.draw(io); 
            prompt = "Player P" + currentTurn + "'s turn - Specify house number or 'q' to quit: ";
            command = parseInput(io);

            if (command > 0){
                nextTurn = move(command);
                gameOver();
                currentTurn = nextTurn; 
            } else {
                io.println("Game over");
                this.draw(io);
                quit = true;
                prompt = "";
                running = false;
                break;
            }
        } 
        
        if (endGame){
            clearBoard();
            io.println("\tplayer 1:" + board[0].numberOfSeeds);
            io.println("\tplayer 2:" + board[7].numberOfSeeds);
            if (board[0].numberOfSeeds > board[7].numberOfSeeds){
                io.println("Player 1 wins!");
            } else if (board[0].numberOfSeeds < board[7].numberOfSeeds){
                io.println("Player 2 wins!");
            } else {
                io.println("A tie!");
            }
        }
    }
    
    public void draw(IO io){
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6[" + board[13].draw() +
                    "] | 5[" + board[12].draw() +
                    "] | 4[" + board[11].draw() +
                    "] | 3[" + board[10].draw() +
                    "] | 2[" + board[9].draw() +
                    "] | 1[" + board[8].draw() +
                    "] | " + board[0].draw() + " |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("| " + board[7].draw() + " | 1[" + board[1].draw() +
                    "] | 2[" + board[2].draw() + 
                    "] | 3[" + board[3].draw() +
                    "] | 4[" + board[4].draw() +
                    "] | 5[" + board[5].draw() +
                    "] | 6[" + board[6].draw() + "] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }
    
    private int parseInput(IO io){
        String input;
        int house;
        
        while (!"".equals(prompt) && !endGame) {
            input = io.readFromKeyboard(prompt);

            try {
                house = Integer.parseInt(input);

                if ((house < 0) || (house > 6)){
                    prompt = "Input has to be between 1 and 6. Try again: ";
                } else if (nextTurn == 2) {
                    if (board[house + 7].numberOfSeeds == 0){
                        io.println("House is empty. Move again.");
                        this.draw(io);
                    } else {
                        return house;
                    }
                } else if (nextTurn == 1) {
                    if (board[house].numberOfSeeds == 0){
                        io.println("House is empty. Move again.");
                        this.draw(io);
                    } else {
                        return house;
                    }
                } 
            } catch (NumberFormatException e){
                if ("q".equals(input)){
                    return 0;
                }
            }
        }
        return 0;
    }
        
    private int move(int command){
        int house = (currentTurn == 2) ? command + 7 : command;
        
        if (board[house].numberOfSeeds == 0) return 0;
        
        plantSeeds(house);
        return lastSeed();
    }
    
    private void plantSeeds(int house){
        houseSelected = house;
        int numberToPlant = board[house].removeAllSeeds();
        
        while (numberToPlant > 1){
            houseSelected++;
            switch (houseSelected) {
                case 14:
                    houseSelected = 0;
                    if (currentTurn == 2){
                        numberToPlant--;
                        board[7].addSeed();
                    }   break;
                case 7:
                    if (currentTurn == 1){
                        numberToPlant--;
                        board[0].addSeed();
                    }   break;
                default:
                    numberToPlant--;
                    board[houseSelected].addSeed();
                    break;
            }
        }
    }
    
    private int lastSeed(){
        houseSelected++;

        if (currentTurn == 1){   
            if (houseSelected == 14) houseSelected = 1;
            
            if (houseSelected == 7){
                board[0].addSeed();
                return 1;
            } else if ((houseSelected < 7) && (board[houseSelected].numberOfSeeds == 0)){
                if (board[14-houseSelected].numberOfSeeds > 0){
                    board[0].addNSeeds(board[14-houseSelected].removeAllSeeds());
                    board[0].addSeed();
                } else {
                    board[houseSelected].addSeed();
                }
                return 2;
            } else if ((houseSelected > 7) || (board[houseSelected].numberOfSeeds > 0)){
                board[houseSelected].addSeed();
                return 2;
            }
            
        } else if (currentTurn == 2){
            if (houseSelected == 7) houseSelected++;
            
            if (houseSelected == 14){
                board[7].addSeed();
                return 2;
            }else if ((houseSelected > 7) && (board[houseSelected].numberOfSeeds == 0)){
                if (board[14-houseSelected].numberOfSeeds > 0){
                    board[7].addNSeeds(board[14-houseSelected].removeAllSeeds());
                    board[7].addSeed();
                } else {
                    board[houseSelected].addSeed();
                }
                return 1;
            }else if ((houseSelected < 7) || (board[houseSelected].numberOfSeeds > 0)){
                board[houseSelected].addSeed();
                return 1;
            }
        } 
        return 0;
    }
    
    private void gameOver(){
        if (nextTurn == checkEmpty()){
            this.endGame = true;
        } else if (nextTurn == 0) {
            this.endGame = true;
        } else {
            this.endGame = false;
        }   
    }
    
    private int checkEmpty(){
        int player1Sum = 0, player2Sum = 0;
        int isEmpty = 0;
                
        for (int i = 1; i < 7; i++){
            player2Sum += board[i+7].numberOfSeeds;
            player1Sum += board[i].numberOfSeeds;
        }
        
        if (player2Sum == 0){
            isEmpty = 2;
        } else if (player1Sum == 0){
            isEmpty = 1;
        } 
        return isEmpty;
    }
    
    private void clearBoard(){
        for (int i = 1; i < 7; i++){
            board[0].addNSeeds(board[i].removeAllSeeds());
            board[7].addNSeeds(board[i+7].removeAllSeeds());
        }
    }
}
