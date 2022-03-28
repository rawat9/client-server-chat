import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        ConnectionController connection = new ConnectionController();

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter connection details: ");
        String user_server = scan.next();
        Integer user_port = scan.nextInt();
//        Connection connection_server = new Connection(user_server, user_port);

    }

}
