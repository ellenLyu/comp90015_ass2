package app;

import common.Consts;
import common.Utils;
import service.iUser;
import view.JoinWhiteBoardView;
import view.StartAppDialog;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class JoinWhiteBoard extends UnicastRemoteObject implements iUser {

    private static JoinWhiteBoardView view;
    private final String userName;
    private final String serverRmi;
    private final String serviceName;
    private final int hostPort;
    private final String hostAddress;
    private final String clientServiceName;
    private service.iWhiteboard whiteboard;


    protected JoinWhiteBoard(String hostAddress, int hostPort, String username) throws RemoteException {
        this.userName = username.trim();
        this.hostAddress = hostAddress;
        this.hostPort = hostPort;


        this.serverRmi = hostAddress + ":" + hostPort;
        this.clientServiceName = "Create";
        this.serviceName = Consts.Service.WHITEBOARD;
    }

    public static void main(String[] args) {

        StartAppDialog startAppDialog = new StartAppDialog();
        Map<String, String> dialogInput = startAppDialog.showDialog();

        // Check input port
        if (!Utils.checkStartAppArgs(dialogInput)) {
            return;
        }

        try {
            JoinWhiteBoard jwb = new JoinWhiteBoard(
                    dialogInput.get(Consts.ServerView.IP_ADDRESS),
                    Integer.parseInt(dialogInput.get(Consts.ServerView.PORT)),
                    dialogInput.get(Consts.ServerView.USERNAME));
            jwb.connect();
        } catch (RemoteException e) {
            // TODO:
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            // RMI
            UserInfo userInfo = new UserInfo(this.userName, this.serverRmi);

            // Get the Remote Whiteboard
            whiteboard = (service.iWhiteboard) Naming.lookup("rmi://" + serverRmi + "/" + serviceName);
            Naming.rebind("rmi://" + serverRmi + "/" + clientServiceName, this);

            // Bind Whiteboard
            System.out.println("bind");

            if (whiteboard.isEmptyRoom()) {
                Utils.popupMessage(Consts.Message.NO_ROOM, Consts.Message.EXIT);
            }

            if (whiteboard.existingName(this.userName)) {
                Utils.popupMessage(Consts.Message.EXIST_NAME, Consts.Message.EXIT);
            }

//            iWhiteboard.registerListener(details);
            view = new JoinWhiteBoardView();

        } catch (RemoteException | NotBoundException | MalformedURLException e) {
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
    public boolean approve(String str) throws RemoteException {
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
