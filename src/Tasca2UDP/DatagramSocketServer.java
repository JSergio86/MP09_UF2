package Tasca2UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class DatagramSocketServer {
    DatagramSocket socket;
    InetAddress clientIP;
    SecretNum secretNum = new SecretNum();
    int intentos = 0;

    //Instàciar el socket
    public void init(int port) throws SocketException {
        socket = new DatagramSocket(port);
        secretNum.pensa(10);
    }

    public void runServer() throws IOException {
        byte [] receivingData = new byte[4];
        byte [] sendingData;

        int clientPort;

        while(true) {
            DatagramPacket packet = new DatagramPacket(receivingData,4);
            socket.receive(packet);
            clientIP = packet.getAddress();
            sendingData = processData(packet.getData(),packet.getLength());
            //Llegim el port i l'adreça del client per on se li ha d'enviar la resposta
            clientPort = packet.getPort();
            packet = new DatagramPacket(sendingData,sendingData.length,clientIP,clientPort);
            socket.send(packet);
        }
    }

    //El server retorna al client el mateix missatge que li arriba però en majúscules
    private byte[] processData(byte[] data, int lenght) {
        int n = ByteBuffer.wrap(data).getInt();
        byte[] missatge;

            missatge = ByteBuffer.allocate(4).putInt(secretNum.comprova(n)).array();
            return missatge;



    }

    public static void main(String[] args) {
        DatagramSocketServer server = new DatagramSocketServer();
        try {
            server.init(5555);
            server.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}