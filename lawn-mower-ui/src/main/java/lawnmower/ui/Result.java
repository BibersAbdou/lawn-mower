package lawnmower.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class Result extends JDialog {

  private JPanel contentPane;
  private JButton buttonOK;
  private JTextArea textResult;

  public Result(List<String> result) {
    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(buttonOK);

    buttonOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onOK();
      }
    });

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
