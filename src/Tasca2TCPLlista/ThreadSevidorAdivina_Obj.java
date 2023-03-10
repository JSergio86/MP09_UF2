package Tasca2TCPLlista;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ThreadSevidorAdivina_Obj implements Runnable {
    /* Thread que gestiona la comunicació de SrvTcPAdivina_Obj.java i un cllient ClientTcpAdivina_Obj.java */
    private Socket clientSocket = null;
    private InputStream in = null;
    private OutputStream out = null;
    private boolean acabat;

    Llista llista;

    List<Integer> listaCliente;


    public ThreadSevidorAdivina_Obj(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        //Al inici de la comunicació el resultat ha de ser diferent de 0(encertat)
        acabat = false;
        //Enllacem els canals de comunicació
        in = clientSocket.getInputStream();
        out = clientSocket.getOutputStream();
        System.out.println("canals i/o creats amb un nou jugador");
    }

    @Override
    public void run() {
        try {
            while (!acabat) {
                //Llegim la jugada
                ObjectInputStream ois = new ObjectInputStream(in);
                llista = (Llista) ois.readObject();

                listaCliente = llista.getNumberList();

                Collections.sort(listaCliente);
                HashSet<Integer> conjuntoCliente = new HashSet<>(listaCliente);
                List<Integer> listaSinDuplicados = new ArrayList<>(conjuntoCliente);
                llista = new Llista(llista.getNom(),listaSinDuplicados);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                objectOutputStream.writeObject(llista);
                objectOutputStream.flush();


            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
