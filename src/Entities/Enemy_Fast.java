/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import Tools.ImageHolder;
import java.awt.Graphics2D;


/**
 * @author  vangradomor
 * 
 * @description faster version of enemy_basic
 */

public class Enemy_Fast extends Enemy{ 
    
    
    /*default move speed of fast enemy*/
    private static final double DEFAULT_SPEED = 1.38;
    
    
    public Enemy_Fast(){
        
        particleAmount = 30;
        
        /*allows enemy to be updated and drawn*/
        isAlive = true;
        
        /*amount of points player gets when enemy killed*/
        points = 500;
        
        /*sets maximum health and gives enemy maximum current health*/
        maxHealth = 9;
        currentHealth = maxHealth;
        /***********************************************************/
        
        /*sets default speed of fast enemy*/
        speed = DEFAULT_SPEED;

        /*loads image to be drawn*/
        image = ImageHolder.fastImage;
        
        /*spawns enemy on edge of screen*/
        spawnEnemy();
    }
    
    /**
     * 
     * @param playerX x location of player to get angle
     * @param playerY y location of player to get angle
     */
    @Override
    public void update(int playerX, int playerY){
        super.update(playerX, playerY);
    }
    
    /**
     * 
     * @param g global graphics object
     */
    @Override
    public void draw(Graphics2D g){
        super.draw(g);
    }
}
