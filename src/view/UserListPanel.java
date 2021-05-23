package view;

import common.Consts;
import service.iWhiteboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.List;

public class UserListPanel extends JPanel {

    private JList<String> list;
    private DefaultListModel<String> model;
    private iWhiteboard whiteboard;


    public UserListPanel(boolean isManager) {

        setBorder(new EmptyBorder(5, 5, 5, 5));

        list = new JList<String>();

        model = new DefaultListModel<>();
        list.setModel(model);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, Color.LIGHT_GRAY));
        list.setPreferredSize(new Dimension(240, 250));

        if (isManager) {
            addKickUser();
        } else {
            list.setEnabled(false);
        }

        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(240, 250));
        add(listScroller);

        setPreferredSize(new Dimension(260, 260));
        setBackground(Color.PINK);
        setVisible(true);
    }


    private void addKickUser() {
        list.addListSelectionListener(e -> {
            String username = list.getSelectedValue();

            if (username.startsWith(Consts.Service.MGR_NAME)) {
                return;
            }

            int res = JOptionPane.showConfirmDialog(null,
                    "Do you confirm to kick " + username,
                    "Kick User",
                    JOptionPane.YES_NO_OPTION);

            if (res == JOptionPane.YES_OPTION) {
                try {
                    whiteboard.removeUser(username.replace(Consts.Service.CLIENT_NAME, ""));
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }
            }
        });
    }

    public void updateList(DefaultListModel<String> model) {
        this.list.setModel(model);
    }

    public DefaultListModel<String> getModel() {
        return model;
    }

    public iWhiteboard getWhiteboard() {
        return whiteboard;
    }

    public void setWhiteboard(iWhiteboard whiteboard) {
        this.whiteboard = whiteboard;
    }
}
