package view;

import common.Consts.PaintType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ToolPanel extends JPanel {

    private final JToolBar toolBar;

    private final PaintPanel paintPanel;

    private Color selectedColor = Color.GRAY;

    public ToolPanel(PaintPanel paintPanel) {

        this.paintPanel = paintPanel;

        setBorder(new EmptyBorder(5, 5, 5, 5));
//        setLayout(null);

        toolBar = new JToolBar();
//        toolBar.setBounds(0, 0, 500, 20);
        toolBar.setFloatable(false);

        setBackground(Color.lightGray);
        add(toolBar);
        addButtons();
    }


    private void addButtons() {

        // Shapes
        JButton btnRectangle = new JButton("Rectangle");
        btnRectangle.addActionListener(e -> paintPanel.setType(PaintType.RECT));
        toolBar.add(btnRectangle);

        JButton btnOval = new JButton("Oval");
        btnOval.addActionListener(e -> paintPanel.setType(PaintType.OVAL));
        toolBar.add(btnOval);

        JButton btnLine = new JButton("Line");
        btnLine.addActionListener(e -> paintPanel.setType(PaintType.LINE));
        toolBar.add(btnLine);

        JButton btnCircle = new JButton("Circle");
        btnCircle.addActionListener(e -> paintPanel.setType(PaintType.CIRCLE));
        toolBar.add(btnCircle);

        JButton btnText = new JButton("Text");
        btnText.addActionListener(e -> paintPanel.setType(PaintType.TEXT));
        toolBar.add(btnText);

        JButton btnEraser = new JButton("Eraser");
        btnEraser.addActionListener(e -> paintPanel.setType(PaintType.ERASER));
        toolBar.add(btnEraser);

        // Color
        JButton btnColor = new JButton("Color");
        btnColor.addActionListener(e -> {
            selectedColor = JColorChooser.showDialog(null, "Please choose the color", selectedColor);
            btnColor.setBackground(selectedColor);
            paintPanel.setSelectedColor(selectedColor);
        });
        toolBar.add(btnColor);
    }
}
