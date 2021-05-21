package view;

import java.awt.*;
import java.io.Serializable;
import java.rmi.Remote;

public class Shape implements Serializable {

    private int x;
    private int y;
    private int x1;
    private int y1;

    private String type;

    private Color color;

    private String text;

    public Shape() {
    }

    /**
     * line, circle, oval and rectangle
     */
    public Shape(int x, int y, int x1, int y1, String type, Color color) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.type = type;
        this.color = color;
    }

    /**
     * text input
     */
    public Shape(int x, int y, String type, Color color, String text) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.color = color;
        this.text = text;
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

    @Override
    public String toString(){
        return "Type: " + type
            + " x: " + x + " y: " + y + " x1: " + x1 + " y1: " + y1 + color;
    }
}
