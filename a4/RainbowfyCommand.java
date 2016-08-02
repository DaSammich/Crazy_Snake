package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class RainbowfyCommand extends AbstractAction {

    private GameWorld gw;

    public RainbowfyCommand(GameWorld gw) {
        super("Rainbowfy Sweepers");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
          for (GameObject o : gw.getWorld()) {
                if (o instanceof Sweeper) {
                      ((Sweeper) o).rainbowfy();
                }
          }
    }
}
