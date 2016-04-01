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
 * @description most basic enemy, slowly moves 
 */

public class Enemy_Basic extends Enemy{
    

    /*default move speed of basic enemy*/
    private static final double DEFAULT_SPEED = 0.34;
    
    public Enemy_Basic(){
        
        particleAmount = 15;
        
        /*allows enemy to be updated and drawn*/
        isAlive = true;
        
        /*amount of points player gets when killed*/
        points = 100;
        
        /*sets maximum health and gives enemy maximum current health*/
        maxHealth = 1;
        currentHealth = maxHealth;
        /*************************************************************/
        
        /*sets speed of basic enemy*/
        speed = DEFAULT_SPEED;

        /*gets image to be drawn on screen*/
        image = ImageHolder.basicImage;
        
        /*spawns enemy on edge of screen*/
        spawnEnemy();
    }
    
    /**
     * 
     * @param playerX  x location of player to get angle
     * @param playerY  y location of player to get angle
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
