package menace;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Class whose function is to Play the Background Sound
 * @author searched in Google to add audio file to java application
 */
public class Game_Audio {
        public Game_Audio()
        {
            //URL url = this.getClass().getResource("/game-music-7408.wav");// Path to the Sound file
            String addr = System.getProperty("user.dir")+"/Menace/src/resources/game-music-7408.wav";
            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            AudioInputStream ais = null;
            try {
                ais = AudioSystem.getAudioInputStream(new File(addr));
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                clip.open(ais);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

}


