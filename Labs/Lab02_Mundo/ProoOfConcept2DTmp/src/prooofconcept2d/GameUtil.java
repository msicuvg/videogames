/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prooofconcept2d;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 *
 * @author servkey
 */
public class GameUtil {
        
    public Clip getClip(String audiofile){
        Clip clip = null;
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(audiofile));
            clip = AudioSystem.getClip();
            clip.open(audioIn);
          
            return clip;
       } catch (Exception ex) {
            Logger.getLogger(GameUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clip;      
    }
}