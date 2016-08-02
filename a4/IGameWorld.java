package a4;

import java.util.*;

public interface IGameWorld {

      public abstract ArrayList<GameObject> getWorld();

      public abstract Snake getSnake();

      public abstract void addScore(int n);

      public abstract int getScore();

      public abstract int getTimer();

      public abstract boolean getSound();

      public abstract void switchSound();

      public abstract void tick();

      public abstract int getLives();

      public abstract void livesMessage();

      public abstract void playerDies();

      public abstract String multiLineLabel();

      public boolean isPaused();

      public Sweeper getSweeper();

      public void makeSweeper(int x, int y);

}
