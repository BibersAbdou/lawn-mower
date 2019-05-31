package lawnmower;

import java.awt.Dimension;
import javax.swing.JFrame;
import lawnmower.ui.LawnUiManager;

public class UILawnMowerApp {





  private static LawnUiManager field ;

  private static void show()
  {
    field = new LawnUiManager();
    JFrame jFrame = new JFrame("Lawn mower");
    jFrame.setContentPane(field.getContent());
    jFrame.setPreferredSize(new Dimension(LawnUiManager.WINDOW_WIDTH, LawnUiManager.WINDOW_HEIGHT));
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jFrame.pack();
    jFrame.setVisible(true);
  }

  public static void main(String[] args)
  {

    show();

  }



}
