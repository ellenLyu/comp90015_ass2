package service.impl;

import service.iUser;
import view.Shape;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Logger;

public class UserListRunnable implements Runnable {


    private final Logger logger = Logger.getLogger(ShapeRunnable.class.getName());

    private iUser client;

    private DefaultListModel<String> usernameList;

    public UserListRunnable(iUser client, DefaultListModel<String> usernameList) {
        this.client = client;
        this.usernameList = usernameList;
    }

    @Override
    public void run() {
        try {
            this.client.updateUserList(this.usernameList);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
