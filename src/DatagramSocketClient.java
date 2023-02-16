import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class DatagramSocketClient {
    InetAddress serverIP;
    int serverPort;
    DatagramSocket socket;

    public void init(String host, int port) throws SocketException, UnknownHostException {
        serverIP = InetAddress.getByName(host);
        serverPort = port;
        socket = new DatagramSocket();
    }

    public void runClient() throws IOException {
        byte [] receivedData = new byte[1024];
        byte [] sendingData;

//a l'inici
        sendingData = getFirstRequest();
//el servidor atén el port indefinidament
        while(mustContinue(sendingData)){
            DatagramPacket packet = new DatagramPacket(sendingData,
                    sendingData.length,
                    serverIP,
                    serverPort);
//enviament de la resposta
            socket.send(packet);

//creació del paquet per rebre les dades
            packet = new DatagramPacket(receivedData, 1024);
//espera de les dades
            socket.receive(packet);
//processament de les dades rebudes i obtenció de la resposta
            sendingData = getDataToRequest(packet.getData(), packet.getLength());
        }
    }

    private byte[] getDataToRequest(byte[] data, int length) {
//procés diferent per cada aplicació
        String msg = new String(data,0,length);
        return msg.getBytes();
    }

    private byte[] getFirstRequest() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Mensaje del usuario");
        String mensaje = sc.nextLine();

        return mensaje.getBytes();

    }

    private boolean mustContinue(byte[] sendingData) {
       // String msg = new String(sendingData,0,1024);
      /*  if(msg.equals("adeu")){
            return false;
        }

       */

          return true;


    }
}
