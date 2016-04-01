/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 *
 * @author vangradomor
 * 
 * @description state to show user game controls
 */
class ControlsState extends GameState {

    /*values of rectangle to click*/
    private static final int RECT_X = 200;
    private static final int RECT_Y = 480;
    private static final int RECT_W = 270;
    private static final int RECT_H = 20;
    /**************************************/
    
    /*location values for string drawing*/
    private static final int MOVE_X = 200;
    private static final int MOVE_Y = 200;
            
    
    private static final int AIM_X = 260;
    private static final int AIM_Y = 300;
    
    private static final int SHOOT_X = 250;
    private static final int SHOOT_Y = 400;
    
    private static final int RETURN_X = 200;
    private static final int RETURN_Y = 500;
    /************************************/
    

    /**
     * 
     * @param gsm global state manager
     */
    public ControlsState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
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
        g.clearRect(0, 0, GamePanel.FRAME_WIDTH, GamePanel.FRAME_HEIGHT);
        g.drawString("WASD / ARROW KEYS TO MOVE", MOVE_X, MOVE_Y);
        g.drawString("MOUSE TO AIM", AIM_X, AIM_Y);
        g.drawString("CLICK TO SHOOT", SHOOT_X, SHOOT_Y);
        g.drawString("CLICK HERE TO RETURN", RETURN_X, RETURN_Y);
    }

    @Override
    public void keyPressed(int k) {

    }

    @Override
    public void keyReleased(int k) {
        
    }

    @Override
    public void mousePressed(MouseEvent m) {
        
    }

    @Override
    public void mouseReleased(MouseEvent m) {
        Rectangle mouse = new Rectangle(m.getX(), m.getY(), 1, 1);
        Rectangle button = new Rectangle(RECT_X, RECT_Y, RECT_W, RECT_H);
        
        if(mouse.intersects(button)){
            gsm.setState(GameStateManager.MENU_STATE);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
    }
}
