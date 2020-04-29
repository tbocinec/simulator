package fmph.simulator.Running;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Context;

public class RunManagement {

    OneRun actualRun;
    Context context;

    public RunManagement(){
        startNewRun();
        context = ContextBuilder.getContext();
    }
    public void reset() {
        startNewRun();
        context.getCarModel().initStartValue();
    }

    public void finish() {
        actualRun = new OneRun();
    }

    void startNewRun(){
        actualRun = new OneRun();
    }

    public void nextTick(){
        actualRun.nextTick();

    }
    public void nextSecondTick() {
        if(actualRun.getRunState() == RunState.run) {
            context.getTimeController().setMaxTime(actualRun.getRunTime(), true);
            context.getMainController().setRightStatusText("Actual time of ride "+actualRun.getRunTime()/1000 +"s");
        }
    }

    public OneRun getActualRun() {
        return actualRun;
    }

    public void setActualRun(OneRun actualRun) {
        this.actualRun = actualRun;
    }


}