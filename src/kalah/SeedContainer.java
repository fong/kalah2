/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
