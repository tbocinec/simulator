package fmph.simulator.app;

import fmph.simulator.vizualization.component.Function;

import java.util.LinkedList;
import java.util.ListIterator;

public class Test {

    public static void main(String[] args) {

        Double alfa = Math.toRadians(-90);
        Double beta = Math.toRadians(270);

        Double res= Function.angle_difference(alfa,beta);
        System.out.println(Math.toDegrees(res));


        LinkedList<Integer>  stack = new LinkedList<>();
        stack.add(1);
        stack.add(2);
        stack.add(3);
        ListIterator<Integer> it = stack.listIterator(stack.size());
        while (it.hasPrevious())
            System.out.println(it.previous());
    }


}
