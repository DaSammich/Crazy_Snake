package a4;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class LeftCommand extends AbstractAction {
    
    private final int WEST = 270;
    private GameWorld gw;
    
    public LeftCommand(GameWorld gw) {
        super("Left");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.getSnake().setDirection(WEST);
        gw.notifyObservers();
    }
}
