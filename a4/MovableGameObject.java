package a4;

import java.awt.Color;

public abstract class MovableGameObject extends GameObject {

      private int speed;
      private int direction;

      public abstract void move(int time);
      
      public abstract void handleWallCollision();

      public MovableGameObject() {
            super();
      }

      public MovableGameObject(float x, float y, Color c, int s, int d) {
            super(x, y, c);
            speed = s;
            
            // multiple the speed in order to cancel the lag from the timer
            speed *= 1000 / 50;
            
            direction = d;
      }

      // accepts parameter d in COMPASS form
      public void setDirection(int d) {
            direction = d;
      }

      // returns the direction in COMPASS form
      public int getDirection() {
            return direction;
      }

      public void reverseDirection() {
            direction += 180;
            if (direction >= 360) {
                  direction %= 360;
            }
      }
      
      public void ricochet() {
            direction += 75;
            if (direction >= 360) {
                  direction %= 360;
            }
      }

      public void setSpeed(int s) {
            speed = s;
      }

      public int getSpeed() {
            return speed;
      }
      
      @Override
      public String toString() {
            String str = new String();
            str += super.toString();
            str += " speed=" + speed + " " + "direction=" + direction;
            return str;
      }
}
