package fmph.simulator.vizualization;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import fmph.simulator.Map;
import fmph.simulator.map.LaserTag;
import fmph.simulator.map.Segment;
import fmph.simulator.map.SegmentShape;
import fmph.simulator.vizualization.component.IdLocation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.transform.Affine;

public class MyCanvas extends Canvas{
	int w=900;int h=900;	
	
	
	
	double  minimum_margin = 50;     // [pixels]
	public static double   identifier_width = 0.05;  // [m]
	public static double identifier_length = 0.08;  // [m]
	double marginx = minimum_margin;
	double marginy = minimum_margin;
	double translate_x = 0;
	double translate_y = 0;
	double[] maprange = {-2.85, 2.85, -1.85, 1.85};
	double q = 100;
	
	double cw = w;
	double ch  = h;
	
	
	public MyCanvas(){
		setWidth(w);
		setHeight(h);			
	}
	public void paint(){			
		GraphicsContext gc = getGraphicsContext2D();
		//clear
		System.out.println("paint");
		gc.setFill(Color.BLUE);
		//gc.setLineWidth(100);
	
		paintMap();
		
	}
	
	public void paintMap() {
		GraphicsContext gc = getGraphicsContext2D();
		Map map = new Map();
		map.loadMap();
		System.out.println(map.getMapName());
		
		
		double segmentCount = map.getMap().getSegments().size();
		
		for(Segment segment : map.getMap().getSegments()) {
			paintSegmentRoad(segment);
		}
		
		for (int i = 0; i < segmentCount; i++)
	        draw_segment_gap_road(map.getMap().getSegments().get(i),
	        		map.getMap().getSegments().get((int) ((i + 1) % segmentCount)));
		
		 for (int i = 0; i < segmentCount; i++)
		        draw_segment_path(map.getMap().getSegments().get(i));
		 
		 for (int i = 0; i < segmentCount; i++)
		        draw_identifiers(map.getMap().getSegments().get(i));
		 
		map.getMap();
	}
	
	
	public void draw_identifiers(Segment segment) {
		int i;
	    java.util.List<LaserTag> tags = segment.getLaserTags();
	    
	     IdLocation idloc = new IdLocation(segment);

	    for (i = 0; i < tags.size(); i++)
	    {
	        if (tags.get(i).getExternal()) continue;
	        
	        if (tags.get(i).getDisabled()) continue;
	        
	        
	        //poss {pos, pos1, pos2, pos3, pos4, gamma};
	        idloc.identifier_location(i, segment);
	        double gamma = idloc.getGamma();

	        ctx.beginPath();
	        ctx.moveTo(tx(poss[1][0]), ty(poss[1][1]));
	        ctx.lineTo(tx(poss[2][0]), ty(poss[2][1]));
	        ctx.lineTo(tx(poss[3][0]), ty(poss[3][1]));
	        ctx.lineTo(tx(poss[4][0]), ty(poss[4][1]));
	        ctx.lineTo(tx(poss[1][0]), ty(poss[1][1]));
	        index_of_pid = recognized_pids.indexOf(tags[i].unique_id);
	        if (index_of_pid >= 0)
	        {
	            if (index_of_pid == recognized_pids.length - 1)
	            {
	                if (recognized_correct[index_of_pid] == 1) ctx.fillStyle="#808000";
	                else ctx.fillStyle="#FFFF00";
	            }
	            else if (recognized_correct[index_of_pid] == 1) ctx.fillStyle="#40FF40";
	            else ctx.fillStyle="#FF4040";
	        } 
	        else ctx.fillStyle="#FFFFFF";
	        ctx.fill();
	        ctx.stroke();

	        ctx.fillStyle="#008F00";
	        ctx.translate(tx(poss[0][0]), ty(poss[0][1]));
	        ctx.rotate(gamma);
	        ctx.fillText(tags[i].type, 0, 0); //4, -7);
	        ctx.rotate(-gamma);
	        ctx.translate(-tx(poss[0][0]), -ty(poss[0][1]));
	        ctx.fillStyle="#FFFFFF";
	    }
	}
	public void draw_segment_path(Segment segment) {
		
		String shape = segment.getSegmentShape().getType();
		GraphicsContext gc = getGraphicsContext2D();
		if(shape.compareTo("line") == 0 ) {
			gc.strokeLine(tx(segment.getStartPose().getX()), ty(segment.getStartPose().getY()),
					tx(segment.getStartPose().getX()), ty(segment.getStartPose().getY()));
			
		}
	

	    else if (shape.compareTo("arc") == 0){
	    	boolean cw = segment.getSegmentShape().getAttributes().getAngle() > 0;		
			gc.beginPath();
			gc.arc(tx(segment.getSegmentShape().getAttributes().getRotCx()),
					ty(segment.getSegmentShape().getAttributes().getRotCy()),
					td(segment.getSegmentShape().getAttributes().getRotRadius()),
					td(segment.getSegmentShape().getAttributes().getRotRadius()),
					Math.toDegrees(narc(segment.getStartPose().getHeading(), cw)),
					 Math.toDegrees(segment.getSegmentShape().getAttributes().getAngle()));
			gc.stroke();
			
			
	  
	    }

		
		
		
	}
	
