package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteMath extends Remote {


    public double add(double a, double b) throws RemoteException;

    public double subtract(double a, double b) throws RemoteException;


}