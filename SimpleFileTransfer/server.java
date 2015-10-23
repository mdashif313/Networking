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
public class server {

  public final static int SOCKET_PORT = 13267;  // you may change this
  public static String FileSourceDirectory ;  // you may change this

  public static void main (String [] args ) throws IOException {
          
      Scanner inFromUser=new Scanner(System.in);
      System.out.println("Give your Source folder path (example format: d:/temp/server/)");
      FileSourceDirectory=inFromUser.nextLine();
      
      FileInputStream fis = null;
      BufferedInputStream bis = null;
      OutputStream os = null;
      ServerSocket servsock = null;
      Socket sock = null;
      try {
          servsock = new ServerSocket(SOCKET_PORT);
          while (true) {
              System.out.println("Give your file name with extension : ");
              Scanner in=new Scanner(System.in);

              String str=in.nextLine();
        
              try {
                  sock = servsock.accept();
                  System.out.println("Accepted connection : " + sock);
                  // send file
          
                  String FileToSend=FileSourceDirectory+str;
          
                  DataOutputStream out =new DataOutputStream(sock.getOutputStream());
                  out.writeUTF(str);
          
                  File myFile = new File (FileToSend);
                  byte [] mybytearray  = new byte [(int)myFile.length()];
                  fis = new FileInputStream(myFile);
                  bis = new BufferedInputStream(fis);
                  bis.read(mybytearray,0,mybytearray.length);
                  os = sock.getOutputStream();
                  System.out.println("Sending " + FileToSend + "(" + mybytearray.length + " bytes)");
                  os.write(mybytearray,0,mybytearray.length);
                  os.flush();
                  System.out.println("Done.");
                  
        }
        finally {
          if (bis != null) bis.close();
          if (os != null) os.close();
          if (sock!=null) sock.close();
        }
      }
    }
    finally {
      if (servsock != null) servsock.close();
    }
  }
}
