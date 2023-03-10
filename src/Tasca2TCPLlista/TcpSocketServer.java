package Tasca2TCPLlista;

import Tasca1TCPSecretNum.SecretNum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TcpSocketServer {
    private int port;

    private TcpSocketServer(int port ) {
        this.port = port;
    }

    private void listen() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while(true) { //esperar connexió del client i llançar thread
                clientSocket = serverSocket.accept();
                //Llançar Thread per establir la comunicació
                ThreadSevidorAdivina_Obj FilServidor = new ThreadSevidorAdivina_Obj(clientSocket);
                Thread client = new Thread(FilServidor);
                client.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(TcpSocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        TcpSocketServer srv = new TcpSocketServer(5558);
        srv.listen();
    }


}