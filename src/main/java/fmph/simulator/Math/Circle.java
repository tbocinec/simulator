package fmph.simulator.Math;

import java.io.Serializable;

public class Circle implements Serializable {
    Point2d center;
    private double radius;

    public Circle(Point2d center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point2d getCenter() {
        return center;
    }

    public void setCenter(Point2d center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
