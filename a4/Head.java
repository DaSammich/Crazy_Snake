package a4;

import java.awt.Color;

public class Head extends MovableGameObject implements ISteerable, ICollider{
      
      private int size;
      
      public Head(float x, float y, Color c, int s, int d, int size) {
            super(x, y, c, s, d);
            this.size = size;
      }

    // Head doesn't move on its own, Snake must tell the head what to do.
      // snake moves as a single unit
      @Override
      public void move(int time) {
            throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void steer(int d) {
            throw new UnsupportedOperationException("Not supported yet.");
      }

      public String toString() {
            String str = new String();
            str += this.getClass().getSimpleName() + ": ";
            str += super.toString();
            return str;
      }

      // not needed
      // used to satisfy interface requirements
      @Override
      public void handleWallCollision() {

      }

      @Override
      public boolean collidesWith(ICollider o) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

      @Override
      public void handleCollision(GameWorld gw) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

      @Override
      public int getSize() {
            return size;
      }

      @Override
      public int getWidth() {
            return size;
      }

      // assume same x and y
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


}
