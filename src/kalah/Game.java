/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kalah;

import com.qualitascorpus.testsupport.IO;

/**
 *
 * @author tofutaco
 */
public class Game {
    boolean lastTurn = false;
    int nextTurn = 2;
    int currentTurn = 1;
    int housePosition;
    boolean victory = false;
    public boolean running;
    boolean quit = false;
    boolean endGame = false;
    String prompt = "Player P1's turn - Specify house number or 'q' to quit: ";
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
            
                command = parseInput(io);

                if (command > 0){
                    nextTurn = move(command);
                    
                    prompt = "Player P" + nextTurn + "'s turn - Specify house number or 'q' to quit: ";
                    currentTurn = nextTurn;
                    
                    if (nextTurn == 0){
                        //this.draw(io);
                        endGame = true;
                        break;
                    } else {
                        gameOver();
                    }

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
            this.draw(io);
            io.println("Game over");
            this.draw(io);
            clearBoard();
            victory = true;
            io.println("\tplayer 1:" + board[0].draw());
            io.println("\tplayer 2:" + board[7].draw());
            if (board[0].numberOfSeeds > board[7].numberOfSeeds){
                io.println("Player 1 wins!");
            } else if (board[0].numberOfSeeds < board[7].numberOfSeeds){
                io.println("Player 2 wins!");
            } else {
                io.println("Tie!");
            }
            running = false;
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
        boolean validInput = false;
        
        while (!validInput) {
            
            if (prompt != ""){
                input = io.readFromKeyboard(prompt);

                try {
                    house = Integer.parseInt(input);

                    if ((house < 0) || (house > 6)){
                        validInput = false;
                        prompt = "Bad input: ";
                    } else if (nextTurn == 2) {
                        if (board[house + 7].numberOfSeeds == 0){
                            io.println("House is empty. Move again.");
                            this.draw(io);
                            validInput = false;
                        } else {
                            return house;
                        }
                    } else if (nextTurn == 1) {
                        if (board[house].numberOfSeeds == 0){
                            io.println("House is empty. Move again.");
                            this.draw(io);
                            validInput = false;
                        } else {
                            return house;
                        }
                    } 
                } catch (NumberFormatException e){
                    if (!"q".equals(input)){
                        validInput = false;
                    } else {
                        validInput = true;
                        return 0;
                    }
                }
            }
        }
        return 0;
    }
        
    private int move(int command){
        int house;
        
        if (currentTurn == 2){
            house = command + 7;
        } else {
            house = command;
        }
        
        if (board[house].numberOfSeeds == 0){
            return 0;
        }

        plantSeeds(house);
        int turn = lastSeed(house);
        
        if (endGame){
            return 0;
        } else {
            return turn;
        }
    }
    
    private void plantSeeds(int house){
        housePosition = house;
        int numberToPlant = board[house].removeAllSeeds();
        
        while (numberToPlant > 1){
            housePosition++;
            //System.out.println("Plant House Position: " + housePosition);
            
            switch (housePosition) {
                case 14:
                    housePosition = 0;
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
                    board[housePosition].addSeed();
                    break;
            }
        }
    }
    
    private int lastSeed(int house){
        housePosition++;
        //System.out.println("Last House Position: " + housePosition);

        if (currentTurn == 1){
            
            if (housePosition == 14){
                housePosition = 1;
            }
           
            if (housePosition == 7){
                board[0].addSeed();
                return 1;
            }
            
            if ((housePosition < 7) && (board[housePosition].numberOfSeeds == 0)){
                if (board[14-housePosition].numberOfSeeds > 0){
                    board[0].addNSeeds(board[14-housePosition].removeAllSeeds());
                    board[0].addSeed();
                } else {
                    board[housePosition].addSeed();
                }
                return 2;
            }
            
            if ((housePosition > 7) || (board[housePosition].numberOfSeeds > 0)){
                board[housePosition].addSeed();
                return 2;
            }

        } else if (currentTurn == 2){
            
            if (housePosition == 7){
                housePosition++;
            }
            
            if (housePosition == 14){
                board[7].addSeed();
                return 2;
            }
            
            if ((housePosition > 7) && (board[housePosition].numberOfSeeds == 0)){
                if (board[14-housePosition].numberOfSeeds > 0){
                    board[7].addNSeeds(board[14-housePosition].removeAllSeeds());
                    board[7].addSeed();
                } else {
                    board[housePosition].addSeed();
                }
                return 1;
            }
            
            if ((housePosition < 7) || (board[housePosition].numberOfSeeds > 0)){
                board[housePosition].addSeed();
                return 1;
            }
        } 
        return 0;
    }
    
    private void gameOver(){        
        if (currentTurn == checkEmpty()){
            if ((currentTurn != nextTurn)){
                //next player completes turn, then game over
                System.out.println("Current != Next");
                endGame = false;
            } else if (nextTurn == checkEmpty()) {
                System.out.println("Game Over");
                endGame = true;
            } else {
                System.out.print("current turn empty not game over");
                endGame = false;
            }
        }
    }
    
    private int checkEmpty(){
        int player1Sum = 0, player2Sum = 0;
        int isEmpty;
                
        for (int i = 1; i < 7; i++){
            player2Sum += board[i].numberOfSeeds;
            player1Sum += board[i+7].numberOfSeeds;
        }
        
        if (player2Sum == 0){
            isEmpty = 2;
        } else if (player1Sum == 0){
            isEmpty = 1;
        } else {
            isEmpty = 0;
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

