package a4;

import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class ResourceLibrary {

      private static Image moneyImg1;
      private static Image moneyImg2;
      private static Image moneyImg3;
      private static Image moneyImgHighlighted1;
      private static Image moneyImgHighlighted2;
      private static Image moneyImgHighlighted3;
      private static Image foodImg;
      private static Sound moneySound;
      private static Sound foodSound1;
      private static Sound foodSound2;
      private static Sound birdSound;
      private static Sound wallSound;
      private static Sound weaselSound;

      public ResourceLibrary() {
            try {
                  moneyImg1 = ImageIO.read(new File("images" + File.separator + "money_bag.png"));
                  moneyImgHighlighted1 = ImageIO.read(new File("images" + File.separator + "money_bag_highlighted.png"));
                  moneyImg2 = ImageIO.read(new File("images" + File.separator + "piggy_bank.png"));
                  moneyImgHighlighted2 = ImageIO.read(new File("images" + File.separator + "piggy_bank_highlighted.png"));

                  moneyImg3 = ImageIO.read(new File("images" + File.separator + "cash_n_coins.png"));
                  moneyImgHighlighted3 = ImageIO.read(new File("images" + File.separator + "cash_n_coins_highlighted.png"));

                  foodImg = ImageIO.read(new File("images" + File.separator + "apple2.png"));

                  moneySound = new Sound("sound" + File.separator + "cash-register-01_converted.wav");
                  foodSound1 = new Sound("sound" + File.separator + "cork_pop_x.wav");
                  foodSound2 = new Sound("sound" + File.separator + "bloop_x.wav");
                  birdSound = new Sound("sound" + File.separator + "rooster-1_converted.wav");
                  wallSound = new Sound("sound" + File.separator + "explosion_x.wav");
                  weaselSound = new Sound("sound" + File.separator + "cat_meow8_converted.wav");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public static Image getMoneyImg1() {
            return moneyImg1;
      }

      public static Image getMoneyImg2() {
            return moneyImg2;
      }

      public static Image getMoneyImg3() {
            return moneyImg3;
      }
      public static Image getMoneyImgHighlighted1() {
            return moneyImgHighlighted1;
      }

      public static Image getMoneyImgHighlighted2() {
            return moneyImgHighlighted2;
      }

      public static Image getMoneyImgHighlighted3() {
            return moneyImgHighlighted3;
      }

      public void playMoneySound() {
            try {
                  moneySound.play();
            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

}
