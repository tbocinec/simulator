package fmph.simulator.Running.Time;

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
    synchronized public double getRunTime(){
        if(lastRealTime != 0){
            double snipTime = System.currentTimeMillis();
            actualTime += snipTime - lastRealTime;
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
}
