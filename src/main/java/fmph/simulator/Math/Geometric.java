package fmph.simulator.Math;

public class Geometric {

    static  public  double distance(Point2d a,Point2d b){
        return  Math.hypot(b.getX() - a.getX(), b.getY()-a.getY());
    }

    static  public  double distance(Circle a,Point2d b){
        return  Math.abs(Math.sqrt(Math.pow(b.getX()-a.getCenter().getX(),2)+Math.pow(b.getY()-a.getCenter().getY(),2))-a.getRadius());
    }
    static public double distance(Line line, Point2d pX)
    {
        Point2d A = line.getPointA();
        Point2d B = line.getPointB();

        double px=B.getX()-A.getX();
        double py=B.getY()-A.getY();
        double temp=(px*px)+(py*py);
        double u=((pX.getX() - A.getX()) * px + (pX.getY() - A.getY()) * py) / (temp);
        if(u>1){
            u=1;
        }
        else if(u<0){
            u=0;
        }
        double x = A.getX() + u * px;
        double y = A.getY() + u * py;

        double dx = x - pX.getX();
        double dy = y - pX.getY();
        double dist = Math.sqrt(dx*dx + dy*dy);
        return dist;

    }

    static public double distance2(Line line, Point2d pX)
    {
        Point2d pA = line.getPointA();
        Point2d pB = line.getPointB();

        double A = pX.getX() - pA.getX();
        double B = pX.getY() - pA.getY();;
        double C = pB.getX() - pA.getX();
        double D = pB.getY() - pA.getY();;;

        double dot = A * C + B * D;
        double len_sq = C * C + D * D;
        double param = -1;
        if (len_sq != 0) //in case of 0 length line
            param = dot / len_sq;

        double xx, yy;

        if (param < 0) {
            xx = pA.getX();
            yy = pA.getY();
        }
        else if (param > 1) {
            xx = pB.getX();
            yy = pB.getY();
        }
        else {
            xx = pA.getX() + param * C;
            yy = pA.getY() + param * D;
        }

        double dx = pX.getX() - xx;
        double dy = pX.getY() - yy;
        return Math.sqrt(dx * dx + dy * dy);
    }




}
