package service.impl;

import view.CreateWhiteBoardView;
import view.JoinWhiteBoardView;

import javax.swing.*;
import java.util.logging.Logger;

public class UserListRunnable implements Runnable {


    private final Logger logger = Logger.getLogger(ShapeRunnable.class.getName());
    private final DefaultListModel<String> usernameList;
    private CreateWhiteBoardView view1;
    private JoinWhiteBoardView view2;

    public UserListRunnable(CreateWhiteBoardView view, DefaultListModel<String> usernameList) {
        this.view1 = view;
        this.usernameList = usernameList;
    }

    public UserListRunnable(JoinWhiteBoardView view, DefaultListModel<String> usernameList) {
        this.view2 = view;
        this.usernameList = usernameList;
    }

    @Override
    public void run() {

        if (view1 != null) {
            sleep();
            view1.updateList(usernameList);
        } else if (view2 != null) {
            sleep();
            view2.updateList(usernameList);
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
