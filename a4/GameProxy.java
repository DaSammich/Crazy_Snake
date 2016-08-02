package a4;

import javax.swing.*;

public class GameProxy implements IGame {

      private Game g;

      public GameProxy(Game g) {
            this.g = g;
      }
      
      public boolean isPaused() {
           return  g.isPaused();
      }

      @Override
      public Timer getTimer() {
            return g.getTimer();
      }

      @Override
      public void stopTimer() {
            g.stopTimer();
      }

      @Override
      public void startTimer() {
            g.startTimer();
      }
      
      
}
