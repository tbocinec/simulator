package fmph.simulator.com;

import fmph.simulator.app.context.Context;
import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.models.CarState;
import org.apache.commons.configuration2.PropertiesConfiguration;

public class MessageParser {

    String msg;
    PropertiesConfiguration config = ContextBuilder.getContext().config;

    public MessageParser(String s) {
        msg = s;
        parseMsg();
    }

    public void parseMsg(){
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();

        if(msg.trim().startsWith("setdir")){
            String dir = msg.split("setdir")[1];
            ContextBuilder.getContext().getCarModel().setWhealAngle(Double.parseDouble(dir));
            ContextBuilder.getContext().getCarModel().applicateLastSpeed();
            return;
        };
        if(msg.trim().startsWith("stop")){
            carState.setGearSpeed(0);
            ContextBuilder.getContext().getConsoleController().addMsg("Car stop");
            ContextBuilder.getContext().getRunManagement().pause();
            return;
        };
        if(msg.trim().startsWith("setpower")){
            String speed = msg.split("setpower")[1].trim();
            carState.setGearSpeed(Integer.parseInt(speed));
            if(config.getBoolean("app.startAfterSetPower")){
                ContextBuilder.getContext().getRunManagement().run();
            }
            return;
        };
        System.out.println("unrecognized  msg "+msg);

    }
}
