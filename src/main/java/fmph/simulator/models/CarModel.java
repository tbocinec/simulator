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
    public static final boolean stepMode = true;
    public static final double BEAMWIDTH = 0.206; // sirka luca [m] original 0.3
    public static final double DISTANCEBETWEENAXLES = 0.26;//35.5;   //vzdialenost medzi prednou a zadnou napravou [m]
    public static final double DISTANCEBETWEENAXLEANDBEAM = 0.189; //20;// vzdialenost medzi prednou napravou a lucom [m] real 0.3
    //Body na luci
    public double Ex = 0;
    public double Ey = 0;
    //spodok luca
    public double Fx = 0;
    public double Fy = 0;
    double posX; //car position in x axis front axle
    double posY; //car position in y axis front axle
    double carAngle; //uhol natocenia celeho automobilu  [stupne, 0=sever]
    double wheelAngle; //uhol natocenia predneho kolesa voci 0 polohe  [stupne, 0=rovno] , todo checkovat max
    double carSpeed; //aktualna rychlost  [m/s]  max speed  25km/h  = 6.9444m/s
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
        posX = ContextBuilder.getContext().getMap().getMap().getSegments().get(0).getStartPose().getX() - 0.185;
        posY = ContextBuilder.getContext().getMap().getMap().getSegments().get(0).getStartPose().getY();
        carAngle = config.getDouble("car.initial.carAngle"); //uhol natocenia celeho automobilu  [stupne, 0=sever]
        wheelAngle =config.getDouble("car.initial.wheelAngle"); //uhol natocenia predneho kolesa voci 0 polohe  [stupne, 0=rovno]
        carSpeed = config.getDouble("car.initial.carSpeed"); //aktualna rychlost  [m/s]
        lastRun = -1;

    }

    public void run() {
        if (lastRun == -1 || carSpeed == 0) {
            lastRun = System.currentTimeMillis();
            return;
        }

        double actualTime = System.currentTimeMillis();

        if (lastRun + minimumTimeInterval < actualTime) {
            double time = actualTime - lastRun;
            double traveledDistance = (time / 1000) * carSpeed *  config.getDouble("app.timeShiftRate");

            carAngle += 180;
            if (Math.abs(wheelAngle) <= 0.5) { //auto ide rovno
                posX = posX + (traveledDistance / 100 * Math.sin(Math.toRadians(carAngle)));
                posY = posY - (traveledDistance / 100 * Math.cos(Math.toRadians(carAngle)));

            } else {
                //double Bx = posX +  (DISTANCEBETWEENAXLES * Math.sin(Math.toRadians(wheelAngle)));
                //double By = posY - (DISTANCEBETWEENAXLES * Math.cos(Math.toRadians(wheelAngle)));
                double Cx = posX + DISTANCEBETWEENAXLES * (Math.cos(Math.toRadians(wheelAngle + carAngle)) /
                        Math.sin(Math.toRadians(wheelAngle)));
                double Cy = posY + DISTANCEBETWEENAXLES * (Math.sin(Math.toRadians(wheelAngle + carAngle)) / Math.sin(
                        Math.toRadians(wheelAngle)));
                //double R = Math.abs(DISTANCEBETWEENAXLES/Math.sin(Math.toRadians(wheelAngle)));
                //double delta_fi = (interval * carSpeed)/DISTANCEBETWEENAXLES * Math.tan(wheelAngle);
                double delta_fi = ((traveledDistance) / DISTANCEBETWEENAXLES) * Math.tan(Math.toRadians(wheelAngle));
                carAngle = (carAngle + delta_fi) % 360;
                posX = Math.cos(Math.toRadians(delta_fi)) * (posX - Cx) - Math.sin(Math.toRadians(delta_fi)) * (posY - Cy) + Cx;
                posY = Math.cos(Math.toRadians(delta_fi)) * (posY - Cy) + Math.sin(Math.toRadians(delta_fi)) * (posX - Cx) + Cy;
            }
            lastRun = actualTime;

            checkIdentifier();
            carAngle -= 180;
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
        Ex = posX + (DISTANCEBETWEENAXLEANDBEAM * Math.sin(Math.toRadians(carAngle)));
        Ey = posY - (DISTANCEBETWEENAXLEANDBEAM * Math.cos(Math.toRadians(carAngle)));
        //spodok luca
        Fx = posX + DISTANCEBETWEENAXLEANDBEAM * Math.sin(Math.toRadians(carAngle))
                - BEAMWIDTH / 2 * Math.cos(Math.toRadians(carAngle));
        Fy = posY - DISTANCEBETWEENAXLEANDBEAM * Math.cos(Math.toRadians(carAngle))
                - BEAMWIDTH / 2 * Math.sin(Math.toRadians(carAngle));


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
                                carSpeed = 0;
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

        ContextBuilder.getContext().getConsoleController().addMsg("Actual segmet : " + segment.getSegmentId() + " tagType " + laserTag.getType());

        Message msg = new Message();
        msg.setTime_stamp(time.toPlainString());
        msg.setTag_id(Integer.parseInt(laserTag.getType()));


        Double alfa = Math.toRadians(laserTag.getGamma());
        Double beta = Math.toRadians(carAngle - 180);
        double titl = Function.angle_difference(beta, alfa);
        msg.setTilt(Math.abs(titl));
        //System.out.println("laserTag " + laserTag.getGamma() + " car angle + " + (carAngle - 180) + " rozdiel = " + titl);
        msg.setCenter_x(-distanceFromX);//tododis
        //msg.setCenter_x(0);//tododis

        ContextBuilder.getContext().getServer().sendMsg(msg.serialize());
    }

    public void setWhealAngle(double signal) {
        double angle = signalToAngle(signal);
        this.setWheelAngle(angle);
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
        setCarSpeed(getLastSpeed());
    }

    public double getLastSpeed() {
        return lastSpeed;
    }

    public void setLastSpeed(double lastSpeed) {
        this.lastSpeed = lastSpeed;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getCarAngle() {
        return carAngle;
    }

    public void setCarAngle(double carAngle) {
        this.carAngle = carAngle;
    }

    public double getWheelAngle() {
        return wheelAngle;
    }

    public void setWheelAngle(double wheelAngle) {
        this.wheelAngle = wheelAngle;
    }

    public double getCarSpeed() {
        return carSpeed;
    }

    public void setCarSpeed(double carSpeed) {
        this.carSpeed = carSpeed;
    }

}
