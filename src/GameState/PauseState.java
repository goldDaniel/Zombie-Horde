/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import Main.GamePanel;
import Main.KeyHandler;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 *
 * @author vangradomor
 * 
 * @description pause screen, not completed yet.
 * 
 *      TODO
 *      -options
 *      -quit
 * 
 */
public class PauseState extends GameState {

    private static final int PAUSED_X   = GamePanel.FRAME_WIDTH / 2 - 150;
    
    private static final int CONTINUE_X = 20;
    private static final int CONTINUE_Y = GamePanel.FRAME_HEIGHT + 64;
    
    
    /**
     * 
     * @param gsm global state machine
     */
    public PauseState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void init() {
        
    }

    @Override
    public void update() {
        
    }

    /**
     * 
     * @param g global graphics object
     */
    @Override
    public void draw(Graphics2D g) {
        g.setFont(new Font("New Times Roman", Font.BOLD, 60));
        g.setColor(Color.BLUE);
        g.drawString("PAUSED", PAUSED_X, GamePanel.FRAME_HEIGHT / 2);
        g.drawString("SPACEBAR TO CONTINUE", CONTINUE_X, CONTINUE_Y);
    }

    @Override
    public void keyPressed(int k) {
        if(KeyHandler.isKeySpace(k)){
            gsm.setState(GameStateManager.PLAYING_STATE);       
        }
    }

    @Override
    public void keyReleased(int k) {
    }

    @Override
    public void mousePressed(MouseEvent m) {
    }

    @Override
    public void mouseReleased(MouseEvent m) {
    }

    @Override
    public void mouseMoved(int x, int y) {

    }
}
