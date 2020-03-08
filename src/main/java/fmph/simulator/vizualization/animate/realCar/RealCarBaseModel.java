package fmph.simulator.vizualization.animate.realCar;

import app.Context;
import fmph.simulator.CarModel;
import fmph.simulator.map.MapSchema;
import fmph.simulator.vizualization.animate.DrawableCar;
import fmph.simulator.vizualization.component.VisualizeConfig;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RealCarBaseModel implements DrawableCar {
    CarModel carModel;
    VisualizeConfig visualizeConfig = VisualizeConfig.GetConfig();

    double width = 40;
    double height = 65;

    public RealCarBaseModel(MapSchema map) {
        carModel = Context.getContext().getCarModel();
        double x = map.getSegments().get(0).getStartPose().getX();
        double y = map.getSegments().get(0).getStartPose().getY();


       // Context.getContext().getCarModel().setPosX(x);
        //Context.getContext().getCarModel().setPosY(y);

    }


    @Override
    public void animateCar(GraphicsContext gc) {


       gc.translate(carModel.getPosX(), carModel.getPosY());
       gc.rotate(carModel.getCarAngle()  + 180);

        draw_car_shape(gc);

        gc.rotate(-carModel.getCarAngle() -180);
        gc.translate(-carModel.getPosX(), -carModel.getPosY());

    }

    private void draw_car_shape(GraphicsContext gc) {
        Paint preColor = gc.getFill();
        gc.setFill(Color.BLUE);
        gc.fillRect(-width/2,-height,width,height);
        gc.strokeLine(-width/2,CarModel.DISTANCEBETWEENAXLEANDBEAM,width/2,CarModel.DISTANCEBETWEENAXLEANDBEAM);
        gc.strokeOval(-1,-1,2,2);
        gc.setFill(preColor);
    }

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
