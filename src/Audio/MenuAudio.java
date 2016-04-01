/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Audio;

import Main.GamePanel;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author vangradomor
 * 
 * @description initializes and holds audio for main menu
 */
public class MenuAudio{
    
    /*sound for moving selection on menu*/
    public static Clip menuMove;
    /*sound for selecting current selection on menu*/
    public static Clip menuSelect;
    
    /*sound playing in menu background*/
    public static Clip backgroundNoise;
    /*used to lower volume of background sound*/
    private static FloatControl bgControl;
    
    /*initalizes audio for menu*/
    public static void menuAudio(){
        
        menuMove = AudioHandler.loadSound(menuMove, GamePanel.jarPath + "/Resources/Audio/menuMove.wav");
        menuSelect = AudioHandler.loadSound(menuSelect, GamePanel.jarPath +  "/Resources/Audio/menuSelect.wav");
        backgroundNoise = AudioHandler.loadSound(backgroundNoise, GamePanel.jarPath +  "/Resources/Audio/backgroundNoise.wav");
        bgControl = (FloatControl)backgroundNoise.getControl(FloatControl.Type.MASTER_GAIN);
        bgControl.setValue(-1f * AudioHandler.volumeScale);
    }
}
