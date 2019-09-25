package com;


import java.net.*; 
import java.io.*; 
  
public class ClientMock 
{ 
    // initialize socket and input output streams 
    private Socket socket            = null; 
    private DataInputStream  input   = null; 
    private DataOutputStream out     = null; 
  
    // constructor to put ip address and port 
    public ClientMock(String address, int port) 
    { 
    	
    	   
        InComeMessege m1 = new InComeMessege(100,200);
        InComeMessege m2 = new InComeMessege(110,210);
      
        // establish a connection 
        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connected"); 
           
            // sends output to the socket 
            out    = new DataOutputStream(socket.getOutputStream()); 
            out.writeUTF(m1.serializable()); 
            //out.writeUTF(m2.serializable()); 
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
        catch(IOException i) 
        { 
            i.printStackTrace();
        } 

  
        
        // close the connection 
        try
        { 
            out.close(); 
            socket.close(); 
        } 
        catch(IOException i) 
        { 
        	 i.printStackTrace();
        } 
    } 
  
    public static void main(String args[]) 
    { 
        ClientMock client = new ClientMock("127.0.0.1", 5006); 
    } 
} 