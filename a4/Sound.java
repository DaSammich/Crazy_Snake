package a4;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

      private Clip clip;
      private AudioInputStream inputStream;

      private String path;

      public Sound(String fileName) {
            try {
                  inputStream = AudioSystem.getAudioInputStream(new File(fileName));
                  clip = AudioSystem.getClip();
                  clip.open(inputStream);
            } catch (Exception e) {
                  e.printStackTrace();
            }

      }
      
      public void start() {
            clip.start();
      }
      
      public void stop() {
            clip.stop();
      }
      
      public void loop() throws Exception {
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
      }

      public void play() throws Exception {
            try {
                  clip.start();

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
      
      public void play(int delay) throws Exception {
            try {
                  clip.start();
                  Thread.sleep(delay);
            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
}
