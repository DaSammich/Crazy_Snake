package a4;

import javax.swing.Timer;

public interface IGame {
      public abstract Timer getTimer();
      public abstract void stopTimer();
      public abstract void startTimer();
}
