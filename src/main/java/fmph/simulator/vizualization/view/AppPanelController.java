package fmph.simulator.vizualization.view;

import app.context.ContextBuilder;
import app.context.interfaces.Contextable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class AppPanelController extends VBox implements Contextable {
    Button pauseRun;
    Button resetButton;
    Slider slideTimeShift;

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
            ContextBuilder.getContext().getCarModel().initStartValue();
        });

        Label timeshiftLabel  = new Label("TimeShift rate");
        slideTimeShift = new Slider();
        slideTimeShift.setMin(0.2);
        slideTimeShift.setMax(10);
        slideTimeShift.setValue(1);
        slideTimeShift.setShowTickLabels(true);
        slideTimeShift.setShowTickMarks(true);
        slideTimeShift.setMajorTickUnit(0.2);
        slideTimeShift.setBlockIncrement(0.2);
        timeshiftLabel.setLabelFor(slideTimeShift);

        this.getChildren().addAll(pauseRun,resetButton,timeshiftLabel,slideTimeShift);

    }


    @Override
    public void registryToContext() {
        context.setAppPanelController(this);
    }
}
