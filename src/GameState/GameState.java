/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 *
 * @author vangradomor
 * 
 * @description abstract method all states must inherit
 */
abstract class GameState {

    //access to the gameStateManager from all classes
    protected GameStateManager gsm;
    
    //initalizes variables in class
    public abstract void init();

    //updates class 
    public abstract void update();
    
    //draws graphics in class
    public abstract void draw(Graphics2D g);
    
    //handles key preses
    public abstract void keyPressed(int k);
    
    //handles key releases
    public abstract void keyReleased(int k);

    //handles mouse presses
    public abstract void mousePressed(MouseEvent m);

    //handles mouse releases
    public abstract void mouseReleased(MouseEvent m);
    
    //handles mouse movement
    public abstract void mouseMoved(int x, int y);
}
