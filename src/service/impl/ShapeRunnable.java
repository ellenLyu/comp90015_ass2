package service.impl;

import view.CreateWhiteBoardView;
import view.JoinWhiteBoardView;
import view.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ShapeRunnable implements Runnable {


    private final Logger logger = Logger.getLogger(ShapeRunnable.class.getName());

    private CreateWhiteBoardView view1;

    private JoinWhiteBoardView view2;

    private List<Shape> shapeList;

    private Shape shape;

    public ShapeRunnable(CreateWhiteBoardView view, List<Shape> shapeList) {
        this.view1 = view;
        this.shapeList = shapeList;
    }

    public ShapeRunnable(CreateWhiteBoardView view, Shape shape) {
        this.view1 = view;
        this.shape = shape;
    }

    public ShapeRunnable(JoinWhiteBoardView view, List<Shape> shapeList) {
        this.view2 = view;
        this.shapeList = shapeList;
    }

    public ShapeRunnable(JoinWhiteBoardView view, Shape shape) {
        this.view2 = view;
        this.shape = shape;
    }


    @Override
    public void run() {
        if (view1 != null) {
            if (shapeList != null) {
                sleep();
                view1.getPaintPanel().clear();
                view1.getPaintPanel().loads((ArrayList<Shape>) shapeList);
            } else {
                view1.getPaintPanel().draw(shape);
            }
        } else if (view2 != null) {
            if (shapeList != null) {
                sleep();
                view2.getPaintPanel().clear();
                view2.getPaintPanel().loads((ArrayList<Shape>) shapeList);
                view2.setVisible(true);
            } else {
                view2.getPaintPanel().draw(shape);
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
