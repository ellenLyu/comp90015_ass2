package view;

import java.awt.*;
import java.io.Serializable;

public class Shape implements Serializable {

    private int x, y, x1, y1;

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


}
