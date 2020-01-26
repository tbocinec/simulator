package com;

import app.Context;
import fmph.simulator.CarModel;
import fmph.simulator.vizualization.console.MessageType;

public class Message {

	final String splitChat = ";";
	final String msgIdentificator = "simMsg";
	final int countParams = 10;

	int tag_id;
	int orientation;
	double current_speed_mm_per_scanline;
	double tilt;
	double center_x;
	double center_y;
	double error;
	double confidence;
	double timeUsed;
	double time_stamp;


	public String  serialize(){
		StringBuilder msg = new StringBuilder();
		msg.append(msgIdentificator).append(splitChat);
		msg.append(tag_id).append(splitChat);
		msg.append(orientation).append(splitChat);
		msg.append(current_speed_mm_per_scanline).append(splitChat);
		msg.append(tilt).append(splitChat);
		msg.append(center_x).append(splitChat);
		msg.append(center_y).append(splitChat);
		msg.append(error).append(splitChat);
		msg.append(confidence).append(splitChat);
		msg.append(timeUsed).append(splitChat);
		msg.append(time_stamp).append(splitChat);
		return msg.toString();
	}

	public boolean isValid(String msg){
			return  msg.startsWith(msgIdentificator) & msg.split(splitChat).length == countParams +1;
	}



	public String getSplitChat() {
		return splitChat;
	}

	public String getMsgIdentificator() {
		return msgIdentificator;
	}

	public int getTag_id() {
		return tag_id;
	}

	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public double getCurrent_speed_mm_per_scanline() {
		return current_speed_mm_per_scanline;
	}

	public void setCurrent_speed_mm_per_scanline(double current_speed_mm_per_scanline) {
		this.current_speed_mm_per_scanline = current_speed_mm_per_scanline;
	}

	public double getTilt() {
		return tilt;
	}

	public void setTilt(double tilt) {
		this.tilt = tilt;
	}

	public double getCenter_x() {
		return center_x;
	}

	public void setCenter_x(double center_x) {
		this.center_x = center_x;
	}

	public double getCenter_y() {
		return center_y;
	}

	public void setCenter_y(double center_y) {
		this.center_y = center_y;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public double getTimeUsed() {
		return timeUsed;
	}

	public void setTimeUsed(double timeUsed) {
		this.timeUsed = timeUsed;
	}

	public double getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(double time_stamp) {
		this.time_stamp = time_stamp;
	}
}
