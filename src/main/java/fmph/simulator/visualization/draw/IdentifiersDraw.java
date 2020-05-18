package fmph.simulator.visualization.draw;

import fmph.simulator.map.LaserTag;
import fmph.simulator.map.MapSchema;
import fmph.simulator.map.Segment;
import fmph.simulator.visualization.component.Function;
import fmph.simulator.visualization.component.IdLocation;
import fmph.simulator.visualization.component.VisualizeConfig;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class IdentifiersDraw extends AbstractDraw {
	
	

public IdentifiersDraw(GraphicsContext graphicContext, MapSchema mapSchema) {
		super(graphicContext,mapSchema);		
	}


public void paint() {
	double segmentCount = mapSchema.getSegments().size();
	for (int i = 0; i < segmentCount; i++)
        draw_identifiers(mapSchema.getSegments().get(i));
 
}
	
	
public void draw_identifiers(Segment segment) {
	int i;
    java.util.List<LaserTag> tags = segment.getLaserTags();
    
    IdLocation idloc = new IdLocation(segment);

    for (i = 0; i < tags.size(); i++)
    {
        if (null != tags.get(i).getExternal() && tags.get(i).getExternal()) continue;
        
        if (null != tags.get(i).getDisabled() && tags.get(i).getDisabled()) continue;
        
        
        //poss {pos, pos1, pos2, pos3, pos4, gamma};
        idloc.identifier_location(i, segment);
        double gamma = idloc.getGamma();   
        
        drawIDMark(idloc);
        drawIDText(idloc,gamma,tags.get(i).getType());
		
        //ctx.fillStyle="#008F00";
        
       
    }
}

public  void drawIDMark(IdLocation idloc) {
	gc.beginPath();  
    gc.setFill(Color.LIGHTGRAY);
    gc.setStroke(Color.RED);
    gc.setLineWidth(1.5);
    
    gc.moveTo(Function.tx(idloc.getPos1()[0]), Function.ty(idloc.getPos1()[1]));
    gc.lineTo(Function.tx(idloc.getPos2()[0]), Function.ty(idloc.getPos2()[1]));
    gc.lineTo(Function.tx(idloc.getPos3()[0]), Function.ty(idloc.getPos3()[1]));
    gc.lineTo(Function.tx(idloc.getPos4()[0]), Function.ty(idloc.getPos4()[1]));
    gc.lineTo(Function.tx(idloc.getPos1()[0]), Function.ty(idloc.getPos1()[1]));
    gc.fill();
    gc.stroke();
  
	
}

public void drawIDText(IdLocation idloc,Double gamma,String tag) {
        //tuhol sa pocita le nraz
     gamma = Math.toDegrees(gamma);
    gc.translate(Function.tx(idloc.getPos()[0]), Function.ty(idloc.getPos()[1]));
    gc.rotate(gamma);

     gc.setLineDashes(0);
     gc.setLineWidth(1);

    double q =  VisualizeConfig.GetConfig().getQ() ;


    if(q <= 180){
        gc.setFill(Color.BLACK);
       gc.setTextAlign(TextAlignment.LEFT);
       gc.setFont(new Font(12));
       gc.fillText(tag, 4, 7);
    }
    else {
        gc.setFill(Color.GREEN);
        gc.setTextAlign(TextAlignment.CENTER);
        double fontSize = 0.045 * q;
        gc.setFont(new Font(fontSize));
        gc.fillText(tag, 0, 0); //4, -7);
    }



    
     //translate and rotate back
     gc.rotate(-gamma);
     gc.translate(-Function.tx(idloc.getPos()[0]), -Function.ty(idloc.getPos()[1]));
     gc.stroke();
}
	
		
	

}
