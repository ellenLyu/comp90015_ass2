package view;

import service.iWhiteboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateWhiteBoardView {

    private final JFrame frame;

    private final PaintPanel paintPanel;

    private final ToolPanel toolPanel;

    private final UserListPanel userListPanel;

    private final ChatPanel chatPanel;

    private final JTextArea errorMessageArea;


    public CreateWhiteBoardView() {

        frame = new JFrame();
        frame.setTitle("Whiteboard - Create");
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
        toolPanel.setBounds(0, 0,  500, 30);
        toolPanel.setVisible(true);
        frame.getContentPane().add(toolPanel, gbc_ToolPanel);

        GridBagConstraints gbc_ListPanel = new GridBagConstraints();
        gbc_ListPanel.fill = GridBagConstraints.BOTH;
        gbc_ListPanel.gridx = 1;
        gbc_ListPanel.gridy = 0;
        gbc_ListPanel.gridwidth = 1;
        gbc_ListPanel.gridheight = 2;
        userListPanel = new UserListPanel(true);
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

        addFileMenu();
        frame.setVisible(true);
    }

    private void addFileMenu() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        // new
        JMenuItem menuBtnNew = new JMenuItem("New");
        mnFile.add(menuBtnNew);
        menuBtnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // open
        JMenuItem menuBtnOpen = new JMenuItem("Open");
        mnFile.add(menuBtnOpen);
        menuBtnOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // save
        JMenuItem menuBtnSave = new JMenuItem("Save");
        mnFile.add(menuBtnSave);
        menuBtnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // saveAs
        JMenuItem menuBtnSaveAs = new JMenuItem("SaveAs");
        mnFile.add(menuBtnSaveAs);
        menuBtnSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // saveAs
        JMenuItem menuBtnClose = new JMenuItem("Close");
        mnFile.add(menuBtnClose);
        menuBtnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        frame.setJMenuBar(menuBar);
    }

    public PaintPanel getPaintPanel() {
        return paintPanel;
    }

    public void updateList(DefaultListModel<String> model) {
        userListPanel.updateList(model);
    }

    public void appendChat(String type, String from, String message) {
        chatPanel.appendMessage(type,from, message);
    }


    public void setWhiteboard(iWhiteboard whiteboard) {
        this.paintPanel.setWhiteboard(whiteboard);
        this.userListPanel.setWhiteboard(whiteboard);
        this.chatPanel.setWhiteboard(whiteboard);
    }

    public void setUserinfo(HashMap<String, String> userInfo) {
        this.paintPanel.setUserInfo(userInfo);
        this.chatPanel.setUserInfo(userInfo);

    }


}
