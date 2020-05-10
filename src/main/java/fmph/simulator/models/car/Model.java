package fmph.simulator.models.car;

public interface Model {
    // sirka luca [m] original 0.3
    double getBEAMWIDTH();

    //vzdialenost medzi prednou a zadnou napravou [m]
    double getDISTANCEBETWEENAXLES();

    //vzdialenost medzi prednou napravou a lucom [m] real 0.3
    double getDISTANCEBETWEENAXLEANDBEAM();

    double getMaxWheelAngle();

    String getName();

    Double getSpeed(int n);
}
