import java.io.IOException;

public class MainServer {
    public static void main(String[] args) throws IOException {
        DatagramSocketServer datagramSocketServer = new DatagramSocketServer();

        datagramSocketServer.init(5555);


        datagramSocketServer.runServer();

    }


}
