package fmph.simulator.vizualization;

import app.Context;
import fmph.simulator.Map;
import fmph.simulator.map.LaserTag;
import fmph.simulator.map.Segment;
import fmph.simulator.vizualization.animate.DrawableCar;
import fmph.simulator.vizualization.animate.idealCar.IdealCar;
import fmph.simulator.vizualization.animate.idealCar.State;
import fmph.simulator.vizualization.animate.realCar.RealCarBaseModel;
import fmph.simulator.vizualization.component.Function;
import fmph.simulator.vizualization.component.VisualizeConfig;
import fmph.simulator.vizualization.console.Message;
import fmph.simulator.vizualization.console.MessageType;
import fmph.simulator.vizualization.draw.*;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
		map = Context.getContext().getMap();

		//System.out.println(map.getMapName());
		state.setAnim_seg(map.getMap().getSegments().get(0)); //default value

		idealCar = new IdealCar(map.getMap());
		realCarModel = new RealCarBaseModel(map.getMap());


		this.setOnMouseClicked(event -> {
			printClickInfo(event);
		});


		
	}

	void resize(Stage primaryStage) {
		this.w = (int) (primaryStage.getWidth()*0.85);
		this.h = (int) (primaryStage.getHeight()*0.85);

		VisualizeConfig.GetConfig().setCw(w);
		VisualizeConfig.GetConfig().setCh(h);

		this.setWidth(w);
		this.setHeight(h);
	}

	public void printClickInfo(MouseEvent event){
		double x = event.getX();
		double y = event.getY();

		Double dx =  Math.round(Function.txinv(x)*100) / 100.0;
		Double dy =  Math.round(Function.tyinv(y)*100) / 100.0;

		new Message("X,Y = ["+x+";"+y+"]  distance X,Y from center  [" + dx  + "m ;"+ dy + "m ]", MessageType.INFO);

		for (Segment segment : Context.getContext().getMap().getMap().getSegments()) {
			int i = 1;
			for (LaserTag laserTag : segment.getLaserTags()) {
				i += 1;
				double xl = laserTag.getX();
				double yl = laserTag.getY();
				double distance = Math.sqrt(Math.pow(x - xl, 2) + Math.pow(y - yl, 2));

				if (Math.abs(distance) < 10) {
					new Message("click on segment " +
							segment.getSegmentId() + " laser= " + laserTag.getType() + " in distance-" + distance, MessageType.INFO);

				}

			}
		}


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
		new CenterDraw(gc,map.getMap()).paint();
	}


}