package fmph.simulator.vizualization.draw;

import fmph.simulator.map.MapSchema;
import fmph.simulator.map.Segment;
import fmph.simulator.vizualization.component.Function;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RoadDraw extends AbstractDraw {
	
	

public RoadDraw(GraphicsContext graphicContext, MapSchema mapSchema) {
		super(graphicContext,mapSchema);		
	}


public void paint() {
	for(Segment segment : mapSchema.getSegments()) {
		paintSegmentRoad(segment);
	}
}
	
	
public void paintSegmentRoad(Segment segment) {	
		
		gc.setFill(Color.BLACK);
		
		
		String shape = segment.getSegmentShape().getType();
		double w = segment.getSegmentWidth() /2;
		if(shape.compareTo("line") == 0 ) {
			//https://kempelen.dai.fmph.uniba.sk/ats/map.php
			
			double h1 = segment.getStartPose().getHeading() - Math.PI /2;
			double h2 = segment.getStartPose().getHeading() + Math.PI /2;
			double[] posS = {segment.getStartPose().getX(),segment.getStartPose().getY()};
			
			double[] pos1 = Function.translate(posS,h1,w);
			double[] pos4 = Function.translate(posS,h2,w);
			
			double[] posE =  {segment.getEndPose().getX(),segment.getEndPose().getY()};
		    double[] pos2 = Function.translate(posE, h1, w);
		    double[] pos3 = Function.translate(posE, h2, w);
		    gc.beginPath();
		    gc.moveTo(Function.tx(pos1[0]), Function.ty(pos1[1]));
	        gc.lineTo(Function.tx(pos2[0]), Function.ty(pos2[1]));
	        gc.lineTo(Function.tx(pos3[0]), Function.ty(pos3[1]));
	        gc.lineTo(Function.tx(pos4[0]), Function.ty(pos4[1]));
	        gc.lineTo(Function.tx(pos1[0]), Function.ty(pos1[1]));
	        //gc.fill();
	        gc.stroke();
	        
		  
	      
	     
		}  
		if(shape.compareTo("arc") == 0 ) {		
			
			boolean cw = segment.getSegmentShape().getAttributes().getAngle() > 0;		
			gc.beginPath();
		
			gc.arc(Function.tx(segment.getSegmentShape().getAttributes().getRotCx()),
					Function.ty(segment.getSegmentShape().getAttributes().getRotCy()),
					Function.td(segment.getSegmentShape().getAttributes().getRotRadius() + w),
					Function.td(segment.getSegmentShape().getAttributes().getRotRadius() + w),
					Math.toDegrees(Function.narc(segment.getStartPose().getHeading(), cw)),
					 Math.toDegrees(segment.getSegmentShape().getAttributes().getAngle()));			
			gc.stroke();
			
			gc.beginPath();
			gc.arc(Function.tx(segment.getSegmentShape().getAttributes().getRotCx()),
					Function.ty(segment.getSegmentShape().getAttributes().getRotCy()),
					Function.td(segment.getSegmentShape().getAttributes().getRotRadius() - w),
					Function.td(segment.getSegmentShape().getAttributes().getRotRadius() - w),
					Math.toDegrees(Function.narc(segment.getStartPose().getHeading(), cw)),
					Math.toDegrees(segment.getSegmentShape().getAttributes().getAngle()));
			
			gc.stroke();
			
			gc.save();
			
		}  
		         
	
		
	}

}
