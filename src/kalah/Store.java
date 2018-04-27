package kalah;

/**
 *
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
    
}
