/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import GameState.GameStateManager;
import Tools.ImageHolder;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author vangradomor
 * 
 * @description handles main gameloop
 */
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseInputListener{

    
    //width and height of game
    public static int FRAME_WIDTH = 1024;
    public static int FRAME_HEIGHT = 768;
    
    //game thread variables
    private Thread thread;
    private boolean running;
    
            
    /*GAMELOOP VARIABLES*******************************************************/
    
    //Updates per second
    private final double UPDATES_PER_SECOND = 60.0;
        
    //Calculate how many ns each frame should take for our target game hertz.
    private final double TIME_BETWEEN_UPDATES = 1000000000 / UPDATES_PER_SECOND;
       
    //At the very most we will update the game this many times before a new render
    private final int MAX_UPDATES = 5;
        
    //last update time.
    private double lastUpdateTime = System.nanoTime();
    
    //Store the last time we rendered.
    private double lastRenderTime = 0;
      
    //If we are able to get as high as this FPS, don't render again.
    private final double TARGET_FPS =  120;
    private final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
    
    /**************************************************************************/
    
    //image everything is drawn to
    private BufferedImage image;
    private Graphics2D g;
    
    private boolean initalized = false;
    
    //game state manager
    private GameStateManager gsm;
    
    
    public static String jarPath;
    
    public String JAR_PATH;
    
    public GamePanel(){
        
        try{
            CodeSource codeSource = Game.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            JAR_PATH = jarFile.getParentFile().getPath();
        }
        catch(URISyntaxException e){
            e.printStackTrace();
        }
        
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        //requests focus for keyListener
        this.setFocusable(true);
        this.requestFocus();
        //adds keyListener
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        
        //creates thread with gameLoop inside 
        startThread();
    }

    @Override
    public void run() {
        init();


        while(running){
            double now = System.nanoTime();
            int updateCount = 0;

            //do as many game updates as we need to, potentially playing catchup.
            while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES ){
               updateGame();
               lastUpdateTime += TIME_BETWEEN_UPDATES;
               updateCount++;
            }
   
            //If for some reason an update takes forever, we don't want to do an insane number of catchups
            if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES){
               lastUpdateTime = now - TIME_BETWEEN_UPDATES;
            }

            draw();
            
            drawToScreen();
            
            lastRenderTime = now;
         
            //Yield until it has been at least the target time between renderings
            while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES){
               Thread.yield();
               now = System.nanoTime();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //absract method is unused
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //key is pressed
        gsm.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //key is lifted
        gsm.keyReleased(e.getKeyCode());
    }

    private void init() {
        //creates image
        image = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
        //gets graphics from image
        g = (Graphics2D)image.getGraphics();
        
        /*sets anti-aliasing***************************************************/
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                                  RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                             RenderingHints.VALUE_ANTIALIAS_ON);
        /**********************************************************************/
        
        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        jarPath = dir.toString();
        
        ImageHolder.initalizeImages();
        
        //creates state manager
        gsm = new GameStateManager();
        //allows loop in run method
        running = true;
        initalized = true;
    }

    private void drawToScreen() {
        //gets graphics from JPanel
        Graphics g2 = this.getGraphics();
        //draws graphics
        g2.drawImage(image, 0, 0, null);
        //clears screen for next frame
        g2.dispose();
    }

    private void draw() {
        //draws graphics from current state
        gsm.draw(g);
    }

    private void updateGame() {
        //update from current state
        gsm.update();
    }

    private void startThread() {
        //creates thread if thread does not exist
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if(initalized){
            gsm.mousePressed(me);
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if(initalized){
            gsm.mouseReleased(me);    
        }
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if(initalized){
            gsm.mouseMoved(me.getX(), me.getY());    
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        if(initalized){
            gsm.mouseMoved(me.getX(), me.getY());    
        }
    }
}
