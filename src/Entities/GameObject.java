
package Entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author vangradomor
 * 
 * @description base class for all entities
 */
public abstract class GameObject {
    

    /*location variables*/
    protected double x;
    protected double y;

    
    /*for collision*/
    protected int hitX;
    protected int hitY;
    protected int  hitWidth;
    protected int hitHeight;

    /*distance to move per frame*/
    protected double speed;
    
    /*image to be drawn on scren*/
    protected BufferedImage image;
    
    /**
     * 
     * @param object what collision is being checked against
     * @return       if is colliding
     */
    public boolean isColliding(GameObject object){
        
        Rectangle r1 = createCollisionRectangle();
        Rectangle r2 = object.createCollisionRectangle();
        if(r1.intersects(r2))                   return true;
        else                                    return false;
    }

    /*creates the rectangle used for bounding box collision*/
    private Rectangle createCollisionRectangle() {
        return new Rectangle(hitX,hitY, hitWidth, hitHeight);
    }
    
    /*returns x location*/
    public double getX(){
        return x;
    }
    
    /*returns y location*/
    public double getY(){
        return y;
    }
    
    /*returns width of hitbox*/
    public int getHitWidth(){
        return hitWidth;
    }
    
    /*returns height of hitbox*/
    public int getHitHeight(){
        return hitHeight;
    }
    
    /**
     * 
     * @param x object x position
     * @param y object y position
     */
    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }
}
