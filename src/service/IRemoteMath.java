package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteMath extends Remote {


    double add(double a, double b) throws RemoteException;

    double subtract(double a, double b) throws RemoteException;


}