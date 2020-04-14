package app;

import fmph.simulator.vizualization.component.Function;

public class Test {

    public static void main(String[] args) {

        Double alfa = Math.toRadians(-90);
        Double beta = Math.toRadians(270);

        Double res= Function.angle_difference(alfa,beta);
        System.out.println(Math.toDegrees(res));
    }


}
