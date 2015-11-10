package metadata;

/**
 * The Constants class stores important variables as constants for later use.
 */
public class Constants {

	// Request (1xx) + Response (2xx)
    //Client mesage: athentication: request an authentification, giving username & password
    public final static short CMSG_AUTH = 101;
    public final static short CMSG_DISCONNECT = 102;

    //Client message: resgister a nex user, given username and password
    public final static short CMSG_REGISTER = 103;
    //Client message: creation of a new player, given player type
    public final static short CMSG_CREATE_CHARACTER = 104;

//chat
    public final static short CMSG_CHAT_ALL = 105;
    public final static short CMSG_CHAT_ONE = 106;
    //connected users
    public final static short CMSG_USERS = 110;
    public final static short CMSG_MOVE = 107;
    public final static short CMSG_KEY = 108;
    public final static short SMSG_MOVE = 207;
    public final static short SMSG_KEY = 208;

//Server message: authentication succeeded (with a boolean?)
    public final static short SMSG_AUTH = 201;
    public final static short SMSG_DISCONNECT = 202;

    //Client message: send a message to the chat, given message
    public final static short CMSG_CHAT = 112;
    //Server message: send a message to the client, given message content and sender
    public final static short SMSG_CHAT = 212;
    public final static short SMSG_REGISTER = 203;
    public final static short REQ_HEARTBEAT = 301;
    public final static short SMSG_CREATE_CHARACTER = 204;

    //chat
    public final static short SMSG_CHAT_ALL = 205;
    public final static short SMSG_CHAT_ONE = 206;
    //connected users
    public final static short SMSG_USERS = 210;

    public final static short CMSG_HEARTBEAT = 113;
    public final static short SMSG_HEARTBEAT = 213;
    public final static short CMSG_SAVE_EXIT_GAME = 119;
    public final static short SMSG_SAVE_EXIT_GAME = 219;

    // Test Request + Response
    public final static short RAND_INT = 1;
    public final static short RAND_STRING = 2;
    public final static short RAND_SHORT = 3;
    public final static short RAND_FLOAT = 4;
    // Other
    public static final int SAVE_INTERVAL = 60000;
    public static final String CLIENT_VERSION = "1.00";
    public static final int TIMEOUT_SECONDS = 90;
}
