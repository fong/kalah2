package kalah;

/**
 *
 * @author Eugene Fong (efon103)
 */
public class GameBoard {
    SeedContainer[] board;
    
    int boardSize;
    int halfBoard;
    
    public GameBoard(int size){
        boardSize = size;
        halfBoard = size/2;
        board = new SeedContainer[boardSize];
        
        board[0] = new Store();
        board[halfBoard] = new Store();

        for (int i = 1; i < halfBoard; i++){
            board[i] = new House(i);
            board[i+halfBoard] = new House(i);
        }
    }
    
    public int get(int container){
        return board[container].numberOfSeeds;
    }
    
    public int getPlayer(int player){
        if (player == 1){
            return board[0].numberOfSeeds;
        }
        else{
            return board[halfBoard].numberOfSeeds;
        }
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
    
    public void addSeed(int container){
        board[container].addSeed();
    }
    
    public int removeAllSeeds(int container){
        return board[container].removeAllSeeds();
    }
    
    public int numberOfSeeds(int container){
        return board[container].numberOfSeeds();
    }
    
    public void addNSeeds(int container, int amount){
        board[container].addNSeeds(amount);
    }
    
    public int checkEmpty(){
        int player1Sum = 0, player2Sum = 0;
        int isEmpty;
                
        for (int i = 1; i < boardSize/2; i++){
            player2Sum += board[i+boardSize/2].numberOfSeeds();
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
    
}
