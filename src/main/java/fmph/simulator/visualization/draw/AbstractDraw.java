package fmph.simulator.visualization.draw;

import fmph.simulator.map.MapSchema;
import javafx.scene.canvas.GraphicsContext;

public abstract class AbstractDraw {
	
	GraphicsContext gc;
	MapSchema mapSchema;
	
	
	public 	AbstractDraw(GraphicsContext graphicContext, MapSchema mapSchema) {
		this.gc = graphicContext;
		this.mapSchema = mapSchema;
		
	}
	
	public abstract void paint();

}
