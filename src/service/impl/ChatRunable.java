package service.impl;

import service.iUser;
import view.CreateWhiteBoardView;
import view.JoinWhiteBoardView;
import view.Shape;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Logger;

public class ChatRunable implements Runnable {
    private final Logger logger = Logger.getLogger(ShapeRunnable.class.getName());

    private CreateWhiteBoardView view1;

    private JoinWhiteBoardView view2;

    private String type, from, message;


    public ChatRunable(JoinWhiteBoardView view, String type, String from, String message) {
        this.view2 = view;
        this.type = type;
        this.from = from;
        this.message = message;
    }

    public ChatRunable(CreateWhiteBoardView view, String type, String from, String message) {
        this.view1 = view;
        this.type = type;
        this.from = from;
        this.message = message;
    }

    @Override
    public void run() {
        if (view1 != null) {
            view1.appendChat(type, from, message);
        } else if (view2 != null) {
            view2.appendChat(type, from, message);
        }
    }


}
