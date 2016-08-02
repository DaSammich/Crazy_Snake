package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class SoundCommand extends AbstractAction {

      GameWorld gw;

      public SoundCommand(GameWorld gw) {
            super("Sound");
            this.gw = gw;
      }

      // gets the true or false from getSound() then notifies observers
      @Override
      public void actionPerformed(ActionEvent e) {
            gw.switchSound();
            // if the sound is currently playing, stop it
            // if it's not playing start it
            // this is because it just switched
            if (gw.isPlaying()) {
                  gw.stopMusic();
            } else if (!gw.isPaused()){
                  gw.startMusic();
            }

            gw.notifyObservers();
      }
}
