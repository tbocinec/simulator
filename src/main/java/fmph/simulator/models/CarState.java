package fmph.simulator.models;

import java.io.Serializable;

public class CarState implements Serializable {
    double posX; //car position in x axis front axle
    double posY; //car position in y axis front axle
    double carAngle; //uhol natocenia celeho automobilu  [stupne, 0=sever]
    double wheelAngle; //uhol natocenia predneho kolesa voci 0 polohe  [stupne, 0=rovno] , todo checkovat max
    double carSpeed; //aktualna rychlost  [m/s]  max speed  25km/h  = 6.9444m/s

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
