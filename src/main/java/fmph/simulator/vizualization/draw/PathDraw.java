package fmph.simulator.vizualization.draw;

import fmph.simulator.map.MapSchema;
import fmph.simulator.map.Segment;
import fmph.simulator.vizualization.component.Function;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PathDraw extends AbstractDraw {
	
	

public PathDraw(GraphicsContext graphicContext, MapSchema mapSchema) {
		super(graphicContext,mapSchema);		
	}


public void paint() {
	double segmentCount = mapSchema.getSegments().size();
	for (int i = 0; i < segmentCount; i++)
        draw_segment_path(mapSchema.getSegments().get(i));
}
	
	
public void draw_segment_path(Segment segment) {
		
		String shape = segment.getSegmentShape().getType();
		
		gc.setLineDashes(10);
		gc.setLineWidth(3);
		gc.setStroke(Color.DARKGREY);
		if(shape.compareTo("line") == 0 ) {
			
			gc.strokeLine(Function.tx(segment.getStartPose().getX()),
					Function.ty(segment.getStartPose().getY()),
					Function.tx(segment.getEndPose().getX()), 
					Function.ty(segment.getEndPose().getY()));			
		}
	

	    else if (shape.compareTo("arc") == 0){
	    	boolean cw = segment.getSegmentShape().getAttributes().getAngle() > 0;		
			gc.beginPath();
			gc.arc(Function.tx(segment.getSegmentShape().getAttributes().getRotCx()),
					Function.ty(segment.getSegmentShape().getAttributes().getRotCy()),
					Function.td(segment.getSegmentShape().getAttributes().getRotRadius()),
					Function.td(segment.getSegmentShape().getAttributes().getRotRadius()),
					Math.toDegrees(Function.narc(segment.getStartPose().getHeading(), cw)),
					 Math.toDegrees(segment.getSegmentShape().getAttributes().getAngle()));
			gc.stroke();
			
			
	  
	    }

		
		
		
	}
	
		
	

}
