package fmph.simulator;

import app.Context;
import fmph.simulator.vizualization.component.Function;

public class CarModel {
	static final double 	BEAMWIDTH = 0.3; // sirka luca [m]
	static final double  DISTANCEBETWEENAXLES = 70;   //vzdialenost medzi prednou a zadnou napravou [m]
	static final double  DISTANCEBETWEENAXLEANDBEAM = 0.3;// vzdialenost medzi prednou napravou a lucom [m]

	//todo rozsirit parameter a stav
	
	
	double posX = 390; //car position in x axis front axle
	double posY = 420; //car position in y axis front axle

	double carAngle = 90; //uhol natocenia celeho automobilu  [stupne, 0=sever]
	double wheelAngle =-27; //uhol natocenia predneho kolesa voci 0 polohe  [stupne, 0=rovno]
	double carSpeed = 1100; //aktualna rychlost  [m/s]
	

	double lastRun = -1;
	double interval = 10;


	public void  run() {
		if(lastRun == -1){
			lastRun = System.currentTimeMillis();
			return;
		}
		double actualTime = System.currentTimeMillis();
		if(lastRun + interval < actualTime){

			double time = actualTime -lastRun;
			//double Bx = posX+  (DISTANCEBETWEENAXLES * Math.sin(Math.toRadians(wheelAngle)));
			//double By = posY - (DISTANCEBETWEENAXLES * Math.cos(Math.toRadians(wheelAngle)));


			double Cx = posX - DISTANCEBETWEENAXLES * (Math.cos(Math.toRadians(wheelAngle+carAngle))/
					Math.sin(Math.toRadians(wheelAngle)));
			double Cy = posY - DISTANCEBETWEENAXLES * (Math.sin(Math.toRadians(wheelAngle+carAngle))/Math.sin(
					Math.toRadians(wheelAngle)));

			//double R = Math.abs(DISTANCEBETWEENAXLES/Math.sin(Math.toRadians(wheelAngle)));
			//double delta_fi = (interval * carSpeed)/DISTANCEBETWEENAXLES * Math.tan(wheelAngle);
			double delta_fi = (( (time	/1000) * carSpeed)/DISTANCEBETWEENAXLES) * Math.tan(Math.toRadians(wheelAngle));

			carAngle = (carAngle + delta_fi)%360;
			posX = Math.cos(Math.toRadians(delta_fi)) * (posX - Cx) + Math.sin(Math.toRadians(delta_fi)) * (posY - Cy) + Cx;
			posY = Math.cos(Math.toRadians(delta_fi)) * (posY - Cy) - Math.sin(Math.toRadians(delta_fi)) * (posX -Cx) + Cy;

			System.out.println(posX + " " + posY);
			Context.getContext().getControlPanel().changeText();
			lastRun = actualTime;
		}


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
