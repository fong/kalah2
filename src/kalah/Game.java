package kalah;

/**
 * Game.java class controls game logic for Kalah
 * @author Eugene Fong (efon103)
 */
public class Game implements IGame{
    GameBoard board;

    int nextTurn = 2;
    int currentTurn = 1;
    boolean endGame = false;
    int houseIter;
    int size;

    public Game(int gameSize){
        size = gameSize;
        board = new GameBoard(size);
    }

    @Override
    public void next(int command){
        nextTurn = move(command);
        gameOver();
        currentTurn = nextTurn; 
    }
    
    @Override
    public int move(int command){
        int house;
        
        if (currentTurn == 2){
            house = command + size/2;
        } else {
            house = command;
        }
        
        if (board.numberOfSeeds(house) == 0){
            return 0;
        }

        plantSeeds(house);
        return lastSeed();
    }
    
    @Override
    public void gameOver(){
        if (nextTurn == board.checkEmpty()){
            endGame = true;
        } else endGame = nextTurn == 0;   
    }
    
    @Override
    public int currentTurn(){
        return currentTurn;
    }
    
    @Override
    public int nextTurn(){
        return nextTurn;
    }
    
    @Override
    public boolean endGame(){
        return endGame;
    }
    
    @Override
    public GameBoard board(){
        return board;
    }
    
    private void plantSeeds(int house){
        houseIter = house;
        int numberToPlant = board.removeAllSeeds(house);
        
        while (numberToPlant > 1){
            houseIter++;
            
            switch (houseIter) {
                case 14:
                    houseIter = 0;
                    if (currentTurn == 2){
                        numberToPlant--;
                        board.addSeed(size/2);
                    }   break;
                case 7:
                    if (currentTurn == 1){
                        numberToPlant--;
                        board.addSeed(0);
                    }   break;
                default:
                    numberToPlant--;
                    board.addSeed(houseIter);
                    break;
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
    
}