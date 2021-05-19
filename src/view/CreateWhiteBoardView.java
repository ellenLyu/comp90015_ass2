package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateWhiteBoardView {

    private final JFrame frame;

    private final PaintPanel paintPanel;

    private ToolPanel toolPanel;

    private JTextArea errorMessageArea;


    public CreateWhiteBoardView() {

        frame = new JFrame();
        frame.setTitle("Whiteboard - Create");
        frame.setBounds(200, 200, 800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        paintPanel = new PaintPanel();
        paintPanel.setBackground(Color.WHITE);
        paintPanel.setBounds(5,100,800,600);
        frame.add(paintPanel);

        toolPanel = new ToolPanel(paintPanel);
        frame.add(toolPanel);

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
}
