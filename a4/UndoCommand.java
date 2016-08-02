package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class UndoCommand extends AbstractAction {

    public UndoCommand() {
        super("Undo");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Undo requested from " + e.getActionCommand()
                + " " + e.getSource().getClass());
    }
}
