
package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;


public class SnakeHitBird extends AbstractAction {
    
    private GameWorld gw;
        
    public SnakeHitBird(GameWorld gw) {
        super("BirdHitSnake (2)");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Snake has collided with Bird!");
        gw.playerDies();
    }
    
}
