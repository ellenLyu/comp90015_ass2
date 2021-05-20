package server;

import common.Consts;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject {

    public Server() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {
//            WhiteboardImpl whiteboard = new WhiteboardImpl();
            Server server = new Server();
            // Create Registry
            int port = Integer.parseInt(args[0]);
            Registry registry = LocateRegistry.createRegistry(port);

            String hostname = InetAddress.getLocalHost().getHostAddress() + ":" + args[0];
            System.out.println(hostname);
            Naming.bind("rmi://" + hostname + "/" + Consts.Service.SERVER, server);

            System.out.println("Whiteboard server ready on:  " + hostname);

        } catch (NumberFormatException nfe) {
            System.out.println("port is invalid");
            nfe.printStackTrace();
        } catch (RemoteException | UnknownHostException | MalformedURLException | AlreadyBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
