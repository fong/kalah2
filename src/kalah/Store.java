package kalah;

/**
 * Store.java is a child of SeedContainer and stores a player's private seed bank
 * @author Eugene Fong (efon103)
 */
public class Store extends SeedContainer {
    
    public Store(){
        this.numberOfSeeds = 0;
    }
    
    @Override
    public void addNSeeds(int seeds){
        this.numberOfSeeds += seeds;
    }
    
    @Override
    public int removeAllSeeds(){
        return 0;
    }
}
