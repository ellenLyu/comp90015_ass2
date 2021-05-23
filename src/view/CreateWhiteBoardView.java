package view;

import app.CreateWhiteBoard;
import common.Consts;
import common.Utils;
import service.iWhiteboard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateWhiteBoardView {

    private final JFrame frame;
    private final ToolPanel toolPanel;
    private final UserListPanel userListPanel;
    private final ChatPanel chatPanel;
    private final JTextArea errorMessageArea;
    private final PaintPanel paintPanel;
    private JFileChooser fileChooser;

    private iWhiteboard iWhiteboard;
    private CreateWhiteBoard user;


    private String pngPath;
    private String serPath;


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
        toolPanel.setBounds(0, 0, 500, 30);
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
        addClose();
        frame.setResizable(false);
        frame.setVisible(true);
    }


    private void addClose() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    iWhiteboard.closeRoom();

                    Thread.sleep(200);
                    System.exit(0);
                } catch (RemoteException | InterruptedException remoteException) {
                    remoteException.printStackTrace();
                }
                e.getWindow().dispose();
            }
        });
    }

    private void addFileMenu() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu mnFile = new JMenu("File");
        fileChooser = new JFileChooser();
        menuBar.add(mnFile);

        // new
        JMenuItem menuBtnNew = new JMenuItem("New");
        mnFile.add(menuBtnNew);
        menuBtnNew.addActionListener(e -> {
            try {
                paintPanel.getWhiteboard().loadNewWhiteboard(new ArrayList<>());
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });

        // open
        JMenuItem menuBtnOpen = new JMenuItem("Open");
        menuBtnOpen.addActionListener(e -> {
            // Set to the current directory
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

            if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(null)) {
                System.out.println(fileChooser.getSelectedFile());

                ObjectInputStream objectInputStream = null;
                List<Shape> shapeList = null;
                try {
                    FileInputStream inputStream = new FileInputStream(fileChooser.getSelectedFile());
                    objectInputStream = new ObjectInputStream(inputStream);
                    shapeList = (List<Shape>) objectInputStream.readObject();
                } catch (Exception e1) {
                    e1.printStackTrace();
                } finally {
                    if (objectInputStream != null) {
                        try {
                            objectInputStream.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                try {
                    paintPanel.getWhiteboard().loadNewWhiteboard(shapeList);
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }
            }


        });
        mnFile.add(menuBtnOpen);

        // save
        JMenuItem menuBtnSave = new JMenuItem("Save");
        mnFile.add(menuBtnSave);
        menuBtnSave.addActionListener(e -> {

            // If no existing saved file
            if (this.pngPath == null || this.serPath == null) {
                saveAsFile();
                return;
            }

            try {
                // Save the png file
                ImageIO.write(paintPanel.save(), Consts.Service.PNG, new File(this.pngPath));

                // Save the ser file
                FileOutputStream fs = new FileOutputStream(this.serPath);
                ObjectOutputStream os = new ObjectOutputStream(fs);
                os.writeObject(paintPanel.getShapeList());
                os.close();

                Utils.popupMessage(Consts.Message.SUCC_SAVED, Consts.Message.BACK);
            } catch (IOException ioe) {
                // TODO Auto-generated catch block
                ioe.printStackTrace();
            }
        });

        // saveAs
        JMenuItem menuBtnSaveAs = new JMenuItem("Save As");
        mnFile.add(menuBtnSaveAs);
        menuBtnSaveAs.addActionListener(e -> {
            saveAsFile();
        });

        // saveAs
        JMenuItem menuBtnClose = new JMenuItem("Close");
        menuBtnClose.addActionListener(e -> {
            try {
                iWhiteboard.closeRoom();
                frame.dispose();
                System.exit(0);
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });
        mnFile.add(menuBtnClose);

        frame.setJMenuBar(menuBar);
    }

    /**
     * Open the file chooser and save the shapes to the image.
     */
    private void saveAsFile() {
        // Set to the current directory
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(null)) {
            File file = fileChooser.getSelectedFile();
            String absolutePath = file.getAbsolutePath();
            File targetFile;

            // Check the file name and extension
            absolutePath = absolutePath.endsWith("." + Consts.Service.PNG) ?
                    absolutePath.replaceAll("." + Consts.Service.PNG, "") : absolutePath;
            absolutePath = absolutePath.endsWith("." + Consts.Service.SER) ?
                    absolutePath.replaceAll("." + Consts.Service.SER, "") : absolutePath;
            System.out.println(absolutePath);

            String pngFilePath = absolutePath + "." + Consts.Service.PNG;
            String serFilePath = absolutePath + "." + Consts.Service.SER;

            // Save the PaintPanel to the image
            try {
//                System.out.println(pngFilePath);
                ImageIO.write(paintPanel.save(), Consts.Service.PNG, new File(pngFilePath));

                FileOutputStream fs = new FileOutputStream(serFilePath);
                ObjectOutputStream os = new ObjectOutputStream(fs);
                os.writeObject(paintPanel.getShapeList());
                os.close();

                // Save the path
                this.pngPath = pngFilePath;
                this.serPath = serFilePath;

                Utils.popupMessage(Consts.Message.SUCC_SAVED, Consts.Message.BACK, absolutePath);
            } catch (IOException ioe) {
                // TODO Auto-generated catch block
                ioe.printStackTrace();
            }
        }
    }

    public PaintPanel getPaintPanel() {
        return paintPanel;
    }

    public void updateList(DefaultListModel<String> model) {
        userListPanel.updateList(model);
    }

    public void appendChat(String type, String from, String message) {
        chatPanel.appendMessage(type, from, message);
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

    public void setUser(CreateWhiteBoard user) {
        this.user = user;
    }
}
