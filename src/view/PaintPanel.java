package view;

import common.Consts.PaintType;
import common.Utils;
import service.iWhiteboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaintPanel extends JPanel {

    private static final Logger logger = Logger.getLogger(PaintPanel.class.getName());
    private final ArrayList<Shape> shapeList;
    private String type = "line";
    // cursor
    private int x;
    private int y;
    private Color selectedColor = Color.BLACK;

    private iWhiteboard whiteboard;

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
                    try {
                        draw(x, y, 0, 0, type, selectedColor);
                    } catch (RemoteException remoteException) {
                        remoteException.printStackTrace();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Release, Get the ending position
                int x1 = e.getX();
                int y1 = e.getY();

                try {
                    draw(x, y, x1, y1, type, selectedColor);
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }


    private void draw(int x, int y, int x1, int y1, String type, Color color) throws RemoteException {
        Graphics2D g = (Graphics2D) getGraphics();
        g.setColor(color);

        Shape shape;

        if (PaintType.TEXT.equals(type)) {
            String input = JOptionPane.showInputDialog("Input:");
            if (Utils.isNotEmpty(input)) {
                g.drawString(input, x, y);
            }
            shape = new Shape(x, y, type, color, input);
        } else {

            int height = Math.abs(y1 - y);
            int width = Math.abs(x1 - x);

            switch (this.type) {
                case PaintType.LINE:
                    g.drawLine(x, y, x1, y1);
                    shape = new Shape(x, y, x1, y1, type, color);
                    break;
                case PaintType.RECT:
                    g.drawRect(Math.min(x, x1), Math.min(y, y1), width, height);
                    shape = new Shape(Math.min(x, x1), Math.min(y, y1), width, height, type, color);
                    break;
                case PaintType.CIRCLE:
                    int round = Math.max(width, height);
                    g.drawOval(Math.min(x, x1), Math.min(y, y1), round, round);
                    shape = new Shape(Math.min(x, x1), Math.min(y, y1), round, round, type, color);
                    break;
                case PaintType.OVAL:
                    g.drawOval(Math.min(x, x1), Math.min(y, y1), width, height);
                    shape = new Shape(Math.min(x, x1), Math.min(y, y1), width, height, type, color);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
        }
        shapeList.add(shape);
        System.out.println(shapeList);

        // Synchronize the shape to other clients
        whiteboard.update(shape);
    }

    public void draw(Shape updateShape) {
        System.out.println("Update the shape from other clients");

        Graphics2D g = (Graphics2D) getGraphics();
        Color color = updateShape.getColor();
        int x = updateShape.getX();
        int y = updateShape.getY();
        int x1 = updateShape.getX1();
        int y1 = updateShape.getY1();
        String type = updateShape.getType();
        String text = updateShape.getText();

        g.setColor(color);

        Shape shape;

        if (PaintType.TEXT.equals(type)) {
            g.drawString(text, x, y);
            shape = new Shape(x, y, type, color, text);
        } else {

            int height = Math.abs(y1 - y);
            int width = Math.abs(x1 - x);

            switch (this.type) {
                case PaintType.LINE:
                    g.drawLine(x, y, x1, y1);
                    shape = new Shape(x, y, x1, y1, type, color);
                    break;
                case PaintType.RECT:
                    g.drawRect(Math.min(x, x1), Math.min(y, y1), width, height);
                    shape = new Shape(Math.min(x, x1), Math.min(y, y1), width, height,
                            type, color);
                    break;
                case PaintType.CIRCLE:
                    int round = Math.max(width, height);
                    g.drawOval(Math.min(x, x1), Math.min(y, y1), round, round);
                    shape = new Shape(Math.min(x, x1), Math.min(y, y1), round, round,
                            type, color);
                    break;
                case PaintType.OVAL:
                    g.drawOval(Math.min(x, x1), Math.min(y, y1), width, height);
                    shape = new Shape(Math.min(x, x1), Math.min(y, y1), width, height,
                            type, color);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
        }
//        shapeList.add(shape);
//        System.out.println(shapeList);
    }


    public void setType(String type) {
        logger.log(Level.INFO, "Type is changed to " + type);
        this.type = type;
    }

    public void setSelectedColor(Color selectedColor) {
        logger.log(Level.INFO, "Color is changed to " + selectedColor.getRGB());
        this.selectedColor = selectedColor;
    }

    public iWhiteboard getWhiteboard() {
        return whiteboard;
    }

    public void setWhiteboard(iWhiteboard whiteboard) {
        this.whiteboard = whiteboard;
    }
}
