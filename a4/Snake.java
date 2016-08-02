package a4;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

public class Snake extends MovableGameObject implements ISteerable, IDrawable, ICollider {

      private Head head;
      private ArrayList<MovableGameObject> fullBody = new ArrayList<MovableGameObject>();
      private final java.util.List<Integer> compass = Arrays.asList(0, 90, 180, 270);
      private double deltaX, deltaY;
      private float newX, newY;
      private int stomach = 0;
      private final int SNAKE_SPEED = 22 * 1000 / 50;
      private int size;
      private Triangle triangle;
      private Color color;

      public Snake(float x, float y, Color c, int s) {
            head = new Head(x, y, c, 0, 0,s);
            setSpeed(SNAKE_SPEED);
            size = s;

            triangle = new Triangle(size, size);
            triangle.translate(getX(), getY());

            setDirection(compass.get(new Random().nextInt(4)));
            initSnakeBody();

      }

      // makes the snake's head and the bodies of the snake into the list
      // we assume the size of the snake parts are about 20 in pixels
      private void initSnakeBody() {
            fullBody.add(head);
            switch (head.getDirection()) {
                  case 0:
                        fullBody.add(new BodySegment(head.getX(), head.getY(), getColor(), head.getSpeed(), head.getDirection(), size));
                        fullBody.add(new BodySegment(head.getX(), head.getY(), getColor(), head.getSpeed(), head.getDirection(), size));
                        fullBody.add(new BodySegment(head.getX(), head.getY(), getColor(), head.getSpeed(), head.getDirection(), size));
                        break;
                  case 90:
                        fullBody.add(new BodySegment(head.getX(), head.getY(), getColor(), head.getSpeed(), head.getDirection(), size));
                        fullBody.add(new BodySegment(head.getX() , head.getY(), getColor(), head.getSpeed(), head.getDirection(), size));
                        fullBody.add(new BodySegment(head.getX() , head.getY(), getColor(), head.getSpeed(), head.getDirection(), size));
                        break;
                  case 180:
                        fullBody.add(new BodySegment(head.getX(), head.getY(), getColor(), head.getSpeed(), head.getDirection(), size));
                        fullBody.add(new BodySegment(head.getX(), head.getY() , getColor(), head.getSpeed(), head.getDirection(), size));
                        fullBody.add(new BodySegment(head.getX(), head.getY() , getColor(), head.getSpeed(), head.getDirection(), size));
                        break;
                  case 270:
                        fullBody.add(new BodySegment(head.getX(), head.getY(), getColor(), head.getSpeed(), head.getDirection(), size));
                        fullBody.add(new BodySegment(head.getX(), head.getY(), getColor(), head.getSpeed(), head.getDirection(), size));
                        fullBody.add(new BodySegment(head.getX(), head.getY(), getColor(), head.getSpeed(), head.getDirection(), size));
                        break;
            }

      }
      
      // disables the snake from going backwards
      // otherwords it stops the back arrow from working
      // relative to the snake's direction
      public void noSpin(Game g) {
            switch (getDirection()) {
                  case 0:
                        // disable the down key
                        g.disableDown();
                        break;
                  case 90:
                        // disable the left key
                        g.disableLeft();
                        break;
                  case 180:
                        // disable the up key
                        g.disableUp();
                        break;
                  case 270:
                        // disable the right key
                        g.disableRight();
                        break;
            }
      }
      
      public ArrayList<MovableGameObject> getfullBody() {
            return fullBody;
      }

      @Override
      public void reverseDirection() {
            head.reverseDirection();
      }

      @Override
      public float getX() {
            return head.getX();
      }

      @Override

      public float getY() {
            return head.getY();
      }

      @Override
      public int getDirection() {
            return head.getDirection();
      }

      @Override
      public int getSpeed() {
            return head.getSpeed();
      }

      public void grow(int n) {
            stomach += n;
      }

      @Override
      public void setDirection(int d) {
            head.setDirection(d);

            //transform the head when a new direction is set
            switch (d) {
                  case 90:
                        // steering right
                        triangle.resetRotate();
                        triangle.rotate(Math.toRadians(270));
                        break;
                  case 180:
                        // steering down
                        triangle.resetRotate();
                        triangle.rotate(Math.toRadians(180));
                        break;
                  case 270:
                        // left
                        triangle.resetRotate();
                        triangle.rotate(Math.toRadians(90));

                        break;
                  case 0:
                        // up
                        triangle.resetRotate();
                        triangle.rotate(Math.toRadians(360));
                        break;
            }

      }

      @Override
      public void setSpeed(int s) {
            head.setSpeed(s);
      }

