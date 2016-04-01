/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Tools.MathTools;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Daniel
 */
class Particle extends GameObject{
    
    /*angle for particle to travel at*/
    private final double angle;
    
    /*used to determine how long particle stay after death*/
    private int lifeTimer;
    
    /*time allowed on screen before deletion (in frames)*/
    private static final int LIFE_MAX = 35;
    
    /*width and height of particle*/
    private  int size = 5;
    
    /*acceleration of particle*/
    private static final double ACCEL = -0.24;
    
    public Particle(double angle, double x, double y){
        
        this.x = x;
        this.y = y;
        
        angle += MathTools.getRandomDouble(-0.5, 0.5);
        
        size += MathTools.getRandomInteger(-1, 1);
        
        this.angle = angle;
        
        speed = MathTools.getRandomDouble(2, 6);
    }
    
    /*updates particle location and speed*/
    public void update(){
        lifeTimer++;
        
        if(speed < 1){
            speed = 1;
        }
        else{
            speed += ACCEL;
        }
        x = x + speed * Math.cos(angle);
        y = y + speed * Math.sin(angle);
    }
    
    /**
     * 
     * @param g global graphics object
     */
    public void draw(Graphics2D g){
        g.setColor(Color.red);
        g.fillOval((int)x, (int)y, size, size);
    }

    /*checks if the particle should dissapear*/
    public boolean shouldDie() {
        if(lifeTimer >= LIFE_MAX)           return true;
        else                                return false;
    }
}
