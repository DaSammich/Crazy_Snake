package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.Color;
import java.util.*;

public class SnakeHitFood extends AbstractAction {
    
    Random r = new Random();

    private GameWorld gw;

    public SnakeHitFood(GameWorld gw) {
        super("SnakeHitFood (4)");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean found = false;
        Random r = new Random();
        for (int i = 0; i < gw.getWorld().size(); i++) {
            if (gw.getWorld().get(i) instanceof Food) {
                gw.getSnake().grow(((Food) gw.getWorld().get(i)).getAmount());
                gw.getWorld().remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("There was no food");
        } else {
            System.out.println("Snake has collided with Food!");
        }
        
        gw.notifyObservers();
    }

}
