/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Rafael
 */
public class PruebaServer {

      public static void main(String[] args) {
        
          int puerto=5001;
          
          //if(args!=null) puerto=Integer.parseInt(args[0]);

        
        System.out.println("Iniciando servidor.....");
        
        Server server = new Server();
        server.start();
        server.setControl(false);
        new Comunicacion(puerto,server).start();
        
        
    }

}
