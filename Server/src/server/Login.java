/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafael
 */
public class Login {

    private final String nick = "rafa";
    private final String password = "1234";
    private Socket so;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private final String mensajenick = "Introduce el nick\r\n;";
    private final String mensajepass = "Introduce el password\r\n;";

    public Login(Socket so, DataInputStream entrada, DataOutputStream salida) {
        this.so = so;
        this.entrada = entrada;
        this.salida = salida;
    }

    public boolean ComprovarIdentificacion() {
        int size;
        byte[] buffer = new byte[1024];
        String orden="";
        try {
            salida.write(mensajenick.getBytes(), 0, mensajenick.getBytes().length);
            size = entrada.read(buffer);
            orden = new String(buffer, 0, size);
            salida.write(("Verificando el nick\r\n").getBytes(), 0, ("Verificando el nick\r\n").getBytes().length);
            if (orden.equals(nick)) {
                salida.write(mensajepass.getBytes(), 0, mensajepass.getBytes().length);
                buffer = new byte[1024];
                size = entrada.read(buffer);
                orden = new String(buffer, 0, size);
                salida.write(("Verificando el password\r\n").getBytes(), 0, ("Verificando el password\r\n").getBytes().length);
                if (orden.equals(password)) {
                    salida.write(("Indentificacion correcta\r\nConexion establecida\r\nBienvenido " + nick + " \r\n;").getBytes(), 0, ("Indentificacion correcta\r\nConexion establecida\r\nBienvenido " + nick + " \r\n;").getBytes().length);
                    System.out.println("Identificacion correcta\nConexion establecida");
                }else{
                    salida.write(("Password incorrecto\r\n;").getBytes(), 0, ("Password incorrecto\r\n;").getBytes().length);
                return false;
                }
            } else {
                salida.write(("Nick incorrecto\r\n;").getBytes(), 0, ("Nick incorrecto\r\n;").getBytes().length);
                System.out.println("Nick incorrecto");
                return false;
            }

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        return true;
    }

}
