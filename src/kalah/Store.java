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
public class Store extends SeedContainer {
    
    public Store(){
        this.numberOfSeeds = 0;
    }
    
    @Override
    public void addNSeeds(int seeds){
        this.numberOfSeeds += seeds;
    }
    
}
