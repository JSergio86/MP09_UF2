package TascaMulticast;

import java.io.IOException;
import java.net.DatagramPacket;
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
    String[] palabras;
    public ServerMulticast(int portValue, String strIp) throws IOException {
        socket = new MulticastSocket(portValue);
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        palabras = new String[]{"La música es una expresión artística que puede transmitir emociones y sentimientos de una manera única.", "El perro ladra al amanecer a la noche dea.", "El café es mi bebida favorita.", "El mar es hermoso al atardecer.", "El aprendizaje continuo es fundamental para mantenernos actualizados y adaptarnos a los cambios constantes del mundo actual.", "El viento mueve las hojas del árbol.", "La nieve cubre todo a su paso."};
    }

    public void runServer() throws IOException {
        DatagramPacket packet;
        byte [] sendingData;

        while(continueRunning){
            Random random = new Random();
            int indiceAleatorio = random.nextInt(palabras.length);
            String palabraAleatoria = palabras[indiceAleatorio];
            byte[] palabraEnviada = palabraAleatoria.getBytes();

            sendingData = ByteBuffer.allocate(palabraEnviada.length).put(palabraEnviada).array();

            packet = new DatagramPacket(sendingData, sendingData.length,multicastIP, port);
            socket.send(packet);

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.getMessage();
            }

        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        //Canvieu la X.X per un número per formar un IP.
        //Que no sigui la mateixa que la d'un altre company
        ServerMulticast srvVel = new ServerMulticast(5557, "224.0.11.111");
        srvVel.runServer();

    }
}
