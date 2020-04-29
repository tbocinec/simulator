package fmph.simulator.vizualization.view;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Contextable;
import fmph.simulator.models.CarModel;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CarInfoController extends VBox implements Contextable {

    Text carSpeed;
    Text carPosX;
    Text carPosY;
    Text carAngle;
    Text carwheelAngle;


    public CarInfoController(){
        registryToContext();
        initText();
    }

    public void initText(){
        carSpeed = new Text("Car speed : ");
        carPosX = new Text("Car X : ");
        carPosY = new Text("Car Y : ");
        carAngle = new Text("Car angle :");
        carwheelAngle = new Text("Car wheel angle : ");

        this.getChildren().add(carSpeed);
        this.getChildren().add(carPosX);
        this.getChildren().add(carPosY);
        this.getChildren().add(carAngle);
        this.getChildren().add(carwheelAngle);


    }

    public void changeText() {
        Platform.runLater(new Runnable() {
            public void run() {
                CarModel carmodel = ContextBuilder.getContext().getCarModel();
                carSpeed.setText( String.format("Car speed : %f", carmodel.getCarSpeed()));
                carPosX.setText(String.format("Car posX : %f m", carmodel.getPosX()));
                carPosY.setText(String.format("Car pos y : %f m", carmodel.getPosY()));
                carAngle.setText(String.format("Car angle : %f deg",  carmodel.getCarAngle()));
                carwheelAngle.setText(String.format("Car wheel angle : %f deg", carmodel.getWheelAngle()));
            }});
    }


    @Override
    public void registryToContext() {
        context.setCarInfoController(this);
    }
}
