package common;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class Utils {

    /**
     * Pop up the message on the interface
     *
     * @param message message text
     * @param mode    EXIT or BACK
     * @param args    values for placeholders in the message
     */
    public static void popupMessage(String message, int mode, String... args) {

        for (String placeholder : args) {
            message = message.replaceFirst("#", placeholder);
        }

        JOptionPane.showConfirmDialog(null,
                message, "Error Message",
                JOptionPane.OK_CANCEL_OPTION);

        if (Consts.Message.EXIT == mode) {
            System.exit(0);
        }
    }

    public static boolean isNotEmpty (String s) {
        int strLen;
        if (s == null || (strLen = s.length()) == 0) {
            return false;
        }

        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(s.charAt(i)))) {
                return true;
            }
        }
        return false;
    }


    public static boolean checkStartAppArgs(Map<String, String> dialogInput) {
        // Check input port
        try {
            int port = Integer.parseInt(dialogInput.get(Consts.ServerView.PORT));
            System.out.println(port);
        } catch (NumberFormatException e) {
            Utils.popupMessage(Consts.Message.INVALID_PORT, Consts.Message.EXIT);
            return false;
        }

        // Check input ip address
        try {
            InetAddress serverIP = InetAddress.getByName(dialogInput.get(Consts.ServerView.IP_ADDRESS));
            System.out.println(serverIP);
        } catch (UnknownHostException e) {
            Utils.popupMessage(Consts.Message.INVALID_IP, Consts.Message.EXIT);
            return false;
        }

        // Check input ip address
        String username = dialogInput.get(Consts.ServerView.USERNAME);
        System.out.println(username);
        if (!Utils.isNotEmpty(username)) {
            Utils.popupMessage(Consts.Message.INVALID_USERNAME, Consts.Message.EXIT);
            return false;
        }

        return true;
    }
}