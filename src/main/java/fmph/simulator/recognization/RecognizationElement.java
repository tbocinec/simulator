package fmph.simulator.recognization;

import fmph.simulator.map.LaserTag;
import fmph.simulator.map.Segment;

public class RecognizationElement {
    private LaserTag laserTag;
    private Segment segment;
    private double alfa;

    public RecognizationElement(LaserTag laserTag, Segment segment,double alfa) {
        this.laserTag = laserTag;
        this.segment = segment;
        this.alfa = alfa;
    }


    public LaserTag getLaserTag() {
        return laserTag;
    }

    public void setLaserTag(LaserTag laserTag) {
        this.laserTag = laserTag;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }
}
