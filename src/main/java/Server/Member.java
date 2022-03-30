package Server;

class Member {
    private String id;
    private String username;
    private String ipAddress;
    private boolean isCoordinator;
    private final ConnectionHandler connection;

    public Member(ConnectionHandler connection) {
        this.connection = connection;
        this.isCoordinator = false;
    }

    public ConnectionHandler getConnection() {
        return this.connection;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setCoordinator() {
        this.isCoordinator = true;
    }

    public boolean getCoordinator() {
        return this.isCoordinator;
    }

    public void closeConnection() {
        if (!this.connection.isInterrupted()) {
            this.connection.interrupt();
        }
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }
}
