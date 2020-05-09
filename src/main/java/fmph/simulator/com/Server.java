package fmph.simulator.com;

//A Java program for a Server 

import fmph.simulator.app.context.Context;
import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.vizualization.console.Message;
import fmph.simulator.vizualization.console.MessageType;

import java.io.*;
import java.net.*;
import java.util.Enumeration;

import static java.lang.Thread.sleep;

public class Server implements Runnable {

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
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
        try {
            ContextBuilder.getContext().getConnectionInfoController().setStatus("Connected");
            ContextBuilder.getContext().getConnectionInfoController().setAdditionalInfo("Client ip" + socket.getRemoteSocketAddress());
        }
        catch (NullPointerException e){
            System.out.println("App wos connected before app context load.");
        }


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
                new Message("Unconnect client, messege not send",MessageType.WARNING);
                return;}

            out.write(bytesLength);
            out.write(utf8Bytes);
            out.flush();
            ContextBuilder.getContext().getConnectionInfoController().setlastComunication(true);
        } catch (IOException e) {
            new Message("Broken connection to  client, messege not send",MessageType.ERROR);
            restart();
            //throw new RuntimeException("Send message problem",e);
        }

    }

    public String getMyIp(){
        String ip = null;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();

                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return ip;
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
                ContextBuilder.getContext().getConnectionInfoController().setlastComunication(false);
            } catch (IOException e) {
                new Message("Connection lost,server restart",MessageType.ERROR);
                ContextBuilder.getContext().getConnectionInfoController().setStatus("unconnect");

                ContextBuilder.getContext().getConnectionInfoController().setAdditionalInfo("");
                restart(); //todo
                return;
            }


        }
    }

    //Function compatibilite with C library
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