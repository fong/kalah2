package kalah;

/**
 * SeedContainer.java is the parent class for all containers that hold seeds
 * @author Eugene Fong (efon103)
 */
public abstract class SeedContainer {
    public int numberOfSeeds;
    
    public void addSeed(){
        numberOfSeeds++;
    }
    
    public abstract void addNSeeds(int seeds);
   
    public abstract int removeAllSeeds();
    
    public int numberOfSeeds(){
        return numberOfSeeds;
    }
    
    public String draw(){
        if (numberOfSeeds > 9){
            return Integer.toString(numberOfSeeds);
        } else {
            return " " + Integer.toString(numberOfSeeds);
        }
    }
}
