package a4;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class Game extends JFrame implements ActionListener {

      private Random r = new Random();

      private GameWorld gw;
      private GameWorldProxy gwp;
      private GameProxy gp;
      private ScoreView scoreView;
      private MapView mapView;
      private javax.swing.Timer timer;
      private boolean paused = false;

      // all the button objects
      private JButton myLeftButton1;
      private JButton myLeftButton2;
      private JButton myLeftButton3;
      private JButton myLeftButton4;
      private JButton myLeftButton5;
      private JButton myLeftButton6;
      private JButton myLeftButton7;
      private JButton myLeftButton8;
      private JButton myLeftButton9;
      private JButton myLeftButton10;
      private JButton myLeftButton11;
      private JButton myLeftButton12;

      // all the command objects
      private SnakeHitBody snakeHitBody;
      private SnakeHitBird snakeHitBird;
      private SnakeHitMoney snakeHitMoney;
      private SnakeHitFood snakeHitFood;
      private SnakeHitWall snakeHitWall;
      private SnakeHitWeasel snakeHitWeasel;
      private QuitCommand quitCommand;
      private TickCommand tickCommand;
      private MCommand mCommand;
      private SaveCommand saveCommand;
      private UndoCommand undoCommand;
      private SoundCommand soundCommand;
      private AboutCommand aboutCommand;
      private ChangeStratCommand changeStratCommand;
      private UpCommand upCommand;
      private RightCommand rightCommand;
      private DownCommand downCommand;
      private LeftCommand leftCommand;
      private PlayCommand playCommand;
      private DeleteCommand deleteCommand;

      // for the button input
      private InputMap imap;
      private ActionMap amap;

      // have the files ready to use, make it public
      protected static ResourceLibrary myLib;

      public Game() {
            // load the resources
            myLib = new ResourceLibrary();

            gw = new GameWorld();
            gp = new GameProxy(this);
            gw.setGame(gp);
            gw.setGame(this);
            gwp = new GameWorldProxy(gw);
            scoreView = new ScoreView(gwp);
            mapView = new MapView(gwp);
            // add the mouse listener
            mapView.addMouseListener(mapView);

            gw.addObserver(scoreView);
            gw.addObserver(mapView);

            gw.initLayout();
            createCommands();

            // initialize the timer (use 40)
            timer = new javax.swing.Timer(40, tickCommand);
            timer.start();

            setTitle("Snake!");
            setSize(1200, 902);

            // center the jframe
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            setJMenuBar(createMenuBar());
            add(createTopPanel(), BorderLayout.NORTH);
            add(createLeftPanel(), BorderLayout.WEST);
            add(createMiddlePanel());
            setVisible(true);
            requestFocus();     // note: must put this after visible is true
      }

      public javax.swing.Timer getTimer() {
            return timer;
      }

      public void startTimer() {
            timer.start();
      }

      public void stopTimer() {
            timer.stop();
      }

      public boolean isPaused() {
            return paused;
      }

      // proxy was not required for commands
      private void createCommands() {
            snakeHitBody = new SnakeHitBody(gw);
            quitCommand = new QuitCommand();
            snakeHitBird = new SnakeHitBird(gw);
            snakeHitMoney = new SnakeHitMoney(gw);
            tickCommand = new TickCommand(gw);
            mCommand = new MCommand(gw);
            snakeHitFood = new SnakeHitFood(gw);
            snakeHitWall = new SnakeHitWall(gw);
            snakeHitWeasel = new SnakeHitWeasel(gw);
            saveCommand = new SaveCommand();
            undoCommand = new UndoCommand();
            soundCommand = new SoundCommand(gw);
            aboutCommand = new AboutCommand();
            changeStratCommand = new ChangeStratCommand(gw);
            upCommand = new UpCommand(gw);
            rightCommand = new RightCommand(gw);
            downCommand = new DownCommand(gw);
            leftCommand = new LeftCommand(gw);
            playCommand = new PlayCommand(gp);
            deleteCommand = new DeleteCommand(gw);

      }

      private JPanel createLeftPanel() {
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new GridLayout(20, 1, 0, 5));
            leftPanel.setBorder(new TitledBorder("Commands: "));
//            leftPanel.setBounds(1000, 1000, 1000, 1000);

            myLeftButton11 = new JButton("Pause");
            myLeftButton11.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
            myLeftButton11.addActionListener(this);
            leftPanel.add(myLeftButton11);

            myLeftButton12 = new JButton("      Delete      ");
            myLeftButton12.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
            myLeftButton12.addActionListener(this);
            myLeftButton12.setEnabled(false);
            leftPanel.add(myLeftButton12);

            JButton myCheckBox = new JButton("Sweeper Outline");
            myCheckBox.setAction(new SwitchOutlineCommand(gw));
            leftPanel.add(myCheckBox);

            JButton myCheckBox2 = new JButton("Center Switch");
            myCheckBox2.setAction(new ShowSweeperCenterCommand(gw));
            leftPanel.add(myCheckBox2);

            JButton myCheckBox3 = new JButton("Rainbowfy");
            myCheckBox3.setAction(new RainbowfyCommand(gw));
            leftPanel.add(myCheckBox3);

            myLeftButton9 = new JButton("Quit");
            myLeftButton9.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
            myLeftButton9.setAction(quitCommand);
            leftPanel.add(myLeftButton9);

            return leftPanel;
      }

      private JMenuBar createMenuBar() {
            JMenuBar bar = new JMenuBar();
            JMenu fileMenu = new JMenu("New");
            JMenuItem checkBox;

            JMenuItem mItem = new JMenuItem("Save");
            mItem.setAction(saveCommand);
            fileMenu.add(mItem);

            mItem = new JMenuItem("Undo");
            mItem.setAction(undoCommand);
            fileMenu.add(mItem);

            checkBox = new JCheckBoxMenuItem("Sound");
            checkBox.setAction(soundCommand);
            checkBox.setSelected(true);
            fileMenu.add(checkBox);

            mItem = new JMenuItem("About");
            mItem.setAction(aboutCommand);
            fileMenu.add(mItem);

            mItem = new JMenuItem("Quit");
            mItem.setAction(quitCommand);
            fileMenu.add(mItem);

            JMenu commandMenu = new JMenu("Command");

            JMenuItem jmItem1 = new JMenuItem("Snake hit Body (1)");
            jmItem1.setAction(snakeHitBody);
            commandMenu.add(jmItem1);

            JMenuItem jmItem2 = new JMenuItem("Bird hit Snake (2)");
            jmItem2.setAction(snakeHitBird);
            commandMenu.add(jmItem2);

            JMenuItem jmItem3 = new JMenuItem("Snake hit Money (3)");
            jmItem3.setAction(snakeHitBody);
            commandMenu.add(jmItem3);

            JMenuItem jmItem4 = new JMenuItem("Snake eats Food (4)");
            jmItem4.setAction(snakeHitFood);
            commandMenu.add(jmItem4);

            JMenuItem jmItem5 = new JMenuItem("Snake hit Wall (5)");
            jmItem5.setAction(snakeHitWall);
            commandMenu.add(jmItem5);

            JMenuItem jmItem6 = new JMenuItem("Weasel hit Snake");
            jmItem6.setAction(snakeHitWeasel);
            commandMenu.add(jmItem6);

            JMenuItem jmItem7 = new JMenuItem("Change Strategies");
            jmItem7.setAction(changeStratCommand);
            commandMenu.add(jmItem7);

            JMenuItem jmItem8 = new JMenuItem("Tick");
            jmItem8.setAction(tickCommand);
            commandMenu.add(jmItem8);

            bar.add(fileMenu);
            bar.add(commandMenu);
            return bar;
      }

      private JPanel createTopPanel() {
            return scoreView;
      }

      // note: bounded the key bindings within this code
      private JPanel createMiddlePanel() {

            JPanel middlePanel = mapView;

            // create the bindings
            int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
            imap = middlePanel.getInputMap(mapName);

            KeyStroke upKey = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
            KeyStroke downKey = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
            KeyStroke leftKey = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
            KeyStroke rightKey = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
            KeyStroke spaceKey = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
            KeyStroke delKey = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);

            imap.put(upKey, "upKey");
            imap.put(downKey, "downKey");
            imap.put(leftKey, "leftKey");
            imap.put(rightKey, "rightKey");
            imap.put(spaceKey, "spaceKey");
            imap.put(delKey, "delKey");

            amap = middlePanel.getActionMap();
            amap.put("upKey", upCommand);
            amap.put("downKey", downCommand);
            amap.put("leftKey", leftCommand);
            amap.put("rightKey", rightCommand);
            amap.put("spaceKey", changeStratCommand);
            amap.put("delKey", deleteCommand);

            this.requestFocus();

            return middlePanel;
      }

      @Override
      public void actionPerformed(ActionEvent e) {
            // pauses the game
            // that button is the pause button
            if (e.getSource() == myLeftButton11) {
                  // stop the music
                  gw.stopMusic();

                  // you have now paused the game, so do this stuff
                  if (myLeftButton11.getText().compareTo("Pause") == 0) {
                        myLeftButton11.setText("Play");
                        stopTimer();
                        paused = true;
                        myLeftButton12.setEnabled(true);
                        turnOffArrows();
                  } // You get here if you hit the play button (used 
                  // to be the pause button but changed
                  // selected objects in the game world
                  else {
                        // if the sound is on start the music again
                        // we turn off music in pause mode 
                        if (gw.getSound()) {
                              gw.startMusic();
                        }
                        // we change the settings to play again
                        turnOnArrows();
                        myLeftButton11.setText("Pause");
                        startTimer();
                        myLeftButton12.setEnabled(false);
                        paused = false;

                        // turn off all the highlighting
                        for (GameObject o : gw.getWorld()) {
                              if (o instanceof ISelectable) {
                                    ((ISelectable) o).turnOff();
                              }
                        }
                  }

            } // this is your delete button
            else if (e.getSource() == myLeftButton12) {
                  ArrayList<GameObject> removeList = new ArrayList<>();
                  // if any object is selected and you push this buttong
                  // then you add them to a list
                  for (GameObject o : gw.getWorld()) {
                        if (o instanceof ISelectable
                                && ((ISelectable) o).isSelected()) {
                              removeList.add(o);
                        }
                  }

                  // now that you are done iterating, remove those objects
                  // from the list you added them into
                  for (GameObject removed : removeList) {
                        gw.getWorld().remove(removed);
                  }
            }
      }

      public void turnOnArrows() {
            amap.put("upKey", upCommand);
            amap.put("downKey", downCommand);
            amap.put("leftKey", leftCommand);
            amap.put("rightKey", rightCommand);
            amap.put("spaceKey", changeStratCommand);
      }

      public void turnOffArrows() {
            amap.put("upKey", null);
            amap.put("downKey", null);
            amap.put("leftKey", null);
            amap.put("rightKey", null);
      }

      // the snake is calling these methods to ensure that you cannot
      // directly turn backwards and same for all directions
      // turn on all the arrows disable the one that needs disabling
      public void disableLeft() {
            turnOnArrows();
            amap.put("leftKey", null);
      }

      public void disableRight() {
            turnOnArrows();
            amap.put("rightKey", null);

      }

      public void disableUp() {
            turnOnArrows();
            amap.put("upKey", null);
      }

      public void disableDown() {
            turnOnArrows();
            amap.put("downKey", null);
      }
}
