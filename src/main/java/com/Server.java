package com;

//A Java program for a Server 

import fmph.simulator.vizualization.animate.idealCar.State;
import sun.java2d.loops.GraphicsPrimitive;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    //state of simulator
    State state = State.getState();
    Thread thread;
    //initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    // constructor with port
    public Server(int port) {
        // starts server and waits for a connection
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Eror with run Server,try another port");
        }
        System.out.println("Server started");


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
            socket.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt(); // todo lok

        }
        System.out.println("Closing connection");
    }

    private void acceptClinet() {
        System.out.println("Waiting for a client ...");
        try {
            socket = server.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client accepted");
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
                line = in.readUTF();
                InComeMessege inm =  InComeMessege.deserializableNewMsg(line);
                inm.save();
            } catch (IOException e) {
                e.printStackTrace(); //todo
            }


        }
    }
}