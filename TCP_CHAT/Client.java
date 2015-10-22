/*
========================================================================
 *Copyright © 2015-2020  by  Md.Ashif Al Nowajesh .All rights reserved.
 *This  file  is  protected by copyright . Any part of  this  file  is
 *strictly prohibited from reproduction , distribution or transmission
 *in any form or by any means , including  photocopying , recording or
 *other electronic or mechanical ways without permission of the author.
========================================================================
*/

package com.ashif.danbox;


import java.util.*;
import java.io.*;
import java.net.*;

        
/**
 *
 * @author Ashif
 */
public class Client {
    
    private Socket client_Socket;
    private DataOutputStream client_Output;
    private DataInputStream client_Input;
    private String host_Name;
    private int port_Num;
    
    public Client(String host_name, int port_num){
        
        this.host_Name=host_name;
        this.port_Num=port_num;
                
    }
    
    private void start_Client(){
        
        try {
            //try to connect to server
            client_Socket= new Socket(host_Name, port_Num);
            
            client_Input  = new DataInputStream(client_Socket.getInputStream());
	    client_Output = new DataOutputStream(client_Socket.getOutputStream());
            
            new ListenFromServer().start();
            new SendToServer().start();
            
        } catch (Exception e) {
            
            System.out.println("Exception inside start_Client() : "+e );
            disconnect_Client();
        }
    }
    
    
    /*
     * the method "disconnect_Client()" is called 
     * to disconnect any Client object from server
     */
    private void disconnect_Client(){
        try { 
            if(client_Input != null) client_Input.close();

            if(client_Output != null) client_Output.close();

            if(client_Socket != null) client_Socket.close();
            
            System.out.println("Client has been disconnected from server : "+host_Name+":"+port_Num);
        }
        catch(Exception e) {
            System.out.println("Exception Inside disconnect_Client() : "+e);
        } 
    }
    
    public static void main(String[] args) {
        
        Client client= new Client("localhost", 2300);
        
        client.start_Client();
        
        //client.disconnect_Client();
        
    }
    
    class ListenFromServer extends Thread {
        
        public void run(){
            
            while (true) {                
                try {
                    System.out.println("Server: "+client_Input.readUTF());
                } 
                catch (Exception e) {
                    System.out.println("Exception inside class ListenFromServer : "+e);
                }
            }
        }
    }
    
    class SendToServer extends Thread {
        
        public void run(){
            
            while (true) {                
                try {
                    Scanner inFromUser= new Scanner(System.in);
                    String str=inFromUser.nextLine();
                    client_Output.writeUTF(str);
                } catch (Exception e) {
                }
            }
        }
    }
    
    
}


