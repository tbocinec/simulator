package fmph.simulator.Running;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fmph.simulator.Running.Time.TimeManagement;
import fmph.simulator.Running.Time.TimeManagementSimple;
import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.models.CarState;
import fmph.simulator.recognization.RecognitionHistory;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public class  OneRun implements Serializable {
    ///problem with use TimeManagment interface because problem of seriali interface field
    TimeManagementSimple timeManagement;
    RecognitionHistory recognitionHistory;
    RunState runState = RunState.readyToRun;
    private CarState carState;
    double createTime;

    private double previousTime = 0;

    public OneRun(){
        timeManagement = new TimeManagementSimple();
        recognitionHistory = new RecognitionHistory();
        createTime = System.currentTimeMillis();
        carState = new CarState();
    }


    public void nextTick() {
        if(runState == RunState.run) {
            double actualTime = timeManagement.getRunTime();
            ContextBuilder.getContext().getCarModel().movie(actualTime,previousTime);
            previousTime = actualTime;
        }
    }

    public void setRunState(RunState runState) {
        if(runState != RunState.run) {
            timeManagement.startPause();
        }
        if(runState == RunState.run){
            timeManagement.run();
        }
        this.runState = runState;
    }

    @JsonIgnore
    public double getRunTimeSecond(){
        return timeManagement.getRunTime()/1000;
    }

    public RunState getRunState() {
        return runState;
    }

    public void setTime(double time) {
        this.timeManagement.setTime(time);
    }

    public RecognitionHistory getRecognitionHistory() {
        return recognitionHistory;
    }

    public void setRecognitionHistory(RecognitionHistory recognitionHistory) {
        this.recognitionHistory = recognitionHistory;
    }
    public double getCreateTime() {
        return createTime;
    }

    public CarState getCarState() {
        return carState;
    }

    public void setCarStateClone(CarState carState) {
        this.carState = SerializationUtils.clone(carState);
    }

    public void setCarState(CarState carState) {
        this.carState = SerializationUtils.clone(carState);
    }

    public TimeManagementSimple getTimeManagement() {
        return timeManagement;
    }

    public void setTimeManagement(TimeManagementSimple timeManagement) {
        this.timeManagement = timeManagement;
    }

    public void setCreateTime(double createTime) {
        this.createTime = createTime;
    }

    public double getPreviousTime() {
        return previousTime;
    }

    public void setPreviousTime(double previousTime) {
        this.previousTime = previousTime;
    }
}
