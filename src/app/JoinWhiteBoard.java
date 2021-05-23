package app;

import common.Consts;
import common.Utils;
import service.iUser;
import service.impl.ChatRunable;
import service.impl.ShapeRunnable;
import service.impl.ThreadPool;
import service.impl.UserListRunnable;
import view.JoinWhiteBoardView;
import view.Shape;
import view.StartAppDialog;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinWhiteBoard extends UnicastRemoteObject implements iUser {

    private JoinWhiteBoardView view;
    private final String userName;
    private final String serverRmi;
    private final String serviceName;
    private final int hostPort;
    private final String hostAddress;
    private final String clientServiceName;
    private service.iWhiteboard whiteboard;
    private HashMap<String, String> userInfo = new HashMap<>();
    private ThreadPool threadPool;


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
            userInfo.put(Consts.Service.USERNAME, this.userName);
            userInfo.put(Consts.Service.RMI_HOST, this.serverRmi);

            // Get the Remote Whiteboard
            whiteboard = (service.iWhiteboard) Naming.lookup("rmi://" + serverRmi + "/" + serviceName);
            Naming.rebind("rmi://" + serverRmi + "/" + this.userName, this);

            // Bind Whiteboard
            if (whiteboard.isEmptyRoom()) {
                Utils.popupErrMessage(Consts.Message.NO_ROOM, Consts.Message.EXIT);
            }

            if (whiteboard.existingName(this.userName)) {
                Utils.popupErrMessage(Consts.Message.EXIST_NAME, Consts.Message.EXIT);
            }

            Map<String, Object> loadDataList = whiteboard.join(userInfo);

            threadPool = new ThreadPool(3);
            view = new JoinWhiteBoardView();
            view.setWhiteboard(whiteboard);
            view.setUserinfo(userInfo);
            view.setVisible(true);

            threadPool.execute(new ShapeRunnable(this, (List<Shape>) loadDataList.get("ShapeList")));
            threadPool.execute(new UserListRunnable(this, (DefaultListModel<String>) loadDataList.get("UsernameList")));
            System.out.println(whiteboard);


        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
            Utils.popupErrMessage(Consts.Message.CONN_FAILED, Consts.Message.EXIT);
        }
    }

    @Override
    public void rcvChatMsg(String type, String from, String message) throws RemoteException {
        threadPool.execute(new ChatRunable(view, type, from, message));
    }



    @Override
    public boolean approve(String str) throws RemoteException {
        return false;
    }

    @Override
    public void updateUserList(DefaultListModel<String> model) throws RemoteException {
        view.updateList(model);
    }

    @Override
    public void updateShape(Shape shape) throws RemoteException {
        view.getPaintPanel().draw(shape);
    }

    @Override
    public void load(List<Shape> shapeList) throws RemoteException {
        view.getPaintPanel().loads(shapeList);
    }

    @Override
    public void leave(String message) throws RemoteException {
        try {
            Naming.unbind("rmi://" + serverRmi + "/" + this.userName);
        } catch (NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
        Utils.popupErrMessage(message, Consts.Message.EXIT);
    }

    @Override
    public void popupMessage(String str, int mode, String... args) throws RemoteException {

    }

    @Override
    public String getUsername() throws RemoteException {
        return this.userName;
    }

    @Override
    public List<Shape> getShapeList() {
        return view.getPaintPanel().getShapeList();
    }

    public HashMap<String, String> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(HashMap<String, String> userInfo) {
        this.userInfo = userInfo;
    }

    public JoinWhiteBoardView getView() {
        return view;
    }
}
