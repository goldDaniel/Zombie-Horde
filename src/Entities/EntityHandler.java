

package Entities;

import Audio.AudioHandler;
import Audio.EnemyAudio;
import Main.GamePanel;
import Tools.GlobalVariables;
import Tools.MathTools;
import Tools.QuadTree;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;


/**
 * @author  vangradomor
 * 
 * @descripton handles updates and drawing of entities package 
 */

public class EntityHandler {

    /*chance to spawn enemy per frame*/
    private int enemyChance;
    
    /*used to time when to change enemychance*/
    private int spawnTimer;
    
    private static final int TWO_SECONDS = 120;
    private static final int MIN_SPAWN_CHANCE = 20;
    
    /*entities in game***************************/
    private final Player player;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Particle> particles;
    /*******************************************/
    
    /**
     * 
     * @param enemyChance the chance to spawn an enemy per frame
     * @param player      the player object
     * @param enemies     the arraylist of enemies
     */
    public EntityHandler(int enemyChance, Player player, ArrayList<Enemy> enemies) {
        this.enemyChance = enemyChance;
        this.player = player;
        this.enemies = enemies;
        particles = new ArrayList<Particle>();
        spawnTimer = 0;
    }
    
    /*spawns enemies and updates all entities*/
    public void update(){
        /*handles creation of new enemies*/
        doEnemySpawning();
        /*updates player*/
        player.update();
        /*updates enemies*/
        updateEnemies();
        /*checks and handles collision*/
        checkCollisions();
        /*updates particles*/
        updateParticles();
    }
    
    /**
     * 
     * @param g global graphics object
     */
    public void draw(Graphics2D g){
        player.draw(g);
        drawEnemies(g);
        drawParticles(g);
    }

