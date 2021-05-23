package common;

public class Consts {

    public static final String[] APPROVAL_OPTIONS = {"Approve", "Reject"};

    public class ServerView {
        public static final String IP_ADDRESS = "ip";

        public static final String PORT = "port";

        public static final String USERNAME = "username";

    }

    public class Service {
        public static final String WHITEBOARD = "Whiteboard";

        public static final String SERVER = "Server";

        public static final String USERNAME = "username";

        public static final String RMI_HOST = "rmiHost";

        public static final String MGR_NAME = "[Manager]: ";

        public static final String CLIENT_NAME = "[User]: ";

        public static final String SERVER_NAME = "[Server]: ";


    }

    public class PaintType {
        public static final String TEXT = "text";

        public static final String LINE = "line";

        public static final String RECT = "rectangle";

        public static final String CIRCLE = "circle";

        public static final String OVAL = "oval";

        public static final String ERASER = "Eraser";

    }

    /**
     * Message
     */
    public class Message {
        // Back to the interface
        public static final int EXIT = 1;

        // Stop the program
        public static final int BACK = 0;

        public static final String CONN_FAILED = "Connection Failed";

        public static final String INVALID_PORT = "Please check the input <Port number>";

        public static final String INVALID_IP = "Please check the input <IP Address>";

        public static final String INVALID_USERNAME = "Please check the input <Username>";

        public static final String NO_ROOM = "Cannot find the room";

        public static final String EXIST_NAME = "The name already exists, please change to another one.";

        public static final String REJECT_REQUEST = "Sorry, the room manager does not approved your request.";

        public static final String BEEN_KICKED = "Sorry, you have been kicked out by the room manager";

        public static final String SUCC_KICKED = "User # has been kicked out.";


    }

}
