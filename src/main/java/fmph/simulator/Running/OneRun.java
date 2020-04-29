package fmph.simulator.Running;

import fmph.simulator.Running.Time.TimeManagment;
import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.recognization.RecognitionHistory;

public class OneRun {


    TimeManagment timeManagment;
    RecognitionHistory recognitionHistory;
    ChangeHistory changeHistory;
    RunState runState = RunState.readyToRun;

    public OneRun(){
        timeManagment = new TimeManagment();
    }


    public void nextTick() {
        if(runState == RunState.run) {
            ContextBuilder.getContext().getCarModel().movie(timeManagment.getRunTimeWithoutPause());
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

    public double getRunTime(){
        return timeManagment.getRunTimeWithoutPause();
    }

    public RunState getRunState() {
        return runState;
    }

}
