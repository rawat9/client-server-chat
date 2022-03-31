package Client;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
class ClientTest {

    private final int port = 6900;

    @Mock
    Socket clientSocket;

    @Mock
    InputStream inputStream;

    @Mock
    OutputStream outputStream;

    @Test
    public void testReadClientStreamLine() throws IOException {
        String inputString = "Hello\n";
        BufferedReader input = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(inputString.getBytes())));
        assertEquals("Hello", readFromInputStream(input));
    }

    @Test
    public void testWriteClientStreamLine() {
        String inputString = "World";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(outContent, true);
        writeToOutputStream(printWriter, inputString);
        assertEquals("World\n", outContent.toString());
    }

    @Test
    public void testServerSocketWithSpecificPortGetsCreated() throws IOException {
        ServerSocket testServerSocket = createServerSocket(port);
        assertEquals(testServerSocket.getLocalPort(), port);
    }

    public static String readFromInputStream(BufferedReader input) throws IOException {
        return input.readLine();
    }

    public static void writeToOutputStream(PrintWriter output, String data) {
        output.println(data);
    }
    public static ServerSocket createServerSocket(int port) throws IOException {
        return new ServerSocket(port);
    }
}