package Server;

public class User {
    private String ID;
    private String username;
    private boolean isCoordinator;

    public User(String ID, String username) {
        this.ID = ID;
        this.username = username;
        isCoordinator = false;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCoordinator() {
       isCoordinator = true;
    }

    public boolean getIsCoordinator() {
        return isCoordinator;
    }
}
