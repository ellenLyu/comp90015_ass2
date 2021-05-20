package service;

import view.Shape;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iUser extends Remote {

    void messageFromServer(String message) throws RemoteException;

    void updateUserList(String[] currentUsers) throws RemoteException;

    boolean approve(String username) throws RemoteException;

    void updateShape(Shape shape) throws RemoteException;

    void reject(String message) throws RemoteException;

    void info(String str) throws RemoteException;

    String getUsername() throws RemoteException;

}
