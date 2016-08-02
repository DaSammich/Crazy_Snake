package a4;

import static java.lang.Math.*;

public class ChaseSnakeStrat implements IStrategy {

      private GameWorld gw;
      private Snake snake;
      private MovableGameObject movObj;

      public ChaseSnakeStrat(GameWorld currentWorld) {
            gw = currentWorld;
      }

      // THIS RETURN IS IN POLAR COORD, NOT COMPASS COORD
      public double getChaseDirection() {
            double snakeX = gw.getSnake().getX();
            double snakeY = gw.getSnake().getY();
            double deltaX = snakeX - movObj.getX();
            double deltaY = snakeY - movObj.getY();

        // make sure there is no division by zero, otherwise make it
            // possible by making deltaX nonzero
            if (deltaX == 0) {
                  deltaX += .001;
            }

            if (deltaX < 0) {
                  return (toDegrees(atan(deltaY / deltaX)) + 180);
            } else {
                  return toDegrees(atan(deltaY / deltaX));
            }
      }

      @Override
      public void connect(MovableGameObject o) {
            movObj = o;
      }

      @Override
      public void apply(int time) {
            // CONVERTING POLAR TO COMPASS
            double chaseDirection = 90 - getChaseDirection();
            movObj.setDirection((int) chaseDirection);
            // same code as the old move() method
            double deltaX = (Math.cos(Math.toRadians(90 - movObj.getDirection()))) * movObj.getSpeed() * time/1000  ;
            double deltaY = (Math.sin(Math.toRadians(90 - movObj.getDirection()))) * movObj.getSpeed() * time/1000;
            float newX = movObj.getX() + (float) deltaX;
            float newY = movObj.getY() + (float) deltaY;
            movObj.setX(newX);
            movObj.setY(newY);
            
            if (newX + ((Weasel) movObj).getWidth()/2 >= 993
                    || newX - ((Weasel) movObj).getWidth()/2 /2 <= 22
                    || newY + ((Weasel) movObj).getHeight()/2 /2 >= 768
                    || newY - ((Weasel) movObj).getHeight()/2 /2 <= 22) {

                  movObj.reverseDirection();
            }            

            // translate necessary objects
            if (movObj instanceof Weasel) {
                  ((Weasel) movObj).getRectangle().translate(deltaX, deltaY);
//                  ((Weasel) movObj).getRectangle().rotate(20);
//                  ((Weasel) movObj).getRectangle().rotate(Math.toRadians(-movObj.getDirection()));
            }

      }

}
