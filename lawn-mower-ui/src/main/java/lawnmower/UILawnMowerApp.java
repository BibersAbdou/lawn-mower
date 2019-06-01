package lawnmower;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import lawnmower.ui.LawnUiManager;

public class UILawnMowerApp {





  private static LawnUiManager field ;

  private static void show()
  {
    field = new LawnUiManager();
    JFrame jFrame = new JFrame("Lawn mower");
    jFrame.setContentPane(field.getContent());
    jFrame.setPreferredSize(new Dimension(LawnUiManager.WINDOW_WIDTH, LawnUiManager.WINDOW_HEIGHT));
    jFrame.setSize(new Dimension(LawnUiManager.WINDOW_WIDTH, LawnUiManager.WINDOW_HEIGHT));
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    jFrame.setLocation(dim.width/2-jFrame.getSize().width/2, dim.height/2-jFrame.getSize().height/2);


    jFrame.setResizable(false);
    jFrame.pack();

    jFrame.setJMenuBar(field.getMenu());


    jFrame.setVisible(true);
  }

  public static void main(String[] args)
  {

    show();

  }



}
