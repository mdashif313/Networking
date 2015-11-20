
package com.ashif.net;



import java.io.*;
import java.net.*;


/**
 *
 * @author Ashif
 */



public class Receiver extends Thread{
    
    private Socket sock;
    private DataInputStream input;
    
    
    public Receiver(Socket sock) throws IOException{
        
        this.sock=sock;
        input=new DataInputStream(sock.getInputStream());
    }
    
    
    public void run(){
        
        while(true){
            try {
                System.out.println(input.readUTF());
                
            } catch (Exception e) {
                System.err.println("Exception inside receiver "+e);
            }
        }
    }
}
