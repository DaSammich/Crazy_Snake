package a4;

import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.util.*;

public class Weasel extends MovableGameObject implements IDrawable, ICollider, ISelectable {

      private ArrayList<IStrategy> stratList = new ArrayList<>();
      private int index = 0;
      private IStrategy strat;
      private double deltaX, deltaY;
      private float newX, newY;
      private int width, height;
      private Rectangle rect;
      private boolean isSelected = false;
      private Sound sound;
      private int timer = 0;

      public Weasel(float x, float y, Color c, int d, int s, int w, int h) {
            super(x, y, c, d, s);
            width = w;
            height = h;
            rect = new Rectangle(w, h, Color.magenta);
            rect.translate(getX(), getY());
            sound = new Sound("sound" + File.separator + "cat_meow8_converted.wav");
      }

      // adds and sets the strategy object
      public void addStrat(IStrategy s) {
            stratList.add(s);
            setStrat(s);
            index++;    // index now points to the current strat
      }

      private void setStrat(IStrategy s) {
            strat = s;
            s.connect(this);
      }

      public void changeStrat() {
            index++;
            // out of bounds checker, reset if it goes out of bounds
            // for its list of strategies
            if (index >= stratList.size()) {
                  index = 0;
            }
            setStrat(stratList.get(index));
      }

      public void showStrats() {
            System.out.println(stratList);
      }

      @Override
      public void move(int time) {
            try {
                  strat.apply(time);
                  timer++;
                  if (timer % 170 == 0) {
                        this.changeStrat();
                  }

            } catch (Exception e) {
                  System.out.println("Strategy not set for a " + getClass().getSimpleName());
                  System.exit(1);
            }
      }

      @Override
      public String toString() {
            String str = new String();
            str += getClass().getSimpleName() + ": ";
            str += super.toString();
            try {
                  str += " " + "Strat=" + strat.getClass().getSimpleName();
            } catch (Exception e) {
                  System.out.println("Strategy not set for a " + getClass().getSimpleName());
                  System.exit(1);
            }
            str += "\n";
            return str;
      }

      @Override
      public void draw(Graphics2D g2d) {
            g2d.setColor(Color.magenta);
            // the commented section shows the regular untranslated version
//            g2d.drawRect(Math.round(getX()), Math.round(getY()), 20, 50);
            rect.draw(g2d);
      }

      // not really calling this leave it with the circle
      // collision detection algorithm
      @Override
      public boolean collidesWith(ICollider o) {
            boolean result = false;

            // this method finds the collision based on rectangles
            // find the top and the bottom
            int thisLeft = (int) getX() - width / 2;
            int thisRight = (int) getX() + width / 2;
            int thisTop = (int) getY() + height / 2;
            int thisBottom = (int) getY() - height / 2;

            int otherLeft = (int) ((GameObject) o).getX() - o.getWidth() / 2;
            int otherRight = (int) ((GameObject) o).getX() + o.getWidth() / 2;
            int otherTop = (int) ((GameObject) o).getY() + o.getHeight() / 2;
            int otherBottom = (int) ((GameObject) o).getY() - o.getHeight() / 2;

            // check for the overlapping
            if (thisRight > otherLeft
                    && thisRight < otherRight
                    && thisBottom < otherTop
                    && thisBottom > otherBottom
                    || thisRight > otherLeft
                    && thisRight < otherRight
                    && thisTop > otherBottom
                    && thisTop < otherTop
                    || thisLeft < otherRight
                    && thisLeft > otherLeft
                    && thisBottom < otherTop
                    && thisBottom > otherBottom
                    || thisLeft < otherRight
                    && thisLeft > otherLeft
                    && thisTop > otherBottom
                    && thisTop < otherTop) {

                  result = true;
            }

            return result;
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
      public void handleCollision(GameWorld gw) {
            playSound(gw);
            gw.playerDies();

      }

      // do not use
      @Override
      public int getSize() {
            return 0;
      }

      @Override
      public int getWidth() {
            return width;
      }

      @Override
      public int getHeight() {
            return height;
      }

      public Rectangle getRectangle() {
            return rect;
      }

      @Override
      public void handleWallCollision() {
            ricochet();
      }

      @Override
      public void setSelected(boolean yesNo) {
            isSelected = yesNo;
      }

      @Override
      public boolean isSelected() {
            return isSelected;
      }

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
            g2d.setColor(Color.blue);
            rect.draw(g2d);
      }

      @Override
      public void turnOff() {
            isSelected = false;
      }

      @Override
      public float getUpperLeftX() {
            return getX() + width/2;
      }

      @Override
      public float getUpperLeftY() {
            return getY() + height/2;
      }

      protected class Rectangle {

            private Point leftBottom;
            private AffineTransform myTranslation, myRotation;
            private Color color;
            private int width, height;

            public Rectangle(int w, int h, Color c) {
                  color = c;
                  width = w;
                  height = h;
                  leftBottom = new Point(-width / 2, -height / 2);
                  myTranslation = new AffineTransform();
                  myRotation = new AffineTransform();
            }

            public void translate(double dx, double dy) {
                  myTranslation.translate(dx, dy);
            }

            public void rotate(double radians) {
                  myRotation.rotate(radians);
            }

            public void draw(Graphics2D g2d) {
                  AffineTransform saveAt = g2d.getTransform();

                  // the rotation is actually done counter clockwise
                  // so to make it match the mathematics coordinates rotation, 
                  // we have to multiply by negative 1 or we get weird side effects
                  g2d.transform(myTranslation);
                  g2d.transform(myRotation);
                  g2d.rotate(Math.toRadians(-getDirection()));

                  g2d.drawRect(leftBottom.x, leftBottom.y, width, height);

                  g2d.setTransform(saveAt);
            }
      }

}
