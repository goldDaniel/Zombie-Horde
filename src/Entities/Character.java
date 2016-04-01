
package Entities;

/**
 *
 * @author vangradomor
 * 
 * @description class that all moving objects inherit, never called directly
 */
public abstract class Character extends GameObject{
   
    
    /*health variables************/
    protected int     currentHealth;
    protected int     maxHealth;
    protected boolean isAlive;
    
    /*attacking variables******/
    protected int     damage;
    
    /*angle to move character at*/
    protected double angle;
    
    /*amount of particles to create on death*/
    protected int particleAmount;


    /*returns current health*/
    public int getHealth(){
        return currentHealth;
    }
    
    /*returns maximum health*/
    public int getMaxHealth(){
        return maxHealth;
    }

    /*returns if character is alive or not*/
    public boolean isAlive(){
        return isAlive;
    }
    
    /*returns damage done by character*/
    public int getDamage(){
        return damage;
    }
    
    /*sets character life flag to false*/
    public void kill(){
        isAlive = false;
    }
    
    /*returns hitbox y value*/
    public double getHitY() {
        return hitY;
    }

    /*returns hitbox x value*/
    public double getHitX() {
        return hitX;
    }
    
    /**
     * 
     * @param speed sets the move speed of character
     */
    public void setSpeed(double speed){
        this.speed = speed;
    }

    /**
     * 
     * @param damage the amount the character is injured by
     */
    public void damaged(int damage){
        if(currentHealth <= 0) isAlive = false;
        else                   currentHealth -= damage;
    }
        
    /*returns movement angle*/
    public double getAngle(){
        return angle;
    }

    /*returns particle amount for death*/
    public int getParticleAmount() {
        return particleAmount;
    }
}
