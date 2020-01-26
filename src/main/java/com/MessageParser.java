package com;

import app.Context;

public class MessageParser {

    String msg;

    public MessageParser(String s) {
        msg = s;
        parseMsg();
    }

    public void parseMsg(){
        if(msg.trim().startsWith("setdir")){
            String dir = msg.split("setdir")[1];
            Context.getContext().getCarModel().setWheelAngle(Double.parseDouble(dir));
            Context.getContext().getConsolePanel().addMsg("Car change dir to " + dir);
            return;
        };
        if(msg.trim().startsWith("stop")){
            Context.getContext().getCarModel().setCarSpeed(0);
            Context.getContext().getConsolePanel().addMsg("Car stop");
            return;
        };
        if(msg.trim().startsWith("setpower")){
            String speed = msg.split("setpower")[1];
            Context.getContext().getCarModel().setCarSpeed(Double.parseDouble(speed) * 0.2);
            Context.getContext().getConsolePanel().addMsg("Set speed " + speed);
            return;
        };
        System.out.println("unrecognized  msg "+msg);

    }
}
