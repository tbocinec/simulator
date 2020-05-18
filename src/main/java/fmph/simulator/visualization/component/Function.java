package fmph.simulator.visualization.component;

import fmph.simulator.map.MapSchema;
import fmph.simulator.map.Segment;

import java.util.List;

public class Function {
	
	public static VisualizeConfig visualizeConfig = VisualizeConfig.GetConfig();
	
	public static double[] translate(double[] pos, double alpha,double d)
	{
		double res[] = {pos[0] + d * Math.sin(alpha), pos[1] + d * Math.cos(alpha)};
	    return   res;
	}
	
	
	public static Segment  find_segment(MapSchema mapSchema, String segId)
	{
	    
	    List<Segment> ss = mapSchema.getSegments();
	    for (int i = 0; i < ss.size(); i++)
	        if (ss.get(i).getSegmentId().compareTo(segId)==0)
	            return ss.get(i);
	    throw new RuntimeException("Non exists segment");
	   
	}
	
	
	public static boolean turn_is_clockwise(double alpha, double  beta)
	{
	    return (angle_difference(alpha, beta) > 0);
	}
	
	public static double  angle_difference(double alpha, double beta)
	{
		double d1 = angle_distance(alpha, beta, 1);
		double d2 = angle_distance(alpha, beta, -1);
	   if (d1 < d2) return d1;
	   return -d2; 
	}
	public static  double angle_distance(double alpha,double beta, int direction)
	{
		double dist = 0;
	    if (direction > 0) dist = beta - alpha;
	    else dist = alpha - beta;
	    while (dist < 0) dist += 2 * Math.PI;
	    while (dist > 2 * Math.PI) dist -= 2 * Math.PI;
	    return dist;
	}


	
	public static double tx(double x)
	{
		
	    return visualizeConfig.getMarginx() + (x - visualizeConfig.getMaprange()[0] + visualizeConfig.getTranslate_x()) * visualizeConfig.getQ();
	}

	public static double  ty(double y)
	{
	    return visualizeConfig.getCh() - (visualizeConfig.getMarginy() + (y - visualizeConfig.getMaprange()[2] +  visualizeConfig.getTranslate_y()) * visualizeConfig.getQ());
	}

	public static double txinv(double x)
	{
		return (x - visualizeConfig.getMarginx()) / visualizeConfig.getQ() + visualizeConfig.getMaprange()[0] - visualizeConfig.getTranslate_x();
	}

	public static double tyinv(double y)
	{
		return ((visualizeConfig.getCh() - y) - visualizeConfig.getMarginy()) / visualizeConfig.getQ() + visualizeConfig.getMaprange()[2] - visualizeConfig.getTranslate_y();
	}



	public static double td(double d)
	{
	    return d * visualizeConfig.getQ();
	}
	
	public static double narc(double alpha, boolean cw)
	{
	    if (cw) alpha += Math.PI;
	    while (alpha < 0) alpha += 2 * Math.PI;
	    while (alpha > 2 * Math.PI) alpha -= 2 * Math.PI;
	    return alpha;
	}


    public static void rescale_shapes() {
		visualizeConfig.setCar_width(visualizeConfig.getCar_width()*visualizeConfig.getCar_width_scale_factor());
		visualizeConfig.setCar_length(visualizeConfig.getCar_length()*visualizeConfig.getCar_length_scale_factor());

    }

	public static double TurnAngle(double carAngle) {
		//pretoze vypocet auta sa pocita v uhloh proti smere hodiniek a zvysok v smere
		return (carAngle*-1)%360;
	}
}
