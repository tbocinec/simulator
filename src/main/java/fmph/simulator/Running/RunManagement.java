package fmph.simulator.Running;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Context;
import fmph.simulator.car.recognization.HistoryElement;
import fmph.simulator.visualization.Tickable;
import fmph.simulator.visualization.console.Message;
import fmph.simulator.visualization.console.MessageType;
import fmph.simulator.visualization.popup.Warning;

public class RunManagement implements Tickable {

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
        context.getCarManagement().initStartValue();
        context.getTimeController().setMaxTime(0,true);
        context.getRecognitionHistoryController().removeAll();
    }

    public void finish() {
        if (actualRun.getRunState() == RunState.readyToRun){
            Warning.newWarning("Finish stat is allowed only after first run","illegal operation","");
        }
        actualRun.setRunState(RunState.finish);
        if(runningHistory.getLast() != actualRun){
            runningHistory.addRun(actualRun);
        }
        else {
           throw new RuntimeException("duplicit save Run");
        }
        context.getCarManagement().getRecognizationSender().killAllFutureSend();
        new Message("Successfully save previous run",MessageType.INFO);

    }
    public synchronized void run() {
        if(actualRun.getRunState() == RunState.finish){
            reset();
        }
        if(actualRun.getRunState() == RunState.stop){
            context.getRunManagement().getActualRun().getRecognitionHistory().removeNews(actualRun.getRunTimeSecond());
            context.getTimeController().setMaxTime(actualRun.getRunTimeSecond(),true);
        }
        actualRun.setRunState(RunState.run);
        context.getCarManagement().checkIdentifierAll();
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
        context.getCarManagement().getRecognizationSender().killAllFutureSend();
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
           // context.getMainController().setRightStatusText("Actual time of ride "+actualRun.getRunTimeSecond() +"s"); this pain is use for next recognization
        }
    }

    public void changeToTime(double time) {
        actualRun.setRunState(RunState.stop);
        HistoryElement nearsTag = context.getRunManagement().getActualRun().getRecognitionHistory().getNearst(time);
        if(nearsTag == null ){
            context.getCarManagement().initStartValue();
            context.getCarManagement().movie(time*1000,0);
        }
        else{
            actualRun.setCarStateClone(nearsTag.getCarStateBefore());
            context.getCarManagement().movie(time*1000,nearsTag.getTimeRecognization()*1000);
        }
        this.actualRun.setTime(time*1000);


    }


    public void getHistorytRun(Double elementCreatetTime) {
        OneRun historyRun = runningHistory.findElement(elementCreatetTime);
        this.actualRun = historyRun;
        context.getTimeController().setMaxTime(historyRun.getRunTimeSecond(),true);
        new Message("Open old historyRun", MessageType.INFO);
        this.actualRun.setRunState(RunState.stop);
        context.getRecognitionHistoryController().removeAll();
        this.actualRun.getRecognitionHistory().showAll();
    }

    public RunningHistory getRunningHistory() {
        return runningHistory;
    }
    public void setRunningHistory(RunningHistory runningHistory) {
        context.getRecognitionHistoryController().removeAll();
        context.getRunHistoryController().removeAll();
        this.runningHistory = runningHistory;
        runningHistory.showAlltoGui();

    }
    public OneRun getActualRun() {
        return actualRun;
    }

    public void setActualRun(OneRun actualRun) {
        this.actualRun = actualRun;
    }

    @Override
    public void tickSecond() {
        nextSecondTick();
    }

    @Override
    public void tick() {
        nextTick();
    }
}
