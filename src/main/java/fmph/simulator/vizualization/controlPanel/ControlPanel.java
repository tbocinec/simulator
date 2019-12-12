package fmph.simulator.vizualization.controlPanel;

import app.Context;
import fmph.simulator.CarModel;
import fmph.simulator.Config;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class ControlPanel extends FlowPane {

    Text carSpeed;
    Text carPosX;
    Text carPosY;
    Text carAngle;
    Text carwheelAngle;


    public ControlPanel(){
        this.setVgap(10);
        this.setHgap(30);
        this.setMaxWidth(100);
        initText();
    }

    public void initText(){
        carSpeed = new Text("car speed : ");
        carPosX = new Text("car pos x : ");
        carPosY = new Text("car pos y : ");
        carAngle = new Text("car angle :");
        carwheelAngle = new Text("Car wheel angle :");

        this.getChildren().add(carSpeed);
        this.getChildren().add(carPosX);
        this.getChildren().add(carPosY);
        this.getChildren().add(carAngle);
        this.getChildren().add(carwheelAngle);



        Label carwheelAngleLabel = new Label("Car wheel angle : ");

        Button change = new Button("save");

        TextField carwheelAngleField = new TextField();

        this.getChildren().add(carwheelAngleLabel);
        this.getChildren().add(carwheelAngleField);
        this.getChildren().add(change);


        change.setOnAction(event -> {
           Context.getContext().getCarModel().setWheelAngle(
                   Double.parseDouble(carwheelAngleField.getText()));
        });
    }

    public void changeText() {

        Platform.runLater(new Runnable() {
            public void run() {
                CarModel carmodel = Context.getContext().getCarModel();
                carSpeed.setText("car speed : " + carmodel.getCarSpeed());
                carPosX.setText("car pos x : " + carmodel.getPosX());
                carPosY.setText("car pos y : " + carmodel.getPosY());
                carAngle.setText("car angle : " + carmodel.getCarAngle());
                carwheelAngle.setText("car wheel angle : " + carmodel.getWheelAngle());
            }});
    }


}
