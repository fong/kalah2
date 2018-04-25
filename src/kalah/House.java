/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kalah;

/**
 *
 * @author Eugene Fong (efon103)
 */
public class House extends SeedContainer {
    
    public House(int player, int storeID){
        this.player = player;
        this.numberOfSeeds = 4;
    }
    
    public void removeAllSeeds(){
        this.numberOfSeeds = 0;
    }
    
}
