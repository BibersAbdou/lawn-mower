package lawnmower.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import lawnmower.controller.MowerController;
import lawnmower.domain.Command;
import lawnmower.domain.Coordinate;
import lawnmower.domain.Direction;
import lawnmower.domain.Field;
import lawnmower.domain.Mower;
import lawnmower.domain.MowerCommand;
import lawnmower.input.ScenarioFileReader;

public class LawnUiManager {


  public static final int WINDOW_WIDTH = 1024;
  public static final int WINDOW_HEIGHT = 740;
  private static final int GROW_SIZE = 70;
  private static final ImageIcon GROWN_ICON = new ImageIcon(
      ClassLoader.getSystemResource("grass.PNG"));
  private static final ImageIcon GROWN_HARVESTED_ICON = new ImageIcon(
      ClassLoader.getSystemResource("harvested.PNG"));
  private static final long SPEED = 1000;
  private static Image MOWER_ICON;


  static {
    BufferedImage img = null;
    try {
      img = ImageIO.read(ClassLoader.getSystemResource("mow.png"));
      MOWER_ICON = img.getScaledInstance(GROW_SIZE, GROW_SIZE,
          Image.SCALE_SMOOTH);


    } catch (IOException e) {
      e.printStackTrace();
      MOWER_ICON = new ImageIcon(ClassLoader.getSystemResource("mow.png")).getImage();
    }
  }

  private Map<Coordinate, GrownLabel> grows = new HashMap<>();
  private boolean active = false;
  private Map<Mower, GrownLabel> affectation = new HashMap<>();
  private Map<Mower, MowerInfo> mowerInfo = new HashMap<>();
  private JPanel fieldPanel;
  private JPanel content;
  private JPanel fieldContainer;
  private JPanel InfoPanel;
  private JLabel warnLabel;
  private JPanel controlPanel;
  private JButton play;
  private JButton pause;
  private JButton skip;
  private JButton load;

  private Field field;
  private ScenarioFileReader scenario;
  private MowerController mowerController;
  private MowerCommand mowerCommand;
  private List<String> result;

  public LawnUiManager() {
    InfoPanel.setLayout(new BoxLayout(InfoPanel, BoxLayout.Y_AXIS));
    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
    controlPanel.setSize(new Dimension(WINDOW_WIDTH, 20));
    fieldContainer.setVisible(false);
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    load.addActionListener(e -> {

      JFileChooser j = new JFileChooser();
      int r = j.showSaveDialog(null);

      // if the user selects a file
      if (r == JFileChooser.APPROVE_OPTION) {
        String file = j.getSelectedFile().getAbsolutePath();

        adapt(file);


      }

    });
    play.addActionListener(e -> {

      active = true;
      play();
    });
  }


  private void adapt(String file) {
    try {
      active = false;
      scenario = new ScenarioFileReader(new File(file));
      mowerController = new MowerController(scenario.getLawn());
      mowerCommand = scenario.nextMowerCommand();

      adapt(scenario.getLawn());
      result = new ArrayList<>();
    } catch (Exception e) {
      System.out.println("error. fail to load scenario file cause  " + e.getMessage());
      e.printStackTrace();
    }
  }


  private void play() {

    new Thread(() -> {
      while (active && mowerCommand != null) {
        try {

          Mower mower = mowerCommand.getMower();
          Iterator<Command> it = mowerCommand
              .getCommandIterator();

          SwingUtilities.invokeLater(() -> moveMower(mower));

          Thread.sleep(SPEED);

          while (active && it.hasNext()) {

            SwingUtilities.invokeLater(() -> warn(""));
            Command c = it.next();
            if (c != null) {
              SwingUtilities.invokeLater(() -> displayMove(mower, c.name()));
              Thread.sleep(SPEED);
              mowerController.move(mower, c);
              SwingUtilities.invokeLater(() -> moveMower(mower));

            }
          }

          result.add(mower.toString());
          mowerCommand = scenario.nextMowerCommand();

        } catch (Exception ex) {

          SwingUtilities.invokeLater(() -> warn(ex.getMessage()));
          ex.printStackTrace();
        }
      }

      System.out.println("result : ");
      for (String s : result) {
        System.out.println(s);
      }
    }).start();

  }

  private void displayMove(Mower mower ,String move) {

    MowerInfo info = mowerInfo.get(mower);

    if(info !=null)
    {
      info.update(move);
    }

  }

  private void moveMower(Mower mower) {
    GrownLabel old = affectation.get(mower);
    if (old != null) {
      old.clear();
      old.setVisible(false);
      old.setVisible(true);
    }
    GrownLabel n = grows.get(mower.getCoordinate());

    if (n != null) {
      n.setDirection(mower.getDirection());

      n.setIcon(GROWN_HARVESTED_ICON);
      n.setVisible(false);
      n.setVisible(true);
      affectation.put(mower, n);
    }

    MowerInfo info = mowerInfo.get(mower);

    if (info == null) {
      info = new MowerInfo(mower);

      InfoPanel.add(info);
      InfoPanel.setVisible(false);
      InfoPanel.setVisible(true);
      mowerInfo.put(mower, info);
    }


  }

