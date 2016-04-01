
package Map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


/**
 * @author vangradomor
 * 
 * @description allows background to scroll and move without being an entity
 */

public class Background {
    
    private BufferedImage image;
    
    private Tile[][] tile;
    
    public Background(String s){
        //loads image from file
        getImageForBackground(s);
        initalizeTiles();
    }

    /*retrieves image for background*/
    private void getImageForBackground(String s) {
        try{
            File file = new File(s);
            image = ImageIO.read(file);
        }
        catch(IOException e){
            System.out.println("Background: " + e);
        }
    }
    
    /*update background, in case it moves*/
    public void update(){
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile[i].length; j++) {
                tile[i][j].update();
            }
        }
    }
    
    /**
     * 
     * @param g global graphics object
     */
    public void draw(Graphics2D g){
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile[i].length; j++) {
                tile[i][j].draw(g);
            }
        }
    }

    /*creates all needed tile objects*/
    private void initalizeTiles() {
        tile = new Tile[16][12];
        
        Tile.setTiledImage(image);
        
        int x = 0;
        int y = 0;
        
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile[i].length; j++) {
                tile[i][j] = new Tile(x, y);
                y += image.getHeight();
            }
            y = 0;
            x += image.getWidth();
        }
    }
}