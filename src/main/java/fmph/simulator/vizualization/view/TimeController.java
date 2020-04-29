package fmph.simulator.vizualization.view;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Contextable;
import fmph.simulator.vizualization.view.uxcomponent.MenuLabelComponent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TimeController extends VBox implements Contextable {

    Slider timeSlider;


    public TimeController(){
        this.setMinWidth(500);
        registryToContext();
        init();
    }

    private void init(){
        timeSlider = new Slider();
        timeSlider.setMin(0);
        timeSlider.setMax(0);
        timeSlider.setValue(0);
        timeSlider.setShowTickLabels(true);

        timeSlider.setSnapToTicks(true);




        this.getChildren().addAll(timeSlider);


    }

    public void setMaxTime(double milisec,boolean actual){
        if(milisec > 50000 || milisec < 1){
            return;
        }
        double second = milisec /1000;
        timeSlider.setMax(second);
        timeSlider.setMajorTickUnit(second/10);
        timeSlider.setBlockIncrement(second/30);
        if(actual){
            setActualTIme(milisec);
        }
    }
    public void setActualTIme(double milisec){
        double second = milisec /1000;

        timeSlider.setValue(second);

    }


    @Override
    public void registryToContext() {
        context.setTimeController(this);
    }
}
