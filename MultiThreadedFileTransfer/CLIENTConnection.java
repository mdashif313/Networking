/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashif.ftrans;


import java.net.*;
import java.io.*;
import java.util.logging.*;
/**
 *
 * @author Ashif
 */
public class CLIENTConnection implements Runnable {

    private Socket clientSocket;
    private BufferedReader in = null;
    
    //Please adjust your own location here according to your pc
    public static final String SourceDirectory="d:/temp/server/";
    
    public CLIENTConnection(Socket client) {
        this.clientSocket = client;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
            String clientSelection;
            while ((clientSelection = in.readLine()) != null) {
                switch (clientSelection) {
                    case "1":
                        receiveFile();
                        break;
                    case "2":
                        String outGoingFileName;
                        while ((outGoingFileName = in.readLine()) != null) {
                            sendFile(outGoingFileName);
                        }

                        break;
                    default:
                        System.out.println("Incorrect command received.");
                        break;
                }
                in.close();
                break;
            }

        } catch (IOException ex) {
            Logger.getLogger(CLIENTConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveFile() {
        try {
            int bytesRead;

            DataInputStream clientData = new DataInputStream(clientSocket.getInputStream());

            String fileName = SourceDirectory+clientData.readUTF();
            
            OutputStream output = new FileOutputStream((fileName));
            long size = clientData.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }

            output.close();
            clientData.close();

            System.out.println("File "+fileName+" received from client.");
        } catch (IOException ex) {
            System.err.println("Client error. Connection closed.");
        }
    }

    public void sendFile(String fileName) {
        try {
            
            String FileToSend=SourceDirectory+fileName;
            
            //handle file read            
            File myFile = new File(FileToSend);
            byte[] mybytearray = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            //bis.read(mybytearray, 0, mybytearray.length);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);

            //handle file send over socket
            OutputStream os = clientSocket.getOutputStream();

            //Sending file name and file size to the server
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(myFile.getName());
            dos.writeLong(mybytearray.length);
            dos.write(mybytearray, 0, mybytearray.length);
            dos.flush();
            System.out.println("File "+fileName+" sent to client.");
        } catch (Exception e) {
            System.err.println("File does not exist!");
        } 
    }
}
