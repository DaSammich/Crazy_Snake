package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class MCommand extends AbstractAction {

    private GameWorld gw;

    public MCommand(GameWorld gw) {
        super("Map");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println();
        System.out.println(gw);
    }
}
