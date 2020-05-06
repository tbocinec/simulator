package fmph.simulator.models;

import java.util.HashMap;

public class SignalToAngle {
    private final HashMap<Integer, Double> mapAngle;
    private final double angle_for_servo_max =0;
    private final double SERVO_MAX = 0 ;

    public SignalToAngle(){
       mapAngle = customVariant();
    }


    public double transformToAngle(double signal) {
        return  useSimpleLinear(signal);
    }

    private double useSimpleLinear(double signal){
        return -0.041 * signal;
    }
    private double analytical(double signal){
        return  (signal*angle_for_servo_max)/SERVO_MAX;
    }

    private double fromTable(double signal){
        Integer pom =  (int) Math.round(signal/10)*10;  //rount to tens
        Integer pozpom = Math.abs(pom);
        Double  angle = getNearsAngle(pozpom);
        if(pom < 0 ){
            return angle;
        }
        return angle*-1;
    }

    private double getNearsAngle(Integer signal) {
        if (signal < 0) {
            return 0;
        }
        return mapAngle.getOrDefault(signal, getNearsAngle(signal - 10));
    }



    private HashMap<Integer, Double>   rightVariant() {
        HashMap<Integer, Double> mapAngle = new HashMap<Integer, Double>();
        mapAngle.put(0, 2.0);
        mapAngle.put(10, 2.5);
        mapAngle.put(20, 3.0);
        mapAngle.put(30, 3.5);
        mapAngle.put(40, 4.0);
        mapAngle.put(50, 5.0);
        mapAngle.put(60, 6.0);
        mapAngle.put(70, 7.0);
        mapAngle.put(80, 8.0);
        mapAngle.put(100, 9.0);
        mapAngle.put(120, 10.0);
        mapAngle.put(140, 11.5);
        mapAngle.put(160, 13.0);
        mapAngle.put(180, 14.0);
        mapAngle.put(200, 15.0);
        mapAngle.put(220, 16.5);
        mapAngle.put(240, 18.0);
        mapAngle.put(250, 18.0);
        mapAngle.put(260, 18.0);
        mapAngle.put(270, 18.0);
        return mapAngle;
    }

    public  HashMap<Integer, Double> leftVariant() {
        HashMap<Integer, Double> mapAngle = new HashMap<Integer, Double>();
        mapAngle.put(0, 0.5);
        mapAngle.put(10, 1.5);
        mapAngle.put(20, 2.2);
        mapAngle.put(30, 3.0);
        mapAngle.put(40, 3.5);
        mapAngle.put(50, 4.5);
        mapAngle.put(60, 5.0);
        mapAngle.put(70, 5.6);
        mapAngle.put(80, 6.2);
        mapAngle.put(100, 7.7);
        mapAngle.put(120, 9.2);
        mapAngle.put(140, 10.0);
        mapAngle.put(160, 10.7);
        mapAngle.put(180, 12.0);
        mapAngle.put(200, 12.8);
        mapAngle.put(220, 13.5);
        mapAngle.put(240, 14.0);
        mapAngle.put(250, 14.5);
        mapAngle.put(260, 15.0);
        mapAngle.put(270, 16.5);
        return mapAngle;
    }

    public  HashMap<Integer, Double> customVariant() {
        HashMap<Integer, Double> mapAngle = new HashMap<Integer, Double>();
        mapAngle.put(0, 0.3);
        mapAngle.put(10, 1.2);
        mapAngle.put(20, 1.8);
        mapAngle.put(30, 2.5);
        mapAngle.put(40, 3.0);
        mapAngle.put(50, 4.0);
        mapAngle.put(60, 4.5);
        mapAngle.put(70, 5.0);
        mapAngle.put(80, 5.5);
        mapAngle.put(100, 6.5);
        mapAngle.put(120, 7.5);
        mapAngle.put(140, 8.0);
        mapAngle.put(160, 8.3);
        mapAngle.put(180, 9.0);
        mapAngle.put(200, 9.5);
        mapAngle.put(220, 10.0);
        mapAngle.put(240, 10.5);
        mapAngle.put(250, 11.3);
        mapAngle.put(260, 12.0);
        mapAngle.put(270, 12.5);
        mapAngle.put(280, 13.0);
        return mapAngle;
    }



}
