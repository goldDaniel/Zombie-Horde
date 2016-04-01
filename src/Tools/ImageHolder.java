/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tools;

import Main.GamePanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Daniel
 * 
 * @description holds and loads images
 */
public class ImageHolder {
    
    /*holds image for player*/
    public static BufferedImage playerImage;
    /*holds image for bullets*/
    public static BufferedImage bulletImage;
    /*holds image for fast enemies*/
    public static BufferedImage fastImage;
    /*holds image for slow enemies*/
    public static BufferedImage slowImage;
    /*holds image for basic enemies*/
    public static BufferedImage basicImage;
    
    
    /*sets images so they can be acessed throughout program*/
    public static void initalizeImages(){
        playerImage = getImage(GamePanel.jarPath + "/Resources/Player/player.png");
        bulletImage = getImage(GamePanel.jarPath + "/Resources/Bullets/default.png");
        fastImage = getImage(GamePanel.jarPath + "/Resources/Enemies/FAST_ZOMBIE.gif");
        slowImage = getImage(GamePanel.jarPath + "/Resources/Enemies/SLOW_ZOMBIE.gif");
        basicImage = getImage(GamePanel.jarPath + "/Resources/Enemies/zombie.gif");
    }
    
    /**
     * 
     * @param s the filepath to image
     * @return  image from filepath
     */
    public static BufferedImage getImage(String s) {
        try{
            
            File file = new File(s);
            return ImageIO.read(file);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
