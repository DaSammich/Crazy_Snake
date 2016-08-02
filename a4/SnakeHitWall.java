
package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;


public class SnakeHitWall extends AbstractAction {
    
    private GameWorld gw;
        
    public SnakeHitWall(GameWorld gw) {
        super("SnakeHitWall (5)");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Snake has collided with Wall!");
        gw.playerDies();
    }
    
}
