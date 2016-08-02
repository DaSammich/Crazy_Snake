package a4;

public interface ICollider {

      public abstract boolean collidesWith(ICollider o);

      public abstract void handleCollision(GameWorld gw);

      public abstract int getSize();

      public abstract int getWidth();

      public abstract int getHeight();

      public abstract float getUpperLeftX();

      public abstract float getUpperLeftY();
}
