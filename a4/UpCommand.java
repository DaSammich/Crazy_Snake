package a4;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class UpCommand extends AbstractAction {
    
    private final int NORTH = 0;
    private GameWorld gw;
    
    public UpCommand(GameWorld gw) {
        super("Up");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.getSnake().setDirection(NORTH);
        gw.notifyObservers();
    }
}
