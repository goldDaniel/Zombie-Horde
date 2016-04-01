
package GameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author vangradomor
 * 
 * @description handles states
 */
public class GameStateManager {
    
    private final ArrayList<GameState> gameStates;
    
    //state currently in
    private int currentState;
    
    
    
    /*all states in the game*******************/
    public static final int MENU_STATE     = 0;
    public static final int PLAYING_STATE  = 1;
    public static final int CONTROLS_STATE = 2;
    public static final int PAUSE_STATE    = 3;
    /******************************************/
    
    public GameStateManager(){

        //creates new list of states  
        gameStates = new ArrayList();
        //sets default state to main menu
        currentState = MENU_STATE;
        //adds main menu to state list
        gameStates.add(new MenuState(this));
        gameStates.add(new PlayingState(this));
        gameStates.add(new ControlsState(this));
        gameStates.add(new PauseState(this));
    }
    
    /**
     * 
     * @param state new state to be drawn and updated
     */
    public void setState(int state){
        //sets the new state
        currentState = state;
    }
    
    /*returns current state*/
    public int getState(){
        return currentState;
    }
    
    /*updates current state*/
    public void update(){
        //updates current state
        gameStates.get(currentState).update();
    }
    
    /**
     * 
     * @param g global graphics object
     */
    public void draw(Graphics2D g){
        //draws current state
        gameStates.get(currentState).draw(g);
    }
    
    /*passes event to current state*/
    public void keyPressed(int k){
        //current state handles keyPressed
        gameStates.get(currentState).keyPressed(k);
    }
    
    /*passes event to current state*/
    public void keyReleased(int k){
        //current state handle keyReleased
        gameStates.get(currentState).keyReleased(k);
    }
    
    /*passes event to current state*/
    public void mousePressed(MouseEvent m){
        gameStates.get(currentState).mousePressed(m);
    }
    
    /*passes event to current state*/
    public void mouseReleased(MouseEvent m){
        gameStates.get(currentState).mouseReleased(m);
    }

    /**
     * 
     * @param x x location of mouse
     * @param y y location of mouse
     */
    public void mouseMoved(int x, int y) {
        gameStates.get(currentState).mouseMoved(x, y);
    }
}
