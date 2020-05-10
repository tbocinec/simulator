package fmph.simulator.recognization;

import fmph.simulator.map.LaserTag;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class NeartTags {
    private static List<String> lastsSend = new ArrayList<String>();
    TreeMap<Double, RecognizationElement> nextTags = new TreeMap<>();

    public NeartTags() {
        this.nextTags =  new TreeMap<>();
    }

    public void add(double timeSecond,RecognizationElement re){
        LaserTag laserTag = re.getLaserTag();
        if(lastsSend.contains(laserTag.getType())){
            return;
        }
        containstLast(laserTag.getType());
        removeLastIfNeed();
        if(lastsSend.size() > 2){lastsSend.remove(0);}
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

    public static  synchronized void addSend(String lasertagType){
        lastsSend.add(lasertagType);
    }

    private synchronized void removeLastIfNeed(){
        if(lastsSend.size() > 2){
            lastsSend.remove(0);
        }
    }
    private synchronized boolean containstLast(String lasertagType){
        return lastsSend.contains(lasertagType);
    }



}
