

package a4;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;


public interface ISelectable {
      public void setSelected(boolean yesNo);
      public boolean isSelected();
      public boolean contains(Point2D p2d);
      public void selectedDraw(Graphics2D g2d);
      public void turnOff();
}
