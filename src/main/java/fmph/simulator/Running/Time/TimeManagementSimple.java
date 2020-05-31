package fmph.simulator.Running.Time;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fmph.simulator.app.context.ContextBuilder;

import java.io.Serializable;

public class TimeManagementSimple implements TimeManagement {

    private double actualTime;
    private double lastRealTime;


    public TimeManagementSimple(){
        actualTime = 0;
    }
    @Override
    public void run(){
        lastRealTime = System.currentTimeMillis();
    }


    @Override
    @JsonIgnore
    synchronized public double getRunTime(){
        if(lastRealTime != 0){
            double snipTime = System.currentTimeMillis();
            actualTime += (snipTime - lastRealTime)* ContextBuilder.getContext().config.getDouble("app.carSpeedRate");
            this.lastRealTime = snipTime;
        }
        return  actualTime;
    }

    public void startPause() {
        lastRealTime = 0;
    }

    @Override
    public void setTime(double time){
        actualTime = time;
        lastRealTime = 0;
    }

    //Geters and Seters for serializable
    public double getActualTime() {
        return actualTime;
    }

    public void setActualTime(double actualTime) {
        this.actualTime = actualTime;
    }

    public double getLastRealTime() {
        return lastRealTime;
    }

    public void setLastRealTime(double lastRealTime) {
        this.lastRealTime = lastRealTime;
    }
}
