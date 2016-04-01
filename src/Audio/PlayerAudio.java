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
 * @description initializes and holds audio for player
 */
public class PlayerAudio {
    
    /*sound for default bullet*/
    public static Clip defaultBullet;
    /*used to lower bullet volume*/
    private static FloatControl bulletControl;
    
    /*sound for player death*/
    public static Clip playerDeath;
    
    /*initalizes audio for player*/
    public static void playerAudio(){
        
        
        playerDeath = AudioHandler.loadSound(playerDeath, GamePanel.jarPath + "/Resources/Audio/playerDeath.wav");
        
        defaultBullet = AudioHandler.loadSound(defaultBullet, GamePanel.jarPath +  "/Resources/Audio/bullet.wav");
        bulletControl = (FloatControl)defaultBullet.getControl(FloatControl.Type.MASTER_GAIN);
                
        bulletControl.setValue(-13f * AudioHandler.volumeScale);
    }
}
