package view;

import common.Consts.PaintType;
import common.Utils;
import service.iWhiteboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private float selectedStroke = 1.0f;

    private iWhiteboard whiteboard;
    private HashMap<String, String> userInfo;

    private BufferedImage image;

    public PaintPanel() {

        shapeList = new ArrayList<>();

        setPreferredSize(new Dimension(480, 500));
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
                        draw(x, y, 0, 0, type, selectedColor, selectedStroke);
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
                    draw(x, y, x1, y1, type, selectedColor, selectedStroke);
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


    /**
     * Draw the shape by this user
     *
     * @param x,    y, x1, y1
     * @param type  type of the shape
     * @param color color of the shape
     * @throws RemoteException
     */
    private void draw(int x, int y, int x1, int y1, String type, Color color, Float stroke) throws RemoteException {
        Graphics2D g = (Graphics2D) getGraphics();
        g.setColor(color);
        g.setStroke(new BasicStroke(stroke));

        Shape shape;

        if (PaintType.TEXT.equals(type)) {
            String input = JOptionPane.showInputDialog("Input:");
            if (Utils.isNotEmpty(input)) {
                g.drawString(input, x, y);
            }
            shape = new Shape(x, y, type, color, input, stroke);
        } else {

            int height = Math.abs(y1 - y);
            int width = Math.abs(x1 - x);

            switch (this.type) {
                case PaintType.LINE:
                    g.drawLine(x, y, x1, y1);
                    shape = new Shape(x, y, x1, y1, type, color, stroke);
                    break;
                case PaintType.RECT:
                    g.drawRect(Math.min(x, x1), Math.min(y, y1), width, height);
                    shape = new Shape(Math.min(x, x1), Math.min(y, y1), width, height, type, color, stroke);
                    break;
                case PaintType.CIRCLE:
                    int round = Math.max(width, height);
                    g.drawOval(Math.min(x, x1), Math.min(y, y1), round, round);
                    shape = new Shape(Math.min(x, x1), Math.min(y, y1), round, round, type, color, stroke);
                    break;
                case PaintType.OVAL:
                    g.drawOval(Math.min(x, x1), Math.min(y, y1), width, height);
                    shape = new Shape(Math.min(x, x1), Math.min(y, y1), width, height, type, color, stroke);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
        }
        this.shapeList.add(shape);

        // Synchronize the shape to other clients
//        logger.info("Updated Shape " + shape);
        whiteboard.update(shape, this.userInfo);

        System.out.println(shapeList);
    }

    /**
     * Update the shape drawn by other users
     *
     * @param shape the shape object by other users
     */
    public void draw(Shape shape) {
        System.out.println(shape);
        Graphics2D g = (Graphics2D) getGraphics();
        Color color = shape.getColor();
        Stroke stroke = new BasicStroke(shape.getStroke());
        int x = shape.getX();
        int y = shape.getY();
        int x1 = shape.getX1();
        int y1 = shape.getY1();
        String type = shape.getType();
        String text = shape.getText();

//        logger.info(shape + "");

        System.out.println(g);
        g.setColor(color);
        g.setStroke(stroke);

        if (PaintType.TEXT.equals(type)) {
            g.drawString(text, x, y);
        } else {
            switch (type) {
                case PaintType.LINE:
                    g.drawLine(x, y, x1, y1);
                    break;
                case PaintType.RECT:
                    g.drawRect(x, y, x1, y1);
                    break;
                case PaintType.CIRCLE:
                case PaintType.OVAL:
                    g.drawOval(x, y, x1, y1);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
        }
        shapeList.add(shape);
    }

    /**
     * Loads a list of shapes, for the total canvas
     *
     * @param shapeList shape list
     */
    public void loads(ArrayList<Shape> shapeList) {
        logger.log(Level.INFO, "Loads the whole canvas. ");
        System.out.println(shapeList);

        this.shapeList.clear();
        this.shapeList.addAll(shapeList);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Shape shape : shapeList) {
            draw(shape);
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

    /**
     * Paint to the image
     *
     * @return BufferedImage
     */
    public BufferedImage save() {
        Dimension imageSize = this.getSize();
        BufferedImage image = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        // Set the background color
        graphics.setBackground(Color.WHITE);
        graphics.fillRect(0, 0, imageSize.width, imageSize.height);
        graphics.drawImage(image, 0, 0, this);

        // Draw the shapes to the image
        for (Shape shape : this.shapeList) {
            shape.save(graphics);
            graphics.drawImage(image, 0, 0, this);
        }
        graphics.dispose();
        return image;
    }


    public void clear() {
        shapeList.clear();

        Dimension imageSize = this.getSize();
        Graphics2D g = (Graphics2D) getGraphics();
        g.clearRect(0, 0, imageSize.width, imageSize.height);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imageSize.width, imageSize.height);
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

    public ArrayList<Shape> getShapeList() {
        return shapeList;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public float getSelectedStroke() {
        return selectedStroke;
    }

    public void setSelectedStroke(float selectedStroke) {
        this.selectedStroke = selectedStroke;
    }
}
