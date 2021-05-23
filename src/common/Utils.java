package common;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Logger;

public class Utils {

    private static final Logger logger = Logger.getLogger(Utils.class.getName());

    /**
     * Pop up the message on the interface
     *
     * @param message message text
     * @param mode    EXIT or BACK
     * @param args    values for placeholders in the message
     */
    public static void popupErrMessage(String message, int mode, String... args) {

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

    public static void popupMessage(String message, int mode, String... args) {

        for (String placeholder : args) {
            message = message.replaceFirst("#", placeholder);
        }

        JOptionPane.showMessageDialog(null, message);

        if (Consts.Message.EXIT == mode) {
            System.exit(0);
        }
    }

    public static boolean isNotEmpty(String s) {
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


    public static boolean isValidStartAppArgs(Map<String, String> dialogInput) {
        // Check input port
        try {
            int port = Integer.parseInt(dialogInput.get(Consts.ServerView.PORT));
            logger.info("Port: " + port);
        } catch (NumberFormatException e) {
            Utils.popupErrMessage(Consts.Message.INVALID_PORT, Consts.Message.EXIT);
            return true;
        }

        // Check input ip address
        try {
            InetAddress serverIP = InetAddress.getByName(dialogInput.get(Consts.ServerView.IP_ADDRESS));
            logger.info("Server IP: " + serverIP);
        } catch (UnknownHostException e) {
            Utils.popupErrMessage(Consts.Message.INVALID_IP, Consts.Message.EXIT);
            return true;
        }

        // Check input ip address
        String username = dialogInput.get(Consts.ServerView.USERNAME);
        logger.info("Username: " + username);
        if (!Utils.isNotEmpty(username)) {
            Utils.popupErrMessage(Consts.Message.INVALID_USERNAME, Consts.Message.EXIT);
            return true;
        }

        return false;
    }
}
