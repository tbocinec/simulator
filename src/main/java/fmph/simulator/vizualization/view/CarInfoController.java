package fmph.simulator.vizualization.view;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Contextable;
import fmph.simulator.models.CarModel;
import fmph.simulator.models.CarState;
import javafx.application.Platform;
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
        carSpeed = new Text("Car gear : ");
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
                CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
                carSpeed.setText( String.format("Car speed : %d", carState.getGearSpeed()));
                carPosX.setText(String.format("Car posX : %f m",carState.getPos().getX()));
                carPosY.setText(String.format("Car pos y : %f m", carState.getPos().getY()));
                carAngle.setText(String.format("Car angle : %f deg",  carState.getCarAngle()));
                carwheelAngle.setText(String.format("Car wheel angle : %f deg", carState.getWheelAngle()));
            }});
    }


    @Override
    public void registryToContext() {
        context.setCarInfoController(this);
    }
}
