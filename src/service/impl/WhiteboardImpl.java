package service.impl;

import app.CreateWhiteBoard;
import common.Consts;
import common.Consts.Service;
import java.util.logging.Logger;
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

    private static final Logger logger = Logger.getLogger(WhiteboardImpl.class.getName());
    private final HashMap<String, iUser> users;
    private iUser manager;

    public WhiteboardImpl() throws RemoteException {
        super();
        users = new HashMap<>();
    }

    /**
     * Synchronize the shape to other clients
     * @param shape the target shape
     * @param userInfo user information of user who draws the shape
     */
    @Override
    public void update(Shape shape, HashMap<String, String> userInfo) throws RemoteException {

        logger.info(shape + "");

        if (!userInfo.get(Service.USERNAME).equals(this.manager.getUsername())) {
            this.manager.updateShape(shape);
        }

        for (iUser user : users.values()) {
            if (!userInfo.get(Service.USERNAME).equals(user.getUsername())) {
                try {
                    user.updateShape(shape);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Join JoinWhiteBoard user to the whiteboard room
     * @param userInfo user information of JoinWhiteBoard user
     * @return True if can join the room
     */
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

    /**
     * Check whether the name is already existing in this room
     * @param username target username
     * @return true is existing
     */
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
     * Add the manager user (CreateWhiteBoard) to the room
     * @param manager CreateWhiteBoard object
     * @return true if created
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
     * @return true if the room is empty (non-manager user)
     * @throws RemoteException
     */
    @Override
    public boolean isEmptyRoom() throws RemoteException {
        return this.manager == null;
    }
}
