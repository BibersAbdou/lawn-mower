package lawnmower.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Result extends JDialog {

  private JPanel contentPane;
  private JButton buttonOK;
  private JTextArea textResult;

  public Result(List<String> result) {

    contentPane =new JPanel();
    buttonOK =new JButton("Ok");
    textResult = new JTextArea();
    textResult.setSize(new Dimension(100,150));

    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
    contentPane.add(textResult);
    contentPane.add(buttonOK);


    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(buttonOK);

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

    buttonOK.addActionListener(e -> onOK());

    textResult.append("result : \n");
    for (String r: result)
    {

      textResult.append(r);
      textResult.append("\n");
    }
  }

  private void onOK() {
    // add your code here
    dispose();
  }
}