      // moves the body, then the head, and then copies the last one's old position
      //and addds it to end; In later version I will change the 1 to the actual size
      // because it represents the seperation between each segment
      @Override
      public void move(int time) {
            moveBodySegments();
            moveHead(time);

            // do the moving for the triangle as well
//            triangle.translate(getX(), getY());
//            triangle.rotate(Math.toRadians(10));
            if (stomach > 0) {
                  MovableGameObject toBeCopied = fullBody.get(fullBody.size() - 1);

                  fullBody.add(new BodySegment(toBeCopied.getX(), toBeCopied.getY(),
                          getColor(), toBeCopied.getSpeed(), toBeCopied.getDirection(), size));

                  stomach--;
            }
      }

      // move the head and include the transformation of its geometric shape
      public void moveHead(int time) {
            deltaX = (Math.cos(Math.toRadians(90 - head.getDirection()))) * head.getSpeed() * time/1000;
            deltaY = (Math.sin(Math.toRadians(90 - head.getDirection()))) * head.getSpeed() * time/1000;
            newX = head.getX() + (float) deltaX;
            newY = head.getY() + (float) deltaY;

            // translate the snake's triangle with the deltas
            triangle.translate(deltaX, deltaY);

            head.setX(newX);
            head.setY(newY);

            // hardcode boundaries :[
      }

      public void moveBodySegments() {
            MovableGameObject bElement;
            MovableGameObject front;
//            for (int i = fullBody.size(); i > 1; i--) {
//                  // get the last segment
//                  bElement = fullBody.get(i - 1);
//                  bElement.move();
//                  frontDir = fullBody.get(i - 2).getDirection();
//                  bElement.setDirection(frontDir);
//            }
            for (int i = fullBody.size(); i > 1; i--) {
                  // get the last segment
                  bElement = fullBody.get(i - 1);
                  front = fullBody.get(i - 2);

                  float dx = front.getX() - bElement.getX();
                  float dy = front.getY() - bElement.getY();

                  // translate here
                  ((BodySegment) bElement).getSquare().translate(dx, dy);
//                  ((BodySegment) bElement).getSquare().translate(10, 10);

                  // move here
                  ((BodySegment) bElement).copyFront(front);

            }
      }

      @Override
      public String toString() {
            String str = new String();
            for (MovableGameObject gameObject : fullBody) {
                  str += (gameObject.toString()) + "\n";
            }
            return str;
      }

      @Override
      public int getSize() {
            return size;
      }

      // html needed to be inserted for newlines in JTextLabel display in JFrame
      public String multiLineLabel() {
            String str = new String();
            for (MovableGameObject gameObject : fullBody) {
                  str += (gameObject.toString()) + "<br>";
            }
            return str;
      }

      @Override
      public void steer(int d) {
            setDirection(d);
      }

      @Override
      public void draw(Graphics2D g2d) {
            color = new Color(0,235,0);
            
            g2d.setColor(color);
            triangle.draw(g2d);
            // draw the head with something
//            g2d.setColor(Color.BLUE);
//            g2d.fillOval(Math.round(this.getX()), Math.round(this.getY()), 20, 20);

            // fill the bodies with something
            g2d.setColor(Color.GREEN);
            for (MovableGameObject o : fullBody) {
                  if (o instanceof BodySegment) {
                        ((BodySegment) o).draw(g2d);
//                        g2d.fillRect(Math.round(o.getX()), Math.round(o.getY()), size, size);
                  }
            }

//            Iterator iter = fullBody.iterator();
//            // move past the head
//            iter.next();
//            while(iter.hasNext()) {
//                  BodySegment current = ((BodySegment)iter.next()).;
//                  current.
//            }
      }

      @Override
      public boolean collidesWith(ICollider o) {
              
            // this method is turned off
            // it finds collisions based on circles
//            boolean result = false;
//
//            int thisCenterX = (int) getX();
//            int thisCenterY = (int) getY();
//            int otherCenterX = (int) ((GameObject) o).getX();
//            int otherCenterY = (int) ((GameObject) o).getY();
//            
//
//            // distance between centers squared
//            int dx = thisCenterX - otherCenterX;
//            int dy = thisCenterY - otherCenterY;
//            int centerDiffSqr = (dx * dx + dy * dy);
//
//            // find square of sum of radaii
//            int thisRadius = size / 2;
//            int otherRadius = o.getSize() / 2;
//            int radiiSqr = (thisRadius * thisRadius + 2 * thisRadius * otherRadius
//                    + otherRadius * otherRadius);
//
//            if (centerDiffSqr <= radiiSqr) {
//                  result = true;
//            }
//
//            return result;
            
            // this method finds the collision based on rectangles
            // find the top and the bottom
            boolean result = false;
            
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
                    && thisTop <= otherTop
                    ) {

                  result = true;
            }

            return result;

      }

      // don't call it because we let the other objects get called 
      // to handle the collision
      @Override
      public void handleCollision(GameWorld gw) {
            System.out.println("snake's handleCollision invoked");
      }

      @Override
      public int getWidth() {
            return size;
      }

      @Override
      public int getHeight() {
            return size;
      }

      // doesn't do anything no need to use
      // needed to satisfy interface requirements
      @Override
      public void handleWallCollision() {
            System.out.println("Snake's handleWallCollision invoked");
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
