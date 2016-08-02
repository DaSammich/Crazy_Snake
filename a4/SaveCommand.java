package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class SaveCommand extends AbstractAction {

    public SaveCommand() {
        super("Save");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Save requested from " + e.getActionCommand()
                + " " + e.getSource().getClass());
    }
}
