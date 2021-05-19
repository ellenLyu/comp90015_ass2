package app;

import common.Consts;
import common.Utils;
import service.iUser;
import service.impl.WhiteboardImpl;
import view.CreateWhiteBoardView;
import view.StartAppDialog;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

// https://github.com/alvinhkh/rmi-whiteboard/blob/master/alvinhkh/RMIWhiteboard/ShapeListServer.java
public class CreateWhiteBoard extends UnicastRemoteObject implements iUser {

    private static CreateWhiteBoardView view;
    private final String userName;
    private final String serverRmi;
    private final String serviceName;
    private final int hostPort;
    private final String hostAddress;
    private final String clientServiceName;
    private service.iWhiteboard whiteboard;


    public CreateWhiteBoard(String hostAddress, int hostPort, String username) throws RemoteException {
        // Input args
        this.userName = username.trim();
        this.hostAddress = hostAddress;
        this.hostPort = hostPort;

        // Get the Server Registry hostname
        this.serverRmi = hostAddress + ":" + hostPort;
        this.clientServiceName = "Create";
        this.serviceName = Consts.Service.WHITEBOARD;
//      this.clientServiceName = "Create";
//      this.GUI = new whiteBroadGUI_create();
    }

    public static void main(String[] args) {

        StartAppDialog startAppDialog = new StartAppDialog();
        Map<String, String> dialogInput = startAppDialog.showDialog();

        // Check input port
        if (!Utils.checkStartAppArgs(dialogInput)) {
            return;
        }

        try {
            CreateWhiteBoard cwb = new CreateWhiteBoard(
                    dialogInput.get(Consts.ServerView.IP_ADDRESS),
                    Integer.parseInt(dialogInput.get(Consts.ServerView.PORT)),
                    dialogInput.get(Consts.ServerView.USERNAME));
            cwb.connect();
        } catch (RemoteException e) {
            // TODO:
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            // RMI
            UserInfo userInfo = new UserInfo(this.userName, this.serverRmi);
            WhiteboardImpl whiteboard = new WhiteboardImpl();
            Naming.bind("rmi://" + serverRmi + "/" + this.serviceName, whiteboard);

            // Create the room
            whiteboard.createRoom(this);

            // Get the Remote Whiteboard
            System.out.println("Initializing the whiteboard.");
            view = new CreateWhiteBoardView();


//            Naming.rebind("rmi://" + serverRmi + "/" + clientServiceName, this);
//            whiteboard = (iWhiteboard) Naming.lookup("rmi://" + serverRmi + "/" + serviceName);


//      GUI.set_wb(wb);
//      GUI.setUsername(userName);
//      GUI.getpanel().setwb(wb);

        } catch (RemoteException | MalformedURLException | AlreadyBoundException e) {
            e.printStackTrace();
            Utils.popupMessage(Consts.Message.CONN_FAILED, Consts.Message.EXIT);
        }
    }

    @Override
    public void messageFromServer(String message) throws RemoteException {

    }

    @Override
    public void updateUserList(String[] currentUsers) throws RemoteException {

    }

    @Override
    public boolean approve(String username) throws RemoteException {
        int n = JOptionPane.showOptionDialog(null,
                username + " wants to share your whiteboard. ",
                "New Peers Join Request",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                Consts.APPROVAL_OPTIONS,
                "");

//        int flag = JOptionPane.showConfirmDialog(null,
//                username + " wants to share your whiteboard. ",
//                "New peers request joining", JOptionPane.YES_NO_OPTION);
//        return flag == 1;

        return false;
    }

    @Override
    public void load(byte[] b) throws RemoteException {

    }

    @Override
    public void reject(String str) throws RemoteException {

    }

    @Override
    public void info(String str) throws RemoteException {

    }

    @Override
    public String getUsername() throws RemoteException {
        return this.userName;
    }
}