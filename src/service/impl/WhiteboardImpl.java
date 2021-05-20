package service.impl;

import app.CreateWhiteBoard;
import common.Consts;
import service.iUser;
import service.iWhiteboard;
import view.Shape;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class WhiteboardImpl extends UnicastRemoteObject implements iWhiteboard {

    private final HashMap<String, iUser> users;
    private iUser manager;

    public WhiteboardImpl() throws RemoteException {
        super();
        users = new HashMap<>();
    }

    /**
     * Synchronize the shape to other clients
     *
     * @param shape
     * @throws RemoteException
     */
    @Override
    public void update(Shape shape) throws RemoteException {

        this.manager.updateShape(shape);

        for (iUser user : users.values()) {
            try {
                user.updateShape(shape);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean join(HashMap<String, String> userInfo) throws RemoteException {
        try {
            iUser client = (iUser) Naming.lookup("rmi://" +
                    userInfo.get(Consts.Service.RMI_HOST) + "/" + userInfo.get(Consts.Service.USERNAME));

            boolean isApproved = manager.approve(userInfo.get(Consts.Service.USERNAME));

            if (isApproved) {
                users.put(Consts.Service.USERNAME, client);
                return true;
            } else {
                client.reject(Consts.Message.REJECT_REQUEST);
                return false;
            }
        } catch (MalformedURLException | NotBoundException e) {
            e.printStackTrace();
            return false;
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
     *
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
     *
     * @return true if the room is empty (no manager user)
     * @throws RemoteException
     */
    @Override
    public boolean isEmptyRoom() throws RemoteException {
        return this.manager == null;
    }
}
