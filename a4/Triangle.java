package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

public class Triangle implements Shape{

      Point top, bottomLeft, bottomRight;
      private AffineTransform myRotation, myTranslation;
      private Color color;

      public Triangle(int base, int height) {
            top = new Point(0, height / 2);
            bottomLeft = new Point(-base / 2, -height / 2);
            bottomRight = new Point(base / 2, -height / 2);

            myRotation = new AffineTransform();
            myTranslation = new AffineTransform();
      }

      public void setColor(Color c) {
            color = c;
      }

      public void rotate(double radians) {
            myRotation.rotate(radians);
      }

      @Override
      public void translate(double dx, double dy) {
            myTranslation.translate(dx, dy);
      }

      public void resetTransform() {
            myRotation.setToIdentity();
            myTranslation.setToIdentity();
      }

      public void resetRotate() {
            myRotation.setToIdentity();
      }

      public void draw(Graphics2D g2d) {
            AffineTransform saveAt = g2d.getTransform();

            g2d.setColor(color);

            Polygon p = new Polygon();
            p.addPoint(top.x, top.y);
            p.addPoint(bottomRight.x, bottomRight.y);
            p.addPoint(bottomLeft.x, bottomLeft.y);

            g2d.transform(myTranslation);
            g2d.transform(myRotation);

            g2d.fillPolygon(p);

            g2d.setTransform(saveAt);
      }
}
