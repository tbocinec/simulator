package fmph.simulator.visualization.view;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Contextable;
import fmph.simulator.visualization.view.uxcomponent.MenuLabelComponent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class AppPanelController extends VBox implements Contextable {
    Button pauseRun;
    Button resetButton;
    Slider slideTimeShift;

    CheckBox showIdealCar;
    CheckBox waitAfterRecognization;

    public AppPanelController(){
        registryToContext();
        init();
    }

    private void init(){
        pauseRun = new Button("Stop/Run");
        pauseRun.setOnAction(event -> {
            ContextBuilder.getContext().getVisualize().pause();
        });

        resetButton = new Button("ResetCar");
        resetButton.setOnAction(event -> {
            ContextBuilder.getContext().getCarManagement().initStartValue();
        });

        Label timeshiftLabel  = new Label("SpeedCare rate");
        slideTimeShift = new Slider();
        slideTimeShift.setMin(0);
        slideTimeShift.setMax(18);
        slideTimeShift.setValue(1);
        slideTimeShift.setShowTickLabels(true);
        slideTimeShift.setShowTickMarks(true);
        slideTimeShift.setMajorTickUnit(0.2);
        slideTimeShift.setBlockIncrement(0.6);
        slideTimeShift.valueProperty().addListener((observable, oldValue, newValue) -> {
            context.config.setProperty("app.carSpeedRate",Double.parseDouble(newValue.toString()));

        });

        timeshiftLabel.setLabelFor(slideTimeShift);


        showIdealCar = new CheckBox();
        showIdealCar.setSelected(context.config.getBoolean("app.showIdealCar"));
        showIdealCar.setOnAction(e -> context.config.setProperty("app.showIdealCar",showIdealCar.isSelected()));

        waitAfterRecognization = new CheckBox();
        waitAfterRecognization.setSelected(context.config.getBoolean("app.waitAfterRecognization"));
        waitAfterRecognization.setOnAction(e -> context.config.setProperty("app.waitAfterRecognization",waitAfterRecognization.isSelected()));


        this.getChildren().add(new MenuLabelComponent("Pause app",pauseRun));
        this.getChildren().add(new MenuLabelComponent("Reset car",resetButton));
        this.getChildren().add(new MenuLabelComponent("Show ideal car",showIdealCar));
        this.getChildren().add(new MenuLabelComponent("Wait After Recognizayation",waitAfterRecognization));

        this.getChildren().addAll(timeshiftLabel,slideTimeShift);

    }


    @Override
    public void registryToContext() {
        context.setAppPanelController(this);
    }
}
