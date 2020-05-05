package fmph.simulator.vizualization.view;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Contextable;
import fmph.simulator.vizualization.view.uxcomponent.MenuLabelComponent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class NoiseController extends VBox implements Contextable {



    public NoiseController(){
        registryToContext();
        init();
    }

    private void init(){
       this.getChildren().add(new Text("Not implemented"));



        this.getChildren().add(new MenuLabelComponent("Random change direction",getExampleSlider()));
        this.getChildren().add(new MenuLabelComponent("Random omitting recognization",getExampleSlider()));
        this.getChildren().add(new MenuLabelComponent("Random send different recognization",getExampleSlider()));
        this.getChildren().add(new MenuLabelComponent("Random send different  attribute",getExampleSlider()));
    }



    @Override
    public void registryToContext() {
        context.setNoiseController(this);
    }

    private Slider getExampleSlider(){
        Slider exampleSlider = new Slider();
        exampleSlider.setMin(0);
        exampleSlider.setMax(100);
        exampleSlider.setValue(0);
        exampleSlider.setShowTickLabels(true);
        exampleSlider.setShowTickMarks(true);
        exampleSlider.setMajorTickUnit(10);
        exampleSlider.setBlockIncrement(10);
        exampleSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("test slider "+newValue);

        });
        return  exampleSlider;
    }
}
