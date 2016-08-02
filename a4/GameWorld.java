package a4;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.*;
import javax.swing.*;

public class GameWorld implements IGameWorld, IObservable {

      private Random r = new Random();

      private ArrayList<GameObject> worldList = new ArrayList<GameObject>();
      private ArrayList<GameObject> toDel = new ArrayList<GameObject>();
      private ArrayList<IObserver> observerList = new ArrayList<IObserver>();
      private GameWorldIterator iter;
      private static int lives = 3;
      private static int score = 0;
      private static int timer = 0;
      private static boolean sound = true;
      private GameProxy gp;
      private Game g;
      private final int TIMER_VALUE = 50;
      private Sound bgMusic = new Sound("sound" + File.separator + "Gasoline Rainbows (loop).wav");
      private boolean playing = false;
      private final int MAX_WALL_THICKNESS = 20;
      // limit the randomness of the wall to be within 1000 pixels 
      private final int MAX_WALL_VARIABLE = 1000;
      private final int HOW_MANY_WALLS = 3;
      private boolean collision = false;

      public boolean isPlaying() {
            return playing;
      }

      public void stopMusic() {
            bgMusic.stop();
            playing = false;
      }

      public void startMusic() {
            try {
                  bgMusic.loop();
            } catch (Exception e) {
                  e.printStackTrace();
            }
            playing = true;
      }

      public void loopMusic() {
            if (sound && !playing) {
                  try {
                        bgMusic.loop();
                        playing = true;
                  } catch (Exception e) {
                        e.printStackTrace();
                  }
            }
      }

      public void initLayout() {
            worldList.clear();
            makeSnake();
            makeFood();
            makeMoney();
            makeBird();
            makeWeasels();
            makeWalls();
            loopMusic();
      }

      // make a bunch of factory methods and randomize the GameObject's locations/speeds/directions
      public void makeSnake() {
//            worldList.add(new Snake(r.nextInt(600), r.nextInt(600), Color.MAGENTA));
            worldList.add(new Snake(r.nextInt(300) + 220, r.nextInt(300) + 220, Color.MAGENTA, 20));
      }

      public void makeFood() {
            worldList.add(new Food(r.nextInt(893) + 50, r.nextInt(660) + 50, Color.YELLOW, 50));
      }

      public void makeMoney() {

            worldList.add(new Money(r.nextInt(880) + 60, r.nextInt(650) + 55, Color.blue, 60));

      }

      public void makeBird() {

            // temporary hard code the bird for testing purposes
            Bird bird1 = new Bird(r.nextInt(600) + 100, r.nextInt(600) + 100, Color.BLUE, r.nextInt(10) + 1, r.nextInt(360), 50, 70);

            Bird bird2 = new Bird(r.nextInt(600) + 100, r.nextInt(600) + 100, Color.BLUE, r.nextInt(10) + 1, r.nextInt(360), 50, 70);
            worldList.add(bird1);
            worldList.add(bird2);

      }

      public void makeWeasels() {
            // testing strategy method
            Weasel weasel = new Weasel(r.nextInt(600) + 100, r.nextInt(600) + 100, Color.ORANGE, r.nextInt(10) + 1, r.nextInt(360), 20, 50);
            Weasel weasel2 = new Weasel(r.nextInt(600) + 100, r.nextInt(600) + 100, Color.ORANGE, r.nextInt(10) + 1, r.nextInt(360), 20, 50);

            weasel.addStrat(new ChaseSnakeStrat(this));
            weasel.addStrat(new MoveWithinBoundStrat(this));
            weasel2.addStrat(new MoveWithinBoundStrat(this));
            weasel2.addStrat(new ChaseSnakeStrat(this));

            worldList.add(weasel);
            worldList.add(weasel2);
      }

      // makes a sweeper in the gameworld at a random location
      public void makeSweeper() {
            Point2D p1 = new Point(r.nextInt(500), r.nextInt(500));
            Point2D p2 = new Point(r.nextInt(500), r.nextInt(500));
            Point2D p3 = new Point(r.nextInt(500), r.nextInt(500));
            Point2D p4 = new Point(r.nextInt(500), r.nextInt(500));

            Sweeper swp = new Sweeper(p1, p2, p3, p4, r.nextInt(893) + 50, r.nextInt(660) + 50, r.nextInt(360), r.nextInt(20) + 80);

            // build the translation to the origin
            // all creations of sweepers are at the origin now
            worldList.add(swp);
      }

