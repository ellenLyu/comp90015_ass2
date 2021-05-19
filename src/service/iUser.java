package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iUser extends Remote {

    public void messageFromServer(String message) throws RemoteException;
    public void updateUserList(String[] currentUsers) throws RemoteException;
    public boolean approve(String username) throws RemoteException;
    public void load(byte[] b) throws RemoteException;
    public void reject(String str)throws RemoteException;
    public void info(String str) throws RemoteException;
    public String getUsername() throws RemoteException;

}
