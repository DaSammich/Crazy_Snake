// The command that executes when the about button is selected on the 
// menu

package a4;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class AboutCommand extends AbstractAction {

    public AboutCommand() {
        super("About");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("About requested from " + e.getActionCommand()
                + " " + e.getSource().getClass());
    }
}
