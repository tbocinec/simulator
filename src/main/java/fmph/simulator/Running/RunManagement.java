package fmph.simulator.Running;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Context;
import fmph.simulator.recognization.HistoryElement;

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
            context.getTimeController().setMaxTime(actualRun.getRunTimeSecond(), true);
            context.getMainController().setRightStatusText("Actual time of ride "+actualRun.getRunTimeSecond() +"s");
        }
    }

    public OneRun getActualRun() {
        return actualRun;
    }

    public void setActualRun(OneRun actualRun) {
        this.actualRun = actualRun;
    }


    public void changeToTime(double time) {
        actualRun.setRunState(RunState.stop);
        HistoryElement nearsTag = context.getRecognizationHistory().getNearst(time);
        if(nearsTag == null){
            context.getCarModel().initStartValue();
            context.getCarModel().movie(time,0);
        }
        else{
            context.getCarModel().setCarState(nearsTag.getCarStateBefore());
            context.getCarModel().movie(time,nearsTag.getTimeRecognization());
        }


    }
}
