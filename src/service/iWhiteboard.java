package service;

import view.Shape;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface iWhiteboard extends Remote {
    void update(Shape shape, HashMap<String, String> userInfo) throws RemoteException;

    boolean isEmptyRoom() throws RemoteException;

    boolean join(HashMap<String, String> userInfo) throws RemoteException;

    boolean judge(String str) throws RemoteException;

    void removeUser(String username) throws RemoteException;

    void broadcast(String msg) throws RemoteException;

    boolean existingName(String username) throws RemoteException;

    void exit(String username) throws RemoteException;

    void end() throws RemoteException;

    boolean createRoom(iUser manager) throws RemoteException;
}
