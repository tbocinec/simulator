package fmph.simulator.vizualization.animate.realCar;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.models.CarModel;
import fmph.simulator.map.MapSchema;
import fmph.simulator.vizualization.animate.DrawableCar;
import fmph.simulator.vizualization.component.Function;
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

    double width = 0.162;
    double height = 0.25;

    public RealCarBaseModel(MapSchema map) {
        carModel = ContextBuilder.getContext().getCarModel();
        double x = map.getSegments().get(0).getStartPose().getX();
        double y = map.getSegments().get(0).getStartPose().getY();


        // Context.getContext().getCarModel().setPosX(x);
        //Context.getContext().getCarModel().setPosY(y);

    }


    @Override
    public void animateCar(GraphicsContext gc) {

        double transform = 180;//aby bol sever hore
        gc.translate(Function.tx(carModel.getCarState().getPosX()), Function.ty(carModel.getCarState().getPosY()));
        gc.rotate(-carModel.getCarState().getCarAngle()  + transform);

        draw_car_shape(gc);
        gc.rotate(+carModel.getCarState().getCarAngle() -transform);
        gc.translate(- Function.tx(carModel.getCarState().getPosX()), -Function.ty(carModel.getCarState().getPosY()));
        gc.setFill(Color.PURPLE);
        gc.fillOval(Function.tx(carModel.Fx),Function.ty(carModel.Fy),5,5);
        gc.fillOval(Function.tx(carModel.Ex),Function.ty(carModel.Ey),5,5);
        gc.setFill(Color.BLACK);
        gc.stroke();
        gc.fill();


    }

    private void draw_car_shape(GraphicsContext gc) {
        Paint preColor = gc.getFill();
        gc.setFill(Color.BLUE);
        Double widthT = Function.td(width);
        Double heightT = Function.td(height);
        Double distanceBetweenAxelAndBeen = Function.td(CarModel.DISTANCEBETWEENAXLEANDBEAM);
        Double withRay = Function.td(CarModel.BEAMWIDTH);

        gc.fillRect(-widthT/2,-heightT,widthT,heightT);
        gc.setFill(Color.RED);
        gc.strokeLine(-withRay/2,distanceBetweenAxelAndBeen,withRay/2,distanceBetweenAxelAndBeen);
        gc.setFill(Color.GREEN);
        gc.strokeOval(-2,-2,4,4);
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
