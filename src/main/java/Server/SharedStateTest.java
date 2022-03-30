package Server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import Client.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
class SharedStateTest {

    private ConnectionHandler connectionHandler1;
    private ConnectionHandler connectionHandler2;
    private long thread1;
    private long thread2;

    @BeforeEach
    public void setUp() {
        thread1 = 1234567;
        thread2 = 1245678;
        connectionHandler1 = mock(ConnectionHandler.class);
        connectionHandler2 = mock(ConnectionHandler.class);
        when(connectionHandler1.getId()).thenReturn(thread1);
        when(connectionHandler2.getId()).thenReturn(thread2);
    }

    @AfterEach
    public void tearDown() {
        // After every test remove all members
        SharedState sharedState = SharedState.getInstance();
        sharedState.removeAllMembers();
    }

    @Test
    void getInstance() {
        // Ensure that it returns instance of SharedState
        SharedState sharedState = SharedState.getInstance();
        assertNotNull(sharedState);
        assertInstanceOf(SharedState.class, sharedState);
    }

    @Test
    void initiateMember() {
        // Initiate member should create new instance of the Member
        // and add it to the Hash map, but not to LinkedList
        SharedState sharedState = SharedState.getInstance();

        HashMap<Long, Member> membersMap = sharedState.getMembersMap();
        LinkedList<String> membersOrder = sharedState.getMembersOrder();

        assertEquals(0, membersMap.size());
        assertEquals(0, membersOrder.size());

        sharedState.initiateMember(connectionHandler1);

        membersMap = sharedState.getMembersMap();

        assertEquals(1, membersMap.size());
        assertEquals(0, membersOrder.size());

        assertEquals(membersMap.get((long) 1234567).getConnection(), connectionHandler1);
    }

    @Test
    void addMessage() {
        // Check that addMessage adds message to the list and is being sent to the user
        SharedState sharedState = SharedState.getInstance();

        String clientId = "123ab";
        String username = "oskar";
        String ipAddress = "127.0.0.1";

        // Firstly, add two clients
        sharedState.initiateMember(connectionHandler1);
        sharedState.addMember(clientId+":"+username, thread1, ipAddress);

        sharedState.initiateMember(connectionHandler2);
        sharedState.addMember("124ab:pedro", thread2, ipAddress);

        Message message = new Message("123ab", "Message", "567cd");
        sharedState.addMessage(message, (long) 1234567);

        List<Message> messages = sharedState.getMessages();
        assertEquals(1, messages.size());
    }

    @Test
    void addMember() {
        SharedState sharedState = SharedState.getInstance();

        LinkedList<String> membersOrder = sharedState.getMembersOrder();
        HashMap<Long, Member> membersMap = sharedState.getMembersMap();

        assertEquals(0, membersOrder.size());
        assertEquals(0, membersMap.size());

        sharedState.initiateMember(connectionHandler1);

        String userId = "123ab";
        String username = "oskar";
        String ipAddress = "127.0.0.1";
        sharedState.addMember(userId+":"+username, thread1, ipAddress);

        membersOrder = sharedState.getMembersOrder();
        membersMap = sharedState.getMembersMap();

        Member createdMember = membersMap.get(thread1);

        assertEquals(1, membersMap.size());
        assertEquals(1, membersOrder.size());

        assertEquals(userId, membersOrder.getFirst());

        assertEquals(userId, createdMember.getId());
        assertEquals(username, createdMember.getUsername());
        assertEquals(ipAddress, createdMember.getIpAddress());

    }

    @Test
    void isClientInfoValid() {
        String clientInfo = "123ab:oskar";

        SharedState sharedState = SharedState.getInstance();
        boolean clientInfoValid = sharedState.isClientInfoValid(clientInfo);

        assertTrue(clientInfoValid);

        sharedState.initiateMember(connectionHandler1);
        sharedState.addMember(clientInfo, thread1, "123.0.0.1");

        clientInfoValid = sharedState.isClientInfoValid(clientInfo);
        assertFalse(clientInfoValid);
    }

    @Test
    void removeAllMembers() {
    }

    @Test
    void removeMember() {
    }
}