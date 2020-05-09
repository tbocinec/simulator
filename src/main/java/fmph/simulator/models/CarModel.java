package fmph.simulator.models;


import fmph.simulator.Math.Geometric;
import fmph.simulator.Math.Line;
import fmph.simulator.Math.Point2d;
import fmph.simulator.Running.RunState;
import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.map.LaserTag;
import fmph.simulator.map.Segment;
import fmph.simulator.models.car.CarManagment;
import fmph.simulator.models.car.Model;
import fmph.simulator.recognization.NeartTag;
import fmph.simulator.recognization.RecognizationElement;
import fmph.simulator.recognization.RecognizationSender;
import org.apache.commons.configuration2.PropertiesConfiguration;

import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class CarModel {

    CarManagment carManagment;
    SignalToAngle signalToAngle;
    RecognizationSender recolonizationSender;

    //Body na luci
    Point2d leftBeen= new Point2d(0,0);
    Point2d centerBeen= new Point2d(0,0);
    Point2d rightBeen= new Point2d(0,0);

    //Stred otacania
    Point2d centerOfTurn = new Point2d(-1,-1);
    //Polomery kolies
    double front_wheel_radius = 0;
    double back_wheel_radius = 0;
    boolean waitAfterRecognization = false;

    private PropertiesConfiguration config;
    double  minimumTimeInterval = 40;

    ArrayList<String> lastSeenTag = new ArrayList<>();



    public CarModel() {
        carManagment = new CarManagment();
        signalToAngle = new SignalToAngle();
        recolonizationSender = new RecognizationSender();
        initStartValue();
    }

    public void initStartValue() {
        config = ContextBuilder.getContext().config;
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        carState.getPos().setX(ContextBuilder.getContext().getMap().getMap().getSegments().get(0).getStartPose().getX() - 0.205);
        carState.getPos().setY(ContextBuilder.getContext().getMap().getMap().getSegments().get(0).getStartPose().getY());
        carState.setCarAngle(config.getDouble("car.initial.carAngle")); //uhol natocenia celeho automobilu  [stupne, 0=sever]
        carState.setWheelAngle(config.getDouble("car.initial.wheelAngle")); //uhol natocenia predneho kolesa voci 0 polohe  [stupne, 0=rovno]
        carState.setCarSpeed(config.getDouble("car.initial.carSpeed")); //aktualna rychlost  [m/s]
        computeHelpPoint();


    }

    public void computeHelpPoint(){
        computeCenterOfTurn();
        computeBackOfVehlice();
        compute_wheel_radius();
    }
    private void computeCenterOfTurn() {
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        Model model = carManagment.getActual();

        centerOfTurn.setX(carState.getPos().getX() - model.getDISTANCEBETWEENAXLES() * (Math.cos(Math.toRadians(carState.getWheelAngle() + carState.getCarAngle())) /
                Math.sin(Math.toRadians(carState.getWheelAngle()))));
        centerOfTurn.setY(carState.getPos().getY()- model.getDISTANCEBETWEENAXLES() * (Math.sin(Math.toRadians(carState.getWheelAngle() + carState.getCarAngle())) /
                Math.sin( Math.toRadians(carState.getWheelAngle()))));
    }
    private void computeBackOfVehlice(){
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        Model model = carManagment.getActual();
        carState.setPosXBack(carState.getPos().getX() + model.getDISTANCEBETWEENAXLES() * Math.sin(Math.toRadians(carState.getCarAngle())));
        carState.setPosYBack(carState.getPos().getY() - model.getDISTANCEBETWEENAXLES() * Math.cos(Math.toRadians(carState.getCarAngle())));
    }
    private void computeBeemPoint(CarState carState, Model model) {
        leftBeen.setX(carState.getPos().getX() -  model.getDISTANCEBETWEENAXLEANDBEAM() * Math.sin(Math.toRadians(carState.getCarAngle()))
                + model.getBEAMWIDTH()/2 * Math.cos(Math.toRadians(carState.getCarAngle())));
        leftBeen.setY(carState.getPos().getY() + model.getDISTANCEBETWEENAXLEANDBEAM()  * Math.cos(Math.toRadians(carState.getCarAngle()))
                + model.getBEAMWIDTH()/2 * Math.sin(Math.toRadians(carState.getCarAngle())));

        centerBeen.setX(carState.getPos().getX()  - (model.getDISTANCEBETWEENAXLEANDBEAM() * Math.sin(Math.toRadians(carState.getCarAngle()))));
        centerBeen.setY(carState.getPos().getY() + (model.getDISTANCEBETWEENAXLEANDBEAM() * Math.cos(Math.toRadians(carState.getCarAngle()))));

        rightBeen.setX(carState.getPos().getX()  - model.getDISTANCEBETWEENAXLEANDBEAM() * Math.sin(Math.toRadians(carState.getCarAngle()))
                - model.getBEAMWIDTH() / 2 * Math.cos(Math.toRadians(carState.getCarAngle())));
        rightBeen.setY(carState.getPos().getY()  + model.getDISTANCEBETWEENAXLEANDBEAM() * Math.cos(Math.toRadians(carState.getCarAngle()))
                -  model.getBEAMWIDTH() / 2 * Math.sin(Math.toRadians(carState.getCarAngle())));
    }
    protected void compute_wheel_radius(){
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        Model model = carManagment.getActual();
        this.front_wheel_radius = Math.pow(Math.abs(model.getDISTANCEBETWEENAXLES() / Math.sin(Math.toRadians(carState.getWheelAngle()))),1);
        this.back_wheel_radius = Math.pow(Math.abs(model.getDISTANCEBETWEENAXLES() / Math.tan(Math.toRadians(carState.getWheelAngle()))),1);
    }




    synchronized  public void  movie(double runTime,double lastRunTime) {
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        Model model = carManagment.getActual();

        if (carState.getCarSpeed() == 0) {
            return;
        }

        if (lastRunTime + minimumTimeInterval < runTime) {
            double timeTravel = runTime - lastRunTime;
            double traveledDistance = (timeTravel / 1000) * carState.getCarSpeed();
            computeBeemPoint(carState, model);

            //carState.setCarAngle(carState.getCarAngle()+180);

            if (Math.abs(carState.getWheelAngle()) <= 0.01) { //auto ide rovno
                // carState.setWheelAngle(0.02);
                carState.getPos().setX(carState.getPos().getX() + (traveledDistance * Math.sin(Math.toRadians(carState.getCarAngle()))));
                carState.getPos().setY(carState.getPos().getY() - (traveledDistance * Math.cos(Math.toRadians(carState.getCarAngle()))));

            } else {
                //double Bx = posX +  (DISTANCEBETWEENAXLES * Math.sin(Math.toRadians(wheelAngle)));
                //double By = posY - (DISTANCEBETWEENAXLES * Math.cos(Math.toRadians(wheelAngle)));
                //double R = Math.abs(DISTANCEBETWEENAXLES/Math.sin(Math.toRadians(wheelAngle)));
                //double delta_fi = (interval * carSpeed)/DISTANCEBETWEENAXLES * Math.tan(wheelAngle);

                computeCenterOfTurn();

                //double delta_fi = -((traveledDistance) / model.getDISTANCEBETWEENAXLES()) * Math.tan(Math.toRadians(carState.getWheelAngle()));
                double R  = Geometric.distance(carState.getPos(),centerOfTurn);
                double delta_fi = (traveledDistance * 180 )/ (Math.PI * R );

                double pomA = model.getDISTANCEBETWEENAXLES() * (Math.cos(Math.toRadians(carState.getWheelAngle() + carState.getCarAngle())) /
                        Math.sin(Math.toRadians(carState.getWheelAngle())));

                double pomB = model.getDISTANCEBETWEENAXLES() * (Math.sin(Math.toRadians(carState.getWheelAngle() + carState.getCarAngle())) /
                        Math.sin(Math.toRadians(carState.getWheelAngle())));


                carState.getPos().setX(Math.cos(Math.toRadians(delta_fi)) * pomA + (Math.sin(Math.toRadians(delta_fi)) * pomB) + centerOfTurn.getX());
                carState.getPos().setY(Math.cos(Math.toRadians(delta_fi)) * pomB - (Math.sin(Math.toRadians(delta_fi)) * pomA) + centerOfTurn.getY());

                carState.setCarAngle((carState.getCarAngle() - delta_fi) % 360);
                // ;


                checkIdentifierActual();
                computeBackOfVehlice();

                ContextBuilder.getContext().getCarInfoController().changeText();

            }
        }


    }



    public void checkIdentifierActual_oldVersion() {
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        Model model = carManagment.getActual();

        if(ContextBuilder.getContext().getRunManagement().getActualRun().getRunState()!= RunState.run){
            return;
        }
        computeBeemPoint(carState,model);

        //Smerove vektory
        double svx =rightBeen.getX() - centerBeen.getX();
        double svy = rightBeen.getY() - centerBeen.getY();

        double A = svx;
        double B = -svy;
        double C = -((B * centerBeen.getX()) + (A * centerBeen.getY()));


        for (Segment segment : ContextBuilder.getContext().getMap().getMap().getSegments()) {
            for (LaserTag laserTag : segment.getLaserTags()) {


                double x = laserTag.getX();
                double y = laserTag.getY();


                double distance = ((B * x) + (A * y) + C) / Math.sqrt((Math.pow(A, 2) + Math.pow(B, 2)));

                if (Math.abs(distance) < 0.006) {
                    double distanceFromX = Math.sqrt((Math.pow(x - centerBeen.getX(), 2) + Math.pow(y - centerBeen.getY(), 2)));
                    if (Math.abs(distanceFromX) < model.getBEAMWIDTH() / 2) {
                        if (lastSeenTag.contains(laserTag.getType())) {
                            break;
                        } else {
                            lastSeenTag.add(laserTag.getType());
                            if (lastSeenTag.size() > 4) { //todo
                                lastSeenTag.remove(0);
                            }
                            recolonizationSender.sendNow(laserTag,segment,distanceFromX);


                        }
                    }
                }
            }
        }

    }

    public void checkIdentifierActual() {
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        Model model = carManagment.getActual();

        if(ContextBuilder.getContext().getRunManagement().getActualRun().getRunState()!= RunState.run){
            return;
        }
        computeBeemPoint(carState,model);
        for (Segment segment : ContextBuilder.getContext().getMap().getMap().getSegments()) {
            for (LaserTag laserTag : segment.getLaserTags()) {

                double x = laserTag.getX();
                double y = laserTag.getY();
                Point2d laserPoint = new Point2d(x,y);

                double distance = Geometric.distance(new Line(rightBeen,leftBeen),laserPoint);

                if (Math.abs(distance) < 0.02) {
                    if (lastSeenTag.contains(laserTag.getType())) {
                        break;
                    }
                    else {
                        lastSeenTag.add(laserTag.getType());
                        if (lastSeenTag.size() > 2) { //todo
                            lastSeenTag.remove(0);
                        }
                        double distanceFromX = Math.sqrt((Math.pow(x - centerBeen.getX(), 2) + Math.pow(y - centerBeen.getY(), 2)));

                        recolonizationSender.sendNow(laserTag,segment,distanceFromX);
                    }
                }
            }
        }
    }

    boolean onlyOne = false;
    public void checkIdentifierAll() {
        if(onlyOne){
            return;
        }
        onlyOne = true;
        System.out.println("Start intentifier all");
        recolonizationSender.killAllFutureSend();

        if(ContextBuilder.getContext().getRunManagement().getActualRun().getRunState()!= RunState.run){
            return;
        }

        double rTime = ContextBuilder.getContext().getRunManagement().getActualRun().getRunTimeSecond();

        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        Model model = carManagment.getActual();


        computeBeemPoint(carState,model);
        //Radius between
        double r1= Geometric.distance(leftBeen,centerOfTurn);
        double r2= Geometric.distance(rightBeen,centerOfTurn);
        double R = Geometric.distance(centerOfTurn,carState.getPos());
        System.out.println("polomer predneho kolesa " +R);
        NeartTag nextTag = new NeartTag();

        System.out.println("stret otacania + x,y +["+ centerOfTurn.getX()+","+centerOfTurn.getY()+"]");
        System.out.println("stret luca + x,y +["+ centerBeen.getX()+","+centerBeen.getY()+"]");
        for (Segment segment : ContextBuilder.getContext().getMap().getMap().getSegments()) {
            for (LaserTag laserTag : segment.getLaserTags()) {
                Point2d tagPoint = new Point2d(laserTag.getX(),laserTag.getY());
                double distanceFromCenter = Geometric.distance(tagPoint,centerOfTurn);
                //is point in actual trajectory
                if( (r1 > distanceFromCenter && r2 < distanceFromCenter ) || (r1 < distanceFromCenter && r2 > distanceFromCenter)){
                    double distanceFromCenterBeem = Geometric.distance(tagPoint,centerBeen);
                    double distanceFromCenterFrontCar = Geometric.distance(tagPoint,carState.getPos());
                    //is behind car and not between car and beem
                    if(distanceFromCenterBeem < distanceFromCenterFrontCar && distanceFromCenterFrontCar > model.getBEAMWIDTH()*0.9) {


                        double st =  Geometric.distance(centerOfTurn, centerBeen);
                        double sb =  Geometric.distance(centerOfTurn, tagPoint);
                        double tb =  Geometric.distance(centerBeen, tagPoint);

                        double alfa =  Math.acos( ((st*st) + (sb*sb)  -(tb*tb) )/ (2 * st *sb));



                        alfa = Math.toDegrees(alfa);

                        double t = (Math.PI * alfa *  R ) / (180 * carState.getCarSpeed());



                        double tt = rTime + t;

                        nextTag.add(tt, new RecognizationElement(laserTag,segment,alfa));
                    }
                }
            }
        }
        recolonizationSender.planSend(nextTag);


    }



    public void setWhealAngle(double signal) {
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        double angle = signalToAngle.transformToAngle(signal);
        carState.setWheelAngle(angle);
        recolonizationSender.updateRecognization();
        ContextBuilder.getContext().getConsoleController().addMsg("Car change dir to " + angle);
        computeHelpPoint();
        checkIdentifierAll();
        compute_wheel_radius();
    }

    public void applicateLastSpeed() {
        waitAfterRecognization = false;
        ContextBuilder.getContext().getRunManagement().run();
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

    public CarManagment getCarManagment() {
        return carManagment;
    }

    public Point2d getCenterOfTurn() {
        return centerOfTurn;
    }

    public void setCenterOfTurn(Point2d centerOfTurn) {
        this.centerOfTurn = centerOfTurn;
    }

    public Point2d getLeftBeen() {
        return leftBeen;
    }

    public void setLeftBeen(Point2d leftBeen) {
        this.leftBeen = leftBeen;
    }

    public Point2d getCenterBeen() {
        return centerBeen;
    }

    public void setCenterBeen(Point2d centerBeen) {
        this.centerBeen = centerBeen;
    }

    public Point2d getRightBeen() {
        return rightBeen;
    }

    public void setRightBeen(Point2d rightBeen) {
        this.rightBeen = rightBeen;
    }


}
