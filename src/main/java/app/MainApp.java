package app;

import app.context.ContextBuilder;
import app.context.interfaces.Context;
import fmph.simulator.vizualization.console.Message;
import fmph.simulator.vizualization.console.MessageType;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp  extends Application implements Runnable {


    public static void main(String[] args) {
        try {
            launch(args);
        }catch (RuntimeException e){
            //todo log to App and Logger
            new Message(e.getMessage(), MessageType.ERROR);
        }

    }


    public void start(Stage primaryStage) throws Exception {
        ContextBuilder.getContext().setPrimaryStage(primaryStage);
        new Thread(this).start();
    }

    public void run() {
        Context context = ContextBuilder.getContext();
        context.getServer().start();
        context.getVisualize();
        context.getVisualize().run();
    }

}
