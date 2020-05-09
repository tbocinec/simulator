package fmph.simulator.Math;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class Line implements Serializable {
    Point2d pointA;
    Point2d pointB;

    public Line(Point2d pointA, Point2d pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public Point2d getPointA() {
        return pointA;
    }

    public void setPointA(Point2d pointA) {
        this.pointA = pointA;
    }

    public Point2d getPointB() {
        return pointB;
    }

    public void setPointB(Point2d pointB) {
        this.pointB = pointB;
    }
}
