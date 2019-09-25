package com;

public class InComeMessege {
	double wheelAngle;
	double carSpeed;
	
	public InComeMessege(double wheelAngle, double carSpeed) {
		this.wheelAngle = wheelAngle;
		this.carSpeed = carSpeed;
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
	
	public String serializable() {
		return "---"+wheelAngle+":"+carSpeed;
	}
	
	public void deserializable(String message) {
		wheelAngle = Double.parseDouble(message.split(":")[0]);
		carSpeed = Double.parseDouble(message.split(":")[1]);
		
	}
}
