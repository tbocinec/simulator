package fmph.simulator.app;

import fmph.simulator.Math.Circle;
import fmph.simulator.Math.Geometric;
import fmph.simulator.Math.Point2d;
import fmph.simulator.vizualization.component.Function;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.*;

public class Test {

    public static void main(String[] args) throws InterruptedException {

        Double alfa = Math.toRadians(90);
        Double beta = Math.toRadians(95); //old -180
        double titl = Function.angle_difference(beta, alfa);
        System.out.println(titl + "   in angle"+Math.toDegrees(titl));


    }


}
