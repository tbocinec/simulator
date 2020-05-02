package fmph.simulator.vizualization.view;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Contextable;
import fmph.simulator.vizualization.view.uxcomponent.MenuLabelComponent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TimeController extends VBox implements Contextable {

    Slider timeSlider;
    ChangeListener<Number> timeListener;


    public TimeController(){
        this.setMinWidth(500);
        registryToContext();
        init();
    }

    private void init(){
        timeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                context.getRunManagement().changeToTime(newValue.doubleValue());
            }
        };
        timeSlider = new Slider();
        timeSlider.setMin(0);
        timeSlider.setMax(0);
        timeSlider.setValue(0);
        timeSlider.setShowTickLabels(true);
        timeSlider.setSnapToTicks(true);

        //we need only onclicket property
        timeSlider.valueProperty().addListener(timeListener);
        this.getChildren().addAll(timeSlider);
    }

    private void setSliderValue(double val){
        timeSlider.valueProperty().removeListener(timeListener);
        timeSlider.setValue(val);
        timeSlider.valueProperty().addListener(timeListener);
    }

    public void setMaxTime(double second,boolean actual){
        if(second > 50000 || second < 1){
            return;
        }

        timeSlider.setMax(second);
        timeSlider.setMajorTickUnit(second/10);
        timeSlider.setBlockIncrement(second/50);
        if(actual){
            setActualTIme(second);
        }
    }
    public void setActualTIme(double second){
        setSliderValue(second);

    }


    @Override
    public void registryToContext() {
        context.setTimeController(this);
    }
}
