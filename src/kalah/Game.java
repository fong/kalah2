package kalah;

import static kalah.GameIO.boardSize;
import static kalah.GameIO.halfBoard;

/**
 * Game.java class controls game logic for Kalah
 * @author Eugene Fong (efon103)
 */
public class Game {
        SeedContainer[] board = new SeedContainer[boardSize];
        
        int nextTurn = 2;
        int currentTurn = 1;
        boolean endGame = false;
        int houseIter;
        
        public Game(){
            board[0] = new Store();
            board[halfBoard] = new Store();

            for (int i = 1; i < halfBoard; i++){
                board[i] = new House(i);
                board[i+halfBoard] = new House(i);
            }
        }

    public void next(int command){
        nextTurn = move(command);
        gameOver();
        currentTurn = nextTurn; 
    }
    
    public int get(int container){
        return board[container].numberOfSeeds;
    }
    
    public String draw(int container){
        return board[container].draw();
    }
    
    public void clearBoard(){
        for (int i = 1; i < halfBoard; i++){
            board[0].addNSeeds(board[i].removeAllSeeds());
            board[halfBoard].addNSeeds(board[i+halfBoard].removeAllSeeds());
        }
    }
        
    private int move(int command){
        int house;
        
        if (currentTurn == 2){
            house = command + halfBoard;
        } else {
            house = command;
        }
        
        if (board[house].numberOfSeeds == 0){
            return 0;
        }

        plantSeeds(house);
        return lastSeed();
    }
    
    private void plantSeeds(int house){
        houseIter = house;
        int numberToPlant = board[house].removeAllSeeds();
        
        while (numberToPlant > 1){
            houseIter++;
            
            switch (houseIter) {
                case 14:
                    houseIter = 0;
                    if (currentTurn == 2){
                        numberToPlant--;
                        board[halfBoard].addSeed();
                    }   break;
                case 7:
                    if (currentTurn == 1){
                        numberToPlant--;
                        board[0].addSeed();
                    }   break;
                default:
                    numberToPlant--;
                    board[houseIter].addSeed();
                    break;
            }
        }
    }
    
    private int lastSeed(){
        houseIter++;

        if (currentTurn == 1){
            if (houseIter == boardSize){
                houseIter = 1;
            }
            if (houseIter == halfBoard){
                board[0].addSeed();
                return 1;
            } else if ((houseIter < halfBoard) && (board[houseIter].numberOfSeeds == 0)){
                if (board[boardSize-houseIter].numberOfSeeds > 0){
                    board[0].addNSeeds(board[boardSize-houseIter].removeAllSeeds());
                    board[0].addSeed();
                } else {
                    board[houseIter].addSeed();
                }
                return 2;
            }else if ((houseIter > halfBoard) || (board[houseIter].numberOfSeeds > 0)){
                board[houseIter].addSeed();
                return 2;
            }  
        } else if (currentTurn == 2){
            
            if (houseIter == halfBoard){
                houseIter++;
            }
            if (houseIter == boardSize){
                board[halfBoard].addSeed();
                return 2;
            } else if ((houseIter > halfBoard) && (board[houseIter].numberOfSeeds == 0)){
                if (board[boardSize-houseIter].numberOfSeeds > 0){
                    board[halfBoard].addNSeeds(board[boardSize-houseIter].removeAllSeeds());
                    board[halfBoard].addSeed();
                } else {
                    board[houseIter].addSeed();
                }
                return 1;
            } else if ((houseIter < halfBoard) || (board[houseIter].numberOfSeeds > 0)){
                board[houseIter].addSeed();
                return 1;
            }
        } 
        return 0;
    }
    
    private int checkEmpty(){
        int player1Sum = 0, player2Sum = 0;
        int isEmpty;
                
        for (int i = 1; i < halfBoard; i++){
            player2Sum += board[i+halfBoard].numberOfSeeds;
            player1Sum += board[i].numberOfSeeds;
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
    
    private void gameOver(){
        if (nextTurn == checkEmpty()){
            endGame = true;
        } else endGame = nextTurn == 0;   
    }
}
