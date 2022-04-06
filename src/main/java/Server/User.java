package Server;

import java.io.Serializable;

public class User implements Serializable {
    private String ID;
    private String username;
    private String ipAddress;
    private boolean isCoordinator;

    public User(String ID, String username, String ipAddress) {
        this.ID = ID;
        this.username = username;
        this.ipAddress = ipAddress;
        this.isCoordinator = false;
    }

    public String getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public void setCoordinator() {
        this.isCoordinator = true;
    }

    public boolean getIsCoordinator() {
        return isCoordinator;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
