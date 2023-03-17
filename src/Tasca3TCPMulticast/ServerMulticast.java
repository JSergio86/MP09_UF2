package Tasca3TCPMulticast;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.util.Random;

public class ServerMulticast {
    /* Servidor Multicast */
    MulticastSocket socket;
    InetAddress multicastIP;
    int port;
    boolean continueRunning = true;
    public ServerMulticast(int portValue, String strIp) throws IOException {
        socket = new MulticastSocket(portValue);
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
    }

    public void runServer() throws IOException, ClassNotFoundException {
        while(continueRunning){
            byte[] receivingData = new byte[1024];
            DatagramPacket packet = new DatagramPacket(receivingData, receivingData.length);

            // Recibir el paquete utilizando el método receive() del DatagramSocket
            socket.receive(packet);
            continueRunning = getData(packet.getData(), packet.getLength());

            socket.close();

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.getMessage();
            }

        }
        socket.close();
    }

    protected boolean getData(byte[] data, int lenght) {
        boolean ret=true;
        String msg = new String(data,0,lenght);
        System.out.println("Mensaje recibido: " + msg);
        return ret;
    }

}