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

    private SharedState sharedState;
    private ConnectionHandler connectionHandler1;
    private ConnectionHandler connectionHandler2;
    private long thread1;
    private long thread2;

    @BeforeEach
    public void setUp() {
        sharedState = SharedState.getInstance();
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

        HashMap<Long, Member> membersMap = sharedState.getMembersMap();
        LinkedList<Long> membersOrder = sharedState.getMembersOrder();

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
        String clientId = "123ab";
        String username = "oskar";
        String ipAddress = "127.0.0.1";

        // Firstly, add two clients
        sharedState.initiateMember(connectionHandler1);
        sharedState.addMember(clientId+":"+username, thread1, ipAddress);

        sharedState.initiateMember(connectionHandler2);
        sharedState.addMember("124ab:pedro", thread2, ipAddress);

        Message message = new Message("123ab", "Message", "124ab");
        sharedState.addMessage(message, (long) thread1);

        List<Message> messages = sharedState.getMessages();
        assertEquals(1, messages.size());
    }

    @Test
    void addMember() {
        LinkedList<Long> membersOrder = sharedState.getMembersOrder();
        HashMap<Long, Member> membersMap = sharedState.getMembersMap();

        // Before adding a user, membersOrder list and membersMap map should be empty
        assertEquals(0, membersOrder.size());
        assertEquals(0, membersMap.size());

        // Create a member
        sharedState.initiateMember(connectionHandler1);

        String userId = "123ab";
        String username = "oskar";
        String ipAddress = "127.0.0.1";
        sharedState.addMember(userId+":"+username, thread1, ipAddress);

        membersOrder = sharedState.getMembersOrder();
        membersMap = sharedState.getMembersMap();

        User createdUser = membersMap.get(thread1).getUser();
        // Newly created member should be coordinator, because he is the first
        assertTrue(createdUser.getIsCoordinator());

        // membersMap and membersOrder should have size equal to 1
        assertEquals(1, membersMap.size());
        assertEquals(1, membersOrder.size());

        // Check if correct info is added to members order
        assertEquals(thread1, membersOrder.getFirst());

        // Check if correct info is added to members map
        assertEquals(userId,createdUser.getID());
        assertEquals(username, createdUser.getUsername());
        assertEquals(ipAddress, createdUser.getIpAddress());
    }

    @Test
    void isClientInfoValid() {
        String clientInfo = "123ab:oskar";

        boolean clientInfoValid = sharedState.isClientInfoValid(clientInfo);

        assertTrue(clientInfoValid);

        sharedState.initiateMember(connectionHandler1);
        sharedState.addMember(clientInfo, thread1, "123.0.0.1");

        clientInfoValid = sharedState.isClientInfoValid(clientInfo);
        assertFalse(clientInfoValid);
    }

    @Test
    void removeAllMembers() {
        // Firstly add some members
        sharedState.initiateMember(connectionHandler1);
        sharedState.initiateMember(connectionHandler2);
        sharedState.addMember("123ab:oskar", thread1, "127.0.0.1");
        sharedState.addMember("124ab:pedro", thread2, "127.0.0.1");

        // There should be two members in a SharedState
        assertEquals(2, sharedState.getMembersOrder().size());
        assertEquals(2, sharedState.getMembersMap().size());

        sharedState.removeAllMembers();

        // After clearing there should not be any member in a SharedState
        assertEquals(0, sharedState.getMembersOrder().size());
        assertEquals(0, sharedState.getMembersMap().size());
    }

    @Test
    void removeMember() {
        // Firstly add some members
        sharedState.initiateMember(connectionHandler1);
        sharedState.initiateMember(connectionHandler2);
        sharedState.addMember("123ab:oskar", thread1, "127.0.0.1");
        sharedState.addMember("124ab:pedro", thread2, "127.0.0.1");

        HashMap<Long, Member> membersMap = sharedState.getMembersMap();
        LinkedList<Long> membersOrder = sharedState.getMembersOrder();

        assertEquals(2, membersMap.size());
        assertEquals(2, membersOrder.size());

        assertFalse(membersMap.get(thread2).getUser().getIsCoordinator());

        sharedState.removeMember(thread1);

        membersMap = sharedState.getMembersMap();
        membersOrder = sharedState.getMembersOrder();

        assertEquals(1, membersMap.size());
        assertEquals(1, membersOrder.size());

        assertTrue(membersMap.get(thread2).getUser().getIsCoordinator());
    }
}