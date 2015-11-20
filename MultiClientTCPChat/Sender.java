package com.ashif.net;


import java.net.*;
import java.io.*;
import java.util.Scanner;


/**
 *
 * @author Ashif
 */


public class Sender extends Thread{

    private Socket sock;
    private Scanner in;
    private DataOutputStream output;
    private String sender;
    
    public Sender(Socket sock, String sender) throws IOException{
        
        this.sock=sock;
        this.sender=sender;
        in=new Scanner(System.in);
        output= new DataOutputStream(sock.getOutputStream());
        
    }
    
    
    public void run() {               
        
        while (true) {            
            try {
                
                String inFromUser=sender+": "+in.nextLine();
                output.writeUTF(inFromUser);
                
            } catch (Exception e) {
                System.err.println("Exception inside sender "+e);
            }
        }
        
    }
    
}
