package fmph.simulator.visualization.component;

import java.io.File;

public class VisualizeConfig {

	double minimum_margin = 50; //150 // [pixels]
	public double identifier_width = 0.05; // [m]
	public double identifier_length = 0.08; // [m]
	double marginx = minimum_margin;
	double marginy = minimum_margin;



	double translate_x = 0;
	double translate_y = 0;
	double[] maprange = {-2.85, 2.85, -1.85, 1.85};
	double q = 100;

	double cw = 1500;
	double ch = 1500;

	double anim_speed = 0.1; //0.01;



	double car_length =  0.35 ; //70; // cm
	double car_width = 0.162;; // cm

	// todo
	double car_image_length = 60;
	double car_image_width = 128;
	double car_length_scale_factor = 0.6216216216216216;
	double car_width_scale_factor = 0.6798986486486486;
	double car_front_wheels_offset = 12;
	String car_img_url = "target" + File.separator + "data" + File.separator + "red_car.png";

	private VisualizeConfig() {


	}

	private static VisualizeConfig visualizeConfig;

	public static VisualizeConfig GetConfig() {
		if (visualizeConfig == null) {
			visualizeConfig = new VisualizeConfig();
		}
		return visualizeConfig;
	}

	public double getCar_length() {
		return car_length;
	}

	public void setCar_length(double car_length) {
		this.car_length = car_length;
	}

	public double getCar_width() {
		return car_width;
	}

	public void setCar_width(double car_width) {
		this.car_width = car_width;
	}

	public double getAnim_speed() {
		return anim_speed;
	}

	public void setAnim_speed(double anim_speed) {
		this.anim_speed = anim_speed;
	}

	public double getCar_front_wheels_offset() {
		return car_front_wheels_offset;
	}

	public void setCar_front_wheels_offset(double car_front_wheels_offset) {
		this.car_front_wheels_offset = car_front_wheels_offset;
	}

	public double getCar_image_length() {
		return car_image_length;
	}

	public void setCar_image_length(double car_image_length) {
		this.car_image_length = car_image_length;
	}

	public double getCar_image_width() {
		return car_image_width;
	}

	public void setCar_image_width(double car_image_width) {
		this.car_image_width = car_image_width;
	}

	public double getCar_length_scale_factor() {
		return car_length_scale_factor;
	}

	public void setCar_length_scale_factor(double car_length_scale_factor) {
		this.car_length_scale_factor = car_length_scale_factor;
	}

	public double getCar_width_scale_factor() {
		return car_width_scale_factor;
	}

	public void setCar_width_scale_factor(double car_width_scale_factor) {
		this.car_width_scale_factor = car_width_scale_factor;
	}

	public String getCar_img_url() {
		return car_img_url;
	}

	public void setCar_img_url(String car_img_url) {
		this.car_img_url = car_img_url;
	}

	public double getMinimum_margin() {
		return minimum_margin;
	}

	public void setMinimum_margin(double minimum_margin) {
		this.minimum_margin = minimum_margin;
	}

	public double getIdentifier_width() {
		return identifier_width;
	}

	public void setIdentifier_width(double identifier_width) {
		identifier_width = identifier_width;
	}

	public double getIdentifier_length() {
		return identifier_length;
	}

	public static void setIdentifier_length(double identifier_length) {
		identifier_length = identifier_length;
	}

	public double getMarginx() {
		return marginx;
	}

	public void setMarginx(double marginx) {
		this.marginx = marginx;
	}

	public double getMarginy() {
		return marginy;
	}

	public void setMarginy(double marginy) {
		this.marginy = marginy;
	}

	public double getTranslate_x() {
		return translate_x;
	}

	public void setTranslate_x(double translate_x) {
		this.translate_x = translate_x;
	}

	public double getTranslate_y() {
		return translate_y;
	}

	public void setTranslate_y(double translate_y) {
		this.translate_y = translate_y;
	}

	public double[] getMaprange() {
		return maprange;
	}

	public void setMaprange(double[] maprange) {
		this.maprange = maprange;
	}

	public double getQ() {
		return q;
	}

	public void setQ(double q) {
		this.q = q;
	}

	public double getCw() {
		return cw;
	}

	public void setCw(double cw) {
		this.cw = cw;
	}

	public double getCh() {
		return ch;
	}

	public void setCh(double ch) {
		this.ch = ch;
	}

}