	public void draw_segment_gap_road(Segment segment1,Segment segment2) {
		if (Math.abs(segment1.getEndPose().getHeading() - segment2.getStartPose().getHeading()) > 0.1 / 180.0 * Math.PI)
	    {
	        boolean cw = turn_is_clockwise( segment1.getEndPose().getHeading(), segment2.getStartPose().getHeading());
			GraphicsContext gc = getGraphicsContext2D();
			gc.beginPath();
			
			gc.arc(tx(segment1.getEndPose().getX()),
					ty(segment1.getEndPose().getY()),
					td(Math.max(segment1.getSegmentWidth(), segment2.getSegmentWidth()) / 2),
					td(Math.max(segment1.getSegmentWidth(), segment2.getSegmentWidth()) / 2),
					Math.toDegrees(narc(segment1.getStartPose().getHeading(), cw)),
					 Math.toDegrees(segment1.getSegmentShape().getAttributes().getAngle()));
			gc.stroke();
			
	    }
		
	}
	
	public boolean turn_is_clockwise(double alpha, double  beta)
	{
	    return (angle_difference(alpha, beta) > 0);
	}
	
	public double  angle_difference(double alpha, double beta)
	{
		double d1 = angle_distance(alpha, beta, 1);
		double d2 = angle_distance(alpha, beta, -1);
	   if (d1 < d2) return d1;
	   return -d2; 
	}
	public double angle_distance(double alpha,double beta, int direction)
	{
		double dist = 0;
	    if (direction > 0) dist = beta - alpha;
	    else dist = alpha - beta;
	    while (dist < 0) dist += 2 * Math.PI;
	    while (dist > 2 * Math.PI) dist -= 2 * Math.PI;
	    return dist;
	}
	
	
	
	public void paintSegmentRoad(Segment segment) {
		GraphicsContext gc = getGraphicsContext2D();
		String shape = segment.getSegmentShape().getType();
		double w = segment.getSegmentWidth() /2;
		if(shape.compareTo("line") == 0 ) {
			//https://kempelen.dai.fmph.uniba.sk/ats/map.php
			
			double h1 = segment.getStartPose().getHeading() - Math.PI /2;
			double h2 = segment.getStartPose().getHeading() + Math.PI /2;
			double[] posS = {segment.getStartPose().getX(),segment.getStartPose().getY()};
			
			double[] pos1 = translate(posS,h1,w);
			double[] pos4 = translate(posS,h2,w);
			
			double[] posE =  {segment.getEndPose().getX(),segment.getEndPose().getY()};
		    double[] pos2 = translate(posE, h1, w);
		    double[] pos3 = translate(posE, h2, w);
			gc.strokeLine(tx(pos1[0]), ty(pos1[1]),tx(pos2[0]), ty(pos2[1]));
			gc.strokeLine(tx(pos2[0]), ty(pos2[1]),tx(pos3[0]), ty(pos3[1]));
			gc.strokeLine(tx(pos3[0]), ty(pos3[1]),tx(pos4[0]), ty(pos4[1]));
			gc.strokeLine(tx(pos4[0]), ty(pos4[1]),tx(pos1[0]), ty(pos1[1]));
			System.out.println("abc " + tx(pos1[0]) +" "+ ty(pos1[1]) +" "+ tx(pos2[0])+" "+  ty(pos2[1]));
		      
	      
	     
		}  
		if(shape.compareTo("arc") == 0 ) {		
			
			boolean cw = segment.getSegmentShape().getAttributes().getAngle() > 0;		
			gc.beginPath();
			gc.arc(tx(segment.getSegmentShape().getAttributes().getRotCx()),
					ty(segment.getSegmentShape().getAttributes().getRotCy()),
					td(segment.getSegmentShape().getAttributes().getRotRadius() + w),
					td(segment.getSegmentShape().getAttributes().getRotRadius() + w),
					Math.toDegrees(narc(segment.getStartPose().getHeading(), cw)),
					 Math.toDegrees(segment.getSegmentShape().getAttributes().getAngle()));
			gc.stroke();
			
			gc.beginPath();
			gc.arc(tx(segment.getSegmentShape().getAttributes().getRotCx()),
					ty(segment.getSegmentShape().getAttributes().getRotCy()),
					td(segment.getSegmentShape().getAttributes().getRotRadius() - w),
					td(segment.getSegmentShape().getAttributes().getRotRadius() - w),
					Math.toDegrees(narc(segment.getStartPose().getHeading(), cw)),
					Math.toDegrees(segment.getSegmentShape().getAttributes().getAngle()));
			
			gc.stroke();
			gc.save();
			
		}  
		    
		     
	
		
	}
	
	public static double[] translate(double[] pos, double alpha,double d)
	{
		double res[] = {pos[0] + d * Math.sin(alpha), pos[1] + d * Math.cos(alpha)};
	    return   res;
	}
	


	
	public double tx(double x)
	{
	    return marginx + (x - maprange[0] + translate_x) * q;
	}

	public double  ty(double y)
	{
	    return ch - (marginy + (y - maprange[2] + translate_y) * q);
	}
	
	public double td(double d)
	{
	    return d * q;
	}
	
	public double narc(double alpha, boolean cw)
	{
	    if (cw) alpha += Math.PI;
	    while (alpha < 0) alpha += 2 * Math.PI;
	    while (alpha > 2 * Math.PI) alpha -= 2 * Math.PI;
	    return alpha;
	}

}