package com;

import app.Context;
import fmph.simulator.CarModel;
import fmph.simulator.vizualization.console.Message;
import fmph.simulator.vizualization.console.MessageType;

public class InComeMessege {
	double wheelAngle;
	double carSpeed;

	String errorMsg;
	
	public InComeMessege(double wheelAngle, double carSpeed) {
		this.wheelAngle = wheelAngle;
		this.carSpeed = carSpeed;
		errorMsg = "";
	}

	public InComeMessege(String error) {
		this.wheelAngle = -1;
		this.carSpeed = -1;
		errorMsg = error;
	}

	public InComeMessege(){

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
		return ""+errorMsg+":"+wheelAngle+":"+carSpeed;
	}
	
	public void deserializable(String message) {
		errorMsg = message.split(":")[0];
		wheelAngle = Double.parseDouble(message.split(":")[1]);
		carSpeed = Double.parseDouble(message.split(":")[2]);
	}

	public static InComeMessege deserializableNewMsg(String message){
		InComeMessege msg = new InComeMessege();
		msg.deserializable(message);
		return  msg;
	}

	public void save() {
		if (this.errorMsg != null && this.errorMsg.length() > 0) {
			new Message(this.errorMsg,MessageType.ERROR);
		} else {
			CarModel car = Context.getContext().getCarModel();
			car.setWheelAngle(this.wheelAngle);
			car.setCarSpeed(this.carSpeed);

			Context.getContext().getControlPanel().changeText();
			//log
			new Message("New Input from car {speed:" + this.carSpeed + ",wheelAngle:" + this.wheelAngle + " }", MessageType.INFO);
		}
	}
}
