
package Entities;

import Audio.AudioHandler;
import Audio.PlayerAudio;
import Main.GamePanel;
import Tools.GlobalVariables;
import Tools.ImageHolder;
import Tools.MathTools;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 *
 * @author vangradomor
 * 
 * @description the entity the user controls
 */
public class Player extends Character {
    
    /*current gun in use*/
    private static int currentGun;
    
    /*movement variables*/
    private boolean keyLeft  = false;
    private boolean keyRight = false;
    private boolean keyUp    = false;
    private boolean keyDown  = false;
    /******************************/
    
    /*mouse location*/
    private double mouseX = 0;
    private double mouseY = 0;
    /***************/
    
    /*bullet variables*/
    private int bulletTimer = 0;
    private int shotSpeed;
    /*******************/
    
    private int score       = 0;

    /*handles player shooting*/
    private boolean isShooting = false;
    private boolean canShoot   = true;
    /*************************/
    
    private boolean playedDeathSound = false;

    /*holds bullets, allows to infinitly extend*/
    private final ArrayList<Bullet> bullets;
    /*******************************************/
    
    /*angle bullet is fired at*/
    private double bulletAngle;
    /**************************/
    
    /*offsets of hitbox location*/
    private static final int X_OFFSET = 24;
    private static final int Y_OFFSET = 4;
    /****************************/
    
    /*size of hitbox square*/
    private static final int HITBOX = 15;
    /***********************/
    
    /*players default movement speed*/
    private static final double DEFAULT_SPEED = 1.64;
    /*******************************/
    
    public Player(){
        /*sets bound for hitbox*/
        hitWidth  = HITBOX;
        hitHeight = HITBOX;
        /***********************/

        currentGun = Bullet.DEFAULT_GUN;
        shotSpeed  = Bullet.DEFAULT_GUN_SPEED;
        
        damage = 1;
        
        x = GamePanel.FRAME_WIDTH / 2;
        y = GamePanel.FRAME_HEIGHT / 2;
        
        /*x and y locations for hitbox*/
        hitX = (int)x + hitWidth  + X_OFFSET;
        hitY = (int)y + hitHeight + Y_OFFSET;
        /******************************/
        
        /*sets default player speed*/
        speed = DEFAULT_SPEED;
        
        isAlive = true;
        canShoot = true;
        
        /*start with max health*/
        currentHealth = maxHealth;
        
        /*player does not start shooting*/
        isShooting = false;
        
        image = ImageHolder.playerImage;

        bullets = new ArrayList();
       
        PlayerAudio.playerAudio();
    }
    
    /*sets location based on key flags*/
    private void handleKeyPresses() {
        //so only one key can change x value
        if(!(keyLeft && keyRight)){
            if(keyLeft)         x -= speed;
            else if(keyRight)   x += speed;
        }
        //so only one key can change y value
        if(!(keyUp && keyDown)){
            if(keyUp)           y -= speed;
            else if(keyDown)    y += speed;
        }
    }
         
    /*moves, handles player and bullets*/
    public void update(){
        /*updates bullets*/
        handleBulletUpdates();
        /*plays death sound if player is dead*/
        if(!isAlive){
            if(!playedDeathSound){
                AudioHandler.playSound(PlayerAudio.playerDeath);
                playedDeathSound = true;
            }
            return;
        }

        bulletTimer();
        handleKeyPresses();
        handleShooting();
        checkScreenBoundaries();
        updateHitBox();
        
        /*angle to rotate player at*/
        angle = (Math.atan2(hitY - mouseY, hitX - mouseX));
        /***************************/

    }
    /**
     * 
     * @param g global graphics object
     */
    public void draw(Graphics2D g){
        /*draws location values for debuging*/
        handleBulletDrawing(g);
        /*draws and rotates player image*/
        handlePlayerDrawing(g);
        /*draws player score on screen*/
        g.setFont(new Font("Arial", Font.BOLD, 28));
    }

    /*handles if player moves off screen*/
    private void checkScreenBoundaries() {
        //if player going out of left edge of scren
        if(x < -20){
            x = -20;
        }
        else if(x > GamePanel.FRAME_WIDTH - 50){
            x = GamePanel.FRAME_WIDTH - 50;
        }
        
        //if player goes above max y value
        if(y > GamePanel.FRAME_HEIGHT - 50){
            y = GamePanel.FRAME_HEIGHT - 50;
        }
        else if(y < -20){
            y = -20;
        }
    }
        
    /**
     * 
     * @param a  sets if player is shooting
     */
    public void setShooting(boolean a){
        isShooting = a;
    }
    
    /*returns if player is shooting or not*/
    public boolean isShooting(){
        return isShooting;
    }

    /*updates hitbox around player*/
    private void updateHitBox() {
        //updates hitbox coordinates
        hitX = (int)x + hitWidth  + X_OFFSET;
        hitY = (int)y + hitHeight + Y_OFFSET;
    }
    
    /**
     * 
     * @param a if key is pressed
     */
    public void setLeft(boolean a){
        keyLeft = a;
    }
    
