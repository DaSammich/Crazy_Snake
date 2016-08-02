// object for a single body segment of the snake
package a4;

import java.awt.Color;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;

public class BodySegment extends MovableGameObject implements ICollider {

      private double deltaX, deltaY;
      private float newX, newY;
      private Square square;
      private int size;
      private Sound sound;

      public BodySegment(float x, float y, Color c, int s, int d, int size) {
            super(x, y, c, s, d);
            this.size = size;
            square = new Square(size);
            square.translate(getX(), getY());
            sound = new Sound("sound" + File.separator + "boing_spring.wav");
      }

      public Square getSquare() {
            return square;
      }

      // get the new location from direction and speed and set it
      // this method is superceded by copy front due to flexibility issues
      @Override
      public void move(int time) {
            deltaX = (Math.cos(Math.toRadians(90 - getDirection()))) * getSpeed() * time / 1000;
            deltaY = (Math.sin(Math.toRadians(90 - getDirection()))) * getSpeed() * time / 1000;
            newX = getX() + (float) deltaX;
            newY = getY() + (float) deltaY;
            setX(newX);
            setY(newY);
      }

      public String toString() {
            String str = new String();
            str += this.getClass().getSimpleName() + ": ";
            str += super.toString();
            return str;
      }

      // allows the body to copy what is in front of it
      // allows the body to copy what is in front of it, more flexible coding
      // it allows me to reset the speed without having side effects, but in exchange
      // I have to hard code the size of the body segments and their seperation
      // (speed flexibility traded for 
      // do not use, currently developing
      public void copyFront(MovableGameObject front) {
            setX(front.getX());
            setY(front.getY());
      }

      public void draw(Graphics2D g2d) {
            g2d.setColor(Color.green);
            square.draw(g2d);
      }

      // do not actually use
      @Override
      public void handleWallCollision() {
            System.out.println("Body's handleWallCollision invoked");
      }

      // generally we use the snake's collision detection,
      // but we put this in for good measure
      @Override
      public boolean collidesWith(ICollider o) {
            boolean result = false;

            // this method finds the collision based on rectangles
            // find the top and the bottom
            int thisLeft = (int) getX() - size / 2;
            int thisRight = (int) getX() + size / 2;
            int thisTop = (int) getY() + size / 2;
            int thisBottom = (int) getY() - size / 2;

            int otherLeft = (int) ((GameObject) o).getX() - o.getWidth() / 2;
            int otherRight = (int) ((GameObject) o).getX() + o.getWidth() / 2;
            int otherTop = (int) ((GameObject) o).getY() + o.getHeight() / 2;
            int otherBottom = (int) ((GameObject) o).getY() - o.getHeight() / 2;

            // check for the overlapping
            if (thisRight >= otherLeft
                    && thisRight <= otherRight
                    && thisBottom <= otherTop
                    && thisBottom >= otherBottom
                    || thisRight >= otherLeft
                    && thisRight <= otherRight
                    && thisTop >= otherBottom
                    && thisTop <= otherTop
                    || thisLeft <= otherRight
                    && thisLeft >= otherLeft
                    && thisBottom <= otherTop
                    && thisBottom >= otherBottom
                    || thisLeft <= otherRight
                    && thisLeft >= otherLeft
                    && thisTop >= otherBottom
                    && thisTop <= otherTop) {

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

      // we assume that the only thing that will have an effect
      // when colliding with a body segment is a snake collision
      @Override
      public void handleCollision(GameWorld gw) {
            playSound(gw);
            gw.playerDies();
      }

      // generally need not use these functions
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
      public float getUpperLeftX() {
            return getX() + size/2;
      }

      @Override
      public float getUpperLeftY() {
            return getY() + size/2;
      }


      public class Square {

            private Point upperLeft, upperRight, lowerLeft, lowerRight;
            private Color color;
            private AffineTransform myTranslation;

            public Square(int s) {
                  upperLeft = new Point(-s / 2, s / 2);
                  upperRight = new Point(s / 2, s / 2);
                  lowerLeft = new Point(-s / 2, -s / 2);
                  lowerRight = new Point(s / 2, -s / 2);

                  color = new Color(0,235,0);

                  myTranslation = new AffineTransform();
            }

            public void translate(double dx, double dy) {
                  myTranslation.translate(dx, dy);
            }

            public void draw(Graphics2D g2d) {
                  AffineTransform saveAt = g2d.getTransform();

                  g2d.setColor(color);

                  Polygon p = new Polygon();
                  p.addPoint(upperLeft.x, upperLeft.y);
                  p.addPoint(upperRight.x, upperRight.y);
                  p.addPoint(lowerRight.x, lowerRight.y);
                  p.addPoint(lowerLeft.x, lowerLeft.y);

                  g2d.transform(myTranslation);

                  g2d.fillPolygon(p);

                  g2d.setTransform(saveAt);
            }
      }
}
