package a4;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class DownCommand extends AbstractAction {
    
    private final int SOUTH = 180;
    private GameWorld gw;
    
    public DownCommand(GameWorld gw) {
        super("Down");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.getSnake().setDirection(SOUTH);
        gw.notifyObservers();
    }
}
