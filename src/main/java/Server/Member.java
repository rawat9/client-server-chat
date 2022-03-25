package Server;

import java.util.UUID;

class Member {
    private UUID id;
    private String username;
    private String ipAddress;
    private final ConnectionHandler connection;

    public Member(ConnectionHandler connection) {
        this.connection = connection;
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
        this.id = UUID.fromString(id);
    }

    public UUID getId() {
        return this.id;
    }

    public void closeConnection() {
        if (!this.connection.isInterrupted()) {
            this.connection.interrupt();
        }
    }
}
