package Server;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SharedStateTest {

    @Test
    void getInstance() {
        // Ensure that it returns instance of SharedState
        SharedState sharedState = SharedState.getInstance();
        assertNotNull(sharedState);
        assertInstanceOf(SharedState.class, sharedState);
    }

    @Test
    void initiateMember() {

    }

    @Test
    void addMessage() {
    }

    @Test
    void addMember() {
    }

    @Test
    void isClientInfoValid() {
    }

    @Test
    void removeAllMembers() {
    }

    @Test
    void removeMember() {
    }
}