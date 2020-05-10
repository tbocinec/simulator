package fmph.simulator.models.car;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Car1to8 implements Model {
    private final double BEAMWIDTH = 0.206;
    private final double DISTANCEBETWEENAXLES = 0.305;//35.5;
    private final  double DISTANCEBETWEENAXLEANDBEAM = 0.206; //20;// vzdialenost medzi prednou napravou a lucom [m] real 0.3
    private final  double MAXWHEELANGLE = 10; //todo change
    private final String name= 	"1:8 (actual)";
    private List<Double> speeds;

    public Car1to8() {
        this.speeds = Stream.of(0.0,0.05,0.1,0.15,0.2,0.25,0.3,0.35,0.4,0.45,0.5).map(e -> (Double)(e))
                .collect(Collectors.toList());
    }



    public double getBEAMWIDTH() {
        return BEAMWIDTH;
    }

    public double getDISTANCEBETWEENAXLES() {
        return DISTANCEBETWEENAXLES;
    }

    public double getDISTANCEBETWEENAXLEANDBEAM() {
        return DISTANCEBETWEENAXLEANDBEAM;
    }

    @Override
    public double getMaxWheelAngle() {
        return MAXWHEELANGLE;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public Double getSpeed(int n) {
        if(speeds != null && n >= 0 && speeds.size() >= n){
            return speeds.get(n);
        }
        return  null;
    }
}
