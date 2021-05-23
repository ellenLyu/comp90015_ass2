package view;

import common.Consts;
import service.iWhiteboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.HashMap;

public class ChatPanel extends JPanel {


    private final JTextField inputField;

    private final JTextPane chatPane;

    private iWhiteboard whiteboard;

    private HashMap<String, String> userInfo;

    public ChatPanel() {

        setBorder(new EmptyBorder(5, 5, 5, 5));

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{200};
        gridBagLayout.rowHeights = new int[]{270, 30};
        gridBagLayout.columnWeights = new double[]{1.0};
        gridBagLayout.rowWeights = new double[]{0.9, 0.1};
        setLayout(gridBagLayout);

        chatPane = new JTextPane();
        chatPane.setPreferredSize(new Dimension(200, 200));
        StyledDocument doc = chatPane.getStyledDocument();
        this.addStylesToDocument(doc);
        JScrollPane scrollPane = new JScrollPane(chatPane);

        GridBagConstraints gbc_ChatPane = new GridBagConstraints();
        gbc_ChatPane.fill = GridBagConstraints.BOTH;
        gbc_ChatPane.gridx = 0;
        gbc_ChatPane.gridy = 0;
        gbc_ChatPane.gridwidth = 1;
        gbc_ChatPane.gridheight = 1;
        add(scrollPane, gbc_ChatPane);

        inputField = new JTextField("Press ENTER...");
        inputField.setForeground(Color.GRAY);
        inputField.setPreferredSize(new Dimension(200, 40));
        GridBagConstraints gbc_InputPane = new GridBagConstraints();
        gbc_InputPane.fill = GridBagConstraints.BOTH;
        gbc_InputPane.gridx = 0;
        gbc_InputPane.gridy = 1;
        gbc_InputPane.gridwidth = 1;
        gbc_InputPane.gridheight = 1;
        add(inputField, gbc_InputPane);
        this.addChatInput();

        setPreferredSize(new Dimension(200, 300));
        setVisible(true);
    }

    public void appendMessage(String type, String username, String message) {
        System.out.println(type + message);
        StyledDocument doc = chatPane.getStyledDocument();
        try {
            if (Consts.Service.SERVER_NAME.equals(type)) {
                doc.insertString(doc.getLength(), type, doc.getStyle("server"));
                doc.insertString(doc.getLength(), message + "\n", doc.getStyle("server"));
            } else {
                doc.insertString(doc.getLength(), type, doc.getStyle("italic"));
                doc.insertString(doc.getLength(), username + ": ", doc.getStyle("bold"));
                doc.insertString(doc.getLength(), message + "\n", doc.getStyle("regular"));
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void addChatInput() {

        inputField.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if ("Press ENTER...".equals(inputField.getText())) {
                    inputField.setText("");
                    inputField.setForeground(Color.BLACK);
                }
            }
        });

        inputField.addKeyListener(new KeyAdapter() {

            /**
             * The keyListener listen the ENTER key pressed event
             * @param e keyPressed event
             */
            @Override
            public void keyPressed(KeyEvent e) {

                if (KeyEvent.VK_ENTER == e.getKeyChar() && !"".equals(inputField.getText())) {

                    String input = inputField.getText();
                    try {
                        whiteboard.broadcast(userInfo.get(Consts.Service.USERNAME), input);
                    } catch (RemoteException remoteException) {
                        remoteException.printStackTrace();
                    }
//                    appendMessage("[Manager] test: ", "Hello");
                    inputField.setText("");
                }
            }
        });
    }

    /**
     * Add the styles to the StyledDocument
     *
     * @param doc StyledDocument
     */
    private void addStylesToDocument(StyledDocument doc) {
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "Monospaced");

        Style bold = doc.addStyle("bold", regular);
        StyleConstants.setBold(bold, true);

        Style italic = doc.addStyle("italic", regular);
        StyleConstants.setItalic(italic, true);
        StyleConstants.setBold(italic, true);

        Style server = doc.addStyle("server", regular);
        StyleConstants.setForeground(server, Color.BLUE);

    }

    public iWhiteboard getWhiteboard() {
        return whiteboard;
    }

    public void setWhiteboard(iWhiteboard whiteboard) {
        this.whiteboard = whiteboard;
    }

    public HashMap<String, String> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(HashMap<String, String> userInfo) {
        this.userInfo = userInfo;
    }
}
