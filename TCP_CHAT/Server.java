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


import java.io.*;
import java.net.*;
import java.util.*;
        

/**
 *
 * @author Ashif
 */


public class Server {
    
    private ServerSocket server_socket;
    private static int unique_id;
    private int server_port;
    private ArrayList<ClientThread> client_List;
    
    /*
     * server constructor 
     */
    public Server(int port_num){
        
        server_port=port_num;
        client_List= new ArrayList<ClientThread>();
        
    }
    
    private void start_Server(){
        
        try {
            server_socket= new ServerSocket(server_port);
            
            while (true) {                
                try {
                    Socket socket= server_socket.accept();
                    
                    ClientThread t= new ClientThread(socket);
                    client_List.add(t);
                    t.start();
                    
                } catch (Exception e) {
                    System.out.println("Exception inside while loop start_Server() :"+e);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception inside start_Server() :"+e);
        }
    }
    
    private synchronized void broadcast(String msg, int id){       
        int i;
        
        for( i=client_List.size()-1 ;i>=0 ;i--){
            ClientThread cl=client_List.get(i);
            
            if(cl.id==id)
                continue;
            
            if(!cl.SendToClient(msg))
                remove_client(i);
        }
    }
    
    private void remove_client(int id){
        int i;
        
        for(i=client_List.size()-1; i>=0;i--){
            ClientThread ct=client_List.get(i);
            
            if(ct.id==id){
                client_List.remove(i);
                return;
            }
        }
        
    }
    
    private void disconnect_Server(){
        
        try {
            server_socket.close();
            
            
        }
        catch (Exception e) {
            System.out.println("Exception inside disconnect_Server() : "+e);
        }
    }
            
    public static void main(String[] args) {
        
        Server server=new Server(2300);
        server.start_Server();
        
    }
    
    /*
     * ClientThread Class to mantain Multiple Client
     */
    
    
    class ClientThread extends Thread{
        
        Socket socket;
        DataInputStream Input;
        DataOutputStream Output;
        int id;

        public ClientThread(Socket socket) {
            this.socket=socket;
            id=++unique_id;
            
            try {
                Input= new DataInputStream(socket.getInputStream());
                Output= new DataOutputStream(socket.getOutputStream());
                
            } 
            catch (Exception e) {
                System.out.println("Exception inside ClientThread(Socket socket) :"+e);
            }
            
        }
        
        public void run(){
            
            while (true) {                
                try {
                    String str= Input.readUTF();
                    
                    broadcast(str,id);
                    
                } catch (Exception e) {
                }
            }
        }
        
        boolean SendToClient(String str){
            
            try {
                Output.writeUTF(str);
                return true;
            } 
            catch (Exception e) {
                System.out.println("Exception inside SendToClient() :"+e);
                return false;
            }
        }
                       
    }
    
    
}
