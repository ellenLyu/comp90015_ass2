package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.tools.javac.util.StringUtils;
import common.Consts.PaintType;
import common.Utils;

public class PaintPanel extends JPanel {

    private static final Logger logger = Logger.getLogger(PaintPanel.class.getName());

    private String type = "line";

    // cursor
    private int x;
    private int y;

    private ArrayList<Shape> shapeList;

    private Color selectedColor = Color.BLACK;

    public PaintPanel() {

        shapeList = new ArrayList<>();

        setBorder(new EmptyBorder(5, 5, 5, 5));

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Press, Get the starting position
                x = e.getX();
                y = e.getY();

                if (PaintType.TEXT.equals(type)) {
                    draw(x, y, 0, 0, type);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Release, Get the ending position
                int x1 = e.getX();
                int y1 = e.getY();

                draw(x, y, x1, y1, type);

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }


    private void draw(int x, int y, int x1, int y1, String type) {
        Graphics2D g = (Graphics2D) getGraphics();

        g.setColor(this.selectedColor);

        if (PaintType.TEXT.equals(type)) {
            String input = JOptionPane.showInputDialog("Input:");
            if (Utils.isNotEmpty(input)) {
                g.drawString(input, x, y);
            }
            shapeList.add(new Shape(x, y, this.type, this.selectedColor, input));
            return;
        }

        int height = Math.abs(y1 - y);
        int width = Math.abs(x1 - x);

        switch (this.type) {
            case PaintType.LINE:
                g.drawLine(x, y, x1, y1);
                shapeList.add(new Shape(x, y, x1, y1, this.type, this.selectedColor));
                break;
            case PaintType.RECT:
                g.drawRect(Math.min(x, x1), Math.min(y, y1), width, height);
                shapeList.add(new Shape(Math.min(x, x1), Math.min(y, y1), width, height,
                        this.type, this.selectedColor));
                break;
            case PaintType.CIRCLE:
                int round = Math.max(width, height);
                g.drawOval(Math.min(x, x1), Math.min(y, y1), round, round);
                shapeList.add(new Shape(Math.min(x, x1), Math.min(y, y1), round, round,
                        this.type, this.selectedColor));
                break;
            case PaintType.OVAL:
                g.drawOval(Math.min(x, x1), Math.min(y, y1), width, height);
                shapeList.add(new Shape(Math.min(x, x1), Math.min(y, y1), width, height,
                        this.type, this.selectedColor));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public void setType(String type) {
        logger.log(Level.INFO, "Type is changed to " + type);
        this.type = type;
    }

    public void setSelectedColor(Color selectedColor) {
        logger.log(Level.INFO, "Color is changed to " + selectedColor.getRGB());
        this.selectedColor = selectedColor;
    }
}
