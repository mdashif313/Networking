
package com.ashif.net;


import java.io.*;
import java.net.*;
import java.util.*;


/**
 *
 * @author Ashif
 */
public class Client {
    
    
    public static void initialize_client(Socket sock, String User_Name){
        try {
            
            DataOutputStream output= new DataOutputStream(sock.getOutputStream());
            output.writeUTF(User_Name);
            
        } catch (Exception e) {
            System.out.println("Exception Inside Client initialize_client "+e);
        }
      
    }
    
    public static void main(String[] args) throws IOException {
    
        Scanner in= new Scanner(System.in);
        System.out.print("Please give your user name : ");
        String inFromUser=in.nextLine();
        Socket sock=new Socket("localhost",3500);
        
        initialize_client(sock,inFromUser);
        System.out.println("Client Window");
        
        
        new Thread(new Sender(sock, inFromUser)).start();
        new Thread(new Receiver(sock)).start();
    }   
    
}
