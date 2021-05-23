package view;

import app.JoinWhiteBoard;
import common.Consts;
import service.iWhiteboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.HashMap;

public class JoinWhiteBoardView {

    private final JFrame frame;

    private final PaintPanel paintPanel;

    private final ToolPanel toolPanel;

    private final UserListPanel userListPanel;

    private final ChatPanel chatPanel;

    private final JTextArea errorMessageArea;

    private JoinWhiteBoard user;

    private iWhiteboard iWhiteboard;


    public JoinWhiteBoardView() {

        frame = new JFrame();
        frame.setTitle("Whiteboard - Join");
        frame.setBounds(200, 200, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{530, 260};
        gridBagLayout.rowHeights = new int[]{40, 280, 280};
        gridBagLayout.columnWeights = new double[]{1.0, 0.6};
        gridBagLayout.rowWeights = new double[]{0.1, 1.0, 1.0};
        frame.getContentPane().setLayout(gridBagLayout);

        GridBagConstraints gbc_PaintPanel = new GridBagConstraints();
        gbc_PaintPanel.fill = GridBagConstraints.BOTH;
        gbc_PaintPanel.gridx = 0;
        gbc_PaintPanel.gridy = 1;
        gbc_PaintPanel.gridwidth = 1;
        gbc_PaintPanel.gridheight = 2;
        paintPanel = new PaintPanel();
        paintPanel.setBackground(Color.WHITE);
//        paintPanel.setBounds(0, 0, 500, 500);
        frame.getContentPane().add(paintPanel, gbc_PaintPanel);

        GridBagConstraints gbc_ToolPanel = new GridBagConstraints();
        gbc_ToolPanel.fill = GridBagConstraints.BOTH;
        gbc_ToolPanel.anchor = GridBagConstraints.WEST;
        gbc_ToolPanel.gridx = 0;
        gbc_ToolPanel.gridy = 0;
        gbc_ToolPanel.gridwidth = 1;
        toolPanel = new ToolPanel(paintPanel);
        toolPanel.setBounds(0, 0, 500, 30);
        toolPanel.setVisible(true);
        frame.getContentPane().add(toolPanel, gbc_ToolPanel);

        GridBagConstraints gbc_ListPanel = new GridBagConstraints();
        gbc_ListPanel.fill = GridBagConstraints.BOTH;
        gbc_ListPanel.gridx = 1;
        gbc_ListPanel.gridy = 1;
        gbc_ListPanel.gridwidth = 1;
        gbc_ListPanel.gridheight = 1;
        userListPanel = new UserListPanel(false);
        frame.getContentPane().add(userListPanel, gbc_ListPanel);

        GridBagConstraints gbc_ChatPanel = new GridBagConstraints();
        gbc_ChatPanel.fill = GridBagConstraints.BOTH;
        gbc_ChatPanel.gridx = 1;
        gbc_ChatPanel.gridy = 2;
        gbc_ChatPanel.gridwidth = 1;
        gbc_ChatPanel.gridheight = 1;
        chatPanel = new ChatPanel();
        frame.getContentPane().add(chatPanel, gbc_ChatPanel);

        errorMessageArea = new JTextArea();
        errorMessageArea.setBounds(10, 284, 434, 91);


        addClose();
        frame.setResizable(false);
        frame.setVisible(true);
    }


    private void addClose() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    iWhiteboard.exit(user.getUsername(), Consts.Service.CLIENT_NAME);
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }
                e.getWindow().dispose();
            }
        });
    }

    public PaintPanel getPaintPanel() {
        return this.paintPanel;
    }

    public void setWhiteboard(iWhiteboard whiteboard) {
        this.iWhiteboard = whiteboard;
        this.paintPanel.setWhiteboard(whiteboard);
        this.userListPanel.setWhiteboard(whiteboard);
        this.chatPanel.setWhiteboard(whiteboard);
    }

    public void setUserinfo(HashMap<String, String> userInfo) {
        this.paintPanel.setUserInfo(userInfo);
        this.chatPanel.setUserInfo(userInfo);

    }

    public void appendChat(String type, String from, String message) {
        chatPanel.appendMessage(type, from, message);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(true);
    }

    public void updateList(DefaultListModel<String> model) {
        userListPanel.updateList(model);
    }

    public JoinWhiteBoard getUser() {
        return user;
    }

    public void setUser(JoinWhiteBoard user) {
        this.user = user;
    }
}
