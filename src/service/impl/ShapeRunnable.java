package service.impl;

import service.iUser;
import view.Shape;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Logger;

public class ShapeRunnable implements Runnable {


    private final Logger logger = Logger.getLogger(ShapeRunnable.class.getName());

    private iUser client;

    private List<Shape> shapeList;

    public ShapeRunnable(iUser client, List<Shape> shapeList) {
        this.client = client;
        this.shapeList = shapeList;
    }

    @Override
    public void run() {
        try {
            this.client.load(this.shapeList);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
