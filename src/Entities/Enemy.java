

package Entities;

import Main.GamePanel;
import Tools.GlobalVariables;
import Tools.MathTools;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;


/**
 * @author vangradomor 
 * 
 * @description Base class all enemies inherit from
 */

public abstract class Enemy extends Character{

    /*points player recieves on death*/
    protected int points;
    
    /*used to determine where enemy will spawn on screen*/
    protected void spawnEnemy() {
        
        /*used to determine if enemy will come from side or top or bottom of screen*/
        int sideOrTop = MathTools.getRandomInteger(0, 1);
        
        /*if coming from left or right side of screeen*/
        if(sideOrTop == 1){
            /*used to determine if left or right side of screen*/
            int leftOrRight = MathTools.getRandomInteger(0, 1);
            
            /*if left*/
            if(leftOrRight == 1){
                x = -50;
            }
            /*if right*/
            else{
                x = GamePanel.FRAME_WIDTH + 50;
            }
            y = MathTools.getRandomInteger(0, GamePanel.FRAME_HEIGHT);
        }
        /*if coming from top or bottom of screen*/
        else{
            /*used to determine if left or right side of screen*/
            int leftOrRight = MathTools.getRandomInteger(0, 1);
            
            /*if left*/
            if(leftOrRight == 1){
                y = -50;
            }
            /*if right*/
            else{
                y = GamePanel.FRAME_HEIGHT + 50;
            }
            x = MathTools.getRandomInteger(0, GamePanel.FRAME_HEIGHT);
        }
        
        this.hitX = (int)x;
        this.hitY = (int)x;
        
        this.hitWidth = image.getWidth();
        this.hitHeight = image.getHeight();
    }
    
    /**
     * 
     * @param playerX X coordinate of player to determine move angle 
     * @param playerY Y coordinate of player to determine move angle
     */
    public void update(int playerX, int playerY){
        /*updates enemy if it is alive*/
        if(isAlive){
            angle = (Math.atan2(y - playerY, x - playerX));
            x = x - speed * Math.cos(angle);
            y = y - speed * Math.sin(angle);
            
            this.hitX = (int)x;
            this.hitY = (int)y;    
        }
    }
        
    /**
     * 
     * @param g global graphics object
     */
    public void draw(Graphics2D g){
        /*draws enemy if it is alive*/
        if(isAlive){
            AffineTransform reset = new AffineTransform();
            
            reset.rotate(0, 0, 0);
            
            g.rotate(angle, hitX + (hitWidth / 2), hitY + (hitHeight / 2));
            
            g.drawImage(image, (int)x, (int)y, null);
            g.setTransform(reset);
            
            if(GlobalVariables.showHitBoxes){
                g.drawRect(hitX, hitY, hitWidth, hitHeight);
            }
            if(GlobalVariables.showHealth){
                g.drawString("" + (currentHealth + 1), hitX, hitY);
            }
            
        }
    }
    
    /*returns points player recives on death*/
    public int getPoints(){
        return points;
    }

}
