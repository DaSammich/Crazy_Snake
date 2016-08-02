package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class TickCommand extends AbstractAction {

    private GameWorld gw;

    public TickCommand(GameWorld gw) {
        super("Tick");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.tick();
        gw.notifyObservers();
    }
}
