package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

      public class Oval {

            private Point upperLeft;
            private AffineTransform myTranslation;
            private AffineTransform myRotation;
            private int width, height;

            // make this a circle 
            public Oval(int w, int h) {
                  width = w;
                  height = h;
                  upperLeft = new Point(-width / 2, -height / 2);

                  myTranslation = new AffineTransform();
                  myRotation = new AffineTransform();
                  // when you make a new oval, make it pointed

            }

            public void translate(double dx, double dy) {
                  myTranslation.translate(dx, dy);
            }

            public void rotate(double radians) {
                  myRotation.rotate(radians);
            }

            public void resetTransform() {
                  myTranslation.setToIdentity();
                  myRotation.setToIdentity();
            }

            public void draw(Graphics2D g2d) {
                  AffineTransform saveAt = g2d.getTransform();
                  // okay the rotation is actually done counter clockwise
                  // so to make it match the mathematics coordinates rotation, 
                  // we have to multiply by negative 1 or we get weird side effects
                  g2d.transform(myTranslation);
                  g2d.transform(myRotation);
                  
                  g2d.fillOval(upperLeft.x, upperLeft.y, width, height);

                  g2d.setTransform(saveAt);
            }
      }