      // makes a sweeper at a given location x and y with random speed
      public void makeSweeper(int x, int y) {
            Point2D p1 = new Point(r.nextInt(500), r.nextInt(500));
            Point2D p2 = new Point(r.nextInt(500), r.nextInt(500));
            Point2D p3 = new Point(r.nextInt(500), r.nextInt(500));
            Point2D p4 = new Point(r.nextInt(500), r.nextInt(500));

            // note that due to transformation, we must compensate for y
            Sweeper swp = new Sweeper(p1, p2, p3, p4, x, y, r.nextInt(360), r.nextInt(20) + 80);

            // build the translation to the origin
            // all creations of sweepers are at the origin now
            worldList.add(swp);
      }

      // hard coded :(
      public void makeWalls() {

            // make the real walls
            makeRealWalls(HOW_MANY_WALLS);

            // these are the shadow walls
            worldList.add(new ShadowWall(14, 396, Color.BLACK, MAX_WALL_THICKNESS, 784));
            worldList.add(new ShadowWall(509, 778, Color.BLACK, 968, MAX_WALL_THICKNESS));
            worldList.add(new ShadowWall(1003, 396, Color.BLACK, MAX_WALL_THICKNESS, 784));
            worldList.add(new ShadowWall(509, 14, Color.BLACK, 968, MAX_WALL_THICKNESS));

      }

      public Wall makeHorizontalWall(Color c, int wallVariable, int h) {
            // wallvariable here is the thing that varies
            // make x and y based off of that
            return new Wall(r.nextInt(1000 + wallVariable) - wallVariable / 2, r.nextInt(780), c, wallVariable, h);
      }

      public Wall makeVerticalWall(Color c, int w, int wallVariable) {
            return new Wall(r.nextInt(1000), r.nextInt(780 + wallVariable) - wallVariable / 2, c, w, wallVariable);
      }

      // this method makes all the real walls
      public void makeRealWalls(int numberOfWalls) {
            // these are the real walls
            // we make the real walls first, so the shadow walls
            // won't interfere with their collision effect
            // note the limits of the coordinate system
            // x: 0 - 968 approximately
            // y: 0 - 784 approximately
            // wall's max thickness 2000
            // make 1 random horizontal wall
            for (int i = 0; i < numberOfWalls; i++) {
                  // if this is 1, then it's vertical, otherwise horizontal
                  int wallDirection = r.nextInt(2);

                  // make the wall's minimum length make it at least a square
                  int wallLength = r.nextInt(MAX_WALL_VARIABLE) + 20;

                  if (wallDirection == 1) {
                        worldList.add(makeVerticalWall(Color.BLACK, MAX_WALL_THICKNESS, wallLength));
                  } else {
                        worldList.add(makeHorizontalWall(Color.BLACK, wallLength, MAX_WALL_THICKNESS));
                  }
            }
      }

      @Override
      public ArrayList<GameObject> getWorld() {
            return worldList;
      }

      public Sweeper getSweeper() {
            return (Sweeper) worldList.get(worldList.size() - 1);
      }

      @Override
      public Snake getSnake() {
            return (Snake) worldList.get(0);
      }

      @Override
      public void addScore(int n) {
            score += n;
      }

      @Override
      public int getScore() {
            return score;
      }

      @Override
      public int getTimer() {
            return timer;
      }

