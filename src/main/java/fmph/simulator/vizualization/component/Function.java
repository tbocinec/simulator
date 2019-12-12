package fmph.simulator.vizualization.component;

import java.util.List;

import fmph.simulator.map.MapSchema;
import fmph.simulator.map.Segment;

public class Function {
	
	public static Config config = Config.GetConfig();
	
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
	    System.out.println("nenasloSegment");
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
		
	    return config.getMarginx() + (x - config.getMaprange()[0] + config.getTranslate_x()) * config.getQ();
	}

	public static double  ty(double y)
	{
	    return config.getCh() - (config.getMarginy() + (y - config.getMaprange()[2] +  config.getTranslate_y()) * config.getQ());
	}
	
	public static double td(double d)
	{
	    return d * config.getQ();
	}
	
	public static double narc(double alpha, boolean cw)
	{
	    if (cw) alpha += Math.PI;
	    while (alpha < 0) alpha += 2 * Math.PI;
	    while (alpha > 2 * Math.PI) alpha -= 2 * Math.PI;
	    return alpha;
	}

}