    /**
     * 
     * @param a if key is pressed
     */
    public void setRight(boolean a){
        keyRight = a;
    }
    
    /**
     * 
     * @param a if key is pressed
     */
    public void setUp(boolean a){
        keyUp = a;
    }
    
    /**
     * 
     * @param a if key is pressed
     */
    public void setDown(boolean a){
        keyDown = a;
    }
    /**
     * 
     * @param x mouse x coordinate
     * @param y mouse y coordinate
     */ 
    public void mouseMoved(int x, int y) {
        mouseX = x;
        mouseY = y;
    }

    
    /*handles spawning of bullets*/
    private void handleShooting() {
       
        /*returns if unable to shoot*/
        if(!isShooting) return;
        if(!canShoot)   return;
        /****************************/

        
        if(currentGun == Bullet.DEFAULT_GUN){
            /*gets angle for bullet to be fired next frame*****************************/
             /*random numbers to make bullets not %100 accurate*************************/
            bulletAngle = (Math.atan2(hitY - mouseY + MathTools.getRandomInteger(-15, 15), 
                           hitX - mouseX + MathTools.getRandomInteger(-15, 15)));
            /**************************************************************************/
            bullets.add(new Bullet(bulletAngle, hitX + (hitWidth / 2), hitY + (hitHeight / 2)));
        }
        else if(currentGun == Bullet.SHOTGUN){
            for (int i = 0; i < Bullet.SHOTGUN_AMOUNT; i++) {
                 bulletAngle = (Math.atan2(hitY - mouseY + MathTools.getRandomInteger(-35, 35), 
                           hitX - mouseX + MathTools.getRandomInteger(-35, 35)));
                bullets.add(new Bullet(bulletAngle, hitX + (hitWidth / 2), hitY + (hitHeight / 2)));
            }
        }
        
        /*plays bullet sound*/
        AudioHandler.playSound(PlayerAudio.defaultBullet);
        canShoot = false;
    }

    /*handles updating bullets and removes them if off screen*/
    private void handleBulletUpdates() {
        /*loops through bullets and updates based on coordinates*/
        if(bullets != null){
            for (int i = 0; i < bullets.toArray().length; i++) {
                bullets.get(i).update();
                if(bullets.get(i).getX() < -20 || bullets.get(i).getX() > GamePanel.FRAME_WIDTH ||
                   bullets.get(i).getY() < -20 || bullets.get(i).getY() > GamePanel.FRAME_HEIGHT){
                   bullets.remove(i);
                }
            }
        }
    }
    
    /**
     * 
     * @param g global graphics object
     */
    private void handleBulletDrawing(Graphics2D g) {
        if(bullets != null){
            for (int i = 0; i < bullets.toArray().length; i++) {
                bullets.get(i).draw(g);
            }    
        }
    }

    /*timer used to determine how often bullet can fire*/
    private void bulletTimer() {
        /*timer used for bullet shots*/
        if     (currentGun == Bullet.DEFAULT_GUN)  shotSpeed = Bullet.DEFAULT_GUN_SPEED;
        else if(currentGun == Bullet.SHOTGUN)      shotSpeed = Bullet.SHOTGUN_SPEED;
        
        if(bulletTimer > shotSpeed){
            bulletTimer = 0;
            canShoot = true;
        }
        else bulletTimer++;
        /****************************/
    }

    /*returns list that holds all bullets*/
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    /**
     * 
     * @param score how many points to add to score
     */
    public void addScore(int score) {
        if(score < 1){
            this.score = 0;
            return;
        }
        this.score += score;
    }

    /**
     * 
     * @param g global graphics object
     */
    private void handlePlayerDrawing(Graphics2D g) {
        
        /*sets reset angle when finished rotation*/
        AffineTransform reset = new AffineTransform();
        
        /*rotates to default angle*/
        reset.rotate(0, 0, 0);
        
        /*rotates image to face mouse*/
        g.rotate(angle, hitX + (hitWidth / 2), hitY + (hitHeight / 2));
        
        /*draws player image on screen*/
        g.drawImage(image, (int)x, (int)y, null);
        
        
        /*draws player hitbox(debug purposes)****************/
        g.setTransform(reset);
        if(GlobalVariables.showHitBoxes){
            g.drawRect(hitX, hitY, hitWidth, hitHeight);
        }
    }

    /*returns current score*/
    public int getScore() {
        return score;
    }

    /**
     * 
     * @param shotSpeed new firing rate of bullet
     */
    public void setShotSpeed(double shotSpeed) {
        this.shotSpeed = (int)shotSpeed;
    }

    /*switches through all weapons player can use*/
    public void switchWeapons() {
        if     (currentGun == Bullet.DEFAULT_GUN) currentGun = Bullet.SHOTGUN;
        else if(currentGun == Bullet.SHOTGUN)     currentGun = Bullet.DEFAULT_GUN;
    }

    
    /*brings player back to life*/
    public void revive() {
        isAlive = true;
    }
}
