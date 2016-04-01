
package Entities;

import Tools.ImageHolder;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *
 * @author vangradomor
 * 
 * @description bullet that the player shoots
 */
public class Bullet extends GameObject{
    
    /*angle bullet will travel at*/
    private final double angle;
    
    /*the default speed of a bullet*/
    private static double DEFAULT_SPEED = 30.5;
    
    
    /*variables for external use*******************/
    public static final int DEFAULT_GUN = 0;
    public static final int DEFAULT_GUN_SPEED = 3;
    
    public static final int SHOTGUN     = 1;
    public static final int SHOTGUN_SPEED = 40;
    public static final int SHOTGUN_AMOUNT = 15;
    /**********************************************/
    
    /**
     * 
     * @param angle angle bullet travels at
     * @param x     starting X coordinate
     * @param y     starting Y coordinate
     */
    public Bullet(double angle, double x, double y) {
    
        //sets coordinates for spawn
        this.x = x;
        this.y = y;
        
        //sets angle for bullet
        this.angle = angle;
        
        
        
        //sets default speed
        speed = DEFAULT_SPEED;
        
        //loads image sprite
        image = ImageHolder.bulletImage;
        
        //sets hitbox based on image size
        hitWidth = image.getWidth();
        hitHeight = image.getHeight();
    }
    
    /*updates bullet location*/
    public void update(){
        /*updates speed based on angle*/
        x = x - speed * Math.cos(angle);
        y = y - speed * Math.sin(angle);
        /******************************/
        /*updates hitbox*/
        hitX = (int)x;
        hitY = (int)y;
        /***************/
    }
    /**
     * 
     * @param g global graphics object
     */
    public void draw(Graphics2D g){
        /*sets reset angle when finished rotation*/
        AffineTransform reset = new AffineTransform();
        /*rotates to default angle*/
        reset.rotate(0, 0, 0);
        /*rotates image to face mouse*/
        g.rotate(angle, x + (hitWidth / 2), y + (hitHeight / 2));
        /*draws player image on screen*/
        g.drawImage(image, (int)x, (int)y, null);
        /*resets graphics rotation****************/
        g.setTransform(reset);
    }
    
    public static void setSpeed(double speed){
        DEFAULT_SPEED = speed;
    }
}
