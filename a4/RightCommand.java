package a4;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class RightCommand extends AbstractAction {
    
    private final int EAST = 90;
    private GameWorld gw;
    
    public RightCommand(GameWorld gw) {
        super("Right");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.getSnake().setDirection(EAST);
        gw.notifyObservers();
    }
}
