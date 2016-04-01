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
 * @description initializes and holds audio for enemies
 */
public class EnemyAudio{
    
    /*holds audio for enemy death sound*/
    public static Clip enemyDeath;
    /*used to lower volume of sound*/
    private static FloatControl enemyControl;
    
    /*initalizes audio for enemies*/
    public static void enemyAudio(){
        
        enemyDeath = AudioHandler.loadSound(enemyDeath, GamePanel.jarPath + "/Resources/Audio/zombieDeath.wav");
        enemyControl = (FloatControl)enemyDeath.getControl(FloatControl.Type.MASTER_GAIN);
        enemyControl.setValue(5f * AudioHandler.volumeScale);
    }
}
