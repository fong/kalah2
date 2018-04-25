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
    int player;
    int numberOfSeeds;
    
    public void addSeed(){
        numberOfSeeds++;
    }
    
    public void removeSeed(){
        numberOfSeeds--;
    }
    
    public String draw(){
        if (numberOfSeeds > 10){
            return Integer.toString(numberOfSeeds);
        } else {
            return " " + Integer.toString(numberOfSeeds);
        }
    }
}
