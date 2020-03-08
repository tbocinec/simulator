package com;

import java.net.*;
import java.io.*;

public class ClientMock {

	// initialize socket and input output streams
	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;

	// constructor to put ip address and port
	public ClientMock(String address, int port) {

		InComeMessage m1 = new InComeMessage(100, 200);

		// establish a connection
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");

			// sends output to the socket
			out = new DataOutputStream(socket.getOutputStream());
			//out.writeUTF(m2.serializable());
			
			
			InputStreamReader isr = new InputStreamReader(System.in);
	        BufferedReader br = new BufferedReader(isr);
	        String line;
	        System.out.println("Enter new wheelAngle carspeed");
	        while (!(line = br.readLine()).trim().equals("")){
	        	String[] readNumbers = line.split(" ");
	        	if(readNumbers.length == 2) {

					InComeMessage msg;
					try {
						msg = new InComeMessage(Double.parseDouble(readNumbers[0]), Double.parseDouble(readNumbers[1]));
					}catch (NumberFormatException e){
						msg = new InComeMessage("Wrong input from simulator");
						System.out.println("Please add input in correct format");
					}
					out.writeUTF(msg.serializable());
					System.out.println("Enter new wheelAngle carspeed");
	        	}
	        }
	        
			
		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			i.printStackTrace();
		}

		/*
		 * close the connection try { //out.close(); //socket.close(); }
		 * catch(IOException i) { i.printStackTrace(); }
		 */
	}



	public static void main(String args[]) {
		ClientMock client = new ClientMock("127.0.0.1", 8085);
	}
}