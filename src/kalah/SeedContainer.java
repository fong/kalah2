package kalah;

/**
 * SeedContainer.java is the parent class for all containers that hold seeds
 * @author Eugene Fong (efon103)
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
