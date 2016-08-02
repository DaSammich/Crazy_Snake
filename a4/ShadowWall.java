package a4;

// ShadowWall is the exact same class as wall
// but does not support collision and its only purposes
// are to show the intended coordinate extent or size
// of the game world and outline it (it is not a solid wall)



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.io.File;

public class ShadowWall extends FixedGameObject implements IDrawable, ICollider {

      private int width, height;
      private Rectangle rect;

      public ShadowWall(float x, float y, Color c, int w, int h) {
            super(x, y, c);
            width = w;
            height = h;
            rect = new Rectangle(width, height, Color.black);
            rect.translate(getX(), getY());
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
            g2d.setColor(Color.BLACK);
            rect.draw(g2d);
      }

      // there is no collision detection for shadow walls
      @Override
      public boolean collidesWith(ICollider o) {
            return false;
      }


      // remember that shadow walls don't do anything
      @Override
      public void handleCollision(GameWorld gw) {

      }

      // do not use, here just for potential use of circular collision
      // detection
      @Override
      public int getSize() {
            return 0;
      }

      // shadow measurements dont exist
      @Override
      public int getWidth() {
            return 0;
      }

      @Override
      public int getHeight() {
            return 0;
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

                  g2d.setColor(Color.gray);

                  g2d.transform(myTranslation);

                  g2d.drawRect(leftBottom.x, leftBottom.y, width, height);

                  g2d.setTransform(saveAt);
            }
      }

}
