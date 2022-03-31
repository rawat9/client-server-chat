package Server;

class Member {
    private final ConnectionHandler connection;
    private User user;

    public Member(ConnectionHandler connection) {
        this.connection = connection;
    }

    public void assignUser(String id, String username, String ipAddress) {
        user = new User(id, username, ipAddress);
    }

    public User getUser() {
        return user;
    }

    public ConnectionHandler getConnection() {
        return this.connection;
    }

    public void closeConnection() {
        if (!this.connection.isInterrupted()) {
            this.connection.interrupt();
        }
    }
}
