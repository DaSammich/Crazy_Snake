package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

public class Food extends FixedGameObject implements IDrawable, ICollider {

      private int amount;
      private Random r = new Random();
      private Oval oval;
      private int size;
      private Sound eat;
      private Sound sound1;
      private Sound sound2;
      private Image foodImg;

      // gives it a random value 
      public Food(float x, float y, Color c, int s) {
            super(x, y, c);
            size = s;
            amount = r.nextInt(10) + 1;
            oval = new Oval(size);
            oval.translate(getX(), getY());
            sound1 = new Sound("sound" + File.separator + "cork_pop_x.wav");
            sound2 = new Sound("sound" + File.separator + "bloop_x.wav");
            
            try {
                  foodImg = ImageIO.read(new File("images" + File.separator + "apple2.png"));
            } catch (Exception e) {
                  e.printStackTrace();
            }
            
      }

      public int getAmount() {
            return amount;
      }

      @Override
      public String toString() {
            String str = new String();
            str += this.getClass().getSimpleName() + ": ";
            str += super.toString();
            str += "amount=" + amount;
            str += "\n";
            return str;
      }

      public int getSize() {
            return size;
      }

      @Override
      public void draw(Graphics2D g2d) {
            g2d.setColor(Color.red);
            // the commented section shows the regular untranslated version
//            g2d.fillOval(Math.round(getX()), Math.round(getY()), size, size);
//            oval.draw(g2d);
            g2d.drawImage(foodImg, (int)getX()-size/2 - 7, (int)getY()-size/2 - 7, null);
      }

      // check collision with the snake
      // assume that the size of the snake is 20
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
            Random r = new Random();
            for (int i = 0; i < gw.getWorld().size(); i++) {
                  if (gw.getWorld().get(i) == this) {
                        gw.getSnake().grow(((Food) gw.getWorld().get(i)).getAmount());
                        gw.getWorld().remove(i);

                        gw.getWorld().add(new Food(r.nextInt(893) + 50, r.nextInt(660) + 50, Color.RED, 50));

                        for (i = r.nextInt(3) + 1; i > 0; i--) {
                              gw.getWorld().add(new Money(r.nextInt(880) + 60, r.nextInt(650) + 55, Color.blue, 60));
                        }

                  }
            }
            playSound(gw);
            gw.notifyObservers();
      }
      
      public void playSound(GameWorld gw) {
            if (gw.getSound()) {
                  try {
                        sound1.play();
                        sound2.play();
                  } catch (Exception e) {
                        e.printStackTrace();
                  }
            }
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


      private class Oval {

            private Point upperLeft;
            private AffineTransform myTranslation;
            private Color color;
            private int size;

            // make this a circle 
            public Oval(int s) {
                  size = s;
                  upperLeft = new Point(-s / 2, -s / 2);
                  color = Color.red;
                  myTranslation = new AffineTransform();
            }

            public void translate(double dx, double dy) {
                  myTranslation.translate(dx, dy);
            }

            public void draw(Graphics2D g2d) {
                  AffineTransform saveAt = g2d.getTransform();

                  g2d.setColor(color);

                  g2d.transform(myTranslation);

                  g2d.fillOval(upperLeft.x, upperLeft.y, size, size);

                  g2d.setTransform(saveAt);
            }
      }
}
