package a4;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Wing {

      private Triangle wing;
      private AffineTransform myTranslation;
      private AffineTransform myRotation;
      private AffineTransform myScale;

      public Wing(int w, int h) {
            wing = new Triangle(w, h);
            
            myTranslation = new AffineTransform();
            myRotation = new AffineTransform();
            myScale = new AffineTransform();
      }
      
      public void translate(double dx, double dy) {
            myTranslation.translate(dx, dy);
      }

      public void rotate(double radians) {
            myRotation.rotate(radians);
      }

      public void scale(double sx, double sy) {
            myScale.scale(sx, sy);
      }

      public void resetTransform() {
            myTranslation.setToIdentity();
            myRotation.setToIdentity();
      }
      
      public void draw(Graphics2D g2d) {
            AffineTransform saveAt = g2d.getTransform();

            g2d.transform(myTranslation);
            g2d.transform(myRotation);
            g2d.transform(myScale);

            wing.draw(g2d);

            g2d.setTransform(saveAt);
      }

}
