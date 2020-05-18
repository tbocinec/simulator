package fmph.simulator.visualization.draw;

import fmph.simulator.map.MapSchema;
import fmph.simulator.map.Segment;
import fmph.simulator.visualization.component.Function;
import fmph.simulator.visualization.component.SegmentPose;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class LabelDraw extends AbstractDraw {
	
	

public LabelDraw(GraphicsContext graphicContext, MapSchema mapSchema) {
		super(graphicContext,mapSchema);		
	}


public void paint() {
	double segmentCount = mapSchema.getSegments().size();
	 for (int i = 0; i < segmentCount; i++)
	        draw_segment_label(mapSchema.getSegments().get(i));
	  

 
}
	
	
public void draw_segment_label(Segment segment) {
	double w = segment.getSegmentWidth()/2;
	//return [pos[0], pos[1], gamma];
	 SegmentPose pose = new SegmentPose(segment, segment.getLength() / 2);
	 double pos[] = pose.getPos();
	 double h1 = pose.getGamma();
	 String shape = segment.getSegmentShape().getType();
	 double h2 = h1;
	 h2 -= Math.PI / 2;
	  double[] pos1 = Function.translate(pos, h2, w * 0.6);
	    gc.translate(Function.tx(pos1[0]), Function.ty(pos1[1]));
	    gc.setFont(new Font(13));
	    gc.setTextAlign(TextAlignment.CENTER);
	    gc.setFill(Color.BLACK);
	    gc.fillText( "["+segment.getSegmentId()+"]", 0, 0);
	    gc.translate(-Function.tx(pos1[0]), -Function.ty(pos1[1]));
	    gc.stroke();
	 
}
	
		
	

}
