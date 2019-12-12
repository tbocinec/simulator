package fmph.simulator.vizualization.console;

import java.util.ArrayList;

public class MessageStack { //add to context
    private static ArrayList<Message> stactkMessages = new ArrayList<Message>();

    public static void AddMsg(Message msg){
        stactkMessages.add(msg);
    }

}
