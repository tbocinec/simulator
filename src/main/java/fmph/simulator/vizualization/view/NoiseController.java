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

    }


    @Override
    public void registryToContext() {
        context.setNoiseController(this);
    }
}
