package a4;

import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class ScoreView extends JPanel implements IObserver {

      private IGameWorld gw;

      private String timeString = "Time: " + 0;
      private JLabel timeLabel = new JLabel(timeString);

      private String scoreString = "Score: " + 0;
      private JLabel scoreLabel = new JLabel(scoreString);

      private String livesString = "Lives: " + 3;
      private JLabel livesLabel = new JLabel(livesString);

      private String soundString = "Sound: " + "Off";
      private JLabel soundLabel = new JLabel(soundString);

      public ScoreView(IGameWorld gw) {
            JPanel upperPanel = new JPanel();
            upperPanel.add(timeLabel);
            upperPanel.add(scoreLabel);
            upperPanel.add(livesLabel);
            upperPanel.add(soundLabel);

            this.add(upperPanel);

            this.setBorder(new LineBorder(Color.blue, 2));
            this.gw = gw;
      }

      @Override
      public void update() {
            // must hard code to get the 1 sec time interval correct :[
            // note: changing msec changes this formula :[
            if (gw.getTimer() % 22 == 0) {
                  timeLabel.setText("Time: " + gw.getTimer()/22);
            }
            
//            timeLabel.setText("Time: " + gw.getTimer());
            scoreLabel.setText("Score: " + gw.getScore());
            livesLabel.setText("Lives: " + gw.getLives());

            if (gw.getSound()) {
                  soundLabel.setText("Sound: " + "On");
            } else {
                  soundLabel.setText("Sound: " + "Off");
            }

      }
}
