// strategy object for the weasel to change strategies
package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class ChangeStratCommand extends AbstractAction {

    private GameWorld gw;

    public ChangeStratCommand(GameWorld o) {
        super("Change Strategies");
        gw = o;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            for (GameObject o : gw.getWorld()) {
                if (o instanceof Weasel) {
                    ((Weasel) o).changeStrat();
                }
            }
            gw.notifyObservers();
        // this checks to see if a strategy has been set
        } catch (Exception f) {
            System.out.println("Strategy not set for a " + getClass().getSimpleName());
            System.exit(1);
        }
    }
}
