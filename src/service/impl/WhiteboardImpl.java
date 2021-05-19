package service.impl;

import app.CreateWhiteBoard;
import app.UserInfo;
import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;
import service.iUser;
import service.iWhiteboard;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class WhiteboardImpl extends UnicastRemoteObject implements iWhiteboard {

    private iUser manager;

    private HashMap<String, iUser> users;

    public WhiteboardImpl() throws RemoteException {
        super();
        users = new HashMap<>();
    }

    @Override
    public void draw(byte[] b) throws RemoteException {

    }



    @Override
    public void registerListener(UserInfo userInfo) throws RemoteException {
        try {
            iUser client = (iUser) Naming.lookup("rmi://" + userInfo.getRmiHost() + "/" + "Create");
            manager.approve(userInfo.getUsername());

            users.put(userInfo.getUsername(), client);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean judge(String str) throws RemoteException {
        return false;
    }

    @Override
    public void removeUser(String username) throws RemoteException {

    }

    @Override
    public void broadcast(String msg) throws RemoteException {

    }

    public boolean existingName(String username) throws RemoteException {
        return manager.getUsername().equals(username) || users.containsKey(username);
    }

    @Override
    public void exit(String username) throws RemoteException {

    }

    @Override
    public void end() throws RemoteException {

    }

    /**
     * Add the manager user (CreateWhiteBoard) to the room/
     * @param manager
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean createRoom(iUser manager) throws RemoteException {


        if (this.manager != null) {
            return false;
        }

        if (manager instanceof CreateWhiteBoard) {
            this.manager = manager;
            System.out.println("Manager " + manager.getUsername() + " has created the room.");
            return true;
        }
        return false;
    }

    /**
     * Check whether the room is empty
     * @return true if the room is empty (no manager user)
     * @throws RemoteException
     */
    @Override
    public boolean isEmptyRoom() throws RemoteException {
        return this.manager == null;
    }
}
