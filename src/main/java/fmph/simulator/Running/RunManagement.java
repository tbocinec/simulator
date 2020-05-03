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
        if(nearsTag == null ){
            context.getCarModel().initStartValue();
            context.getCarModel().movie(time*1000,0);
        }
        else{
            System.out.println(  (nearsTag.getCarStateBefore() == context.getCarModel().getCarState())+"a dopice") ;


            context.getCarModel().setCarState(nearsTag.getCarStateBefore());
            //double d = time  -  nearsTag.getTimeRecognization()  ;
            //.out.println("set to tag: "+nearsTag.getLaserTag().getType() + " and movie " + d + " time = "+time + " lastTagTime"+nearsTag.getTimeRecognization());
            context.getCarModel().movie(time*1000,nearsTag.getTimeRecognization()*1000);
        }
        this.actualRun.setTime(time);


    }
}
