package fmph.simulator.Math;

public class Geometric {

    static  public  double distance(Point2d a,Point2d b){
        return  Math.hypot(b.getX() - a.getX(), b.getY()-a.getY());
    }

    static  public  double distance(Circle a,Point2d b){
        return  Math.abs(Math.sqrt(Math.pow(b.getX()-a.getCenter().getX(),2)+Math.pow(b.getY()-a.getCenter().getY(),2))-a.getRadius());
    }



}
