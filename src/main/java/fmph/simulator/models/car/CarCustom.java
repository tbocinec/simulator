package fmph.simulator.models.car;

import fmph.simulator.app.context.ContextBuilder;
import org.apache.commons.configuration2.PropertiesConfiguration;

public class CarCustom implements Model {
    private  double BEAMWIDTH;
    private  double DISTANCEBETWEENAXLES ;
    private  double DISTANCEBETWEENAXLEANDBEAM ;
    private   double MAXWHEELANGLE;
    private  String name= 	"Car from config";

    public CarCustom(){
        PropertiesConfiguration config = ContextBuilder.getContext().config;
        this.BEAMWIDTH = config.getDouble("CarCustom.BEAMWIDTH");
        this.DISTANCEBETWEENAXLES = config.getDouble("CarCustom.DISTANCEBETWEENAXLES");
        this.DISTANCEBETWEENAXLEANDBEAM = config.getDouble("CarCustom.DISTANCEBETWEENAXLEANDBEAM");
        this.MAXWHEELANGLE =  config.getDouble("CarCustom.MAXWHEELANGLE");

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
}
