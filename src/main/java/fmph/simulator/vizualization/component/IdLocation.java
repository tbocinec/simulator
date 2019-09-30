	package fmph.simulator.vizualization.component;

import fmph.simulator.map.LaserTag;
import fmph.simulator.map.Segment;
import fmph.simulator.vizualization.MyCanvas;

public class IdLocation {
	
	
	private String shape;
	private boolean lntype;
	private boolean arctype;
	private Double h1;
	private double[] centre;
	private double[] spos;
	private Double r;
	
	private double[] pos;
	private double[] pos1;
	private double[] pos2;
	private double[] pos3;
	private double[] pos4;
    private double gamma;


	public IdLocation(Segment s) {
		super();
		shape = s.getSegmentShape().getType();
		lntype = (shape.compareTo("line")==0);
		arctype = (shape.compareTo("arc")==0);
		double[] sposConstructor = { s.getStartPose().getY(), s.getStartPose().getY() };
		spos = sposConstructor;
		
		h1 = s.getStartPose().getHeading();
		if(arctype) {
			double[] centreConstructor = {s.getSegmentShape().getAttributes().getRotCx(), s.getSegmentShape().getAttributes().getRotCy()};
			centre = centreConstructor;
			r = s.getSegmentShape().getAttributes().getRotRadius();
			
		}
	// return [lntype, arctype, spos, h1, centre, r];
	}
	
	public void identifier_location(int i,Segment  seg)
	{
	    LaserTag tag = seg.getLaserTags().get(i);
	    Double loc = tag.getRelativeLocation();
	    
	    if (lntype)
	    {
	        pos = MyCanvas.translate(spos, h1, loc);
	        gamma = h1;
	    }
	    else if (arctype)
	    {
	        double alpha = loc / r;
	        double beta;
	        if (seg.getSegmentShape().getAttributes().getAngle() > 0)
	        {
	            gamma = h1 + alpha;
	            beta = gamma - Math.PI / 2;
	        }
	        else
	        {
	            gamma = h1 - alpha;
	            beta = gamma + Math.PI / 2;
	        }
	        pos = MyCanvas.translate(centre, beta, r);
	    }
	    pos1 = MyCanvas.translate(pos, gamma - Math.PI / 2, MyCanvas.identifier_width  / 2);
	    pos1 = MyCanvas.translate(pos1, gamma, - MyCanvas.identifier_length / 2);
	    pos2 = MyCanvas.translate(pos1, gamma, MyCanvas.identifier_length);
	    pos4 = MyCanvas.translate(pos1, gamma + Math.PI / 2, MyCanvas.identifier_width );
	    pos3 = MyCanvas.translate(pos4, gamma, MyCanvas.identifier_length);

//	    return {pos, pos1, pos2, pos3, pos4, gamma};
	}
	
	
	
	public double[] getPos() {
		return pos;
	}

	public void setPos(double[] pos) {
		this.pos = pos;
	}

	public double[] getPos1() {
		return pos1;
	}

	public void setPos1(double[] pos1) {
		this.pos1 = pos1;
	}

	public double[] getPos2() {
		return pos2;
	}

	public void setPos2(double[] pos2) {
		this.pos2 = pos2;
	}

	public double[] getPos3() {
		return pos3;
	}

	public void setPos3(double[] pos3) {
		this.pos3 = pos3;
	}

	public double[] getPos4() {
		return pos4;
	}

	public void setPos4(double[] pos4) {
		this.pos4 = pos4;
	}

	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public void setCentre(double[] centre) {
		this.centre = centre;
	}

	public void setSpos(double[] spos) {
		this.spos = spos;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public boolean isLntype() {
		return lntype;
	}

	public void setLntype(boolean lntype) {
		this.lntype = lntype;
	}

	public boolean isArctype() {
		return arctype;
	}

	public void setArctype(boolean arctype) {
		this.arctype = arctype;
	}

	public Double getH1() {
		return h1;
	}

	public void setH1(Double h1) {
		this.h1 = h1;
	}

	public double[] getCentre() {
		return centre;
	}


	public double[] getSpos() {
		return spos;
	}



	public Double getR() {
		return r;
	}

	public void setR(Double r) {
		this.r = r;
	}



}
