package fmph.simulator;


import app.Context;
import com.Message;
import fmph.simulator.map.LaserTag;
import fmph.simulator.map.Segment;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CarModel {
    public static final double BEAMWIDTH = 12; // sirka luca [m] original 0.3
    public static final double DISTANCEBETWEENAXLES = 70;   //vzdialenost medzi prednou a zadnou napravou [m]
    public static final double DISTANCEBETWEENAXLEANDBEAM = 20;// vzdialenost medzi prednou napravou a lucom [m] real 0.3

    //todo rozsirit parameter a stav


    double posX = 320; //car position in x axis front axle
    double posY = 420; //car position in y axis front axle

    //double posX = 350; //car position in x axis front axle
    //double posY = 370; //car position in y axis front axle

    double carAngle = 270; //uhol natocenia celeho automobilu  [stupne, 0=sever]
    double wheelAngle = 1.00; //uhol natocenia predneho kolesa voci 0 polohe  [stupne, 0=rovno]

    double carSpeed = 0; //aktualna rychlost  [m/s]


    double lastRun = -1;
    double minimumTimeInterval = 40;

    double count = 0;

    ArrayList<LaserTag> lastSeenTag = new ArrayList<>();

    public void run() {
        if (lastRun == -1) {
            lastRun = System.currentTimeMillis();
            return;
        }


        double actualTime = System.currentTimeMillis();
        if (lastRun + minimumTimeInterval < actualTime) {


            if(carSpeed == 0){
                lastRun = actualTime;
                return;
            }

            double time = actualTime - lastRun;
            double traveledDistance = (time / 1000) * carSpeed * 1000;

            checkIdentifier();
            if (Math.abs(wheelAngle) <= 1) { //auto ide rovno
                posX = posX + (traveledDistance / 100 * Math.sin(Math.toRadians(carAngle)));
                posY = posY - (traveledDistance / 100 * Math.cos(Math.toRadians(carAngle)));

            } else {
                //double Bx = posX +  (DISTANCEBETWEENAXLES * Math.sin(Math.toRadians(wheelAngle)));
                //double By = posY - (DISTANCEBETWEENAXLES * Math.cos(Math.toRadians(wheelAngle)));
                double Cx = posX - DISTANCEBETWEENAXLES * (Math.cos(Math.toRadians(wheelAngle + carAngle)) /
                        Math.sin(Math.toRadians(wheelAngle)));
                double Cy = posY - DISTANCEBETWEENAXLES * (Math.sin(Math.toRadians(wheelAngle + carAngle)) / Math.sin(
                        Math.toRadians(wheelAngle)));
                //double R = Math.abs(DISTANCEBETWEENAXLES/Math.sin(Math.toRadians(wheelAngle)));
                //double delta_fi = (interval * carSpeed)/DISTANCEBETWEENAXLES * Math.tan(wheelAngle);
                double delta_fi = ((traveledDistance) / DISTANCEBETWEENAXLES) * Math.tan(Math.toRadians(wheelAngle));
                carAngle = (carAngle + delta_fi) % 360;
                posX = Math.cos(Math.toRadians(delta_fi)) * (posX - Cx) + Math.sin(Math.toRadians(delta_fi)) * (posY - Cy) + Cx;
                posY = Math.cos(Math.toRadians(delta_fi)) * (posY - Cy) - Math.sin(Math.toRadians(delta_fi)) * (posX - Cx) + Cy;

            }
            lastRun = actualTime;
            Context.getContext().getControlPanel().changeText();

        }


    }

    public String checkIdentifier() {

        double transoform = 180;
        carAngle += transoform;
	/*
	//vrch luca
		double Ex = posX -  DISTANCEBETWEENAXLEANDBEAM * Math.sin(Math.toRadians(carAngle))
				+ BEAMWIDTH/2 * Math.cos(Math.toRadians(carAngle));


		double Ey = posY + DISTANCEBETWEENAXLEANDBEAM  * Math.cos(Math.toRadians(carAngle))
				+ BEAMWIDTH/2 * Math.sin(Math.toRadians(carAngle));


	 */
        //stred luca
        double Ex = posX - DISTANCEBETWEENAXLEANDBEAM * Math.sin(Math.toRadians(carAngle));


        double Ey = posY + DISTANCEBETWEENAXLEANDBEAM * Math.cos(Math.toRadians(carAngle));


        double Fx = posX - DISTANCEBETWEENAXLEANDBEAM * Math.sin(Math.toRadians(carAngle))
                - BEAMWIDTH / 2 * Math.cos(Math.toRadians(carAngle));
        double Fy = posY + DISTANCEBETWEENAXLEANDBEAM * Math.cos(Math.toRadians(carAngle))
                - BEAMWIDTH / 2 * Math.sin(Math.toRadians(carAngle));

        carAngle -= transoform;


        double svx = Fx - Ex;
        double svy = Fy - Ey;

        double A = svy;
        double B = -svx;
        double C = -((B * Ex) + (A * Ey));


        for (Segment segment : Context.getContext().getMap().getMap().getSegments()) {

            for (LaserTag laserTag : segment.getLaserTags()) {

                double x = laserTag.getX();
                double y = laserTag.getY();
                double distance = ((B * x) + (A * y) + C) / Math.sqrt((Math.pow(A, 2) + Math.pow(B, 2)));

                //je vzdialena od kolmice na priamku

                if (Math.abs(distance) < 10) {

                    //je  na luci
                    double distanceFromX = Math.sqrt((Math.pow(x - Ex, 2) + Math.pow(y - Ey, 2)));

                    if (distanceFromX < BEAMWIDTH / 2) {

                        if (lastSeenTag.contains(laserTag)) {
                            break;
                        } else {
                            lastSeenTag.add(laserTag);
                            if (lastSeenTag.size() > 4) { //todo
                                lastSeenTag.remove(0);
                            }
                            Context.getContext().getConsolePanel().addMsg("Actual segmet : " + segment.getSegmentId() + " tagType " + laserTag.getType());
                            Context.getContext().setLastSeenlaserTag(laserTag);

                            Message msg = new Message();
                            BigDecimal time = new BigDecimal(System.currentTimeMillis());
                            msg.setTime_stamp(time.toPlainString());
                            msg.setTag_id(Integer.parseInt(laserTag.getType()));
                            msg.setTilt(carAngle - laserTag.getGamma());
                            msg.setCenter_x(distanceFromX);

                            Context.getContext().getServer().sendMsg(msg.serialize());
                        }
                    }
                }
            }
        }
        return "1";
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
