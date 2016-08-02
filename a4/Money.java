package a4;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Random;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Money extends FixedGameObject implements IChameleon, IDrawable, ICollider, ISelectable {

      private Random randGen = new Random();
      private int value;
      private Money copy;
      private GameWorld gw;
      private int size;
      private Oval oval;
      private boolean isSelected = false;
      private Sound sound;
      private Image moneyImg;
      private Image moneyImg2;
      private Image moneyImg3;
      private Image moneyImgHighlighted;
      private Image moneyImgHighlighted2;
      private Image moneyImgHighlighted3;
      private Image currentImg;
      private Image currentImgHighlighted;

      public Money(float x, float y, Color c, int size) {
            super(x, y, c);
            this.size = size;
            value = randGen.nextInt(100) + 1;
            oval = new Oval(size);
            oval.translate(getX(), getY());
            sound = new Sound("sound" + File.separator + "cash-register-01_converted.wav");

            switch (randGen.nextInt(3)) {
                  case (0):
                        currentImg = ResourceLibrary.getMoneyImg1();
                        currentImgHighlighted = ResourceLibrary.getMoneyImgHighlighted1();
                        break;
                  case (1):
                        currentImg = ResourceLibrary.getMoneyImg2();
                        currentImgHighlighted = ResourceLibrary.getMoneyImgHighlighted2();
                        break;
                  case (2):
                        currentImg = ResourceLibrary.getMoneyImg3();
                        currentImgHighlighted = ResourceLibrary.getMoneyImgHighlighted3();
                        break;
            }

      }

      public int getValue() {
            return value;
      }

      @Override
      public String toString() {
            String str = new String();
            str += getClass().getSimpleName() + ": ";
            str += super.toString();
            str += "value=" + value + " ";
            str += "\n";
            return str;
      }

      @Override
      public void setColor(Color c) {
            Field[] fs = getClass().getSuperclass().getSuperclass().getDeclaredFields();
            fs[2].setAccessible(true);
            try {
                  fs[2].set(this, c);
            } catch (IllegalArgumentException ex) {
                  Logger.getLogger(Money.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                  Logger.getLogger(Money.class.getName()).log(Level.SEVERE, null, ex);
            }
            fs[2].setAccessible(false);
      }

      @Override
      public void draw(Graphics2D g2d) {
            g2d.setColor(Color.blue);
            // the commented section shows the regular untranslated version
//            g2d.drawOval(Math.round(getX()), Math.round(getY()), size, size);
//            oval.draw(g2d);
            g2d.drawImage(currentImg, (int) getX() - size / 2, (int) getY() - size / 2, null);
      }

      @Override
      public boolean collidesWith(ICollider o) {
            boolean result = false;

            int thisCenterX = (int) getX() + (size / 2);
            int thisCenterY = (int) getY() + (o.getSize() / 2);
            int otherCenterX = (int) ((GameObject) o).getX() + (o.getSize() / 2);
            int otherCenterY = (int) ((GameObject) o).getY() + (o.getSize() / 2);

            // distance between centers squared
            int dx = thisCenterX - otherCenterX;
            int dy = thisCenterY - otherCenterY;
            int centerDiffSqr = (dx * dx + dy * dy);

            // find square of sum of radaii
            int thisRadius = size / 2;
            int otherRadius = o.getSize() / 2;
            int radiiSqr = (thisRadius * thisRadius + 2 * thisRadius * otherRadius
                    + otherRadius * otherRadius);

            if (centerDiffSqr <= radiiSqr) {
                  result = true;
            }

            return result;
      }

      @Override
      public void handleCollision(GameWorld gw) {

            for (int i = 0; i < gw.getWorld().size(); i++) {
//                  if (gw.getWorld().get(i) instanceof Money) {
//                        gw.addScore(((Money) gw.getWorld().get(i)).getValue());
//                        gw.getWorld().remove(i);
//                        found = true;
//                        break;
//                  }
                  if (gw.getWorld().get(i) == this) {
                        gw.addScore(((Money) gw.getWorld().get(i)).getValue());
                        gw.getWorld().remove(i);
                  }
            }
            playSound(gw);

      }

      public void playSound(GameWorld gw) {
            if (gw.getSound()) {
                  try {
                        sound.play();
                  } catch (Exception e) {
                        e.printStackTrace();
                  }
            }
      }

      @Override
      public int getSize() {
            return size;
      }

      @Override
      public int getWidth() {
            return size;
      }

      @Override
      public int getHeight() {
            return size;
      }

      @Override
      public void setSelected(boolean yesNo) {
            isSelected = yesNo;
      }

      @Override
      public boolean isSelected() {
            return isSelected;
      }

      // for the selection, we assume that everything is a 
      // rectangle
      @Override
      public boolean contains(Point2D p) {
            double px = p.getX();
            double py = p.getY();

            float xLoc = getX();
            float yLoc = getY();
            if ((px >= xLoc - getWidth() / 2) && (px <= xLoc + getWidth() / 2)
                    && (py >= yLoc - getHeight() / 2) && (py <= yLoc + getHeight() / 2)) {
                  return true;
            } else {
                  return false;
            }
      }

      @Override
      public void selectedDraw(Graphics2D g2d) {
            g2d.setColor(Color.orange);
//            oval.draw(g2d);
            g2d.drawImage(currentImgHighlighted, (int) getX() - size / 2, (int) getY() - size / 2, null);
      }

      @Override
      public void turnOff() {
            isSelected = false;
      }

      @Override
      public float getUpperLeftX() {
            return getX() + size/2;
      }

      @Override
      public float getUpperLeftY() {
            return getY() + size/2;
      }


      private class Oval {

            private Point upperLeft;
            private AffineTransform myTranslation;
            private Color color;
            private int size;

            // make this a circle 
            public Oval(int s) {
                  size = s;
                  upperLeft = new Point(-s / 2, -s / 2);
                  color = Color.blue;
                  myTranslation = new AffineTransform();
            }

            public void translate(double dx, double dy) {
                  myTranslation.translate(dx, dy);
            }

            public void draw(Graphics2D g2d) {
                  AffineTransform saveAt = g2d.getTransform();
//                  g2d.setColor(color);

                  g2d.transform(myTranslation);

                  g2d.drawOval(upperLeft.x, upperLeft.y, size, size);

                  g2d.setTransform(saveAt);
            }
      }
}
