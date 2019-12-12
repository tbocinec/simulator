package fmph.simulator.vizualization.console;


import app.Context;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class Message {

    String msg;
    MessageType type;
    Timestamp timestamp;

    static SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");




    public Message(String msg, MessageType type, Timestamp timestamp) {
        this.msg = msg;
        this.type = type;
        this.timestamp = timestamp;
        MessageStack.AddMsg(this);
        Context.getContext().getConsolePanel().addMsg(this);
    }

    public Message(String msg, MessageType type) {
        this(msg,type,new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public String toString() {
        return  formater.format(timestamp) + " " + type + " " + msg;
    }
}
