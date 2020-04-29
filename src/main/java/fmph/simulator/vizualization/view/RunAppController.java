package fmph.simulator.vizualization.view;

import fmph.simulator.Running.RunState;
import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Contextable;
import fmph.simulator.models.CarModel;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RunAppController extends HBox implements Contextable {

    Button run;
    Button stop;
    Button pause;
    Button reset;

    public RunAppController(){
        registryToContext();
        init();
    }

    public void init(){
        run = new Button("Run");
        run.setOnMouseClicked(e -> context.getRunManagement().getActualRun().setRunState(RunState.run));
        stop = new Button("Finish");
        stop.setOnMouseClicked(e -> context.getRunManagement().finish());
        pause = new Button("Pause");
        pause.setOnMouseClicked(e -> context.getRunManagement().getActualRun().setRunState(RunState.stop));
        reset = new Button("Reset");
        reset.setOnMouseClicked(e -> context.getRunManagement().reset());

        this.getChildren().addAll(run,stop,pause,reset);
    }


    @Override
    public void registryToContext() {
        context.setRunAppController(this);
    }
}
