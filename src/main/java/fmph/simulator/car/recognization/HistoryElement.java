package fmph.simulator.car.recognization;

import fmph.simulator.map.LaserTag;
import fmph.simulator.map.Segment;
import fmph.simulator.car.CarState;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryElement implements Serializable {
    double timeRecognization;
    double timeReciveInfo;
    CarState carStateBefore;
    CarState carStatAfter;
    Segment segment;
    LaserTag laserTag;
    //BigDecimal time;

    SimpleDateFormat formatDate = new SimpleDateFormat("hh:s");

    public HistoryElement(){

    }

    public HistoryElement(double timeRecognization, CarState carStateBefore,
                         Segment segment, LaserTag laserTag) {
        this.timeRecognization = timeRecognization;
        this.carStateBefore = carStateBefore;
        this.segment = segment;
        this.laserTag = laserTag;
    }

    public void addRecognizationInfo(double timeReciveInfo,CarState carStatAfterReceive){
        this.timeReciveInfo = timeReciveInfo;
        this.carStatAfter = carStatAfterReceive;
    }

    @Override
    public String toString() {
        String dateString = formatDate.format(new Date((long)timeRecognization));
        return String.format("Tag [%s] in segment [%s] at time %s",laserTag.getType(),segment.getSegmentId(),dateString);
    }


    public double getTimeRecognization() {
        return timeRecognization;
    }

    public void setTimeRecognization(double timeRecognization) {
        this.timeRecognization = timeRecognization;
    }

    public double getTimeReciveInfo() {
        return timeReciveInfo;
    }

    public void setTimeReciveInfo(double timeReciveInfo) {
        this.timeReciveInfo = timeReciveInfo;
    }

    public CarState getCarStateBefore() {
        return carStateBefore;
    }

    public void setCarStateBefore(CarState carStateBefore) {
        this.carStateBefore = carStateBefore;
    }

    public CarState getCarStatAfter() {
        return carStatAfter;
    }

    public void setCarStatAfter(CarState carStatAfter) {
        this.carStatAfter = carStatAfter;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public LaserTag getLaserTag() {
        return laserTag;
    }

    public void setLaserTag(LaserTag laserTag) {
        this.laserTag = laserTag;
    }
}