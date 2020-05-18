package fmph.simulator.visualization.draw;

import fmph.simulator.map.MapSchema;
import fmph.simulator.map.Segment;
import fmph.simulator.visualization.component.Function;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class RoadGapDraw extends AbstractDraw {
	
	

public RoadGapDraw(GraphicsContext graphicContext, MapSchema mapSchema) {
		super(graphicContext,mapSchema);		
	}


public void paint() {
	double segmentCount = mapSchema.getSegments().size();

	for (int i = 0; i < segmentCount; i++)
		  draw_segment_gap_road(mapSchema.getSegments().get(i),
		  mapSchema.getSegments().get((int) ((i + 1) % segmentCount)));

			
 
}
	
	
public void draw_segment_gap_road(Segment segment1,Segment segment2) {
	//todo
	if (Math.abs(segment1.getEndPose().getHeading() - segment2.getStartPose().getHeading()) > 0.1 / 180.0 * Math.PI)
    {
		
		// tu moze byt problem		
        boolean cw = Function.turn_is_clockwise( segment1.getEndPose().getHeading(), segment2.getStartPose().getHeading());
	
		gc.beginPath();
		gc.setFill(Color.ORANGE);
		gc.arc(Function.tx(segment1.getEndPose().getX()),
				Function.ty(segment1.getEndPose().getY()),
				Function.td(Math.max(segment1.getSegmentWidth(), segment2.getSegmentWidth()) / 2),
				Function.td(Math.max(segment1.getSegmentWidth(), segment2.getSegmentWidth()) / 2),
				Math.toDegrees(Function.narc(segment1.getStartPose().getHeading(), cw)),
				Math.toDegrees(segment1.getSegmentShape().getAttributes().getAngle()));
		gc.stroke();
		gc.fill();
		
    }
	
}
	
		
	

}
