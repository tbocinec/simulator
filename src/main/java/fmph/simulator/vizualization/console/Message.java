package fmph.simulator.vizualization.console;


import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.exceptions.ContextException;
import fmph.simulator.vizualization.view.ConsoleController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Queue;


public class Message {

    static private Queue<Message> messageQueue = new LinkedList();
    static public MessageHistory messageHistory =  new MessageHistory();

    String msg;
    public MessageType type;
    Timestamp timestamp;

    static SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");


    public Message(String msg, MessageType type, Timestamp timestamp) {
        this.msg = msg;
        this.type = type;
        this.timestamp = timestamp;
        messageQueue.add(this);
        messageHistory.add(this);
        showAllAvaibleMsg();
    }

    public Message(String msg, MessageType type) {
        this(msg,type,new Timestamp(System.currentTimeMillis()));
    }

    public static void showAllAvaibleMsg(){
        try {
            ConsoleController consoleController = ContextBuilder.getContext().getConsoleController();
            Message messageToShow = getMessageQueue().poll();
            while (messageToShow != null){
                consoleController.addMsg(messageToShow);
                messageToShow = getMessageQueue().poll();
            }
        } catch (ContextException e) {
            //todo log
        }
    }


    public  synchronized static Queue<Message> getMessageQueue() {
        return messageQueue;
    }


    @Override
    public String toString() {
        return  formater.format(timestamp) + " " + type + " " + msg;
    }
}
