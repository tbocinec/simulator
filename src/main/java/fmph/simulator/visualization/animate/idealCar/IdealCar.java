package fmph.simulator.visualization.animate.idealCar;

import fmph.simulator.map.MapSchema;
import fmph.simulator.map.Segment;
import fmph.simulator.visualization.animate.DrawableCar;
import fmph.simulator.visualization.component.Function;
import fmph.simulator.visualization.component.SegmentPose;
import fmph.simulator.visualization.component.VisualizeConfig;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.*;



public class IdealCar implements DrawableCar {

	Boolean show_car = true;
	VisualizeConfig visualizeConfig = VisualizeConfig.GetConfig();
	State state = State.getState();

	double width = visualizeConfig.getCar_image_width() * visualizeConfig.getCar_width_scale_factor();
	double height = visualizeConfig.getCar_image_length() * visualizeConfig.getCar_length_scale_factor();
	double offset = visualizeConfig.getCar_front_wheels_offset() * visualizeConfig.getCar_width_scale_factor();

	MapSchema mapSchema;

	public IdealCar(MapSchema mapSchema) {
		this.mapSchema = mapSchema;
		state.setNext_anim_seg(Function.find_segment(mapSchema, "0001")); // todo nestaci dat vzdy prvy z pola ?
		select_next_anim_seg();
	}

	public void animateCar(GraphicsContext gc) {

		SegmentPose pose = new SegmentPose(state.getAnim_seg(), state.getAnim_pos());

		gc.translate(Function.tx(pose.getPos()[0]), Function.ty(pose.getPos()[1]));
		gc.rotate(Math.toDegrees(pose.getGamma() + Math.PI / 2));


		draw_car_shape(gc);
		//draw_image(gc);

		
		gc.rotate(Math.toDegrees(-pose.getGamma() - Math.PI / 2));
		gc.translate(-Function.tx(pose.getPos()[0]), -Function.ty(pose.getPos()[1]));

		state.setAnim_pos(state.getAnim_pos() + visualizeConfig.getAnim_speed());
		state.setAnim_tacho(state.getAnim_tacho() + visualizeConfig.getAnim_speed());

		state.setTacho_counter(state.getTacho_counter() + 1);
		if (state.getTacho_counter() == 25) {
			state.setTacho_counter(0);
		}

		if (state.getAnim_pos() > state.getAnim_seg_len()) {
			select_next_anim_seg();
		}

	}

	private void draw_car_shape(GraphicsContext gc) {
		gc.fillRect( -offset, -height / 2, 60, 40);
		gc.strokeLine(-15,20,-15,-20);
	}

	private void draw_image(GraphicsContext gc) {
		InputStream is = null;
		try {
			is = new BufferedInputStream(
					new FileInputStream("data/red_car.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Image car = new Image(is);
		gc.drawImage(car,0,0,80,50);
	}

	private void select_next_anim_seg() {

		state.setAnim_seg(state.getNext_anim_seg());

		Segment tmpSegment = Function.find_segment(mapSchema, 
				state.getAnim_seg().getNextSegments()
				.get((int) Math.floor(Math.random() * state.getAnim_seg().getNextSegments().size())));
		state.setNext_anim_seg(tmpSegment);

		state.setAnim_pos(0);

		state.setAnim_seg_len(state.getAnim_seg().getLength());

		if (state.getAnim_seg().getSegmentShape().getType().compareTo("line") == 0) {
			state.setCurrent_anim_offset(state.getOld_anim_shorten_delta());
		} else {
			state.setCurrent_anim_offset(0);
		}
		state.setOld_anim_shorten_delta(state.getAnim_shorten_delta());

		if (state.getNext_anim_seg().getSegmentShape().getType().compareTo("arc") == 0) {
			double radius = state.getNext_anim_seg().getSegmentShape().getAttributes().getRotRadius();
			state.setAnim_shorten_delta(radius * (1 - Math.cos(Math.asin(visualizeConfig.getCar_length() / 200.0 / radius))));
		} else {
			state.setAnim_shorten_delta(0);
		}
	}

	private Image getImgCar() {
		////gc.drawImage(getImgCar(), -offset, -height / 2);
		File file = new File(visualizeConfig.getCar_img_url());

		InputStream targetStream = null;
		try {
			targetStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		return new Image(targetStream, this.width, this.height, false, false);
	}
}
