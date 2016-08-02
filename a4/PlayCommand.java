package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class PlayCommand extends AbstractAction {

      private GameProxy g;
      private boolean playing;

      public PlayCommand(GameProxy g) {
//            super("Pause");
            this.g = g;
            playing = true;
      }

      @Override
      public void actionPerformed(ActionEvent e) {
            if (playing) {
                  g.getTimer().stop();
                  playing = !playing;
                  putValue("Play", this);
            }
            else {
                  g.getTimer().start();
                  playing = !playing;
                  putValue("Pause", this);
            }
      }

}
