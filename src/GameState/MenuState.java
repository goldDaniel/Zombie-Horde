
package GameState;

import Audio.AudioHandler;
import Audio.MenuAudio;
import Main.GamePanel;
import Main.KeyHandler;
import Map.Background;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 *
 * @author vangradomor
 * 
 * @description main menu of game
 */
class MenuState extends GameState {

    /*MENU OPTIONS****************************/
    private static final int START      = 0;
    private static final int CONTROLS   = 1;
    private static final int QUIT       = 2;
    /****************************************/

    //currently selected option
    private int currentChoice = 0;
    
    //options from main menu
    private final String[] options = {"START", "CONTROLS", "QUIT"};
    
    //color and font for title
    private Color titleColor;
    private Font  titleFont;
    
    //font for options
    private Font defaultFont;
    private Font selectFont;
    
    private Background bg;
    
    private static final int TITLE_X = 150;
    private static final int TITLE_Y = 160;
    
    private static final int OPTIONS_X = GamePanel.FRAME_WIDTH / 2 - 130;
    private static final int OPTIONS_Y = 300;
    
    
    //to only allow one keyPress at a time 
    private boolean keyPressed = false;
    
    /**
     * 
     * @param gsm global state machine
     */
    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    @Override
    public void init() {
        try{
            //creates background image
            //initalizes title color
            titleColor = new Color(77, 0, 0);
            //initalizes title font
            titleFont = new Font("Century Gothic", Font.BOLD, 98);
            //initalizes options font
            defaultFont = new Font("Arial", Font.PLAIN, 36);
            selectFont = new Font("Arial", Font.BOLD, 48);
            bg = new Background(GamePanel.jarPath + "/Resources/Background/grassTile.jpg");
            
            MenuAudio.menuAudio();
        }
        catch(Exception e){
            System.out.println("MenuState: " + e);
        }
    }

    @Override
    public void update() {
        if(!MenuAudio.backgroundNoise.isActive()){
            AudioHandler.playSound(MenuAudio.backgroundNoise);
        }
    }
    
    /**
     * 
     * @param g global graphics object
     */
    @Override
    public void draw(Graphics2D g) {
        g.clearRect(0, 0, GamePanel.FRAME_WIDTH, GamePanel.FRAME_HEIGHT);
        bg.draw(g);
        //sets title color
        g.setColor(titleColor);
        //sets title font
        g.setFont(titleFont);
        //draws title 
        g.drawString("ZOMBIE HORDE", TITLE_X, TITLE_Y);
        //loops through options on screen
        for (int i = 0; i < options.length; i++) {
            //if current selection
            if(i == currentChoice){
                g.setFont(selectFont);
                g.setColor(Color.red);
            }
            //if non selected option
            else{
                g.setFont(defaultFont);
                g.setColor(Color.white);
            }
            //draws current option on screen
            g.drawString(options[i], OPTIONS_X, OPTIONS_Y + (i* 100));
        }
    }

    @Override
    public void keyPressed(int k) {
        //so you cannot hold down a key to select on menu
        if(!keyPressed){
            keyPressed = true;
            if(KeyHandler.isKeyEnter(k) ||
               KeyHandler.isKeySpace(k)){
                //activates current selection
                select();
                MenuAudio.backgroundNoise.stop();
                AudioHandler.playSound(MenuAudio.menuSelect);
            }
            if(KeyHandler.isKeyUp(k)){
                currentChoice --;
                //if you press up key while on start
                if(currentChoice < START ){
                    //go to quit
                    currentChoice = QUIT;
                }
                AudioHandler.playSound(MenuAudio.menuMove);
            }
            if(KeyHandler.isKeyDown(k)){
                currentChoice++;
                //if you press down key while on quit
                if(currentChoice > QUIT){
                    //go to start
                    currentChoice = START;
                }
                AudioHandler.playSound(MenuAudio.menuMove);
            }
       }
    }

    @Override
    public void keyReleased(int k) {
       keyPressed = false;

    }

    /*checks current selection and action upon selection*/
    private void select() {
        if(currentChoice == START){
            //start
            gsm.setState(GameStateManager.PLAYING_STATE);
        }
        else if(currentChoice == CONTROLS){
            //settings
            gsm.setState(GameStateManager.CONTROLS_STATE);
        }
        else if(currentChoice == QUIT){
            //quit
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent m) {

    }

    @Override
    public void mouseReleased(MouseEvent m) {

    }

    @Override
    public void mouseMoved(int x, int y) {
        
    }
}
