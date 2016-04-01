
package GameState;

import Audio.AudioHandler;
import Audio.EnemyAudio;
import Audio.PlayingAudio;
import Entities.Bullet;
import Entities.Enemy;
import Entities.Enemy_Basic;
import Entities.Enemy_Fast;
import Entities.Enemy_Slow;
import Entities.EntityHandler;
import Entities.Player;
import Main.GamePanel;
import Main.KeyHandler;
import Map.Background;
import Tools.GlobalVariables;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author vangradomor
 * 
 * @description state that handles actual gameplay
 */
class PlayingState extends GameState {

    /*player object*/
    private Player player;
    
    /*all enemies added to this list*/
    private ArrayList<Enemy> enemies;
    
    /*creates handler to update and draw entities*/
    private EntityHandler handler;
    
    /*background to be drawn and updated*/
    private Background grass;

    /*disallows updates*/
    private boolean isPaused;

    /*chance of enemy spawning*/
    private int enemyChance;
    
    /*entities on screen*/
    private int entitiesCount;
    
    
    /*used to count and display time played*/
    private int timer;
    private int showTimer;
    /**************************************/
    
    /*if constructor is finised*/
    private static boolean isInited = false;
    
    /*places to draw on screen*********************************************/
    private static final int MIDDLE_X = GamePanel.FRAME_WIDTH / 2 - 170;
    
    private static final int SCORE_X = GamePanel.FRAME_WIDTH - 250;
    private static final int SCORE_Y = 30;
    /**********************************************************************/
    
    
    /*selections for after player death*/
    private static final int NEW_GAME  = 0;
    private static final int QUIT_GAME = 1;
    /**********************************/
 
    /*current choice for game over screen*/
    private static int currentChoice = 0;
    
    
    /*so you cannot hold key during game over screen*/
    private static boolean keyPressed = false;
    
    /*options for game over screen*/
    private static final String[] options = {"NEW GAME", "QUIT"};
    
    /**
     * 
     * @param gsm global state machine
     */
    public PlayingState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    @Override
    public void init() {
        setDefaults()
                ;
        PlayingAudio.PlayingAudio();
        EnemyAudio.enemyAudio();

        
        isInited = true;
    }

    @Override
    public void update() {
        entitiesCount = (enemies.toArray().length + player.getBullets().toArray().length);
        updateSoundLoop();
        if(isPaused) return;   
        handleDebugMode();
        updateTimer();
        handler.handleSpawning();
        handler.update();
    }

    /**
     * 
     * @param g global graphics object
     */
    @Override
    public void draw(Graphics2D g) {
        g.clearRect(0, 0, GamePanel.FRAME_WIDTH, GamePanel.FRAME_HEIGHT);
        
        grass.draw(g);
        handler.draw(g);
        
        if(!player.isAlive()){
            drawGameOver(g);
            return;
        }
        else if(isPaused){
            g.drawString("PAUSED", MIDDLE_X, GamePanel.FRAME_HEIGHT / 2);
        }
        drawGameHUD(g);
        
    }

    @Override
    public void keyPressed(int k) {
        if(player.isAlive()){
            handleGameplayPresses(k);
        }
        else{
            handleGameOverPresses(k);
        }
        if(KeyHandler.isKeyConsole(k)){
            GlobalVariables.debugMode = !GlobalVariables.debugMode;
        }
        
    }

    @Override
    public void keyReleased(int k) {
        if(KeyHandler.isKeyLeft(k)){
            player.setLeft(false);
        }
        else if(KeyHandler.isKeyRight(k)){
            player.setRight(false);
        }
        else if(KeyHandler.isKeyUp(k)){
            player.setUp(false);
        }
        else if(KeyHandler.isKeyDown(k)){
            player.setDown(false);
        }
        keyPressed = false;
    }

