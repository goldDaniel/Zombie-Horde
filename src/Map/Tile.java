/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Map;

import Main.GamePanel;
import Tools.GlobalVariables;
import Tools.MathTools;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author Daniel
 */
class Tile{

    /*image static to not waste memory, since all images are the same*/
    private static BufferedImage bgImage;
    
    private double x;
    private double y;
    
    /*sets tile starting location*/
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * 
     * @param g global graphics object
     */
    public void draw(Graphics2D g){
        if(x < 0 || x > GamePanel.FRAME_WIDTH) return;
        if(y < 0 || y > GamePanel.FRAME_HEIGHT)return;
        g.drawImage(bgImage, (int)x, (int)y, null);
        
        /*if for some reason you want spinning tiles AND random colors*/
        if(GlobalVariables.lagMachine && GlobalVariables.danceParty){
            int angle = 0;
            int direction = MathTools.getRandomInteger(0, 4);
            
            if     (direction == 0) angle = 90;
            else if(direction == 1) angle = 180;
            else if(direction == 2) angle = 270;
        
            AffineTransform reset = new AffineTransform();
        
            /*rotates to default angle*/
            reset.rotate(0, 0, 0);
        
            /*rotates image to face mouse*/
            g.rotate(angle, x + (64 / 2 - 1), y + (64 / 2));
        
            int r = MathTools.getRandomInteger(0, 4);
           
            if(r == 0)      g.setColor(Color.red);
            else if(r == 1) g.setColor(Color.green);
            else if(r == 2) g.setColor(Color.cyan);
            else if(r == 3) g.setColor(Color.yellow);
            else if(r == 4) g.setColor(Color.magenta);
            
            g.fillRect((int)x, (int)y, bgImage.getWidth(), bgImage.getHeight());
        
            g.setTransform(reset);
        }
        /*if for some reason you want randomly colored tiles*/
        else if(GlobalVariables.danceParty){
            int r = MathTools.getRandomInteger(0, 4);
            if(r == 0)      g.setColor(Color.red);
            else if(r == 1) g.setColor(Color.green);
            else if(r == 2) g.setColor(Color.cyan);
            else if(r == 3) g.setColor(Color.yellow);
            else if(r == 4) g.setColor(Color.magenta);
            
            g.fillRect((int)x, (int)y, bgImage.getWidth(), bgImage.getHeight());
        }
        /*if you wanna slow down the game along with getting a headache*/
        else if(GlobalVariables.lagMachine){
            int angle = 0;
            int direction = MathTools.getRandomInteger(0, 3);
            
            if     (direction == 0) angle = 90;
            else if(direction == 1) angle = 180;
            else if(direction == 2) angle = 270;
        
            AffineTransform reset = new AffineTransform();
        
            /*rotates to default angle*/
            reset.rotate(0, 0, 0);
        
            /*rotates image to face mouse*/
            g.rotate(angle, x + (64 / 2), y + (64 / 2));
        
            /*draws player image on screen*/
            g.drawImage(bgImage, (int)x, (int)y, null);
        
            g.setTransform(reset);
        }
    }
    
    /*sets tiled image*/
    public static final void setTiledImage(BufferedImage image){
        bgImage = image;
    }

    public void update() {
        
    }
}
