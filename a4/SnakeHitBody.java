
package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;


public class SnakeHitBody extends AbstractAction {
    
    private GameWorld gw;
        
    public SnakeHitBody(GameWorld gw) {
        super("SnakeHitBody (1)");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Snake has collided with body segment!");
        gw.playerDies();
    }
    
}
