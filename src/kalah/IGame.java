/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kalah;

/**
 *
 * @author tofutaco
 */
public interface IGame {

    public void next(int command);
        
    public int move(int command);
    
    public void gameOver();
}
