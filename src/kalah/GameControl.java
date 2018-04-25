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
public class GameControl {
    int turn = 1;
    int playerTurn = 1;
    public boolean running;
    SeedContainer[] board = new SeedContainer[14];

    public GameControl(){
            board[0] = new Store(1);
            board[7] = new Store(2);
            
            for (int i = 1; i < 7; i++){
                board[i] = new House(1,i);
                board[i+7] = new House(2,i);
            }
    }
    
    public void tick(IO io){
 
        while (running){
            String command = parseInput(io);
            if ("q".equals(command)){
                running = false;
                io.println("Game over");
                this.draw(io);
            } else {
                
            }
        }
    }
    
    private void draw(IO io){
            io.println("+----+-------+-------+-------+-------+-------+-------+----+");
            io.println("| P2 | 6[" + board[6].draw() +
                        "] | 5[" + board[5].draw() +
                        "] | 4[" + board[4].draw() +
                        "] | 3[" + board[3].draw() +
                        "] | 2[" + board[2].draw() +
                        "] | 1[" + board[1].draw() +
                        "] | " + board[0].draw() + " |");
            io.println("|    |-------+-------+-------+-------+-------+-------|    |");
            io.println("| " + board[7].draw() + " | 1[" + board[8].draw() +
                        "] | 2[" + board[9].draw() + 
                        "] | 3[" + board[10].draw() +
                        "] | 4[" + board[11].draw() +
                        "] | 5[" + board[12].draw() +
                        "] | 6[" + board[13].draw() + "] | P1 |");
            
            io.println("+----+-------+-------+-------+-------+-------+-------+----+");
            if (running){
                io.println("Player " + turn + "'s turn - Specify house number or 'q' to quit: ");
            }
    }
    
    private String parseInput(IO io){
        String input = io.readFromKeyboard("Input: ");
        boolean validInput = false;
        
        while (!validInput){
            try {
                int house = Integer.parseInt(input);
                if ((house < 0) || (house > 6)){
                    io.println("Invalid Input");
                    io.println("Please enter a number between 1 and 6: ");
                    validInput = false;
                } else {
                    validInput = true;
                }
            } catch (NumberFormatException e){
                if (!"q".equals(input)){
                    io.println("To quit game, press 'q': ");
                    validInput = false;
                } else {
                    validInput = true;
                }
            }
            if (!validInput){
                input = io.readFromKeyboard("Input: ");
            }
        }
        return input;
    }
        
    private boolean moveStones(int house){
        int container;
        int otherPlayer;
        
        if (playerTurn == 2){
            container = house + 7;
            otherPlayer = 1;
        } else {
            container = house;
            otherPlayer = 2;
        }
        
        if (board[container].numberOfSeeds == 0){
            return false;
        }
        
        while(board[container].numberOfSeeds > 0){
            
        }
            
        return true;
    }
    
    private boolean plantSeeds(int house){
        
        
        
        return true;
    }
    
    private void capture(int player, int house){
        
    }
}
