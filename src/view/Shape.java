package view;

import common.Consts;

import java.awt.*;
import java.io.Serializable;

public class Shape implements Serializable {

    private int x;
    private int y;
    private int x1;
    private int y1;

    private String type;

    private Color color;

    private String text;

    private Float stroke;


    public Shape() {
    }

    /**
     * line, circle, oval and rectangle
     */
    public Shape(int x, int y, int x1, int y1, String type, Color color, Float stroke) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.type = type;
        this.color = color;
        this.stroke = stroke;
    }

    /**
     * text input
     */
    public Shape(int x, int y, String type, Color color, String text, Float stroke) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.color = color;
        this.text = text;
        this.stroke = stroke;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public String getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public String getText() {
        return text;
    }

    public Float getStroke() {
        return stroke;
    }

    public void setStroke(Float stroke) {
        this.stroke = stroke;
    }

    @Override
    public String toString() {
        return "Type: " + type
                + " x: " + x + " y: " + y + " x1: " + x1 + " y1: " + y1 + color;
    }

    public void save(Graphics2D g) {

        g.setColor(color);
        g.setStroke(new BasicStroke(stroke));

        if (Consts.PaintType.TEXT.equals(type)) {
            g.drawString(text, x, y);
        } else {
            switch (type) {
                case Consts.PaintType.LINE:
                    g.drawLine(x, y, x1, y1);
                    break;
                case Consts.PaintType.RECT:
                    g.drawRect(x, y, x1, y1);
                    break;
                case Consts.PaintType.CIRCLE:
                case Consts.PaintType.OVAL:
                    g.drawOval(x, y, x1, y1);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
        }
    }
}
