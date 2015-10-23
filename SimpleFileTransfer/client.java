/*
========================================================================
 *Copyright © 2015-2020  by  Md.Ashif Al Nowajesh .All rights reserved.
 *This  file  is  protected by copyright . Any part of  this  file  is
 *strictly prohibited from reproduction , distribution or transmission
 *in any form or by any means , including  photocopying , recording or
 *other electronic or mechanical ways without permission of the author.
========================================================================
*/


package com.ashif.net;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Ashif
 */
public class client {

  public final static int SOCKET_PORT = 13267;      // you may change this
  public final static String SERVER = "127.0.0.1";  // localhost
  public static String FileDownloadDirectory ;  // you may change this
  public final static int FILE_SIZE = 6022386;
  
  

  public static void main (String [] args ) throws IOException {
    
    Scanner inFromUser=new Scanner(System.in);
    System.out.println("Give your Source folder path (example format: d:/temp/client_1/)");
    FileDownloadDirectory=inFromUser.nextLine();
      
      
    int bytesRead;
    int current = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    Socket sock = null;
    
      while (true) {          
          try {
      sock = new Socket(SERVER, SOCKET_PORT);
      System.out.println("Connecting...");
      
      DataInputStream in = new DataInputStream(sock.getInputStream());
      String str=in.readUTF();
      
      String FileToRecive=FileDownloadDirectory+str;

      // receive file
      byte [] mybytearray  = new byte [FILE_SIZE];
      InputStream is = sock.getInputStream();
      fos = new FileOutputStream(FileToRecive);
      bos = new BufferedOutputStream(fos);
      bytesRead = is.read(mybytearray,0,mybytearray.length);
      current = bytesRead;

      do {
         bytesRead =
            is.read(mybytearray, current, (mybytearray.length-current));
         if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead > -1);

      bos.write(mybytearray, 0 , current);
      bos.flush();
      System.out.println("File " + FileToRecive
          + " downloaded (" + current + " bytes read)");    
      }
    finally {
      if (fos != null) fos.close();
      if (bos != null) bos.close();
      if (sock != null) sock.close();
    }
   }       
  }
  

}
