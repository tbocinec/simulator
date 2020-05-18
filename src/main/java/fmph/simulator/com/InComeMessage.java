package fmph.simulator.com;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.car.CarState;
import fmph.simulator.visualization.console.Message;
import fmph.simulator.visualization.console.MessageType;

import java.util.regex.PatternSyntaxException;

@Deprecated
public class InComeMessage {

	final static String SPLIT_CHAR = ";";
	final static String WRONG_MESSAGE_FORMAT = "Wrong format message, deserialize message failed";

	double wheelAngle;
	double carSpeed;
	private String errorMsg;

	
	public InComeMessage(double wheelAngle, double carSpeed) {
		this.wheelAngle = wheelAngle;
		this.carSpeed = carSpeed;
		errorMsg = "";
	}

	public InComeMessage(String error) {
		this.wheelAngle = -1;
		this.carSpeed = -1;
		errorMsg = error;
	}

	public InComeMessage() {

	}


	public String serializable() {
		return new  StringBuilder().append(SPLIT_CHAR).
				append(errorMsg).append(SPLIT_CHAR).
				append(wheelAngle).append(SPLIT_CHAR).
				append(carSpeed).
				toString();
	}
	
	public void deserializable(String message) {
		try {
			errorMsg = message.split(SPLIT_CHAR)[0];
			wheelAngle = Double.parseDouble(message.split(SPLIT_CHAR)[1]);
			carSpeed = Double.parseDouble(message.split(SPLIT_CHAR)[2]);
		} catch (NumberFormatException | PatternSyntaxException e) {
			new Message(WRONG_MESSAGE_FORMAT, MessageType.ERROR);
		}
	}

	public static InComeMessage deserializableNewMsg(String message){
		InComeMessage msg = new InComeMessage();
		msg.deserializable(message);
		return  msg;
	}

	public void save() {
		if (this.errorMsg != null && this.errorMsg.length() > 0) {
			new Message(this.errorMsg,MessageType.ERROR);
		} else {
			CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
			//carState.setWheelAngle(this.wheelAngle);
			//carState.setCarSpeed(this.carSpeed);  //coment after change speed politic to speedgear
			ContextBuilder.getContext().getCarInfoController().changeText();
			//log
			new Message("New Input from car {speed:" + this.carSpeed + ",wheelAngle:" + this.wheelAngle + " }", MessageType.INFO);
		}
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
