import java.io.IOException;

public class MainCliente {
    public static void main(String[] args) throws IOException {
        DatagramSocketClient datagramSocketClient = new DatagramSocketClient();

        datagramSocketClient.init("localhost",5555);

        datagramSocketClient.runClient();

    }
}
