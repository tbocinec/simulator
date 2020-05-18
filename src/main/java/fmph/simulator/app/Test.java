package fmph.simulator.app;

import fmph.simulator.visualization.component.Function;

public class Test {

    public static void main(String[] args) throws InterruptedException {

        Double alfa = Math.toRadians(90);
        Double beta = Math.toRadians(95); //old -180
        double titl = Function.angle_difference(beta, alfa);
        System.out.println(titl + "   in angle"+Math.toDegrees(titl));


    }


}
