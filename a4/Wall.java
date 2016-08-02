package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.io.File;

public class Wall extends FixedGameObject implements IDrawable, ICollider {

      private int width, height;
      private Rectangle rect;
      private Sound sound;

      public Wall(float x, float y, Color c, int w, int h) {
            super(x, y, c);
            width = w;
            height = h;
            rect = new Rectangle(width, height, Color.black);
            rect.translate(getX(), getY());
            sound = new Sound("sound" + File.separator + "explosion_x.wav");
      }

      public String toString() {
            String str = new String();
            str += this.getClass().getSimpleName() + ": "
                    + super.toString();
            str += "width=" + width + " height=" + height;
            str += "\n";
            return str;
      }

      @Override
      public void draw(Graphics2D g2d) {
            rect.draw(g2d);
      }

      // not really calling this leave it with the circle
      // collision detection algorithm
      @Override
      public boolean collidesWith(ICollider o) {
            boolean result = false;

            // don't count body segment into wall
            if (o instanceof BodySegment) {
                  result = false;
            } else {

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

      // do not use, here just for potential use of circular collision
      // detection
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

      @Override
      public float getUpperLeftX() {
            return getX() + width/2;
      }

      @Override
      public float getUpperLeftY() {
            return getY() + height/2;
      }

      public static class Rectangle {

            private Point leftBottom;
            private AffineTransform myTranslation;
            private Color color;
            private int width, height;

            // make this a circle 
            public Rectangle(int w, int h, Color c) {
                  color = c;
                  width = w;
                  height = h;
                  leftBottom = new Point(-width / 2, -height / 2);
                  myTranslation = new AffineTransform();
            }

            public void translate(double dx, double dy) {
                  myTranslation.translate(dx, dy);
            }

            public void draw(Graphics2D g2d) {
                  AffineTransform saveAt = g2d.getTransform();

                  g2d.setColor(color);

                  g2d.transform(myTranslation);

                  g2d.fillRect(leftBottom.x, leftBottom.y, width, height);

                  g2d.setTransform(saveAt);
            }
      }

}
