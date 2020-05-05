package fmph.simulator.app;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Context;
import fmph.simulator.vizualization.console.Message;
import fmph.simulator.vizualization.console.MessageType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp  extends Application implements Runnable {


    public static void main(String[] args) {
        try {
            launch(args);
        }catch (Exception e){
            e.printStackTrace();
            new Message(e.getMessage(), MessageType.ERROR);
        }

    }



    public void start(Stage primaryStage) throws Exception {
        ContextBuilder.getContext().setPrimaryStage(primaryStage);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                close(0);
            }
        });
        new Thread(this).start();
    }

    public void run() {
        Context context = ContextBuilder.getContext();
        context.getServer().start();
        context.getVisualize();
        context.getVisualize().run();
    }

    public static  void close(int statusCode){
        Platform.exit();
        System.exit(statusCode);
    }

    public static void reset() {
        Context context = ContextBuilder.getContext();
        context.getRecognitionHistoryController().removeAll();
        context.getRunHistoryController().removeAll();
        context.getRunManagement().reset();
        Message.removeAll();
        new Message("New start",MessageType.INFO);
    }


}
