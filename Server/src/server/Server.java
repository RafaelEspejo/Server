/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.awt.Cursor;
import javax.swing.JTextField;


/**
 *
 * @author Rafael
 */
public class Server extends Thread{
    private boolean ejecutar=true;
    private boolean CONTROL=true;
    /*private InterfazGrafica console;
    private javax.swing.JTextArea textConsole;
    public Server(InterfazGrafica console){
        this.console=console;
    }*/
    public void run(){
        int i=1,n=0;
        /*textConsole=console.getConsole();
        textConsole.setSelectionStart(1000);
        textConsole.setSelectionEnd(1000);*/
        while (ejecutar){
            /*System.out.print(String.format("%c[%d;%df",0x1B,n,1));
            n++;
            if (n==50) n=6;*/
            
            //textConsole.setText(textConsole.getText()+"El valor de la i es "+i+"\n");
            if(CONTROL) System.out.println("El valor de la i es "+i);
            i++;
            /*n++;
            if (n==19) n=0;*/
            try {
                this.sleep(1000);
            } catch (InterruptedException ex) {
                
            }
        }
    }
    public boolean getEjecutar(){
        return ejecutar;
    }
    public void setEjecutar(boolean ejecutar){
        this.ejecutar=ejecutar;
    }
    public void setControl(boolean CONTROL){
        this.CONTROL=CONTROL;
    }
}
