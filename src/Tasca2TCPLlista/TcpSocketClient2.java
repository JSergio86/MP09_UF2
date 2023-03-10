package Tasca2TCPLlista;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TcpSocketClient2 extends Thread {
    private String Nom;
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private Scanner scin;
    private boolean continueConnected;
    private Llista llista;

    private TcpSocketClient2(String hostname, int port) {
        try {
            socket = new Socket(InetAddress.getByName(hostname), port);
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (UnknownHostException ex) {
            System.out.println("Error de connexió. No existeix el host: " + ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        continueConnected = true;
        scin = new Scanner(System.in);
        List<Integer> listaNumeros = new ArrayList<>();
        listaNumeros.add(3);
        listaNumeros.add(3);
        listaNumeros.add(8);
        listaNumeros.add(1);
        listaNumeros.add(2);
        listaNumeros.add(5);

        llista = new Llista(Nom, listaNumeros);

    }

    public void run() {
        String msg = null;
        while (continueConnected) {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                objectOutputStream.writeObject(llista);
                objectOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            llista = getRequest();
        }

        close(socket);

    }

    private Llista getRequest() {
        try {
            ObjectInputStream ois = new ObjectInputStream(in);
            llista = (Llista) ois.readObject();

            for(int i=0;i<llista.getNumberList().size();i++){
                System.out.println(llista.getNumberList().get(i));
            }

            continueConnected=false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return llista;
    }


    private void close(Socket socket) {
        //si falla el tancament no podem fer gaire cosa, només enregistrar
        //el problema
        try {
            //tancament de tots els recursos
            if (socket != null && !socket.isClosed()) {
                if (!socket.isInputShutdown()) {
                    socket.shutdownInput();
                }
                if (!socket.isOutputShutdown()) {
                    socket.shutdownOutput();
                }
                socket.close();
            }
        } catch (IOException ex) {
            //enregistrem l'error amb un objecte Logger
            Logger.getLogger(TcpSocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        String jugador, ipSrv;

        //Demanem la ip del servidor i nom del jugador
        Scanner sip = new Scanner(System.in);
        System.out.println("Nom jugador:");
        jugador = sip.next();

        TcpSocketClient2 clientTcp = new TcpSocketClient2("localhost", 5558);
        clientTcp.Nom = jugador;
        clientTcp.start();
    }
}