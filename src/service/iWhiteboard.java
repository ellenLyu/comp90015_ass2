package service;

import app.CreateWhiteBoard;
import app.UserInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iWhiteboard extends Remote {
    public void draw(byte[] b) throws RemoteException;
    public boolean isEmptyRoom() throws RemoteException;
    public void registerListener(UserInfo userInfo)throws RemoteException;
    public boolean judge(String str) throws RemoteException;
    public void removeUser(String username) throws RemoteException;
    public void broadcast(String msg) throws RemoteException;
    public boolean existingName(String username) throws RemoteException;
    public void exit(String username) throws RemoteException;
    public void end() throws RemoteException;
    public boolean createRoom(iUser manager) throws RemoteException;
}
