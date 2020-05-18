package fmph.simulator.visualization.component;

import fmph.simulator.map.Segment;


public class SegmentPose {
	double gamma;
	double[] pos;
	
	public SegmentPose(Segment seg,double d) {
		
		//return [pos[0], pos[1], gamma];
		if(seg.getSegmentShape().getType().compareTo("line")==0) {
			double[] pompos = {seg.getStartPose().getX(),seg.getStartPose().getY()};
			pos = Function.translate(pompos, seg.getStartPose().getHeading(), d);
			gamma  = seg.getStartPose().getHeading();
		}
		
		else if(seg.getSegmentShape().getType().compareTo("arc")==0)
	    {
	        double r = seg.getSegmentShape().getAttributes().getRotRadius(); 
	        double alpha = d / r;
	        double beta;
	        if (seg.getSegmentShape().getAttributes().getAngle() > 0)
	        {
	            gamma = seg.getStartPose().getHeading() + alpha;
	            beta = gamma - Math.PI / 2;
	        }
	        else 
	        {
	            gamma = seg.getStartPose().getHeading() - alpha; 
	            beta = gamma + Math.PI / 2;
	        }
	        double[] pompos = {seg.getSegmentShape().getAttributes().getRotCx(),
					seg.getSegmentShape().getAttributes().getRotCy()};
	        pos = Function.translate(pompos, beta, r);
	    }
	}
	
	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public double[] getPos() {
		return pos;
	}

	public void setPos(double[] pos) {
		this.pos = pos;
	}
	
}
