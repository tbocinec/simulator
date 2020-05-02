package fmph.simulator.Running.Time;

public class TimeManagement2 implements TimeManagement {

    private double actualTime;
    private double lastRealTime;


    public TimeManagement2(){
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
