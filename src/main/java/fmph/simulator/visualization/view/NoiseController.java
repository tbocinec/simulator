package fmph.simulator.visualization.view;

import fmph.simulator.app.context.interfaces.Contextable;
import fmph.simulator.visualization.console.Message;
import fmph.simulator.visualization.console.MessageType;
import fmph.simulator.visualization.view.uxcomponent.MenuLabelComponent;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class NoiseController extends VBox implements Contextable {

    Slider changeDirecion;
    Slider radndomBreak;
    Slider changePWM;
    Slider ommitingRec;
    Slider difRecognization;
    Slider difAttribute;


    public NoiseController(){
        registryToContext();
        init();
    }

    private void init(){
        changeDirecion = buildDefaultSlider("Random change directio");
        this.getChildren().add(new MenuLabelComponent("Random change direction", changeDirecion));

        radndomBreak = buildDefaultSlider("Random break");
        this.getChildren().add(new MenuLabelComponent("Random break",radndomBreak));

        changePWM = buildDefaultSlider("Random change PWM");
        this.getChildren().add(new MenuLabelComponent("Random change PWM", changePWM));

        ommitingRec = buildDefaultSlider("Random omitting recognization");
        this.getChildren().add(new MenuLabelComponent("Random omitting recognization",ommitingRec));

        difRecognization = buildDefaultSlider("Random send different recognization");
        this.getChildren().add(new MenuLabelComponent("Random send different recognization", difRecognization));

        difAttribute = buildDefaultSlider("Random send different  attribute");
        this.getChildren().add(new MenuLabelComponent("Random send different  attribute",difAttribute));
    }



    @Override
    public void registryToContext() {
        context.setNoiseController(this);
    }

    private Slider buildDefaultSlider(String  name){
        Slider exampleSlider = new Slider();
        exampleSlider.setMin(0);
        exampleSlider.setMax(100);
        exampleSlider.setValue(0);
        exampleSlider.setShowTickLabels(true);
        exampleSlider.setShowTickMarks(true);
        exampleSlider.setMajorTickUnit(10);
        exampleSlider.setBlockIncrement(10);
        exampleSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            new Message("Noise control for "+name + " to new value "+ newValue, MessageType.INFO);
        });
        return  exampleSlider;
    }

    public static boolean ifProbably(double n){
       return  ((Math.random() *100)+1) < n;
    }

    public double getChangeDirecion() {
        return changeDirecion.valueProperty().get();
    }

    public double getRadndomBreak() {
        return radndomBreak.valueProperty().get();
    }

    public double getChangePWM() {
        return changePWM.valueProperty().get();
    }

    public double getOmmitingRec() {
        return ommitingRec.valueProperty().get();
    }

    public double getDifRecognization() {
        return difRecognization.valueProperty().get();
    }

    public double getDifAttribute() {
        return difAttribute.valueProperty().get();
    }
}
