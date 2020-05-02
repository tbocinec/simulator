package fmph.simulator.Running;

import fmph.simulator.Running.Time.TimeManagment;
import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.recognization.RecognitionHistory;

public class OneRun {


    TimeManagment timeManagment;
    RecognitionHistory recognitionHistory;
    ChangeHistory changeHistory;
    RunState runState = RunState.readyToRun;

    private double previousTime = 0;

    public OneRun(){
        timeManagment = new TimeManagment();
    }


    public void nextTick() {
        if(runState == RunState.run) {
            double actualTime = timeManagment.getRunTimeWithoutPause();
            ContextBuilder.getContext().getCarModel().movie(actualTime,previousTime);
            previousTime = actualTime;
        }
    }

    public void setRunState(RunState runState) {
        if(runState == RunState.stop) {
            timeManagment.startPause();
        }
        if(runState == RunState.run){
            timeManagment.run();
        }
        this.runState = runState;
    }

    public double getRunTimeSecond(){
        return timeManagment.getRunTimeWithoutPause()/1000;
    }

    public RunState getRunState() {
        return runState;
    }

}
