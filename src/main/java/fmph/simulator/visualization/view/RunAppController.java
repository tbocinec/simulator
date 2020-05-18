package fmph.simulator.visualization.view;

import fmph.simulator.app.context.interfaces.Contextable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

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
        run.setOnMouseClicked(e -> context.getRunManagement().run());
        stop = new Button("Finish");
        stop.setOnMouseClicked(e -> context.getRunManagement().finish());
        pause = new Button("Pause");
        pause.setOnMouseClicked(e -> context.getRunManagement().pause());
        reset = new Button("Reset");
        reset.setOnMouseClicked(e -> context.getRunManagement().reset());

        this.getChildren().addAll(run,stop,pause,reset);
    }


    @Override
    public void registryToContext() {
        context.setRunAppController(this);
    }
}