    /**
     * 
     * @param g global graphics object
     */
    private void drawEnemies(Graphics2D g) {
        if(!enemies.isEmpty()){
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).draw(g);
            }
        }
    }
    
    /*gets player location and updates enemies*/
    private void updateEnemies() {
        /*gets player x and y to set enemy angle*/
        int playerX = (int)player.getHitX();
        int playerY = (int)player.getHitY();
        /***************************************/
        
        if(!enemies.isEmpty()){
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).update(playerX, playerY);
            }
        }
    }
    
    /*calls multiple collision check methods*/
    private void checkCollisions() {
        checkEnemyPlayerCollision();
        checkEnemyBulletCollision();
    }

    /*check collisions between enemy and player*/
    private void checkEnemyPlayerCollision() {
        if(GlobalVariables.godmode) return;
        if(!enemies.isEmpty()){
            //creates new quadtree that fits screen
            QuadTree tree = new QuadTree(0, new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT));
            //clears screen
            tree.clear();
            //adds enemies to quadtree
            for (int i = 0; i < enemies.size(); i++) {
                tree.insert(enemies.get(i));
            }
            //list to hold collideable objects
            ArrayList<Enemy> returnObjects = new ArrayList();
            
            //gets objects for collideable list
            for (int i = 0; i < enemies.size(); i++) {
                tree.retrieve(returnObjects, player);
            }
            //checks remaining object collisions
            for (int i = 0; i < returnObjects.size(); i++) {
                try{
                    if(player.isColliding(returnObjects.get(i))){
                        player.kill();
                    }    
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /*checks collisions between bullets and enemies*/
    private void checkEnemyBulletCollision() {
        if(player.getBullets().isEmpty()) return;
        if(!enemies.isEmpty()){
            //creates new quadtree that fits screen
            QuadTree tree = new QuadTree(0, new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT));
            //clears screen
            tree.clear();
            //adds enemies to quadtree
            for (int i = 0; i < enemies.size(); i++) {
                tree.insert(enemies.get(i));
            }
            //list to hold collideable objects
            ArrayList<Enemy> returnObjects = new ArrayList();
            ArrayList<Bullet> bullets = player.getBullets();
            //gets objects for collideable list
            returnObjects.clear();
            for (int i = 0; i < bullets.size(); i++) {
                tree.retrieve(returnObjects, bullets.get(i));
            }
            
            //checks remaining object collisions
            for (int i = 0; i < returnObjects.size(); i++) {
                try{
                    /*loops through bullets*/
                    for (int j = 0; j < bullets.size(); j++) {
                        /*if bullet is colliding with collideable obhect*/
                        if(bullets.get(j).isColliding(returnObjects.get(i))){
                            /*deals damage to enemy*/
                            returnObjects.get(i).damaged(player.getDamage());
                            /*removes bullet from list of bullets*/
                            bullets.remove(j);
                            
                            /*creates partcle to show enemy has been damaged******************************/
                            double x = returnObjects.get(i).getX() + returnObjects.get(i).getHitWidth() / 2;
                            double y = returnObjects.get(i).getY() + returnObjects.get(i).getHitHeight() / 2;
                            
                            particles.add(new Particle(returnObjects.get(i).getAngle(), x, y));
                            /*****************************************************************************/
                        }
                    }
                    /*if damaged enemy is no longer alive*/
                    if(!returnObjects.get(i).isAlive()){
                        /*loop through list that holds all enemies*/
                        for (int j = 0; j < enemies.size(); j++) {
                            /*if enemy in list is the dead enemy*/
                            if(returnObjects.get(i).equals(enemies.get(j))){
                                /*play enemy death sound*/
                                AudioHandler.playSound(EnemyAudio.enemyDeath);
                                
                                /*creates particles based on how many the enemy gives off*************************/
                                int amount = returnObjects.get(i).getParticleAmount();
                                for (int k = 0; k < amount; k++) {
                                    double x = returnObjects.get(i).getX() + returnObjects.get(i).getHitWidth() / 2;
                                    double y = returnObjects.get(i).getY() + returnObjects.get(i).getHitHeight() / 2;
                            
                                    particles.add(new Particle(returnObjects.get(i).getAngle(), x, y));
                                }
                                /********************************************************************************/
                               
                                /*removes enemy from collidable list*/
                                returnObjects.remove(i);
                                /*updates player score*/
                                player.addScore(enemies.get(j).getPoints());
                                /*removes enemy from updateable list*/
                                enemies.remove(j);
                            }
                       }
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }    
    }

    /*kills all enemies, awards no points*/
    public void killAll() {
        enemies.clear();
    }

    /*updates timer that decides how fast the dificulty curve is*/
    public void handleSpawning() {
        if(spawnTimer < TWO_SECONDS){
            spawnTimer++;
        }
        else{
            spawnTimer = 0;
            if(enemyChance < MIN_SPAWN_CHANCE) enemyChance = MIN_SPAWN_CHANCE;
            else                 enemyChance -= 10;
            
        }
    }

    /**
     * 
     * @param g global graphics object
     */
    private void drawParticles(Graphics2D g) {
        if(!particles.isEmpty()){
            for (int i = 0; i < particles.size(); i++) {
                particles.get(i).draw(g);
            }
        }
    }

    /*loops through and updates particle list*/
    private void updateParticles() {
        if(!particles.isEmpty()){
            for (int i = 0; i < particles.size(); i++) {
                particles.get(i).update();
                if(particles.get(i).shouldDie()){
                    particles.remove(i);
                }
            }
        }
    }

    private void doEnemySpawning() {
        if(player.isAlive()){
            if(enemies.size() < 500){
                /*random chance to spawn basic enemy*/
                if(MathTools.getRandomInteger(0, enemyChance) == 0){
                    enemies.add(new Enemy_Basic());
                    enemies.add(new Enemy_Basic());
                    if(MathTools.getRandomInteger(0, 100) == 0){
                        enemies.add(new Enemy_Slow());
                    }
                }
                /*random chance to spaw fast enemy*/
                if(MathTools.getRandomInteger(0, enemyChance + 1000) == 0){
                    enemies.add(new Enemy_Fast());
                    enemies.add(new Enemy_Slow());
                }
            }
        }
    }
}
