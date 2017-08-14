/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 *
 * @author Rafael
 */
public class Comunicacion extends Thread {

    public ServerSocket sc = null;
    public Socket so = null;
    private Server server;
    private LinkedList<Control> threads = new LinkedList<Control>();

    public Comunicacion(int PUERTO, Server server) {
        try {
            this.server = server;
            sc = new ServerSocket(PUERTO, 10);/* crea socket servidor que escuchara en puerto 5000*/

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void run() {
        while (true) {

            try {
                System.out.println("Esperando clientes");
                so = sc.accept();
                System.out.println("Un cliente se ha conectado.");
                DataInputStream entrada = new DataInputStream(so.getInputStream());
                DataOutputStream salida = new DataOutputStream(so.getOutputStream());
                byte[] buffer = new byte[1024];
                int size = entrada.read(buffer);
                String orden = new String(buffer, 0, size);
                if (orden.equals("hello server")) {
                    Login login = new Login(so, entrada, salida);
                    if (login.ComprovarIdentificacion()) {
                        Control p = new Control(so, server, this, entrada, salida);
                        p.start();
                        threads.add(p);
                    } else {
                        entrada.close();
                        salida.close();
                        so.close();
                    }
                } else {
                    entrada.close();
                    salida.close();
                    so.close();
                }

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    public LinkedList<Control> getThreads() {
        return threads;
    }
}
