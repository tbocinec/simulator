package fmph.simulator.recognization;

import fmph.simulator.map.LaserTag;

import java.util.TreeMap;

public class NeartTag {
    TreeMap<Double, RecognizationElement> nextTags = new TreeMap<>();

    public NeartTag() {
        this.nextTags =  new TreeMap<>();
    }

    public void add(double timeSecond,RecognizationElement re){
        LaserTag laserTag = re.getLaserTag();
        if( null != laserTag.getDisabled() && laserTag.getDisabled()){
            return;
        }
        if( null != laserTag.getExternal() && laserTag.getExternal()){
            return;
        }
        nextTags.put(timeSecond,re);
    }

    public TreeMap<Double, RecognizationElement> getNextTags() {
        return nextTags;
    }
}
