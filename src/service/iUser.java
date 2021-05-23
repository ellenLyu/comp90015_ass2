package service;

import view.Shape;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface iUser extends Remote {

    void rcvChatMsg(String type, String from, String message) throws RemoteException;

    void updateUserList(DefaultListModel<String> model) throws RemoteException;

    boolean approve(String username) throws RemoteException;

    void updateShape(Shape shape) throws RemoteException;

    void load(List<Shape> shapeList) throws RemoteException;

    void leave(String message) throws RemoteException;

    void popupMessage(String message, int mode, String... args) throws RemoteException;

    String getUsername() throws RemoteException;

    List<Shape> getShapeList() throws RemoteException;
}
