/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Audio;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author vangradomor
 * 
 * @description loads and play passed audio files
 */
public class AudioHandler {
    
    /*used to adjust volume level*/
    public static float volumeScale = 1;
    
    /**
     * 
     * @param sound clip to load into
     * @param s     path to wanted audio
     * @return      
     */
    protected static Clip loadSound(Clip sound, String s) {
        try{
            
            File file = new File(s);
            
            /*opens audio file for bullets***********************/
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            sound = AudioSystem.getClip();
            sound.open(audio);   
            return sound;
            /***************************************************/
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 
     * @param clip plays audio clip
     */
    public static void playSound(Clip clip){
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
}
