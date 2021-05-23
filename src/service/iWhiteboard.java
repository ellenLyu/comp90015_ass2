package service;

import view.Shape;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface iWhiteboard extends Remote {
    void update(Shape shape, HashMap<String, String> userInfo) throws RemoteException;

    void loadNewWhiteboard(List<Shape> shapeList) throws RemoteException;

    boolean isEmptyRoom() throws RemoteException;

    Map<String, Object> join(HashMap<String, String> userInfo) throws RemoteException;

    void removeUser(String username, int selectedIdx) throws RemoteException;

    void broadcast(String from, String message) throws RemoteException;

    boolean existingName(String username) throws RemoteException;

    void exit(String username, String userType) throws RemoteException;

    void closeRoom() throws RemoteException;

    boolean createRoom(iUser manager) throws RemoteException;
}
