package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class BirdImage {

      private Oval oval;
      private Triangle head;
      private ArrayList<Wing> wings;
      private Wing leftWing;
      private Wing rightWing;
      private Triangle tail;
      private AffineTransform myTranslation;
      private AffineTransform myRotation;
      private AffineTransform myScale;
      private double wingOffset = 0;
      private double wingIncrement = .5;
      private double maxWingOffset = 3.5;
      

      public BirdImage() {
            myTranslation = new AffineTransform();
            myRotation = new AffineTransform();
            myScale = new AffineTransform();

            wings = new ArrayList<Wing>();

            oval = new Oval(50, 80);

            head = new Triangle(10, 30);
            // give each of the bird image components
            // some nice gaps in between the shapes
            head.translate(0, 35 + 25);

            rightWing = new Wing(30, 50);
            rightWing.translate(-55, 0);
            rightWing.rotate(Math.toRadians(90));

            leftWing = new Wing(30, 50);
            leftWing.translate(55, 0);
            leftWing.rotate(Math.toRadians(-90));

            tail = new Triangle(30, 50);
            tail.translate(0, -35 - 35);

            wings.add(leftWing);
            wings.add(rightWing);

      }

      public void update() {
            wingOffset += wingIncrement;

            for (Wing w : wings) {
                  if (w == rightWing) {
                        w.translate(-wingOffset, 0);
                  } else {
                        w.translate(wingOffset, 0);
                  }
            }

            if (Math.abs(wingOffset) >= maxWingOffset) {
                  wingIncrement *= -1;
            }
      }

      public void resetRotation() {
            myRotation.setToIdentity();
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
            
            Color darkYellow = new Color(255,220,0);
            
            g2d.setColor(darkYellow);
            g2d.transform(myTranslation);
            g2d.transform(myRotation);
            g2d.transform(myScale);

            oval.draw(g2d);
            head.draw(g2d);
            leftWing.draw(g2d);
            rightWing.draw(g2d);
            tail.draw(g2d);

            g2d.setTransform(saveAt);
      }

      public void selectedDraw(Graphics2D g2d) {
            AffineTransform saveAt = g2d.getTransform();
            g2d.setColor(Color.blue);
            g2d.transform(myTranslation);
            g2d.transform(myRotation);
            g2d.transform(myScale);

            oval.draw(g2d);
            head.draw(g2d);
            leftWing.draw(g2d);
            rightWing.draw(g2d);
            tail.draw(g2d);

            g2d.setTransform(saveAt);
      }

}
