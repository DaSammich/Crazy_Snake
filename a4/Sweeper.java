package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Random;

public class Sweeper extends MovableGameObject implements IDrawable, ICollider {

      private final int MAX_LEVEL = 3;
      private Point2D[] pointList;
      private Polygon collisionPolygon;
      private Polygon displayPolygon;
      private Random r = new Random();
      private Color color;
      private int left = Integer.MAX_VALUE;
      private int right = Integer.MIN_VALUE;
      private int top = Integer.MIN_VALUE;
      private int bot = Integer.MAX_VALUE;
      private Point center;
      private Point lowerLeft;
      private int width;
      private int height;
      private AffineTransform myTranslation;
      private AffineTransform myScale;
      private AffineTransform myRotation;
      // we will use these coordinates instead of the one provided in the movable game object
      private int xLoc, yLoc;
      private int direction;
      private int speed;
      private boolean outLineSwitch = true;
      private boolean centerOn = false;
      private boolean rainbowfy = false;
      private Sound sound;
      private Sound sound2;

      public Sweeper(Point2D p1, Point2D p2, Point2D p3, Point2D p4, int x, int y, int d, int s) {
            setX(x);
            setY(y);
            setDirection(d);
            setSpeed(s);

            myRotation = new AffineTransform();
            myTranslation = new AffineTransform();
            myScale = new AffineTransform();

            pointList = new Point2D[4];

            pointList[0] = p1;
            pointList[1] = p2;
            pointList[2] = p3;
            pointList[3] = p4;

            // calculate the left, right, top and bottom points
            // this calculates the top, bot, right, left
            // also note that the measurements are in screen or world coordinates
            createMeasurements();

            // make your polygon here
            // i made the translation to the points of the polygon because
            // i cannot just make a polygon with a center, i have to generate
            // a random polygon and move it to the origin based on its center
            // and then retranslate it from there, but that negates the effect
            // of the collision detection, so i compensate for it here and i have to
            // make a copy of the polygon, one that is used for drawing
            collisionPolygon = new Polygon();
            collisionPolygon.addPoint((int) pointList[0].getX() - center.x + (int) getX(), (int) pointList[0].getY() - center.y + (int) getY());
            collisionPolygon.addPoint((int) pointList[1].getX() - center.x + (int) getX(), (int) pointList[1].getY() - center.y + (int) getY());
            collisionPolygon.addPoint((int) pointList[2].getX() - center.x + (int) getX(), (int) pointList[2].getY() - center.y + (int) getY());
            collisionPolygon.addPoint((int) pointList[3].getX() - center.x + (int) getX(), (int) pointList[3].getY() - center.y + (int) getY());

            // this one is the drawn polygon
            displayPolygon = new Polygon();
            displayPolygon.addPoint((int) pointList[0].getX(), (int) pointList[0].getY());
            displayPolygon.addPoint((int) pointList[1].getX(), (int) pointList[1].getY());
            displayPolygon.addPoint((int) pointList[2].getX(), (int) pointList[2].getY());
            displayPolygon.addPoint((int) pointList[3].getX(), (int) pointList[3].getY());

            color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(185));

            // since I "translated" the polygon up there, i have to "undo" the translations
//            polygon.translate(center.x - (int)getX(),center.y - (int)getY());
            // move this to the origin to begin with, then we 
            // need to translate it to its real coordinates
            translate(-center.x, -center.y);
            translate(getX(), getY());

