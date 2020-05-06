package fmph.simulator.models;

import fmph.simulator.Math.Point2d;
import fmph.simulator.app.context.ContextBuilder;


import java.io.Serializable;
import java.net.ContentHandler;

public class CarState implements Serializable {

    Point2d pos = new Point2d(0,0);
    //double posX; //car position in x axis front axle
    //double posY; //car position in y axis front axle
    double carAngle; //uhol natocenia celeho automobilu  [stupne, 0=sever]
    double wheelAngle; //uhol natocenia predneho kolesa voci 0 polohe  [stupne, 0=rovno] , todo checkovat max
    double carSpeed; //aktualna rychlost  [m/s]  max speed  25km/h  = 6.9444m/s

    double posXBack; //car position in x axis front axle
    double posYBack; //car position in y axis front axle



    public double getPosYBack() {
        return posYBack;
    }

    public void setPosYBack(double posYBack) {
        this.posYBack = posYBack;
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
        return carSpeed * ContextBuilder.getContext().config.getDouble("app.carSpeedRate");
    }

    public void setCarSpeed(double carSpeed) {
        this.carSpeed = carSpeed;
    }

    public double getPosXBack() {
        return posXBack;
    }

    public void setPosXBack(double posXBack) {
        this.posXBack = posXBack;
    }

    public Point2d getPos() {
        return pos;
    }

    public void setPos(Point2d pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "CarState{" +
                "posX=" + pos.getX() +
                ", posY=" + pos.getY() +
                ", carAngle=" + carAngle +
                ", wheelAngle=" + wheelAngle +
                ", carSpeed=" + carSpeed +
                ", posXBack=" + posXBack +
                ", posYBack=" + posYBack +
                '}';
    }
}
