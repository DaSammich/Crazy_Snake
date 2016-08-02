package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class ShowSweeperCenterCommand extends AbstractAction {

    private GameWorld gw;

    public ShowSweeperCenterCommand(GameWorld gw) {
        super("Show Center");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
          for (GameObject o : gw.getWorld()) {
                if (o instanceof Sweeper) {
                      ((Sweeper) o).switchCenter();
                }
          }
    }
}
