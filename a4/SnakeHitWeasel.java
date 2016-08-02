package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class SnakeHitWeasel extends AbstractAction {

    private GameWorld gw;

    public SnakeHitWeasel(GameWorld gw) {
        super("SnakeHitWeasel");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Snake has collided with Weasel!");
        gw.playerDies();
    }

}
