package fmph.simulator.Running;

import fmph.simulator.Running.Time.TimeManagement;
import fmph.simulator.Running.Time.TimeManagementSimple;
import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.recognization.RecognitionHistory;

public class OneRun {
    TimeManagement timeManagement;
    RecognitionHistory recognitionHistory;
    ChangeHistory changeHistory;
    RunState runState = RunState.readyToRun;

    private double previousTime = 0;

    public OneRun(){
        timeManagement = new TimeManagementSimple();
    }


    boolean one = false;
    public void nextTick() {
        if(one){
            //only debug
            System.out.println("pohzb");
            ContextBuilder.getContext().getCarModel().movie(1500000,0);
            one = false;
        }
        if(runState == RunState.run) {
            double actualTime = timeManagement.getRunTime();
            ContextBuilder.getContext().getCarModel().movie(actualTime,previousTime);
            previousTime = actualTime;
        }
    }

    public void setRunState(RunState runState) {
        if(runState == RunState.stop) {
            timeManagement.startPause();
        }
        if(runState == RunState.run){
            timeManagement.run();
        }
        this.runState = runState;
    }

    public double getRunTimeSecond(){
        return timeManagement.getRunTime()/1000;
    }

    public RunState getRunState() {
        return runState;
    }

    public void setTime(double time) {
        this.timeManagement.setTime(time);
    }
}
