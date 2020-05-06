package fmph.simulator.recognization;

import fmph.simulator.map.LaserTag;
import fmph.simulator.map.Segment;

public class RecognizationElement {
    private LaserTag laserTag;
    private Segment segment;

    public RecognizationElement(LaserTag laserTag, Segment segment) {
        this.laserTag = laserTag;
        this.segment = segment;
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
}
