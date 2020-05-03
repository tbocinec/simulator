package fmph.simulator.models;


import fmph.simulator.Running.RunState;
import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.com.Message;
import fmph.simulator.map.LaserTag;
import fmph.simulator.map.Segment;
import fmph.simulator.recognization.HistoryElement;
import fmph.simulator.vizualization.component.Function;
import fmph.simulator.vizualization.console.MessageType;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.lang3.SerializationUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class CarModel {

    public static final double BEAMWIDTH = 0.206; // sirka luca [m] original 0.3
    public static final double DISTANCEBETWEENAXLES = 0.206;//35.5;   //vzdialenost medzi prednou a zadnou napravou [m]
    public static final double DISTANCEBETWEENAXLEANDBEAM = 0.189; //20;// vzdialenost medzi prednou napravou a lucom [m] real 0.3

    //Body na luci
    public double Ex = 0;
    public double Ey = 0;
    //spodok luca
    public double Fx = 0;
    public double Fy = 0;
    //Stred otacania
    double Cx=0;
    double Cy=0;

    //Polomery kolies
    double front_wheel_radius = 0;
    double back_wheel_radius = 0;


    private CarState carState = new CarState();
    private PropertiesConfiguration config;


    double lastSpeed;
    double minimumTimeInterval = 40;

    ArrayList<String> lastSeenTag = new ArrayList<>();
    private HistoryElement lastRecognizationHistoryElement;

    private HashMap<Integer, Double> mapAngle;



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

        //computeCenterOfTurn();
        //compute_wheel_radius();

    }



    synchronized  public void  movie(double runTime,double lastRunTime) {
        if (carState.getCarSpeed() == 0) {
            return;
        }

        if (lastRunTime + minimumTimeInterval < runTime) {
            double timeTravel = runTime - lastRunTime;
            double traveledDistance = (timeTravel / 1000) * carState.getCarSpeed() *  config.getDouble("app.carSpeedRate");

            //carState.setCarAngle(carState.getCarAngle()+180);

            if (Math.abs(carState.getWheelAngle()) <= 0.5) { //auto ide rovno
                carState.setPosX(carState.getPosX() + (traveledDistance  * Math.sin(Math.toRadians(carState.getCarAngle()))));
                carState.setPosY(carState.getPosY() - (traveledDistance  * Math.cos(Math.toRadians(carState.getCarAngle()))));

            } else {
                //double Bx = posX +  (DISTANCEBETWEENAXLES * Math.sin(Math.toRadians(wheelAngle)));
                //double By = posY - (DISTANCEBETWEENAXLES * Math.cos(Math.toRadians(wheelAngle)));
                //double R = Math.abs(DISTANCEBETWEENAXLES/Math.sin(Math.toRadians(wheelAngle)));
                //double delta_fi = (interval * carSpeed)/DISTANCEBETWEENAXLES * Math.tan(wheelAngle);

                computeCenterOfTurn();
                double delta_fi = -((traveledDistance) / DISTANCEBETWEENAXLES) * Math.tan(Math.toRadians(carState.getWheelAngle()));


                double pomA  =  DISTANCEBETWEENAXLES * (Math.cos(Math.toRadians(carState.getWheelAngle() + carState.getCarAngle())) /
                        Math.sin(Math.toRadians(carState.getWheelAngle())));

                double pomB =  DISTANCEBETWEENAXLES * (Math.sin(Math.toRadians(carState.getWheelAngle() + carState.getCarAngle())) /
                        Math.sin( Math.toRadians(carState.getWheelAngle())));



                carState.setPosX(Math.cos(Math.toRadians(delta_fi)) * pomA + (Math.sin(Math.toRadians(delta_fi)) *  pomB) + Cx);
                carState.setPosY(Math.cos(Math.toRadians(delta_fi)) * pomB - (Math.sin(Math.toRadians(delta_fi)) * pomA) + Cy);

                carState.setCarAngle((carState.getCarAngle() - delta_fi) % 360);
               // ;
            }
            //lastRun = actualTime;
            checkIdentifier();
            computeBackOfVehlice();
            compute_wheel_radius();//todo rm
            //carState.setCarAngle(carState.getCarAngle()-180);
            ContextBuilder.getContext().getCarInfoController().changeText();

        }


    }

    private void computeCenterOfTurn() {
        Cx =   carState.getPosX() - DISTANCEBETWEENAXLES * (Math.cos(Math.toRadians(carState.getWheelAngle() + carState.getCarAngle())) /
                Math.sin(Math.toRadians(carState.getWheelAngle())));
        Cy =  carState.getPosY() - DISTANCEBETWEENAXLES * (Math.sin(Math.toRadians(carState.getWheelAngle() + carState.getCarAngle())) /
                Math.sin( Math.toRadians(carState.getWheelAngle())));
    }

    public void checkIdentifier() {
        if(ContextBuilder.getContext().getRunManagement().getActualRun().getRunState()!= RunState.run){
            return;
        }
        //vrch luca
        //double Ex = posX -  DISTANCEBETWEENAXLEANDBEAM * Math.sin(Math.toRadians(carAngle))
        //		+ BEAMWIDTH/2 * Math.cos(Math.toRadians(carAngle));sssssss
        //double Ey = posY + DISTANCEBETWEENAXLEANDBEAM  * Math.cos(Math.toRadians(carAngle))
        //		+ BEAMWIDTH/2 * Math.sin(Math.toRadians(carAngle));
        //stred luca
        Ex = carState.getPosX() - (DISTANCEBETWEENAXLEANDBEAM * Math.sin(Math.toRadians(carState.getCarAngle())));
        Ey = carState.getPosY() + (DISTANCEBETWEENAXLEANDBEAM * Math.cos(Math.toRadians(carState.getCarAngle())));
        //spodok luca
        Fx = carState.getPosX() - DISTANCEBETWEENAXLEANDBEAM * Math.sin(Math.toRadians(carState.getCarAngle()))
                - BEAMWIDTH / 2 * Math.cos(Math.toRadians(carState.getCarAngle()));
        Fy = carState.getPosY() + DISTANCEBETWEENAXLEANDBEAM * Math.cos(Math.toRadians(carState.getCarAngle()))
                - BEAMWIDTH / 2 * Math.sin(Math.toRadians(carState.getCarAngle()));


        //Smerove vektory
        double svx = Fx - Ex;
        double svy = Fy - Ey;

        double A = svx;
        double B = -svy;
        double C = -((B * Ex) + (A * Ey));


        for (Segment segment : ContextBuilder.getContext().getMap().getMap().getSegments()) {
            for (LaserTag laserTag : segment.getLaserTags()) {


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
                            double rTime = ContextBuilder.getContext().getRunManagement().getActualRun().getRunTimeSecond();//todo check if simulator akcept my time
                            BigDecimal time = new BigDecimal(rTime);
                            new fmph.simulator.vizualization.console.Message("Recognized new tag with id " + laserTag.getType(), MessageType.INFO);
                            lastRecognizationHistoryElement = new HistoryElement(rTime, SerializationUtils.clone(carState),segment,laserTag);
                            ContextBuilder.getContext().getRunManagement().getActualRun().getRecognitionHistory().addTag(lastRecognizationHistoryElement);
                            if (config.getBoolean("app.waitAfterRecognization"))  {
                                carState.setCarSpeed(0);
                            }
                            sendRecognizedInfo(segment, laserTag, distanceFromX,time);

                        }
                    }
                }
            }
        }

    }

    private void sendRecognizedInfo(Segment segment, LaserTag laserTag, double distanceFromX,BigDecimal time) {

        Message msg = new Message();
        msg.setTime_stamp(time.toPlainString());
        msg.setTag_id(Integer.parseInt(laserTag.getType()));


        Double alfa = Math.toRadians(laserTag.getGamma());
        Double beta = Math.toRadians(carState.getCarAngle() ); //old -180
        double titl = Function.angle_difference(beta, alfa);
        msg.setTilt(Math.abs(titl));
        msg.setCenter_x(-distanceFromX);//tododis
        ContextBuilder.getContext().getServer().sendMsg(msg.serialize());
    }

    public void setWhealAngle(double signal) {
        double angle = signalToAngle(signal);
        carState.setWheelAngle(angle);
        if(lastRecognizationHistoryElement != null){
            lastRecognizationHistoryElement.addRecognizationInfo(ContextBuilder.getContext().getRunManagement().getActualRun().getRunTimeSecond(),SerializationUtils.clone(carState));
            lastRecognizationHistoryElement = null;
        }
        ContextBuilder.getContext().getConsoleController().addMsg("Car change dir to " + angle);
    }

    private void computeBackOfVehlice(){
            carState.setPosXBack(carState.getPosX() + DISTANCEBETWEENAXLES * Math.sin(Math.toRadians(carState.getCarAngle())));
            carState.setPosYBack(carState.getPosY() - DISTANCEBETWEENAXLES * Math.cos(Math.toRadians(carState.getCarAngle())));
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
    
    protected void compute_wheel_radius(){
        this.front_wheel_radius = Math.pow(Math.abs(DISTANCEBETWEENAXLES / Math.sin(Math.toRadians(carState.getWheelAngle()))),1);
        this.back_wheel_radius = Math.pow(Math.abs(DISTANCEBETWEENAXLES / Math.tan(Math.toRadians(carState.getWheelAngle()))),1);
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
        this.carState = SerializationUtils.clone(carState);
    }

    public double getFront_wheel_radius() {
        return front_wheel_radius;
    }

    public void setFront_wheel_radius(double front_wheel_radius) {
        this.front_wheel_radius = front_wheel_radius;
    }

    public double getBack_wheel_radius() {
        return back_wheel_radius;
    }

    public void setBack_wheel_radius(double back_wheel_radius) {
        this.back_wheel_radius = back_wheel_radius;
    }

    public double getCx() {
        return Cx;
    }

    public void setCx(double cx) {
        Cx = cx;
    }

    public double getCy() {
        return Cy;
    }

    public void setCy(double cy) {
        Cy = cy;
    }



}
