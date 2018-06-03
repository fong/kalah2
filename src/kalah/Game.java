package kalah;

/**
 * Game.java class controls game logic for Kalah
 * @author Eugene Fong (efon103)
 */
public class Game {
        
        GameBoard board;
        public int nextTurn = 2;
        public int currentTurn = 1;
        public boolean endGame = false;
        int size;
        
        int houseIter;
        
        public Game(int size){
            board = new GameBoard(size);
        }

    public void next(int command){
        nextTurn = move(command);
        gameOver();
        currentTurn = nextTurn; 
    }
        
    private int move(int command){
        int house;
        
        if (currentTurn == 2){
            house = command + size/2;
        } else {
            house = command;
        }
        
        if (board.get(house) == 0){
            return 0;
        }

        plantSeeds(house);
        return lastSeed();
    }
    
    private void plantSeeds(int house){
        houseIter = house;
        int numberToPlant = board.removeAllSeeds(house);
        
        while (numberToPlant > 1){
            houseIter++;
            
            if (houseIter == size) {
                houseIter = 0;
                if (currentTurn == 2){
                    numberToPlant--;
                    board.addSeed(size/2);
                }
            }else if (houseIter == size/2){
                if (currentTurn == 1){
                    numberToPlant--;
                    board.addSeed(0);
                }
            } else {
                numberToPlant--;
                board.addSeed(houseIter);
            }
        }
    }
    
    private int lastSeed(){
        houseIter++;

        if (currentTurn == 1){
            if (houseIter == size){
                houseIter = 1;
            }
            if (houseIter == size/2){
                board.addSeed(0);
                return 1;
            } else if ((houseIter < size/2) && (board.numberOfSeeds(houseIter) == 0)){
                if (board.numberOfSeeds(size-houseIter) > 0){
                    board.addNSeeds(0, board.removeAllSeeds(size-houseIter));
                    board.addSeed(0);
                } else {
                    board.addSeed(houseIter);
                }
                return 2;
            }else if ((houseIter > size/2) || (board.numberOfSeeds(houseIter) > 0)){
                board.addSeed(houseIter);
                return 2;
            }  
        } else if (currentTurn == 2){
            
            if (houseIter == size/2){
                houseIter++;
            }
            if (houseIter == size){
                board.addSeed(size/2);
                return 2;
            } else if ((houseIter > size/2) && (board.numberOfSeeds(houseIter) == 0)){
                if (board.numberOfSeeds(size-houseIter) > 0){
                    board.addNSeeds(size/2, board.removeAllSeeds(size-houseIter));
                    board.addSeed(size/2);
                } else {
                    board.addSeed(houseIter);
                }
                return 1;
            } else if ((houseIter < size/2) || (board.numberOfSeeds(houseIter) > 0)){
                board.addSeed(houseIter);
                return 1;
            }
        } 
        return 0;
    }
    
    private void gameOver(){
        if (nextTurn == board.checkEmpty()){
            endGame = true;
        } else endGame = nextTurn == 0;   
    }
}
