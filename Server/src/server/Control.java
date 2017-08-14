/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Rafael
 */
public class Control extends Thread {

    private Server server;
    private Comunicacion comunicacion;
    private boolean ejecutar = true;
    private Socket so = null;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private String orden = "";
    private long id;
    private LinkedList<Control> threads;

    public Control(Socket so) {
        this.so = so;
        initServer();
        //this.run();

    }

    public Control(Socket so, Server server, Comunicacion comunicacion,DataInputStream entrada,DataOutputStream salida) {
        this.so = so;
        //this.run();
        this.server = server;
        this.comunicacion = comunicacion;
        this.entrada=entrada;
        this.salida=salida;
        id = this.getId();
        
    }

    public void initServer() {
        server = new Server();
        server.start();
        server.setControl(true);
    }

    private void initControl() {
        boolean ejecutar = true;
        byte[] ls;
        int size;
        String[] veri = new String[3];
        String p;
        try {

            
           /* ls = new byte[1024];
            size = entrada.read(ls);
            orden=new String(ls,0,size);
            if (orden.contains("connect")) {
                System.out.println("Conexion establecida");
                p = "Conexion establecida\r\n";
                salida.write(p.getBytes(), 0, p.getBytes().length);
                veri = orden.split(" ");
                System.out.println("Verificando autenticidad....");
                p = "Verificando autenticidad....\r\n";
                salida.write(p.getBytes(), 0, p.getBytes().length);
                if (veri[2].equals("rafa") && veri[3].equals("1234")) {
                    System.out.println("Permiso concedido");
                    p = "Permiso concedido\r\nBienvenido " + veri[1] + "\r\n;";
                    salida.write(p.getBytes(), 0, p.getBytes().length);
*/
                    while (ejecutar) {
                        //System.out.print(String.format("%c[%d;%df",0x1B,51,2));
                        //orden = "";

                        ls = new byte[1024];

                        size = entrada.read(ls);

                        orden=new String(ls,0,size);
                       
                        System.out.print(orden);
                        System.out.println(" " + size);
                        //System.out.println("Orden recibida");
                        if (orden.equals("stopall")) {
                            p = "Deteniendo el servidor...........\r\n";
                            salida.write(p.getBytes(), 0, p.getBytes().length);
                            p = "Servidor detenido, finalizando la aplicacion\r\n;";
                            salida.write(p.getBytes(), 0, p.getBytes().length);
                            ejecutar = false;
                            //this.sleep(1000);
                            //server.setEjecutar(false);
                            server.setControl(ejecutar);
                            server.setEjecutar(ejecutar);
                            entrada.close();
                            salida.close();
                            so.close();
                            comunicacion.sc.close();
                            comunicacion.stop();
                            threads=comunicacion.getThreads();
                            for (int i = 0; i < threads.size(); i++) {
                                if (threads.get(i).getId()!=id) {
                                    threads.get(i).entrada.close();
                                    threads.get(i).salida.close();
                                    threads.get(i).so.close();
                                    threads.get(i).stop();
                                }
                            }
                        } else if (orden.equals("logout")) {
                            p = "Cerrando conexion\r\n;";
                            salida.write(p.getBytes(), 0, p.getBytes().length);
                            ejecutar = false;
                            entrada.close();
                            salida.close();
                            so.close();
                        } /*else if (control.contains("stop")) {
                         switch (control) {
                         case "stop1":
                         System.out.println("Deteniendo el servidor 1 ...........");
                         server.get(0).ejecutar = false;
                         server.remove(0);
                         System.out.println("Servidor 1 detenido");
                         break;
                         case "stop2":
                         System.out.println("Deteniendo el servidor 2 ...........");
                         server.get(1).ejecutar = false;
                         server.remove(1);
                         System.out.println("Servidor 2 detenido");
                         break;
                         case "stop3":
                         System.out.println("Deteniendo el servidor 3 ...........");
                         server.get(2).ejecutar = false;
                         server.remove(2);
                         System.out.println("Servidor 3 detenido");
                         break;
                         case "stop4":
                         System.out.println("Deteniendo el servidor 4 ...........");
                         server.get(3).ejecutar = false;
                         server.remove(3);
                         System.out.println("Servidor 4 detenido");
                         break;
                         }
                         if (server.isEmpty()) {
                         ejecutar = false;
                         }
                         }*/ else {
                            p = "El comando de control "+orden+" no existe\r\n;";
                            salida.write(p.getBytes(), 0, p.getBytes().length);

                        }
                    }
/*
                } else {
                    p = "No existe el usuario, pruebelo otra vez ;";
                    salida.write(p.getBytes(), 0, p.getBytes().length);
                }
            } else {
                p = "No es el comando de conexion intentelo de nuevo ;";
                salida.write(p.getBytes(), 0, p.getBytes().length);
            }*/
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void run() {
        

        initControl();

    }

    public boolean getEjecutar() {
        return ejecutar;
    }
}
