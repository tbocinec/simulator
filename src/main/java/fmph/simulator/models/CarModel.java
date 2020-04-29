package fmph.simulator.models;


import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.com.Message;
import fmph.simulator.map.LaserTag;
import fmph.simulator.map.Segment;
import fmph.simulator.vizualization.component.Function;
import fmph.simulator.vizualization.console.MessageType;
import org.apache.commons.configuration2.PropertiesConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class CarModel {

    public static final double BEAMWIDTH = 0.206; // sirka luca [m] original 0.3
    public static final double DISTANCEBETWEENAXLES = 0.26;//35.5;   //vzdialenost medzi prednou a zadnou napravou [m]
    public static final double DISTANCEBETWEENAXLEANDBEAM = 0.189; //20;// vzdialenost medzi prednou napravou a lucom [m] real 0.3
    //Body na luci
    public double Ex = 0;
    public double Ey = 0;
    //spodok luca
    public double Fx = 0;
    public double Fy = 0;


    CarState carState = new CarState();

    double lastSpeed;
    double lastRun = -1;
    double minimumTimeInterval = 40;
    ArrayList<String> lastSeenTag = new ArrayList<>();
    private HashMap<Integer, Double> mapAngle;
    private PropertiesConfiguration config;


    public CarModel() {
        initStartValue();
    }

    public void initStartValue() {
        config = ContextBuilder.getContext().config;
        carState.setPosX(ContextBuilder.getContext().getMap().getMap().getSegments().get(0).getStartPose().getX() - 0.185);
        carState.setPosY(ContextBuilder.getContext().getMap().getMap().getSegments().get(0).getStartPose().getY());
        carState.setCarAngle(config.getDouble("car.initial.carAngle")); //uhol natocenia celeho automobilu  [stupne, 0=sever]
        carState.setWheelAngle(config.getDouble("car.initial.wheelAngle")); //uhol natocenia predneho kolesa voci 0 polohe  [stupne, 0=rovno]
        carState.setCarSpeed(config.getDouble("car.initial.carSpeed")); //aktualna rychlost  [m/s]
        lastRun = -1;

    }

    public void movie(double runTime) {

        if (lastRun == -1 || carState.getCarSpeed() == 0) {
            lastRun = runTime;
            return;
        }


        double actualTime = runTime;

        if (lastRun + minimumTimeInterval < actualTime) {
            double time = actualTime - lastRun;
            double traveledDistance = (time / 1000) * carState.getCarSpeed() *  config.getDouble("app.timeShiftRate");

            carState.setCarAngle(carState.getCarAngle()+180);
            if (Math.abs(carState.getWheelAngle()) <= 0.5) { //auto ide rovno
                carState.setPosX(carState.getPosX() + (traveledDistance / 100 * Math.sin(Math.toRadians(carState.getCarAngle()))));
                carState.setPosY(carState.getPosY() - (traveledDistance / 100 * Math.cos(Math.toRadians(carState.getCarAngle()))));

            } else {
                //double Bx = posX +  (DISTANCEBETWEENAXLES * Math.sin(Math.toRadians(wheelAngle)));
                //double By = posY - (DISTANCEBETWEENAXLES * Math.cos(Math.toRadians(wheelAngle)));
                double Cx = carState.getPosX() + DISTANCEBETWEENAXLES * (Math.cos(Math.toRadians(carState.getWheelAngle() + carState.getCarAngle())) /
                        Math.sin(Math.toRadians(carState.getWheelAngle())));
                double Cy =  carState.getPosY() + DISTANCEBETWEENAXLES * (Math.sin(Math.toRadians(carState.getWheelAngle() + carState.getCarAngle())) / Math.sin(
                        Math.toRadians(carState.getWheelAngle())));
                //double R = Math.abs(DISTANCEBETWEENAXLES/Math.sin(Math.toRadians(wheelAngle)));
                //double delta_fi = (interval * carSpeed)/DISTANCEBETWEENAXLES * Math.tan(wheelAngle);
                double delta_fi = ((traveledDistance) / DISTANCEBETWEENAXLES) * Math.tan(Math.toRadians(carState.getWheelAngle()));
                carState.setCarAngle((carState.getCarAngle() + delta_fi) % 360);
                carState.setPosX(Math.cos(Math.toRadians(delta_fi)) * (carState.getPosX() - Cx) - Math.sin(Math.toRadians(delta_fi)) * ( carState.getPosY() - Cy) + Cx);
                carState.setPosY(Math.cos(Math.toRadians(delta_fi)) * ( carState.getPosY() - Cy) + Math.sin(Math.toRadians(delta_fi)) * (carState.getPosX() - Cx) + Cy);
            }
            lastRun = actualTime;

            checkIdentifier();
            carState.setCarAngle(carState.getCarAngle()-180);
            ContextBuilder.getContext().getCarInfoController().changeText();
        }


    }

    public String checkIdentifier() {
        //vrch luca
        //double Ex = posX -  DISTANCEBETWEENAXLEANDBEAM * Math.sin(Math.toRadians(carAngle))
        //		+ BEAMWIDTH/2 * Math.cos(Math.toRadians(carAngle));sssssss
        //double Ey = posY + DISTANCEBETWEENAXLEANDBEAM  * Math.cos(Math.toRadians(carAngle))
        //		+ BEAMWIDTH/2 * Math.sin(Math.toRadians(carAngle));
        //stred luca
        Ex = carState.getPosX() + (DISTANCEBETWEENAXLEANDBEAM * Math.sin(Math.toRadians(carState.getCarAngle())));
        Ey = carState.getPosY() - (DISTANCEBETWEENAXLEANDBEAM * Math.cos(Math.toRadians(carState.getCarAngle())));
        //spodok luca
        Fx = carState.getPosX() + DISTANCEBETWEENAXLEANDBEAM * Math.sin(Math.toRadians(carState.getCarAngle()))
                - BEAMWIDTH / 2 * Math.cos(Math.toRadians(carState.getCarAngle()));
        Fy = carState.getPosY() - DISTANCEBETWEENAXLEANDBEAM * Math.cos(Math.toRadians(carState.getCarAngle()))
                - BEAMWIDTH / 2 * Math.sin(Math.toRadians(carState.getCarAngle()));


        //Smerove vektory
        double svx = Fx - Ex;
        double svy = Fy - Ey;

        double A = svx;
        double B = -svy;
        double C = -((B * Ex) + (A * Ey));


        for (Segment segment : ContextBuilder.getContext().getMap().getMap().getSegments()) {
            for (LaserTag laserTag : segment.getLaserTags()) {

                //double x = Function.txinv(laserTag.getX());
                //double y = Function.txinv(laserTag.getY());


                double x = laserTag.getX();
                double y = laserTag.getY();


                double distance = ((B * x) + (A * y) + C) / Math.sqrt((Math.pow(A, 2) + Math.pow(B, 2)));

                if (Math.abs(distance) < 0.006) {
                    double distanceFromX = Math.sqrt((Math.pow(x - Ex, 2) + Math.pow(y - Ey, 2)));
                    if (Math.abs(distanceFromX) < BEAMWIDTH / 2) {
                        if (lastSeenTag.contains(laserTag.getType())) {
                            break;
                        } else {
                            lastSeenTag.add(laserTag.getType());
                            if (lastSeenTag.size() > 4) { //todo
                                lastSeenTag.remove(0);
                            }
                            BigDecimal time = new BigDecimal(System.currentTimeMillis());
                            new fmph.simulator.vizualization.console.Message("Recognized new tag with id " + laserTag.getType(), MessageType.INFO);
                            ContextBuilder.getContext().getRecognizationHistory().addTag(laserTag,segment,time);
                            if (config.getBoolean("app.waitAfterRecognization"))  {
                                carState.setCarSpeed(0);
                            }
                            sendRecognizedInfo(segment, laserTag, distanceFromX,time);

                        }
                    }
                }
            }
        }
        return "1";
    }

    private void sendRecognizedInfo(Segment segment, LaserTag laserTag, double distanceFromX,BigDecimal time) {

        Message msg = new Message();
        msg.setTime_stamp(time.toPlainString());
        msg.setTag_id(Integer.parseInt(laserTag.getType()));


        Double alfa = Math.toRadians(laserTag.getGamma());
        Double beta = Math.toRadians(carState.getCarAngle() - 180);
        double titl = Function.angle_difference(beta, alfa);
        msg.setTilt(Math.abs(titl));
        msg.setCenter_x(-distanceFromX);//tododis
        ContextBuilder.getContext().getServer().sendMsg(msg.serialize());
    }

    public void setWhealAngle(double signal) {
        double angle = signalToAngle(signal);
        carState.setWheelAngle(angle);
        ContextBuilder.getContext().getConsoleController().addMsg("Car change dir to " + angle);
    }

    public double signalToAngle(double signal) {
        return -0.051 * signal;
        // return  -0.062 * signal +4;


        /*
        loadAngle();
        boolean analitical = false;
        if(analitical){
            double angle_for_servo_max =0;
            double SERVO_MAX = 0 ;
            return  (signal*angle_for_servo_max)/SERVO_MAX;
        }
        else{
            Integer pom =  (int) Math.round(signal/10)*10;  //rount to tens
            Integer pozpom = Math.abs(pom);
            Double  angle = getNearsAngle(pozpom);
            if(pom < 0 ){
                return angle;
            }
            return angle*-1;
        }
    */
    }

    private double getNearsAngle(Integer signal) {
        if (signal < 0) {
            return 0;
        }
        return mapAngle.getOrDefault(signal, getNearsAngle(signal - 10));
    }

    private void loadAngle() {
        if (mapAngle == null) {
            mapAngle = SignalToAngle.makeMap();
        }

    }

    public void applicateLastSpeed() {
        carState.setCarSpeed(getLastSpeed());
    }

    public double getLastSpeed() {
        return lastSpeed;
    }

    public void setLastSpeed(double lastSpeed) {
        this.lastSpeed = lastSpeed;
    }

    public CarState getCarState() {
        return carState;
    }

    public void setCarState(CarState carState) {
        this.carState = carState;
    }



}