      // implementation of Iterator method
      @Override
      public void tick() {
            timer++;

            // make a sweeper every 10 seconds or so
            // 22 ticks is about 1 sec, so 220 is about 10 sec
            // but first remove all the old sweepers
            if (timer % 220 == 0) {
                  // gather the sweepers
                  for (GameObject o : worldList) {
                        if (o instanceof Sweeper) {
                              toDel.add(o);
                        }
                  }
                  // delete them all
                  worldList.removeAll(toDel);

                  // clear the to Delete buffer
                  toDel.clear();

                  // add the new sweeper
                  makeSweeper();
            }

            collision = false;

            // move and age the components 
            iter = getIterator();
            while (iter.hasNext()) {

                  // move the objects
                  if (worldList.get(iter.nextIndex()) instanceof MovableGameObject) {
                        ((MovableGameObject) worldList.get(iter.nextIndex())).move(TIMER_VALUE);
                  }
                  // age the objects
                  if (worldList.get(iter.nextIndex()) instanceof FixedGameObject) {
                        ((FixedGameObject) worldList.get(iter.nextIndex())).age();
                  }

                  iter.next();
            }

            iter = getIterator();

            // turned on single collision, only 1 iterator checks if
            // snake colliders with anything else
            // had a bug with multiple collisions happening, so we made
            // a flag to check if collisions happened already
            if (!collision) {
                  ICollider snake = (ICollider) iter.next();
                  while (iter.hasNext()) {
                        ICollider otherObj = (ICollider) iter.next();
                        if (otherObj != snake) {
                              if (snake.collidesWith(otherObj)) {
                                    otherObj.handleCollision(this);
                                    collision = true;

                                    // break out of the statement to avoid potential
                                    // multiple collisions
                                    break;
//                              snake.handleCollision(this);
                              }
                        }
                  }
            }

            // check for collision with body segments
            // this must be seperate because of the way the body
            // segments are stored within the snake class, in an arraylist
            // check if the had collides with any body segments
            if (!collision) {

                  iter = getIterator();
                  ArrayList<MovableGameObject> snakeBody = ((Snake) iter.next()).getfullBody();
                  Iterator snakeBodyIter = snakeBody.iterator();
                  ICollider head = (ICollider) snakeBodyIter.next();
                  while (snakeBodyIter.hasNext()) {
                        ICollider otherObj = (ICollider) snakeBodyIter.next();
                        if (otherObj != head) {
                              if (otherObj.collidesWith(head)) {
                                    otherObj.handleCollision(this);
                                    collision = true;
                                    break;
//                              snake.handleCollision(this);
                              }
                        }
                  }
            }

            // check here for sweeper collisions
            if (!collision) {
                  outerLoop:
                  for (GameObject s : worldList) {
                        if (s instanceof Sweeper) {
                              for (GameObject o : worldList) {
                                    if (s != o && ((Sweeper) s).collidesWith((ICollider) o)) {
                                          if (o instanceof Snake) {
                                                ((Sweeper) s).handleCollision(this, o);
                                                // special case to deal with the snake collision
                                                ((Sweeper) s).playSound1(this);
                                                this.playerDies();
                                                collision = true;
                                                break outerLoop;
                                                // if a snake hit the sweeper, we can break out of the loop

                                          } else if (!(o instanceof Wall) && !(o instanceof BodySegment)) {
                                                // can't really have sweeper remove things while inside this loops
                                                ((Sweeper) s).playSound2(this);
                                                toDel.add(o);
                                          }
                                    }
                              }
                        }
                  }
            }

            // now that we are out of the loop we can safely delete them
            worldList.removeAll(toDel);
            toDel.clear();

            // make sure that the snake cannot spin
            Snake snake = (Snake) (getSnake());
            snake.noSpin(g);

      }

      @Override
      public int getLives() {
            return lives;
      }

      @Override
      public boolean getSound() {
            return sound;
      }

      public boolean getCollisionFlag() {
            return collision;
      }

      @Override
      public void switchSound() {
            sound = !sound;
      }

      @Override
      public void livesMessage() {
            if (getLives() > 1) {
                  System.out.println("Player has " + getLives() + " lives left");
            } else {
                  System.out.println("Player has " + getLives() + " life left");
            }
      }

      @Override
      public void playerDies() {
            lives--;
            score = 0;
            timer = 0;
            if (lives == 0) {
                  int answer = JOptionPane.showConfirmDialog(null, "Do you "
                          + " want to play again?", "Game Over!", JOptionPane.YES_NO_OPTION,
                          JOptionPane.QUESTION_MESSAGE);

                  if (answer != JOptionPane.YES_OPTION) {
                        System.exit(0);
                  } else {
                        lives += 3;
                  }
            }
            initLayout();
            notifyObservers();

            if (lives != 0) {
                  try {
                        Thread.sleep(200);
                  } catch (Exception e) {
                        e.printStackTrace();
                  }
            }

      }

      @Override
      public String toString() {
            String str = new String();
            for (GameObject gameObject : worldList) {
                  str += gameObject.toString();
            }
            return str;
      }

      @Override
      public String multiLineLabel() {
            String str = new String();
            str += "<html>";
            str += getSnake().multiLineLabel();
            for (GameObject o : worldList) {
                  if (o instanceof Snake) {

                  } else {
                        str += o.toString() + "<br>";
                  }
            }
            str += "</html>";
            return str;
      }

      public GameWorldIterator getIterator() {
            return new GameWorldIterator(getWorld());
      }

      @Override
      public void addObserver(IObserver o) {
            observerList.add(o);
      }

      public void setGame(GameProxy gp) {
            this.gp = gp;
      }

      public GameProxy getGame() {
            return gp;
      }

      public void setGame(Game g) {
            this.g = g;
      }

      @Override
      public boolean isPaused() {
            return gp.isPaused();
      }

      @Override
      public void notifyObservers() {
            for (IObserver o : observerList) {
                  o.update();
            }
      }

}
