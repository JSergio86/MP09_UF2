package TascaMulticast;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ClienteMulticast {
    /*Client que s'afegeix al Multicast SrvVelocitats.java que treu mitjanaes de velocitat */

    private boolean continueRunning = true;
    private MulticastSocket socket;
    private InetAddress multicastIP;
    private int port;
    private List<Integer> velocitats;
    private Integer sumVel;
    NetworkInterface netIf;
    InetSocketAddress group;

    public ClienteMulticast(int portValue, String strIp) throws IOException {
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        socket = new MulticastSocket(port);
        sumVel = 0;
        velocitats = new ArrayList();
        netIf = socket.getNetworkInterface();
        group = new InetSocketAddress(strIp,portValue);
    }

    public void runClient() throws IOException, InterruptedException {
        DatagramPacket packet;
        byte [] receivedData = new byte[120];

        socket.joinGroup(group,netIf);
        System.out.printf("Connectat a %s:%d%n",group.getAddress(),group.getPort());

        while(continueRunning){
            packet = new DatagramPacket(receivedData, 120);
            socket.setSoTimeout(5000);
            try{
                socket.receive(packet);
                continueRunning = getData(packet.getData(), packet.getLength());
            }catch(SocketTimeoutException e){
                System.out.println("S'ha perdut la connexió amb el servidor.");
                continueRunning = false;
            }
        }
        Thread.sleep(1000);

        socket.leaveGroup(group,netIf);
        socket.close();
    }

    protected boolean getData(byte[] data, int lenght) {
        boolean ret=true;
        String msg = new String(data,0,lenght);
        System.out.println(msg);

        return ret;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ClienteMulticast cvel = new ClienteMulticast(5557, "224.0.11.111");
        cvel.runClient();
        System.out.println("Parat!");

    }
}
