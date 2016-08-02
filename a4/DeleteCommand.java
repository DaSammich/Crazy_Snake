package a4;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;

public class DeleteCommand extends AbstractAction {

      private GameWorld gw;

      public DeleteCommand(GameWorld gw) {
            super("     Delete      ");
            this.gw = gw;
      }

      @Override
      public void actionPerformed(ActionEvent e) {
            ArrayList<GameObject> removeList = new ArrayList<>();
                  // if any object is selected and you push this buttong
            // then you add them to a list
            for (GameObject o : gw.getWorld()) {
                  if (o instanceof ISelectable
                          && ((ISelectable) o).isSelected()) {
                        removeList.add(o);
                  }
            }

                  // now that you are done iterating, remove those objects
            // from the list you added them into
            for (GameObject removed : removeList) {
                  gw.getWorld().remove(removed);
            }
                        
      }
}
