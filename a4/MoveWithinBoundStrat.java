package a4;

public class MoveWithinBoundStrat implements IStrategy {

      private GameWorld gw;
      private MovableGameObject movObj;

      public MoveWithinBoundStrat(GameWorld currentWorld) {
            gw = currentWorld;
      }

      @Override
      public void connect(MovableGameObject o) {
            movObj = o;
      }

      // moves the object within the boundaries. If it hits a 
      // boundary it reverses its direction
      @Override
      public void apply(int time) {
            // same code as the old move() method (look at bird's move method)
            double deltaX = (Math.cos(Math.toRadians(90 - movObj.getDirection()))) * movObj.getSpeed() * time/1000;
            double deltaY = (Math.sin(Math.toRadians(90 - movObj.getDirection()))) * movObj.getSpeed() *time/1000;
            float newX = movObj.getX() + (float) deltaX;
            float newY = movObj.getY() + (float) deltaY;

            // temporarily leave this for the translation
            if (movObj instanceof Weasel) {
                  ((Weasel) movObj).getRectangle().translate(deltaX, deltaY);
            }
            
            if (newX + ((Weasel) movObj).getWidth()/2 >= 993
                    || newX - ((Weasel) movObj).getWidth()/2 <= 22
                    || newY + ((Weasel) movObj).getHeight()/2>= 768
                    || newY - ((Weasel) movObj).getHeight()/2<= 22) {

                  movObj.reverseDirection();
            }        

            movObj.setX(newX);
            movObj.setY(newY);

      }

}
