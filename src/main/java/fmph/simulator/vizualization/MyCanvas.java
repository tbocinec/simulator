package fmph.simulator.vizualization;

import fmph.simulator.Map;
import fmph.simulator.vizualization.animate.DrawableCar;
import fmph.simulator.vizualization.animate.idealCar.IdealCar;
import fmph.simulator.vizualization.animate.idealCar.State;
import fmph.simulator.vizualization.animate.realCar.RealCarBaseModel;
import fmph.simulator.vizualization.draw.IdentifiersDraw;
import fmph.simulator.vizualization.draw.LabelDraw;
import fmph.simulator.vizualization.draw.PathDraw;
import fmph.simulator.vizualization.draw.RoadDraw;
import fmph.simulator.vizualization.draw.RoadGapDraw;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class MyCanvas extends Canvas{
	int w=700;int h=600;
	GraphicsContext gc;
	Map map;
	State state = State.getState();
	IdealCar idealCar;
	DrawableCar realCarModel;
	
	public MyCanvas(){
		setWidth(w);
		setHeight(h);	
		gc = getGraphicsContext2D();
		map = new Map();
		map.loadMap();
		System.out.println(map.getMapName());
		state.setAnim_seg(map.getMap().getSegments().get(0)); //default value

		idealCar = new IdealCar(map.getMap());
		realCarModel = new RealCarBaseModel(map.getMap());
		
	}
	public void paint(){
		Platform.runLater(new Runnable() {
			public void run(){
				clearPain(gc);
				paintMap();
				paintCars();
			}});

	}
	
	public void clearPain(GraphicsContext gc2) {
		gc.clearRect(0, 0, w, h);	
	}
	
	public void paintCars() {
		idealCar.animateCar(gc);
		realCarModel.animateCar(gc);

	}
	
	public void paintMap() {		
		new RoadDraw(gc,map.getMap()).paint();
		new PathDraw(gc, map.getMap()).paint();
		new IdentifiersDraw(gc, map.getMap()).paint();
		new LabelDraw(gc, map.getMap()).paint();
		new RoadGapDraw(gc,map.getMap()).paint();
	}

}