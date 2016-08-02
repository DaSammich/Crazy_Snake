package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class QuitCommand extends AbstractAction {

    public QuitCommand() {
        super("Quit");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Quit requested from " + e.getActionCommand() +
                " " + e.getSource().getClass());
        
        int result = JOptionPane.showConfirmDialog( null, "Are you " +
                " sure you want to exit?", "Confriem Exit", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

}
