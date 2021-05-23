package view;

import common.Consts;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class StartAppDialog extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField addrTextField;
    private JTextField portTextField;
    private JLabel addrLabel;
    private JLabel portLabel;
    private JTextField nameTextField;
    private JLabel usernameLabel;

    public StartAppDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        setVisible(false);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public Map<String, String> showDialog() {
        this.pack();
        this.setBounds(200, 200, 450, 210);
        this.setVisible(true);

        Map<String, String> res = new HashMap<>();
        res.put(Consts.ServerView.IP_ADDRESS, getAddrTextField());
        res.put(Consts.ServerView.PORT, getPortTextField());
        res.put(Consts.ServerView.USERNAME, getNameTextField());

        return res;
    }

    public String getAddrTextField() {
        return addrTextField.getText();
    }

    public String getPortTextField() {
        return portTextField.getText();
    }

    public String getNameTextField() {
        return nameTextField.getText();
    }
}
