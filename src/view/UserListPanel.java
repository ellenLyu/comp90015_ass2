package view;

import common.Consts;
import service.iWhiteboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class UserListPanel extends JPanel {

    private final JList<String> list;
    private final DefaultListModel<String> model;
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
        }

        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(240, 250));
        add(listScroller);

        setPreferredSize(new Dimension(260, 260));
        setVisible(true);
    }


    private void addKickUser() {
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                System.out.println("====> " + e);
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
                        whiteboard.removeUser(username.replace(Consts.Service.CLIENT_NAME, ""),
                                list.getSelectedIndex());
                    } catch (RemoteException remoteException) {
                        remoteException.printStackTrace();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
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
