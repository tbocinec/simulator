package fmph.simulator.Running;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Context;
import fmph.simulator.recognization.HistoryElement;
import fmph.simulator.vizualization.console.Message;
import fmph.simulator.vizualization.console.MessageType;
import fmph.simulator.vizualization.popup.Warning;

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
        if (actualRun.getRunState() == RunState.readyToRun){
            Warning.newWarning("A simulation that has not been started cannot be reset","illegal operation","");
            return;
        }
        startNewRun();
        context.getCarModel().initStartValue();
        context.getTimeController().setMaxTime(0,true);
    }

    public void finish() {
        if (actualRun.getRunState() == RunState.readyToRun){
            Warning.newWarning("Finish stat is allowed only after first run","illegal operation","");
        }
        actualRun.setRunState(RunState.finish);
        if(runningHistory.getLast() != actualRun){
            runningHistory.addRun(actualRun);
        }

    }
    public void run() {
        if(actualRun.getRunState() == RunState.finish){
            reset();
        }
        if(actualRun.getRunState() == RunState.stop){
            context.getRunManagement().getActualRun().getRecognitionHistory().removeNews(actualRun.getRunTimeSecond());
            context.getTimeController().setMaxTime(actualRun.getRunTimeSecond(),true);
        }
        actualRun.setRunState(RunState.run);
    }

    public void pause() {
        if (actualRun.getRunState() == RunState.stop){
            Warning.newWarning("The simulation is currently stopped","illegal operation","");
            return;
        }
        if (actualRun.getRunState() == RunState.readyToRun){
            Warning.newWarning("A simulation that has not been started cannot be stopped","illegal operation","");
            return;
        }
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
            actualRun.setCarState(nearsTag.getCarStateBefore());
            context.getCarModel().movie(time*1000,nearsTag.getTimeRecognization()*1000);
        }
        this.actualRun.setTime(time*1000);


    }


    public void getHistorytRun(Double elementCreatetTime) {
        OneRun historyRun = runningHistory.findElement(elementCreatetTime);
        this.actualRun = historyRun;
        new Message("Open old historyRun", MessageType.INFO);
    }
}
