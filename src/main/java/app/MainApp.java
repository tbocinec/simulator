package app;

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
        Context context = Context.getContext();
        context.setPrimaryStage(primaryStage);
        new Thread(this).start();
    }

    public void run() {
        Context context = Context.getContext();
        context.getServer().start();
        context.getVisualize();
        context.getVisualize().run();
    }

}
