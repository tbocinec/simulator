package fmph.simulator.vizualization.animate.realCar;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.map.MapSchema;
import fmph.simulator.models.CarModel;
import fmph.simulator.models.CarState;
import fmph.simulator.models.car.Model;
import fmph.simulator.vizualization.animate.DrawableCar;
import fmph.simulator.vizualization.component.Function;
import fmph.simulator.vizualization.component.VisualizeConfig;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.apache.commons.configuration2.PropertiesConfiguration;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RealCarBaseModel implements DrawableCar {
    CarModel carModel;
    VisualizeConfig visualizeConfig = VisualizeConfig.GetConfig();
    PropertiesConfiguration config = ContextBuilder.getContext().config;

    double width = 0.162;
    double height = 0.25;

    public RealCarBaseModel(MapSchema map) {
        carModel = ContextBuilder.getContext().getCarModel();
        double x = map.getSegments().get(0).getStartPose().getX();
        double y = map.getSegments().get(0).getStartPose().getY();
        // Context.getContext().getCarModel().setPosX(x);
        //Context.getContext().getCarModel().setPosY(y);
        width =  visualizeConfig.getCar_width();
        height =  visualizeConfig.getCar_length();

    }


    @Override
    public void animateCar(GraphicsContext gc) {
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        gc.translate(Function.tx(carState.getPosX()), Function.ty(carState.getPosY()));
        gc.rotate(-carState.getCarAngle()+180);
        draw_car_shape(gc);
        gc.rotate(+carState.getCarAngle()-180);
        gc.translate(- Function.tx(carState.getPosX()), -Function.ty(carState.getPosY()));

        if(config.getBoolean("view.beamPoint")){
            drawBeamPoint(gc);
        }

        if(config.getBoolean("view.carTrajectory")){
            drawCarTrajectory(gc);
        }
        if(config.getBoolean("view.carBackPoint")){
            drawCarBackPoint(gc);
        }
        gc.stroke();
        gc.fill();

    }

    private void drawBeamPoint(GraphicsContext gc){
        gc.setFill(Color.web(config.getString("color.car.beamPoint")));
        gc.fillOval(Function.tx(carModel.getCenterOfTurn().getX())-2,Function.ty(carModel.getCenterOfTurn().getY())-2,4,4);
        gc.fillOval(Function.tx(carModel.getLeftBeen().getX())-2,Function.ty(carModel.getLeftBeen().getY())-2,4,4);
    }

    private void drawCarBackPoint(GraphicsContext gc) {
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        gc.setFill(Color.web(config.getString("color.car.backPoint")));
        double x = Function.tx(carState.getPosXBack());
        double y = Function.ty(carState.getPosYBack());
        gc.fillOval(x-3,y-3,3,3);
    }



    private void drawCarTrajectory(GraphicsContext gc) {
        double centerX = carModel.getCenterOfTurn().getX();
        double centerY = carModel.getCenterOfTurn().getY();
        double radius_front_wheel = Function.td(carModel.getFront_wheel_radius());
        double radius_back_wheel = Function.td(carModel.getBack_wheel_radius());

        gc.setFill(Color.web(config.getString("color.car.center.radius")));

        gc.fillOval(Function.tx(centerX)-4,
                Function.ty(centerY)-4,
                4,   4);

        gc.setStroke(Color.web(config.getString("color.car.trajectory.front")));

        gc.strokeOval(Function.tx(centerX)-radius_front_wheel,
                Function.ty(centerY)-radius_front_wheel,
                radius_front_wheel*2,
                radius_front_wheel*2);

        gc.setStroke(Color.web(config.getString("color.car.trajectory.back")));
        gc.strokeOval(Function.tx(centerX)-radius_back_wheel,
                Function.ty(centerY)-radius_back_wheel,
                radius_back_wheel*2,
                radius_back_wheel*2);
    }

    private void draw_car_shape(GraphicsContext gc) {
        Model model = ContextBuilder.getContext().getCarModel().getCarManagment().getActual();
        Paint preColor = gc.getFill();
        gc.setFill(Color.BLUE);
        Double widthT = Function.td(width);
        Double heightT = Function.td(height);
        Double distanceBetweenAxelAndBeen = Function.td(model.getDISTANCEBETWEENAXLEANDBEAM());
        Double withRay = Function.td(model.getBEAMWIDTH());

        gc.fillRect(-widthT/2,-heightT,widthT,heightT);
        gc.setFill(Color.RED);
        gc.strokeLine(-withRay/2,distanceBetweenAxelAndBeen,withRay/2,distanceBetweenAxelAndBeen);
        gc.setFill(Color.GREEN);
        gc.strokeOval(-2,-2,4,4);
        gc.setFill(preColor);
    }

    //obsoleted
    private void draw_image(GraphicsContext gc) {
        InputStream is = null;
        try {
            is = new BufferedInputStream(
                    new FileInputStream("data/red_car.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Image car = new Image(is);
        gc.drawImage(car,0,0,80,50);
    }
}
