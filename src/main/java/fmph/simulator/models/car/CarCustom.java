package fmph.simulator.models.car;

import fmph.simulator.app.context.ContextBuilder;
import org.apache.commons.configuration2.PropertiesConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarCustom implements Model {
    private  double BEAMWIDTH;
    private  double DISTANCEBETWEENAXLES ;
    private  double DISTANCEBETWEENAXLEANDBEAM ;
    private   double MAXWHEELANGLE;
    private List<Double> speeds;
    private  String name= 	"Car from config";

    public CarCustom(){
        PropertiesConfiguration config = ContextBuilder.getContext().config;
        this.BEAMWIDTH = config.getDouble("CarCustom.BEAMWIDTH");
        this.DISTANCEBETWEENAXLES = config.getDouble("CarCustom.DISTANCEBETWEENAXLES");
        this.DISTANCEBETWEENAXLEANDBEAM = config.getDouble("CarCustom.DISTANCEBETWEENAXLEANDBEAM");
        this.MAXWHEELANGLE =  config.getDouble("CarCustom.MAXWHEELANGLE");
        speeds = Stream.of(config.getString("CarCustom.speeds") .split(",")).map(e -> Double.valueOf(e)).
                collect(Collectors.toList());


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
