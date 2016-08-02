package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class SnakeHitMoney extends AbstractAction {

    private GameWorld gw;

    public SnakeHitMoney(GameWorld gw) {
        super("SnakeHitMoney (3)");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean found = false;

        for (int i = 0; i < gw.getWorld().size(); i++) {
            if (gw.getWorld().get(i) instanceof Money) {
                gw.addScore(((Money) gw.getWorld().get(i)).getValue());
                gw.getWorld().remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("There was no money");
        } else {
            System.out.println("Snake has collided with money!");
        }
        gw.notifyObservers();
    }

}
