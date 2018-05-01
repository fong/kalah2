package kalah;

/**
 *
 * @author Eugene Fong (efon103)
 * 
 */
public class SeedContainer {
    int numberOfSeeds;
    
    public void addSeed(){
        numberOfSeeds++;
    }
    
    public void addNSeeds(int seeds){
    }
    
    public void removeSeed(){
        numberOfSeeds--;
    }

    public int removeAllSeeds(){
        return 0;
    }
    
    public String draw(){
        if (numberOfSeeds > 9){
            return Integer.toString(numberOfSeeds);
        } else {
            return " " + Integer.toString(numberOfSeeds);
        }
    }
}
