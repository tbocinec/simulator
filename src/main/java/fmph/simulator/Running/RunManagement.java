package fmph.simulator.Running;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Context;
import fmph.simulator.recognization.HistoryElement;

public class RunManagement {

    OneRun actualRun;
    Context context;
    RunningHistory runningHistory;

    public RunManagement(){
        startNewRun();
        runningHistory = new RunningHistory();
        context = ContextBuilder.getContext();
    }
    public void reset() {
        startNewRun();
        context.getCarModel().initStartValue();
    }

    public void finish() {
        if(runningHistory.getLast() != actualRun){
            runningHistory.addRun(actualRun);
        }

    }
    public void run() {
        if(actualRun.getRunState() == RunState.finish){
            reset();
        }
        if(actualRun.getRunState() == RunState.stop){
            System.out.println("Run after stop " + actualRun.getRunTimeSecond());
            context.getRunManagement().getActualRun().getRecognitionHistory().removeNews(actualRun.getRunTimeSecond());
            context.getTimeController().setMaxTime(actualRun.getRunTimeSecond(),true);
        }
        actualRun.setRunState(RunState.run);
    }

    public void pause() {
        actualRun.setRunState(RunState.stop);
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
        HistoryElement nearsTag = context.getRunManagement().getActualRun().getRecognitionHistory().getNearst(time);
        if(nearsTag == null ){
            context.getCarModel().initStartValue();
            context.getCarModel().movie(time*1000,0);
        }
        else{
            context.getCarModel().setCarState(nearsTag.getCarStateBefore());
            context.getCarModel().movie(time*1000,nearsTag.getTimeRecognization()*1000);
        }
        this.actualRun.setTime(time*1000);


    }


}