            sound = new Sound("sound" + File.separator + "Evil_Laugh_appear_converted.wav");
            sound2 = new Sound("sound" + File.separator + "evil-laugh_converted.wav");
      }

      public void switchOutline() {
            outLineSwitch = !outLineSwitch;
      }

      public void switchCenter() {
            centerOn = !centerOn;
      }

      public void rainbowfy() {
            rainbowfy = !rainbowfy;
      }

      public void createMeasurements() {
            for (int i = 0; i < pointList.length; i++) {
                  if (pointList[i].getY() > top) {
                        top = (int) pointList[i].getY();
                  }
            }
            for (int i = 0; i < pointList.length; i++) {
                  if (pointList[i].getY() < bot) {
                        bot = (int) pointList[i].getY();
                  }
            }
            for (int i = 0; i < pointList.length; i++) {
                  if (pointList[i].getX() > right) {
                        right = (int) pointList[i].getX();
                  }
            }
            for (int i = 0; i < pointList.length; i++) {
                  if (pointList[i].getX() < left) {
                        left = (int) pointList[i].getX();
                  }
            }

            lowerLeft = new Point(left, bot);
            width = right - left;
            height = top - bot;
            center = new Point(left + width / 2, bot + height / 2);

      }

      public double lengthOf(Point2D p1, Point2D p2) {
            double x0 = p1.getX(), y0 = p1.getY();
            double xf = p2.getX(), yf = p2.getY();
            double x, y;

            x = xf - x0;
            y = yf - y0;

            return Math.sqrt(x * x + y * y);
      }

      public boolean straightEnough(Point2D[] cpa) {
            double d1 = lengthOf(cpa[0], cpa[1]) + lengthOf(cpa[1], cpa[2]) + lengthOf(cpa[2], cpa[3]);
            double d2 = lengthOf(cpa[0], cpa[3]);

            double epsilon = .1;

            if (Math.abs(d1 - d2) < epsilon) {
                  return true;
            } else {
                  return false;
            }
      }

      public void drawBezierCurve(Point2D[] controlPointArray, int level, Graphics2D g2d) {
            if (straightEnough(controlPointArray) || level > MAX_LEVEL) {
                  // draw here, always 4 points
                  g2d.drawLine((int) controlPointArray[0].getX(), (int) controlPointArray[0].getY(), (int) controlPointArray[1].getX(), (int) controlPointArray[1].getY());
                  g2d.drawLine((int) controlPointArray[1].getX(), (int) controlPointArray[1].getY(), (int) controlPointArray[2].getX(), (int) controlPointArray[2].getY());
                  g2d.drawLine((int) controlPointArray[2].getX(), (int) controlPointArray[2].getY(), (int) controlPointArray[3].getX(), (int) controlPointArray[3].getY());

            } else {

                  Point[] leftSubArray = new Point[4];
                  Point[] rightSubArray = new Point[4];

                  subdivideCurve(controlPointArray, leftSubArray, rightSubArray);
                  drawBezierCurve(leftSubArray, level + 1, g2d);
                  drawBezierCurve(rightSubArray, level + 1, g2d);
            }
      }

      public void subdivideCurve(Point2D[] q, Point[] r, Point[] s) {
            Point r0 = new Point();
            Point r1 = new Point();
            Point r2 = new Point();
            Point r3 = new Point();

            Point s0 = new Point();
            Point s1 = new Point();
            Point s2 = new Point();
            Point s3 = new Point();

            r0.setLocation(q[0]);
            r[0] = r0;

            r1.setLocation((q[0].getX() + q[1].getX()) / 2.0, (q[0].getY() + q[1].getY()) / 2.0);
            r[1] = r1;

            r2.setLocation(r[1].getX() / 2.0 + (q[1].getX() + q[2].getX()) / 4.0, r[1].getY() / 2.0 + (q[1].getY() + q[2].getY()) / 4.0);
            r[2] = r2;

            s3.setLocation(q[3]);
            s[3] = s3;

            s2.setLocation((q[2].getX() + q[3].getX()) / 2.0, (q[2].getY() + q[3].getY()) / 2.0);
            s[2] = s2;

            s1.setLocation(s[2].getX() / 2.0 + (q[1].getX() + q[2].getX()) / 4.0, s[2].getY() / 2.0 + (q[1].getY() + q[2].getY()) / 4.0);
            s[1] = s1;

            r3.setLocation((r[2].getX() + s[1].getX()) / 2.0, (r[2].getY() + s[1].getY()) / 2.0);
            r[3] = r3;

            s0.setLocation(r[3]);
            s[0] = s0;
      }

      @Override
      public void draw(Graphics2D g2d) {
            AffineTransform saveAt = g2d.getTransform();

            g2d.transform(myTranslation);
            g2d.transform(myScale);
            g2d.transform(myRotation);

            g2d.setColor(Color.black);

            if (outLineSwitch) {
                  g2d.draw(displayPolygon);
            }

            if (centerOn) {
                  g2d.fillOval(center.x, center.y, 10, 10);
            }

            g2d.setColor(color);

            if (rainbowfy) {
                  g2d.setColor(new Color(r.nextInt(230), r.nextInt(230), r.nextInt(230)));
            }

            drawBezierCurve(pointList, 0, g2d);
            g2d.setTransform(saveAt);
      }

      public boolean contains(Point2D p2d) {
            return collisionPolygon.contains(p2d);
      }

      public int getLeft() {
            return left;
      }

      public int getRight() {
            return right;
      }

      public int getTop() {
            return top;
      }

      public int getBot() {
            return bot;
      }

      public Point getCenter() {
            return center;
      }

      public void rotate(double radians) {
            myRotation.rotate(radians);
      }

      public void translate(double dx, double dy) {
            myTranslation.translate(dx, dy);
            collisionPolygon = new Polygon();
            collisionPolygon.addPoint((int) pointList[0].getX() - center.x + (int) getX() + (int) dx, (int) pointList[0].getY() - center.y + (int) getY() + (int) dy);
            collisionPolygon.addPoint((int) pointList[1].getX() - center.x + (int) getX() + (int) dx, (int) pointList[1].getY() - center.y + (int) getY() + (int) dy);
            collisionPolygon.addPoint((int) pointList[2].getX() - center.x + (int) getX() + (int) dx, (int) pointList[2].getY() - center.y + (int) getY() + (int) dy);
            collisionPolygon.addPoint((int) pointList[3].getX() - center.x + (int) getX() + (int) dx, (int) pointList[3].getY() - center.y + (int) getY() + (int) dy);
      }

      public void scale(double sx, double sy) {
            myScale.scale(sx, sy);
      }

      public void resetTransform() {
            myTranslation.setToIdentity();
            myRotation.setToIdentity();
      }

      @Override
      public boolean collidesWith(ICollider o) {
            return collisionPolygon.intersects(o.getUpperLeftX(), o.getUpperLeftY(), o.getHeight(), o.getWidth());
      }

      // not used, use the below one
      @Override
      public void handleCollision(GameWorld gw) {
            System.out.println("Sweeper Collision!");
      }

      public void playSound2(GameWorld gw) {
            if (gw.getSound()) {
                  try {
                        sound2.play();
                  } catch (Exception e) {
                        e.printStackTrace();
                  }
            }
      }

      public void playSound1(GameWorld gw) {
            if (gw.getSound()) {
                  try {
                        sound.play();
                  } catch (Exception e) {
                        e.printStackTrace();
                  }
            }
      }

      // use this to handle the collisions
      public void handleCollision(GameWorld gw, GameObject o) {

      }

      @Override
      public int getSize() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

      // we use these methods primarily to detect collision
      // however, that's not really good with the sweeper,
      // the area would be too large, so we will just return 0
      // and no collisions will be detected when calling these methods
      @Override
      public int getWidth() {
            return 0;
      }

      @Override
      public int getHeight() {
            return 0;
      }

      @Override
      public void move(int time) {
            double deltaX = (Math.cos(Math.toRadians(90 - getDirection()))) * getSpeed() * time / 1000;
            double deltaY = (Math.sin(Math.toRadians(90 - getDirection()))) * getSpeed() * time / 1000;
            float newX = getX() + (float) deltaX;
            float newY = getY() + (float) deltaY;

            // the actual movement of the sweeper beast
            this.translate(deltaX, deltaY);

            setX(newX);
            setY(newY);

      }

      @Override
      public void handleWallCollision() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
