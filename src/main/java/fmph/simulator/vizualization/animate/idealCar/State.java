package fmph.simulator.vizualization.animate.idealCar;

import fmph.simulator.map.Segment;

//actual State of animation
public class State {
	
	private static State state;
	
	Segment anim_seg;
	Segment next_anim_seg;
	double anim_pos = 0;
	double anim_tacho;
	double tacho_counter;
	double anim_shorten_delta;
	double old_anim_shorten_delta;
	double current_anim_offset;
	
	double anim_seg_len = 1;
	
	private State() {}
	
	public static State getState() {
		if(state == null) {
			state = new State();
		}
		return state;
	}

	
	
	
	public double getAnim_shorten_delta() {
		return anim_shorten_delta;
	}

	public void setAnim_shorten_delta(double anim_shorten_delta) {
		this.anim_shorten_delta = anim_shorten_delta;
	}

	public double getOld_anim_shorten_delta() {
		return old_anim_shorten_delta;
	}

	public void setOld_anim_shorten_delta(double old_anim_shorten_delta) {
		this.old_anim_shorten_delta = old_anim_shorten_delta;
	}

	public double getCurrent_anim_offset() {
		return current_anim_offset;
	}

	public void setCurrent_anim_offset(double current_anim_offset) {
		this.current_anim_offset = current_anim_offset;
	}

	public Segment getNext_anim_seg() {
		return next_anim_seg;
	}

	public void setNext_anim_seg(Segment next_anim_seg) {
		this.next_anim_seg = next_anim_seg;
	}

	public double getAnim_seg_len() {
		return anim_seg_len;
	}

	public void setAnim_seg_len(double anim_seg_len) {
		this.anim_seg_len = anim_seg_len;
	}

	public double getAnim_tacho() {
		return anim_tacho;
	}

	public void setAnim_tacho(double anim_tacho) {
		this.anim_tacho = anim_tacho;
	}

	public double getTacho_counter() {
		return tacho_counter;
	}

	public void setTacho_counter(double tacho_counter) {
		this.tacho_counter = tacho_counter;
	}

	public Segment getAnim_seg() {
		return anim_seg;
	}
	public void setAnim_seg(Segment anim_seg) {
		this.anim_seg = anim_seg;
	}
	public double getAnim_pos() {
		return anim_pos;
	}
	public void setAnim_pos(double anim_pos) {
		this.anim_pos = anim_pos;
	}
	
	
}