    @Override
    public void mousePressed(MouseEvent m) {
        if(m.getButton() == MouseEvent.BUTTON1){
            player.setShooting(true);    
        }
        else if(m.getButton() == MouseEvent.BUTTON3){
            player.switchWeapons();
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent m) {
        if(m.getButton() == MouseEvent.BUTTON1){
            player.setShooting(false);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        player.mouseMoved(x, y);
    }

    /*a big if-else if block to check for entered commands*/
    private void handleDebugMode() {
        if(GlobalVariables.debugMode){
            
            String s = JOptionPane.showInputDialog("Enter a command: ");
           
            try{
                String[] command = s.split(" ");
            
                if(command[0].equals("show")){
                    if(command[1].equals("hitbox")){
                        GlobalVariables.showHitBoxes = true;
                    }
                    else if(command[1].equals("health")){
                        GlobalVariables.showHealth = true;
                    }
                }
                else if(command[0].equals("hide")){
                    if(command[1].equals("hitbox")){
                        GlobalVariables.showHitBoxes = false;
                    }
                    else if(command[1].equals("health")){
                        GlobalVariables.showHealth = false;
                    }
                }
                else if(command[0].equals("player")){
                    if(command[1].equals("speed")){
                        double speed = Double.parseDouble(command[2]);
                        player.setSpeed(speed);
                    }
                }
                else if(command[0].equals("bullet")){
                    if(command[1].equals("speed")){
                        double speed = Double.parseDouble(command[2]);
                        Bullet.setSpeed(speed);
                    }
                }
                else if(command[0].equals("godmode")){
                    if(command[1].equals("true")){
                        GlobalVariables.godmode = true;
                    }
                    else if(command[1].equals("false")){
                        GlobalVariables.godmode = false;
                    }
                }
                else if(command[0].equals("spawn")){
                    if(command[1].equals("basic")){
                        int amount = Integer.parseInt(command[2]);
                        for (int i = 0; i < amount; i++) {
                            enemies.add(new Enemy_Basic());
                        }
                    }
                    else if(command[1].equals("fast")){
                        int amount = Integer.parseInt(command[2]);
                        for (int i = 0; i < amount; i++) {
                            enemies.add(new Enemy_Fast());
                        }               
                    }
                    else if(command[1].equals("slow")){
                        int amount = Integer.parseInt(command[2]);
                        for (int i = 0; i < amount; i++) {
                            enemies.add(new Enemy_Slow());
                        }               
                    }
                }
                else if(command[0].equals("killall")){
                    handler.killAll();
                }
                else if(command[0].equals("hurtmyeyes")){
                    if(command[1].equals("true")){
                        GlobalVariables.lagMachine = true;
                    }
                    else if(command[1].equals("false")){
                        GlobalVariables.lagMachine = false;
                    }
                }
                else if(command[0].equals("danceparty")){
                    if(command[1].equals("true")){
                        GlobalVariables.danceParty = true;
                    }
                    else if(command[1].equals("false")){
                        GlobalVariables.danceParty = false;
                    }
                }
                else if(command[0].equals("revive")){
                    player.revive();
                }
            }
            /*general execption used to catch multiple error types
             * NumberFormatException & ArrayOutOfBoundsException*/
            catch(Exception e){
                e.printStackTrace();
            }
            GlobalVariables.debugMode = false;
        }
    }

    /*if constructor is finished and song not playing*/
    private void updateSoundLoop() {
        if(isInited){
            if(!PlayingAudio.song.isActive()){
                AudioHandler.playSound(PlayingAudio.song);
            }
        }
    }

    /*updates timer to display*/
    private void updateTimer() {
        /*game updates 60 times per second
         *this will add one to a timer to show
         *every 60 updates, making a 
         *semi-accurate timer *****************/
        if(timer <= 60) timer++;
        else{
            timer = 0;
            showTimer++;
        }
    }

    /**
     * 
     * @param g global graphics object
     */
    private void drawGameOver(Graphics2D g) {
        
        g.setFont(new Font("Arial", Font.BOLD, 64));
        g.setColor(Color.BLACK);
        g.drawString("GAME OVER", MIDDLE_X, GamePanel.FRAME_HEIGHT / 2 - 100);
        g.setColor(Color.WHITE);
        g.drawString("SCORE: " + player.getScore(), MIDDLE_X, GamePanel.FRAME_HEIGHT / 2 - 20);
        
        /*draws selection for after death********************************/
        for (int i = 0; i < options.length; i++) {
            //if current selection
            if(i == currentChoice) g.setColor(Color.red);
            //if non selected option
            else g.setColor(Color.white);
            
            g.setFont(new Font("Arial", Font.BOLD, 38));
            //draws current option on screen
            g.drawString(options[i], MIDDLE_X, GamePanel.FRAME_HEIGHT / 2 + 100 + (i* 40));
        }
        /********************************************************************/
    }

    /**
     * 
     * @param g global graphics object
     */
    private void drawGameHUD(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("SCORE: " + player.getScore(), SCORE_X, SCORE_Y);
        g.drawString("ENTITIES: " + entitiesCount, SCORE_X, SCORE_Y + 22);
        g.drawString("TIME PLAYING: " + showTimer, SCORE_X, SCORE_Y + 44);
    }
    
    /**
     * 
    * @param k int passed to check key
    */
    private void handleGameplayPresses(int k) {
        if(KeyHandler.isKeyLeft(k)){
            player.setLeft(true);
        }
        else if(KeyHandler.isKeyRight(k)){
            player.setRight(true);
        }
        else if(KeyHandler.isKeyUp(k)){
            player.setUp(true);
        }
        else if(KeyHandler.isKeyDown(k)){
            player.setDown(true);
        }
        else if(KeyHandler.isKeySpace(k)){
            /*pauses the game********************************/
            if(player.isAlive()){
                player.setLeft(false);
                player.setRight(false);
                player.setUp(false);
                player.setDown(false);
                player.setShooting(false);
                gsm.setState(GameStateManager.PAUSE_STATE);
                /*******************************************/
            }
        }
    }

    /**
     * 
     * @param k int passed to check key
     */
    private void handleGameOverPresses(int k) {
        if(!keyPressed){
            keyPressed = true;
            if(KeyHandler.isKeyEnter(k) ||
               KeyHandler.isKeySpace(k)){
                //activates current selection
                select();
            }
            if(KeyHandler.isKeyUp(k)){
                if(currentChoice == NEW_GAME) currentChoice = QUIT_GAME;
                else                          currentChoice = NEW_GAME;
            }
            if(KeyHandler.isKeyDown(k)){
                if(currentChoice == NEW_GAME) currentChoice = QUIT_GAME;
                else                          currentChoice = NEW_GAME;
            }
       }
    }

    /*performs selected action on game over screen*/
    private void select() {
        if(currentChoice == NEW_GAME){
            isInited = false;
            setDefaults();
        }
        else{
            System.exit(0);
        }
    }

    /*sets default values for playing. used at new game*/
    private void setDefaults() {
        enemyChance = 500;
        
        timer = 0;
        showTimer = 0;
        
        
        
        player = new Player();
        enemies = new ArrayList<Enemy>();
        grass = new Background(GamePanel.jarPath + "/Resources/Background/grassTile.jpg");
        
        player.addScore(-1);
        
        handler = new EntityHandler(enemyChance, player, enemies);
    }
}