  private void adapt(Field field) {

    content.setVisible(false);
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
    fieldContainer.setVisible(false);
    this.field = field;

    fieldPanel.setPreferredSize(new Dimension((field.getWidth() + 3) * GROW_SIZE + 20,
        (field.getHeight() + 3) * GROW_SIZE + 20));
    fieldPanel.setMaximumSize(fieldPanel.getPreferredSize());
    fieldPanel.setMinimumSize(fieldPanel.getPreferredSize());
    fieldPanel.setSize(fieldPanel.getPreferredSize());

    FlowLayout manager = new FlowLayout();
    manager.setHgap(1);
    manager.setVgap(1);
    fieldPanel.setLayout(manager);
    fieldPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    clear();
    generateLawn();

    content.setVisible(true);
    fieldContainer.setVisible(true);
  }

  private void clear() {

    grows.values().forEach(g -> {
      fieldPanel.remove(g);
    });

    mowerInfo.values().forEach(i ->
    {
      InfoPanel.remove(i);
    });
    grows = new HashMap<>();
    affectation = new HashMap<>();
    mowerInfo = new HashMap<>();
  }


  private void generateLawn() {


    for (int i = 0; i <= field.getWidth()+2; i++) {
      addAxisLabl("" , SwingConstants.CENTER, SwingConstants.CENTER);
    }

    for (int j = field.getHeight(); j >= 0; j--) {

      addAxisLabl("" + j, SwingConstants.CENTER, SwingConstants.CENTER);

      for (int i = 0; i <= field.getWidth(); i++) {
        GrownLabel rec = new GrownLabel();
        rec.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rec.setPreferredSize(new Dimension(GROW_SIZE, GROW_SIZE));
        rec.setSize(new Dimension(GROW_SIZE, GROW_SIZE));

        grows.put(Coordinate.of(i, j), rec);
        fieldPanel.add(rec);


      }

      addAxisLabl("" , SwingConstants.CENTER, SwingConstants.CENTER);
    }

    addAxisLabl("y/x", SwingConstants.CENTER, SwingConstants.CENTER);
    for (int i = 0; i <= field.getWidth(); i++) {
      addAxisLabl("" + i, SwingConstants.CENTER, SwingConstants.CENTER);

    }


  }

  private void addAxisLabl(String s, int hPos, int vPos) {
    JLabel label = new JLabel(s);
    label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    label.setPreferredSize(new Dimension(GROW_SIZE, GROW_SIZE));
    label.setSize(new Dimension(GROW_SIZE, GROW_SIZE));
    label.setHorizontalTextPosition(hPos);
    label.setHorizontalAlignment(hPos);
    label.setVerticalAlignment(vPos);
    fieldPanel.add(label);
  }


  public JPanel getFieldPanel() {
    return fieldPanel;
  }


  public JPanel getContent() {
    return content;
  }


  public JPanel getFieldContainer() {
    return fieldContainer;
  }

  public void warn(String format) {
    this.warnLabel.setText(format);
  }


  class GrownLabel extends JLabel {

    GrownLabel() {
      super(GROWN_ICON);
    }

    private Integer lawndirection = null;

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      if (lawndirection != null) {
        if (MOWER_ICON != null) {
          Graphics2D g2 = (Graphics2D) g;
          g2.rotate(((3 + lawndirection) * Math.PI) / 2, this.getSize().getWidth() / 2,
              this.getSize().getHeight() / 2);
          g2.drawImage(MOWER_ICON, 0, 0, null);
        }
      }


    }


    void clear() {
      this.lawndirection = null;
    }

    void setDirection(Direction direction) {
      this.lawndirection = direction.ordinal();
    }
  }

  static class MowerInfo extends JPanel {


    private Label infoLabel;
    private Label movesLabel;
    private String info = "";
    private String moves = "";
    private Mower mower;

    MowerInfo(Mower mower) {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

      LineBorder border = new LineBorder(Color.BLACK, 1);
      setBorder(border);

      info = "Mower " + mower.getId() + " : " + mower.toString();
      moves = "Moves : ";

      infoLabel = new Label(info);
      movesLabel = new Label(moves);

      setSize(new Dimension(WINDOW_WIDTH, 50));

      add(infoLabel);
      add(movesLabel);
      this.mower = mower;
    }

    void update(String move) {
      if (move != null) {
        moves += move;
      }

      info = "Mower " + mower.getId() + " : " + mower.toString();

      infoLabel.setText(info);
      movesLabel.setText(moves);

      infoLabel.setVisible(false);
      infoLabel.setVisible(true);

      movesLabel.setVisible(false);
      movesLabel.setVisible(true);
    }
  }

}