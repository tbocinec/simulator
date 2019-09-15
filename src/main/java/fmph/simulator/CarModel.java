package fmph.simulator;

public class CarModel {
	static final double 	BEAMWIDTH = 10; // sirka luca [m]
	static final double  DISTANCEBETWEENAXLES = 20;   //vzdialenost medzi prednou a zadnou napravou [m] 
	static final double  DISTANCEBETWEENAXLEANDBEAM = 20;// vzdialenost medzi prednou napravou a lucom [m]

	double posX; //car position in x axis
	double posY; //car position in y axis
	double carAngle; //uhol natocenia celeho automobilu  [stupne, 0=sever]
	double wheelAngle; //uhol natocenia predneho kolesa voci 0 polohe  [stupne, 0=rovno]
	double carSpeed; //aktualna rychlost  [m/s]
	
	
	public void  run() {
		 posX += carSpeed * 1;
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
