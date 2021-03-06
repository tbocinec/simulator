package fmph.simulator.visualization.animate.realCar;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.map.MapSchema;
import fmph.simulator.car.CarManagement;
import fmph.simulator.car.CarState;
import fmph.simulator.car.car.Model;
import fmph.simulator.visualization.animate.DrawableCar;
import fmph.simulator.visualization.component.Function;
import fmph.simulator.visualization.component.VisualizeConfig;
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
    CarManagement carManagement;
    VisualizeConfig visualizeConfig = VisualizeConfig.GetConfig();
    PropertiesConfiguration config = ContextBuilder.getContext().config;

    double width = 0.162;
    double height = 0.30;

    public RealCarBaseModel(MapSchema map) {
        carManagement = ContextBuilder.getContext().getCarManagement();
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
        gc.translate(Function.tx(carState.getPos().getX()), Function.ty(carState.getPos().getY()));
        gc.rotate(-carState.getCarAngle()+180);
        draw_car_shape(gc);
        gc.rotate(+carState.getCarAngle()-180);
        gc.translate(- Function.tx(carState.getPos().getX()), -Function.ty(carState.getPos().getY()));

        if(config.getBoolean("view.beam")){
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
        gc.fillOval(Function.tx(carManagement.getRightBeen().getX())-3,Function.ty(carManagement.getRightBeen().getY())-3,6,6);
        gc.fillOval(Function.tx(carManagement.getLeftBeen().getX())-3,Function.ty(carManagement.getLeftBeen().getY())-3,6,6);
        gc.fillOval(Function.tx(carManagement.getCenterBeen().getX())-3,Function.ty(carManagement.getCenterBeen().getY())-3,6,6);
    }

    private void drawCarBackPoint(GraphicsContext gc) {
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        gc.setFill(Color.web(config.getString("color.car.backPoint")));
        double x = Function.tx(carState.getPosXBack());
        double y = Function.ty(carState.getPosYBack());
        gc.fillOval(x-3,y-3,3,3);
    }



    private void drawCarTrajectory(GraphicsContext gc) {
        double centerX = carManagement.getCenterOfTurn().getX();
        double centerY = carManagement.getCenterOfTurn().getY();
        double radius_front_wheel = Function.td(carManagement.getFront_wheel_radius());
        double radius_back_wheel = Function.td(carManagement.getBack_wheel_radius());

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
        Model model = ContextBuilder.getContext().getCarManagement().getModelManagment().getActual();
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
