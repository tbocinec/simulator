package com;

//A Java program for a Server 

import fmph.simulator.vizualization.animate.idealCar.State;
import fmph.simulator.vizualization.console.Message;
import fmph.simulator.vizualization.console.MessageType;
import sun.java2d.loops.GraphicsPrimitive;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Server implements Runnable {
    //state of simulator
    State state = State.getState();
    Thread thread;
    Integer port;
    //initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;


    // constructor with port
    public Server(int port) {
        this.port = port;
        // starts server and waits for a connection
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            new Message("Error with run Server,try another port", MessageType.ERROR);
            System.out.println("Error with run Server,try another port");
        }
        System.out.println("Application server start at port : "+port);
        new Message("Application server start at port : "+port, MessageType.INFO);
    }

    public void restart(){
        stop();
        start();
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }


    public void run() {
        acceptClinet();
        readMeesageLoop();
        stop();

    }

    public void stop() {
        // close connection
        try {
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt(); // todo lok

        }
        new Message("Server: Close connection", MessageType.INFO);
    }

    private void acceptClinet() {
        new Message("Server: Waiting for connection car", MessageType.INFO);
        try {
            socket = server.accept();
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Message("Server: Client (car) was connecting", MessageType.INFO);
    }

    public void sendMsg(String msg){
        byte[] utf8Bytes = null;
        try {
            utf8Bytes = msg.getBytes("UTF8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Send message problem",e);
        }
        byte[] bytesLength = intToBytes(utf8Bytes.length);
        try {
            if(out == null){
                System.out.println("chyba");
                return;}

            out.write(bytesLength);
            out.write(utf8Bytes);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Send message problem",e);
        }

    }


    private void readMeesageLoop() {
        try {
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace(); //todo
        }
        String line = "";

        while (!line.equals("Over")) {
            try {
                byte[] len =  new byte[14];
                in.read(len,0,4);
                byte[] msgbyte = new byte[byteToInt(len)];
                in.read(msgbyte,0,byteToInt(len));
                new MessageParser(new String(msgbyte));

            } catch (IOException e) {
                e.printStackTrace();
                restart(); //todo
                return;
            }


        }
    }



    private static int byteToInt(byte [] b){
         return
                (b[0]<< 0)&0x000000ff|
                (b[1]<< 8)&0x0000ff00|
                 (b[2]<<16)&0x00ff0000|
                        (b[3]<<24)&0xff000000;
    }
    private static byte[] intToBytes(final int data) {
        return new byte[] {
                (byte)((data >> 0) & 0xff),
                (byte)((data >> 8) & 0xff),
                (byte)((data >> 16) & 0xff),
                (byte)((data >> 24) & 0xff),
        };
    }

    private void printarray(byte[] array){
        for(int i=0; i< array.length ; i++) {
            System.out.print(array[i] +" ");
        }
    }
}