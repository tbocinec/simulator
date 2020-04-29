package fmph.simulator.com;

import fmph.simulator.app.context.ContextBuilder;

public class MessageParser {

    String msg;

    public MessageParser(String s) {
        msg = s;
        parseMsg();
    }

    public void parseMsg(){
        if(msg.trim().startsWith("setdir")){
            String dir = msg.split("setdir")[1];
            ContextBuilder.getContext().getCarModel().setWhealAngle(Double.parseDouble(dir));
            ContextBuilder.getContext().getCarModel().applicateLastSpeed();
            return;
        };
        if(msg.trim().startsWith("stop")){
            ContextBuilder.getContext().getCarModel().getCarState().setCarSpeed(0);
            ContextBuilder.getContext().getConsoleController().addMsg("Car stop");
            return;
        };
        if(msg.trim().startsWith("setpower")){
            String speed = msg.split("setpower")[1];
            ContextBuilder.getContext().getCarModel().getCarState().setCarSpeed(Double.parseDouble(speed) * 1.3);
            ContextBuilder.getContext().getCarModel().setLastSpeed(Double.parseDouble(speed) * 1.3);
            ContextBuilder.getContext().getConsoleController().addMsg("Set speed " + speed);
            return;
        };
        System.out.println("unrecognized  msg "+msg);

    }
}
