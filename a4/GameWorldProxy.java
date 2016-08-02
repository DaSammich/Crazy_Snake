package a4;

import java.util.ArrayList;

public class GameWorldProxy implements IObservable, IGameWorld {

      private GameWorld gw;

      public GameWorldProxy(GameWorld gw) {
            this.gw = gw;
      }

      // throw exceptions for methods we do not allow
      @Override
      public void addObserver(IObserver o) {
            throw new UnsupportedOperationException("Not supported.");
      }

      @Override
      public ArrayList<GameObject> getWorld() {
            return gw.getWorld();
      }

      @Override
      public Snake getSnake() {
            throw new UnsupportedOperationException("Not supported.");
      }

      @Override
      public void addScore(int n) {
            throw new UnsupportedOperationException("Not supported.");
      }

      @Override
      public int getScore() {
            return gw.getScore();
      }

      @Override
      public int getTimer() {
            return gw.getTimer();
      }

      @Override
      public void tick() {
            gw.tick();
      }

      @Override
      public int getLives() {
            return gw.getLives();
      }

      @Override
      public void livesMessage() {
            gw.livesMessage();
      }

      @Override
      public void playerDies() {
            gw.playerDies();
            gw.notifyObservers();
      }

      @Override
      public void notifyObservers() {
            gw.notifyObservers();
      }

      @Override
      public boolean getSound() {
            return gw.getSound();
      }

      @Override
      public String toString() {
            return gw.toString();
      }

      @Override
      public String multiLineLabel() {
            return gw.multiLineLabel();
      }

      @Override
      public void switchSound() {
            gw.switchSound();
      }

      public void setGame(GameProxy gp) {
            gw.setGame(gp);
      }

      public GameProxy getGame() {
            return gw.getGame();
      }

      public boolean isPaused() {
            return gw.isPaused();
      }

      @Override
      public Sweeper getSweeper() {
            return (Sweeper) gw.getSweeper();
      }

      @Override
      public void makeSweeper(int x, int y) {
            gw.makeSweeper(x, y);
      }

}
