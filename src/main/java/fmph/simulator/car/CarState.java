package fmph.simulator.car;

import fmph.simulator.Math.Point2d;
import fmph.simulator.visualization.console.Message;
import fmph.simulator.visualization.console.MessageType;


import java.io.Serializable;

public class CarState implements Serializable {

    private Point2d pos = new Point2d(0,0);
    private double carAngle; //uhol natocenia celeho automobilu  [stupne, 0=sever]
    private double wheelAngle; //uhol natocenia predneho kolesa voci 0 polohe  [stupne, 0=rovno] , todo checkovat max
    private int gearSpeed=0; //aktualna rychlost  [m/s]  max speed  25km/h  = 6.9444m/s

    private double posXBack; //car position in x axis front axle
    private double posYBack; //car position in y axis front axle



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

    public int getGearSpeed() {
        return gearSpeed;
    }

    public void setGearSpeed(int gearSpeed) {
        new Message("Geared speed  "+gearSpeed, MessageType.INFO);
        this.gearSpeed = gearSpeed;
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
                ", carGear=" + gearSpeed +
                ", posXBack=" + posXBack +
                ", posYBack=" + posYBack +
                '}';
    }
}
