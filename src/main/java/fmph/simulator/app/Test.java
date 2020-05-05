package fmph.simulator.app;

import fmph.simulator.Math.Circle;
import fmph.simulator.Math.Geometric;
import fmph.simulator.Math.Point2d;
import fmph.simulator.vizualization.component.Function;

import java.util.LinkedList;
import java.util.ListIterator;

public class Test {

    public static void main(String[] args) {

        Point2d p = new Point2d(1,1);
        Circle c1 = new Circle(new Point2d(0,0),2);
        System.out.println(Geometric.distance(c1,p));

    }


}
