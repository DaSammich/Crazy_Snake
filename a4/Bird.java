// the bird object
package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;

public class Bird extends MovableGameObject implements IDrawable, ICollider, ISelectable {

      // we're going to use the width and height as scalars from now on for the bird image
      private int width, height;
      private double deltaX, deltaY;
      private float newX, newY;
      private Oval oval;
      private boolean isSelected = false;
      private Sound sound;
      private BirdImage birdImage;

      public Bird(float x, float y, Color c, int d, int s, int w, int h) {
            super(x, y, c, d, s);
            width = w;
            height = h;
            birdImage = new BirdImage();
            birdImage.translate(getX(), getY());

            // rotate it so that the bird faces the right directions
            // remember we used compass directions, that was surely confusing
            // so we have to compensate with these operations
            birdImage.rotate(Math.toRadians(90 - getDirection()));
            birdImage.rotate(Math.toRadians(-90));
            sound = new Sound("sound" + File.separator + "rooster-1_converted.wav");
      }

      // find the new location and set it
      // includes the translation of its geometric shape
      @Override
      public void move(int time) {

            // This new move method makes the bird bounce off of walls
            double deltaX = (Math.cos(Math.toRadians(90 - getDirection()))) * getSpeed() * time / 1000;
            double deltaY = (Math.sin(Math.toRadians(90 - getDirection()))) * getSpeed() * time / 1000;
            float newX = getX() + (float) deltaX;
            float newY = getY() + (float) deltaY;

            // translate the bird's shapes & update the images
            birdImage.translate(deltaX, deltaY);
            birdImage.update();

            // hard code wall collision :[
            if (newX + width / 2 >= 993
                    || newX - width / 2 <= 22
                    || newY + height / 2 >= 768
                    || newY - height / 2 <= 22) {

                  // if your bird hits a wall then reverse the direction
                  // and realign the bird, poor thing
                  reverseDirection();
                  birdImage.resetRotation();
                  birdImage.rotate(Math.toRadians(90 - getDirection()));
                  birdImage.rotate(Math.toRadians(-90));

            }

            setX(newX);
            setY(newY);

      }

      @Override
      public String toString() {
            String str = new String();
            str += getClass().getSimpleName() + ": ";
            str += super.toString();
            str += "\n";
            return str;
      }

      @Override
      public void draw(Graphics2D g2d) {
            g2d.setColor(Color.black);
            // the commented section shows the regular untranslated version
//            g2d.drawOval(Math.round(getX()), Math.round(getY()), width, height);

            birdImage.draw(g2d);
      }

      // We use rectangular comparison for this collision
      // we assume what is coming in can only be a snake object
      @Override
      public boolean collidesWith(ICollider o) {

            boolean result = false;

            // this method finds the collision based on rectangles
            // find the top and the bottom
            int thisLeft = (int) getX() - width / 2;
            int thisRight = (int) getX() + height / 2;
            int thisTop = (int) getY() + getWidth() / 2;
            int thisBottom = (int) getY() - getHeight() / 2;

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

      // temporarily turn this off/don't use
      // works with ovals that need to use the cicle
      // collision detection method, otherwise, don't use
      // was left in here for testing purposes and fufill iterface
      @Override
      public int getSize() {
            return (width + height) / 2;
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
            birdImage.selectedDraw(g2d);
      }

      @Override
      public void turnOff() {
            isSelected = false;
      }

      @Override
      public float getUpperLeftX() {
            return getX() + width / 2;
      }

      @Override
      public float getUpperLeftY() {
            return getY() + height / 2;
      }

}
