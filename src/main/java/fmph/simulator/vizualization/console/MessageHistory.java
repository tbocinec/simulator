package fmph.simulator.vizualization.console;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class MessageHistory {

    Queue<Message> msg;

    static SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");

    public  MessageHistory(){
        msg =  new LinkedList();
    }

    public void add(Message message) {
        msg.add(message);
    }

    @Override
    public String toString() {
        return msg.stream().map(e ->  formater.format(e.timestamp) + " " + e.type + " " + e.msg).collect(Collectors.joining("\n"));
    }
}
