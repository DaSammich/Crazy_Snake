package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class SwitchOutlineCommand extends AbstractAction {

    private GameWorld gw;

    public SwitchOutlineCommand(GameWorld gw) {
        super("Sweeper Outline");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
          for (GameObject o : gw.getWorld()) {
                if (o instanceof Sweeper) {
                      ((Sweeper) o).switchOutline();
                }
          }
    }
}
