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
    
    public House(int storeID){
        this.numberOfSeeds = 4;
    }

    @Override
    public int removeAllSeeds(){
        int temp = numberOfSeeds;
        this.numberOfSeeds = 0;
        return temp;
    }    
}
