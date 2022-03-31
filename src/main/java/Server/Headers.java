package Server;

public enum Headers {
    MESSAGE, // Informs the server that next lines will contain the message
    CLIENT_INFO_AWAITING, // informs the connection that server is waiting for the username and unique id
    CLIENT_INFO_SENDING, // informs the server that next line will contain the information about client
    CLIENT_INFO_VALID, // informs the client that username and unique id is valid
    CLIENT_INFO_INVALID, // Informs the client that the client info was invalid
    USERS_LIST, // Informs the client that the next line contains list of all users connected
    INVALID_HEADER,
    COORDINATOR_INFO, // Informs Client that user is a coordinator
}
