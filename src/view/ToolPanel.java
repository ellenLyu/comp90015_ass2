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

        toolBar = new JToolBar();
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
//        toolBar.add(btnEraser);

        // Color
        JButton btnColor = new JButton("Color");
        btnColor.setOpaque(true);
        btnColor.setBackground(selectedColor);
        btnColor.setForeground(selectedColor);

        btnColor.addActionListener(e -> {
            selectedColor = JColorChooser.showDialog(null, "Please choose the color", selectedColor);
            btnColor.setBackground(selectedColor);
            btnColor.setForeground(selectedColor);
            paintPanel.setSelectedColor(selectedColor);
        });
        toolBar.add(btnColor);

        JLabel strokeLabel = new JLabel("Stroke:");
        strokeLabel.setBorder(new EmptyBorder(0, 5, 0, 0));
        toolBar.add(strokeLabel);

        JComboBox<String> strokeComboBox = new JComboBox<>();
        strokeComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"1.0", "1.2", "1.4", "1.6", "1.8", "2.0"}));
        strokeComboBox.addActionListener(e -> {

            String selected = (String) strokeComboBox.getSelectedItem();
            Float selectStroke;

            if (selected != null) {
                selectStroke = Float.parseFloat(selected);
            } else {
                selectStroke = 1.0f;
            }

            paintPanel.setSelectedStroke(selectStroke);
        });
        toolBar.add(strokeComboBox);
    }
}
