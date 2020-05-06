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


        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        ScheduledFuture<?> future = executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

            }
        }, 5, 10, TimeUnit.SECONDS);

        future.cancel(true);



    }


}
