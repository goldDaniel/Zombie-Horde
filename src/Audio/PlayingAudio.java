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
 */
public class PlayingAudio {
    
    
    /*sound that plays during gameplay*/
    public static Clip song;
    /*used to lower song volume*/
    private static FloatControl songControl;
    
    /*initalizes audio for during gameplay*/
    public static void PlayingAudio(){
        song = AudioHandler.loadSound(song, GamePanel.jarPath + "/Resources/Audio/song.wav");
        songControl = (FloatControl)song.getControl(FloatControl.Type.MASTER_GAIN);
        songControl.setValue(-12f * AudioHandler.volumeScale);
    }
}
