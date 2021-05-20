package view;

import javax.swing.*;
import java.awt.*;

public class JoinWhiteBoardView {

    private final JFrame frame;

    private final PaintPanel paintPanel;

    private final ToolPanel toolPanel;

    private final JTextArea errorMessageArea;

    public JoinWhiteBoardView() {
        frame = new JFrame();
        frame.setTitle("Whiteboard - Join");
        frame.setBounds(200, 200, 800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        paintPanel = new PaintPanel();
        paintPanel.setBackground(Color.WHITE);
        paintPanel.setBounds(5, 100, 800, 600);
        frame.add(paintPanel);

        toolPanel = new ToolPanel(paintPanel);
        frame.add(toolPanel);

        errorMessageArea = new JTextArea();
        errorMessageArea.setBounds(10, 284, 434, 91);

        frame.setVisible(true);
    }

    public PaintPanel getPaintPanel() {
        return this.paintPanel;
    }
}
