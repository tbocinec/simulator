package fmph.simulator.models.car;

public class Car1to8 implements Model {
    private final double BEAMWIDTH = 0.206;
    private final double DISTANCEBETWEENAXLES = 0.305;//35.5;
    private final  double DISTANCEBETWEENAXLEANDBEAM = 0.206; //20;// vzdialenost medzi prednou napravou a lucom [m] real 0.3
    private final  double MAXWHEELANGLE = 10; //todo change
    private final String name= 	"1:8 (actual)";

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
}
